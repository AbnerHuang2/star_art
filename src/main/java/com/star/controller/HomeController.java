package com.star.controller;

import com.star.model.HostHolder;
import com.star.model.api.CommonResult;
import com.star.model.entity.User;
import com.star.model.vo.HomeStartVo;
import com.star.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    HomeService homeService;

    @RequestMapping("/")
    public CommonResult<User> index(HttpServletResponse response){
        User user = hostHolder.getUser();
        if(user!=null){
            System.out.println(user.getUserEmail());
            return  CommonResult.success(user);
        }
        return  CommonResult.failed("请先登录");
    }

    @RequestMapping("/getHomeStart")
    public CommonResult<List<HomeStartVo>> getHomeStart(){
        List<HomeStartVo> list = homeService.getHomeStart();
        if(list!=null){
            return  CommonResult.success(list,"获取HomeStart成功");
        }
        return  CommonResult.failed("获取HomeStart失败");
    }

}
