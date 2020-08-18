package com.star.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.mapper.SelectCourseDao;
import com.star.model.entity.SelectCourse;
import com.star.model.vo.SelectCourseVo;
import com.star.service.CourseService;
import com.star.service.SelectCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/6/26
 */
@Service
public class SelectCourseServiceImpl implements SelectCourseService {
    @Resource
    SelectCourseDao selectCourseDao;

    @Autowired
    CourseService courseService;

    public PageInfo<SelectCourseVo> getSelectCourseByUserId(Long userId, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(SelectCourse.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<SelectCourse> list = selectCourseDao.selectByExample(example);
        PageInfo<SelectCourse> scPageInfo = new PageInfo<>(list);

        List<SelectCourseVo> vos = new ArrayList<>();
        for(SelectCourse sc : list){
            SelectCourseVo vo = new SelectCourseVo();
            vo.setSelectCourse(sc);
            vo.setCourse(courseService.getCourseById(sc.getCourseId()));

            vos.add(vo);
        }
        PageInfo<SelectCourseVo> pageInfo = new PageInfo<>(vos);
        pageInfo.setPages(scPageInfo.getPages());
        pageInfo.setPageNum(scPageInfo.getPageNum());
        pageInfo.setPageSize(scPageInfo.getPageSize());
        pageInfo.setTotal(scPageInfo.getTotal());

        return pageInfo;
    }

    public boolean hasLearn(Long userId,Long courseId){
        if(userId==null || courseId==null){
            return false;
        }
        Example example = new Example(SelectCourse.class);
        example.createCriteria()
                .andEqualTo("userId",userId)
                .andEqualTo("courseId",courseId);

        SelectCourse selectCourse = selectCourseDao.selectOneByExample(example);
        return selectCourse==null ? false : true;
    }
}
