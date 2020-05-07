package com.star.controller;

import com.star.model.api.CommonResult;
import com.star.model.entity.Major;
import com.star.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/19
 */
@RestController
@RequestMapping("/major")
public class MajorController {
    @Autowired
    MajorService majorService;

    @RequestMapping("/getAllMajor")
    public CommonResult<List<Major>> getAllMajor(){
        List<Major> list = majorService.getAllMajor();
        if(list!=null && list.size()>0){
            return CommonResult.success(list,"获取专业成功");
        }
        return CommonResult.failed("获取相关专业失败");
    }

}
