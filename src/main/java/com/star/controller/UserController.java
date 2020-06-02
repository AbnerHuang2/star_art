package com.star.controller;

import com.github.pagehelper.PageInfo;
import com.star.model.api.CommonResult;
import com.star.model.entity.User;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public CommonResult<List<User>> getHotUsers(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "4")int pageSize){
        List<User> list = userService.getHotUsers(page,pageSize);
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

    @RequestMapping(value = "/updateUserInfo" , produces = "application/json;charset=UTF-8")
    public CommonResult<User> updateUserInfo(@RequestBody User user){
        if(user!=null){
            User res = userService.updateUserInfo(user);
            if(res!=null){
                return CommonResult.success(user,"更新用户信息成功");
            }
        }
        return CommonResult.failed("更新用户信息失败");
    }

    @RequestMapping("/getAllUsers")
    public CommonResult<PageInfo<User>> getAllUsers(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "8")int pageSize){
        PageInfo<User> pageInfo = userService.getAllUserByPage(page,pageSize);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"分页获取全部用户成功");
        }
        return CommonResult.failed("分页获取全部用户失败");
    }
    @RequestMapping("/getNormalUsers")
    public CommonResult<PageInfo<User>> getNormalUsers(@RequestParam(defaultValue = "") String name,
                                                       @RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "8")int pageSize){
        PageInfo<User> pageInfo = userService.getNormalUserByPage(name,page,pageSize);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"分页获取普通用户成功");
        }
        return CommonResult.failed("分页获取普通用户失败");
    }

}
