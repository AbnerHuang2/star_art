package com.star.controller;

import com.github.pagehelper.PageInfo;
import com.star.model.HostHolder;
import com.star.model.api.CommonResult;
import com.star.model.entity.SelectCourse;
import com.star.model.entity.User;
import com.star.model.vo.SelectCourseVo;
import com.star.service.SelectCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Abner
 * @CreateDate 2020/5/16
 */
@RestController
@RequestMapping("/selectCourse")
public class SelectCourseController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    SelectCourseService selectCourseService;

    @RequestMapping("/getSelectCourses")
    public CommonResult<PageInfo<SelectCourseVo>> getSelectCourses(Long userId , @RequestParam(defaultValue = "1")Integer page,
                                                                   @RequestParam(defaultValue = "8")Integer pageSize){
        PageInfo<SelectCourseVo> pageInfo = selectCourseService.getSelectCourseByUserId(userId,page,pageSize);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"获取选课成功");
        }
        return CommonResult.failed("获取选课失败");
    }
    @RequestMapping("/hasLearn")
    public CommonResult<Boolean> hasLearn(Long courseId){
        User user = hostHolder.getUser();
        if(user==null){
            return CommonResult.failed("请先登录");
        }
        boolean res = selectCourseService.hasLearn(user.getId(),courseId);
        if(res){
            return CommonResult.success(res,"已选该课程");
        }
        return CommonResult.failed("未选该课程");
    }
}
