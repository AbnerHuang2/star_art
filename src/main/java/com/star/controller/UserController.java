package com.star.controller;

import com.star.model.api.CommonResult;
import com.star.model.entity.User;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/29
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/getHotUsers")
    public CommonResult<List<User>> getHotUsers(){
        List<User> list = userService.getHotUsers();
        if(list!=null && list.size()>0){
            return CommonResult.success(list,"获取人气选手成功");
        }
        return CommonResult.failed("获取人气选手失败");
    }

    @RequestMapping("/getUser")
    public CommonResult<User> getUser(Long id){
        User user = userService.getUserById(id);
        if(user!=null){
            return CommonResult.success(user,"获取用户成功");
        }
        return CommonResult.failed("获取用户失败");
    }

}
