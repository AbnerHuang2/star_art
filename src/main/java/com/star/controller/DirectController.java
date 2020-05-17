package com.star.controller;

import com.star.model.api.CommonResult;
import com.star.model.entity.Direction;
import com.star.service.DirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/19
 */
@RestController
@RequestMapping("/direct")
public class DirectController {
    @Autowired
    DirectService directService;

    @RequestMapping("/getDirects")
    public CommonResult<List<Direction>> getDirects(Long majorId){
        List<Direction> list =  directService.getDirectByMajorId(majorId);
        if(list!=null && list.size()>0){
            return CommonResult.success(list,"获取相关专业方向成功");
        }
        return CommonResult.failed("获取相关专业方向失败");
    }
    @RequestMapping("/getUserDirects")
    public CommonResult<List<Direction>> getUserDirects(String userDirects){
        List<Direction> list =  directService.getUserDirects(userDirects);
        if(list!=null && list.size()>0){
            return CommonResult.success(list,"获取用户专业方向成功");
        }
        return CommonResult.failed("获取用户专业方向失败");
    }
    @RequestMapping("/getRestDirects")
    public CommonResult<List<Direction>> getRestDirects(String userDirects){
        System.out.println(userDirects);
        List<Direction> list =  directService.getRestDirects(userDirects);
        if(list!=null && list.size()>0){
            return CommonResult.success(list,"获取用户专业方向成功");
        }
        return CommonResult.failed("获取用户专业方向失败");
    }
}
