package com.star.controller;

import com.github.pagehelper.PageInfo;
import com.star.model.api.CommonResult;
import com.star.model.entity.User;
import com.star.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/29
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;


    @RequestMapping("/getRecommandTeachers")
    public CommonResult<List<User>> getRecommandTeachers(int size){
        List<User> list = teacherService.getRecommandTeachers(1,size);
        if(list!=null && list.size()>0){
            return CommonResult.success(list,"获取推荐老师成功");
        }
        return CommonResult.failed("获取推荐老师失败");
    }

    @RequestMapping("/getTeachers")
    public CommonResult<PageInfo<User>> getTeachers(int page, int pageSize){
        PageInfo<User> pageInfo = teacherService.getTeachers(page,pageSize);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"分页获取老师成功");
        }
        return CommonResult.failed("分页获取老师失败");
    }

    @RequestMapping("/getOneTeacher")
    public CommonResult<User> getOneTeacher(Long id){
        User user = teacherService.getTeacherById(id);
        if(user!=null){
            return CommonResult.success(user,"获取老师成功");
        }
        return CommonResult.failed("获取老师失败");
    }

}
