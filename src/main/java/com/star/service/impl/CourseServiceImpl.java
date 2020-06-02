package com.star.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.mapper.CourseDao;
import com.star.model.HostHolder;
import com.star.model.entity.Course;
import com.star.model.entity.Major;
import com.star.model.entity.User;
import com.star.model.vo.HomeCourseResult;
import com.star.service.CourseService;
import com.star.service.DirectService;
import com.star.service.MajorService;
import com.star.service.RecommandService;
import com.star.utils.StarUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/5/21
 */
@Service
@Log4j2
public class CourseServiceImpl implements CourseService {
    @Autowired
    HostHolder hostHolder;

    @Resource
    CourseDao courseDao;

    @Autowired
    MajorService majorService;

    @Autowired
    DirectService directService;

    @Autowired
    RecommandService recommandService;

    String Sort_hot = "(stu_num+follow_num*2+comment_num*3) desc";

    /**
     * 获取首页相关课程。
     * 智能推荐。
     * 思路：
     *      先获取用户的登录状态，获取用户的感兴趣的专业方向，根据专业方向推荐相关热门课程。
     * @return
     */
    @Override
    @Cacheable(value = "homeCourses")
    public HomeCourseResult getHomeCourseResult(int page, int pageSize){
        User user = hostHolder.getUser();
        if(user==null){
            log.info("未登录用户的首页");
            return setDefaultHomeCourseResult(page, pageSize);
        }
        String directs = user.getUserDirections();
        if(StringUtils.isEmpty(directs)){
            return setDefaultHomeCourseResult(page, pageSize);
        }
        log.info("登录用户并且已选专业方向的首页");
        HomeCourseResult homeCourseResult = new HomeCourseResult();
        //设置pubCourseList
        homeCourseResult.setPubCourseList(getCourseByTeacherAndPage(
                2l,page, pageSize));

        List<Course> artCourseList = new ArrayList<Course>();
        List<Course> musicCourseList = new ArrayList<Course>();

        List<Integer> directList = StarUtil.getDirects(directs);
        //添加美术专业推荐
        Major art = majorService.getMajorByName("美术专业");
        artCourseList = getCourseByMajorAndDirectListAndPage(art.getId(),directList,page, pageSize);
        if(artCourseList.size()<pageSize){
            artCourseList.addAll(getCourseByMajorAndPage(1L,1,pageSize-musicCourseList.size()));
        }
        homeCourseResult.setArtCourseList(artCourseList);
        //添加音乐专业推荐
        Major music = majorService.getMajorByName("音乐专业");
        musicCourseList = getCourseByMajorAndDirectListAndPage(music.getId(),directList,page, pageSize);
        if(musicCourseList.size()<pageSize){
            musicCourseList.addAll(getCourseByMajorAndPage(2L,1,pageSize-musicCourseList.size()));
        }
        homeCourseResult.setMusicCourseList(musicCourseList);

        return homeCourseResult;
    }

    public HomeCourseResult setDefaultHomeCourseResult(int page, int pageSize){
        HomeCourseResult homeCourseResult = new HomeCourseResult();
        homeCourseResult.setPubCourseList(getCourseByTeacherAndPage(
                2l,page, pageSize));
        homeCourseResult.setArtCourseList(getCourseByMajorAndPage(1l,page, pageSize));
        homeCourseResult.setMusicCourseList(getCourseByMajorAndPage(2l,page, pageSize));
        return homeCourseResult;
    }

    public PageInfo<Course> getAllCourseByPage(String name,int page,int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(Course.class);
        if(!StringUtils.isEmpty(name)){
            example.and().andLike("courseName","%"+name+"%");
        }
        List<Course> list = courseDao.selectByExample(example);
        PageInfo<Course> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<Course> getCourseBySort(String sort){
        Example example = new Example(Course.class);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }else{
            example.setOrderByClause(Sort_hot);
        }
        return courseDao.selectByExample(example);
    }

    /**
     * 课程选择
     * @param majorId
     * @param directId
     * @param tag
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Cacheable("courseResult")
    public PageInfo<Course> getCourses(Long majorId, Long directId, String tag, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(Course.class);
        Example.Criteria criteria = example.createCriteria();
        if(majorId!=null){
            criteria.andCondition("direct_id in (select id from major_direct where major_id="+majorId+")");
        }
        if(directId!=null){
            criteria.andEqualTo("directId",directId);
        }
        if(tag!=null){
            criteria.andLike("tags","%"+tag+"%");
        }

        example.setOrderByClause(Sort_hot);

        List<Course> list = courseDao.selectByExample(example);
        PageInfo<Course> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 利用协同过滤算法推荐课程
     * @param uid
     * @param size
     * @param type 推荐类型，基于用户(1)还是基于物品(2)
     * @return
     */
    public List<Course> getRecommandCourses(Long uid,int size,int type){
        List<Course> resList = new ArrayList<>();
        List<JSONObject> list = recommandService.getRecommendCourseByUid(uid,size,type);
        if(list.size()>0){
            for(JSONObject json : list){
                Course course = getCourseById(json.getLong("itemId"));
                resList.add(course);
            }
        }
        return resList;
    }

    /**
     * 通过专业Id获取相关课程
     * @param majorId
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getCourseByMajorAndPage(Long majorId,int page, int pageSize){

        PageHelper.startPage(page,pageSize);
        Example example = new Example(Course.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("direct_id in (select id from major_direct where major_id="+majorId+")");
        example.setOrderByClause(Sort_hot);
        return courseDao.selectByExample(example);
    }

    /**
     * 通过专业id和专业方向id列表获取相关课程(针对登录用户进行推荐用的)
     * @param majorId
     * @param directs
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getCourseByMajorAndDirectListAndPage(Long majorId,List<Integer> directs,int page, int pageSize){
        if(directs==null || directs.size()<0){
            return null;
        }
        PageHelper.startPage(page,pageSize);

        StringBuilder directStr = new StringBuilder();
        directStr.append("(");
        for (Integer i : directs){
            directStr.append(i+",");
        }
        directStr.deleteCharAt(directStr.length()-1);
        directStr.append(")");

        Example example = new Example(Course.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("direct_id in "+directStr.toString());
        criteria.andCondition("direct_id in (select id from major_direct where major_id="+majorId+")");
        example.setOrderByClause(Sort_hot);
        return courseDao.selectByExample(example);
    }

    /**
     * 分页查询某种类型的热门课程
     *
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getHotCourseByTypeAndPage(int type, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(Course.class);
        example.setOrderByClause(Sort_hot);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("courseType", type);
        return courseDao.selectByExample(example);
    }

    public List<Course> getCourseByDirectAndPage(Long directId,int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(Course.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("directId", directId);
        return courseDao.selectByExample(example);
    }

    public List<Course> getCourseByDirectListAndPage(List<Integer> directs,int page, int pageSize){
        if(directs==null || directs.size()<0){
            return null;
        }
        PageHelper.startPage(page,pageSize);

        StringBuilder directStr = new StringBuilder();
        directStr.append("(");
        for (Integer i : directs){
            directStr.append(i+",");
        }
        directStr.deleteCharAt(directStr.length()-1);
        directStr.append(")");

        Example example = new Example(Course.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("direct_id in "+directStr.toString());
        example.setOrderByClause(Sort_hot);
        return courseDao.selectByExample(example);

    }

    /**
     * 分页获取最新的课程
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getLatelyCourseByPage(int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Condition condition = new Condition(Course.class);
        condition.orderBy("pubTime").desc();    //这个是entity中对应的property，不是数据库中的

        return courseDao.selectByExample(condition);
    }

    /**
     * 根据teacherId查询相关课程(发布时间排序)
     * @param teacherId
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getCourseByTeacherAndPage(Long teacherId,int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Condition condition = new Condition(Course.class);
        Condition.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("teacherId", teacherId);
        condition.and(criteria);
        condition.orderBy("pubTime").desc();

        return courseDao.selectByExample(condition);
    }

    public Course getHotestCourse(){

        Example example = new Example(Course.class);
        example.setOrderByClause("(comment_num*3 + follow_num*2) desc");

        List<Course> list = courseDao.selectByExample(example);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Cacheable(value = "course",key = "#id")
    public Course getCourseById(Long id){
        return courseDao.selectByPrimaryKey(id);
    }

    public List<Course> getCourseByName(String name){
        Example example = new Example(Course.class);
        example.createCriteria().andLike("courseName","%"+name+"%");
        return courseDao.selectByExample(example);
    }

    public List<Course> getCourseByTag(String tag){
        Example example = new Example(Course.class);
        example.createCriteria().andLike("tags","%"+tag+"%");
        return courseDao.selectByExample(example);
    }

    public int addCourse(Course course){
        return courseDao.insert(course);
    }

    @CachePut(value = "course",key = "#courseId")
    public Course updateCourseComment(Long courseId,int commentNum){
        Course course = courseDao.selectByPrimaryKey(courseId);
        if(course!=null){
            course.setCommentNum(commentNum);
            int res = courseDao.updateByPrimaryKey(course);
            if(res == 1 ){
                return course;
            }
        }
        return null;
    }
}
