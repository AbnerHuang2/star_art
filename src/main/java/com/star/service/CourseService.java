package com.star.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.mapper.CourseDao;
import com.star.model.HostHolder;
import com.star.model.entity.*;
import com.star.model.vo.HomeCourseResult;
import com.star.utils.StarUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/14
 */

public interface CourseService {

    /**
     * 获取首页相关课程。
     * 都是智能推荐。
     * 思路：
     *      先获取用户的登录状态，获取用户的感兴趣的专业方向，根据专业方向推荐相关热门课程。
     * @return
     */
    public HomeCourseResult getHomeCourseResult(int page, int pageSize);

    public HomeCourseResult setDefaultHomeCourseResult(int page, int pageSize);

    public PageInfo<Course> getAllCourseByPage(String name,int page,int pageSize);

    public List<Course> getCourseBySort(String sort);

    public PageInfo<Course> getCourses(Long majorId, Long directId, String tag,String name, int page, int pageSize);

    /**
     * 利用协同过滤算法推荐课程
     * @param uid
     * @param size
     * @param type 推荐类型，基于用户(1)还是基于物品(2)
     * @return
     */
    public List<Course> getRecommandCourses(Long uid,int size,int type);
    /**
     * 通过专业Id获取相关课程
     * @param majorId
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getCourseByMajorAndPage(Long majorId,int page, int pageSize);
    /**
     * 通过专业id和专业方向id列表获取相关课程(针对登录用户进行推荐用的)
     * @param majorId
     * @param directs
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getCourseByMajorAndDirectListAndPage(Long majorId,List<Integer> directs,int page, int pageSize);
    /**
     * 分页查询某种类型的热门课程
     *
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getHotCourseByTypeAndPage(int type, int page, int pageSize);
    public List<Course> getCourseByDirectAndPage(Long directId,int page, int pageSize);
    public List<Course> getCourseByDirectListAndPage(List<Integer> directs,int page, int pageSize);
    /**
     * 分页获取最新的课程
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getLatelyCourseByPage(int page, int pageSize);
    /**
     * 根据teacherId查询相关课程(发布时间排序)
     * @param teacherId
     * @param page
     * @param pageSize
     * @return
     */
    public List<Course> getCourseByTeacherAndPage(Long teacherId,int page, int pageSize);
    public Course getHotestCourse();
    public Course getCourseById(Long id);
    public List<Course> getCourseByName(String name);
    public List<Course> getCourseByTag(String tag);
    public int addCourse(Course course);
    public Course updateCourseComment(Long courseId,int commentNum);


}
