package com.star.controller;

import com.github.pagehelper.PageInfo;
import com.star.model.api.CommonResult;
import com.star.model.entity.Course;
import com.star.model.vo.HomeCourseResult;
import com.star.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Abner
 * @CreateDate 2020/4/15
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @RequestMapping("/homeCourses")
    public CommonResult<HomeCourseResult> homeCourses(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "4")int pageSize){
        HomeCourseResult homeCourseResult = courseService.getHomeCourseResult(page,pageSize);
        return CommonResult.success(homeCourseResult,"获取首页相关课程成功");
    }

    @RequestMapping("/getCourses")
    public CommonResult<PageInfo<Course>> getCourses(Long majorId, Long directId, String tag,
                                                     @RequestParam(defaultValue = "")String name,
                                                     @RequestParam(defaultValue = "1")int page,
                                                     @RequestParam(defaultValue = "8")int pageSize){
        if(StringUtils.isEmpty(name)){
            name = null;
        }
        PageInfo<Course> pageInfo = null;
        pageInfo = courseService.getCourses(majorId,directId,tag,name,page,pageSize);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"获取课程成功");
        }
        return CommonResult.failed("获取课程失败");
    }

    @RequestMapping("/getSingleCourse")
    public CommonResult<Course> getSingleCourse(Long courseId){
        Course course = courseService.getCourseById(courseId);
        if(course!=null){
            return CommonResult.success(course,"获取课程成功");
        }
        return CommonResult.failed("获取课程失败");
    }

    @RequestMapping("/getAllCourses")
    public CommonResult<PageInfo<Course>> getAllCourses(String name, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "8")int pageSize){
        PageInfo<Course> pageInfo = null;
        pageInfo = courseService.getAllCourseByPage(name,page,pageSize);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"获取课程成功");
        }
        return CommonResult.failed("获取课程失败");
    }

}
