package com.star.controller;

import com.star.model.HostHolder;
import com.star.model.api.CommonResult;
import com.star.model.entity.User;
import com.star.service.FollowService;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author Abner
 * @createDate 2020/5/18
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @RequestMapping("/follow")
    public CommonResult<Boolean> follow(Integer entityType, Long entityId){
        User user = hostHolder.getUser();
        if(user==null){
            return CommonResult.failed("请先登录");
        }
        boolean res = followService.follow(user.getId(),entityType,entityId);
        if(res){
            return CommonResult.success(res,"关注成功");
        }
        return CommonResult.failed("关注失败");
    }

    @RequestMapping("/unFollow")
    public CommonResult<Boolean> unFollow(Integer entityType, Long entityId){
        User user = hostHolder.getUser();
        if(user==null){
            return CommonResult.failed("请先登录");
        }
        boolean res = followService.unFollow(user.getId(),entityType,entityId);
        if(res){
            return CommonResult.success(res,"取消关注成功");
        }
        return CommonResult.failed("取消关注失败");
    }

    @RequestMapping("/getFollowStatus")
    public CommonResult<Boolean> getFollowStatus(Integer entityType, Long entityId){
        User user = hostHolder.getUser();
        if(user==null){
            return CommonResult.failed("请先登录");
        }
        boolean res = followService.getFollowStatus(user.getId(),entityType,entityId);

        return CommonResult.success(res,res?"已关注":"未关注");
    }

    @RequestMapping("/getFollowPeople")
    public CommonResult<List<User>> getFollowPeople(Long userId){

        List<User> res = followService.getFollowPeople(userId);
        if(res!=null){
            return CommonResult.success(res,"获取关注列表成功");
        }
        return CommonResult.failed("获取关注列表失败");
    }

    @RequestMapping("/getFans")
    public CommonResult<List<User>> getFans(Integer entityType, Long entityId){
        System.out.println(entityType+" "+entityId);
        List<User> res = followService.getFans(entityType,entityId);
        if(res!=null){
            return CommonResult.success(res,"获取粉丝列表成功");
        }
        return CommonResult.failed("获取粉丝列表失败");
    }

}
