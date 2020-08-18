package com.star.service.impl;

import com.star.constant.EntityType;
import com.star.constant.RedisConstant;
import com.star.mapper.FollowDao;
import com.star.model.entity.User;
import com.star.service.FollowService;
import com.star.service.UserService;
import com.star.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Abner
 * @createDate 2020/6/26
 */
@Service
@Log4j2
public class FollowServiceImpl implements FollowService {
    @Resource
    FollowDao followDao;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    /**
     * 关注业务
     * 成功 true
     * 失败 false
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean follow(Long userId,int entityType, Long entityId){
        String otherKey = RedisConstant.getFANSKey(entityType,entityId);
        String meKey = RedisConstant.getFollowKey(EntityType.Entity_User.getType(),userId);
        //给对方添加一个粉丝
        long res = redisUtil.sSet(otherKey,String.valueOf(userId));
        //给自己的关注列表添加一个关注
        long res2 = redisUtil.sSet(meKey,String.valueOf(entityType+":"+entityId));
        if(res==1 && res2==1){
            return true;
        }else if(res==1){
            //UNDO添加粉丝操作
            redisUtil.setRemove(otherKey,String.valueOf(userId));
        }else if(res2==1){
            //UNDO添加关注操作
            redisUtil.setRemove(meKey,String.valueOf(entityType+":"+entityId));
        }
        return false;
    }

    /**
     * 查看粉丝数
     * @param entityType
     * @param entityId
     * @return
     */
    public List<User> getFans(int entityType, Long entityId){
        String key = RedisConstant.getFANSKey(entityType,entityId);
        Set<Object> set =  redisUtil.sGet(key);
        Set<Long> resSet = new HashSet<>();
        for(Object item : set){
            try{
                Long t = Long.valueOf(item.toString());
                resSet.add(t);
            }catch (Exception e){
                log.error("获取关注时格式转换错误");
            }
        }
        List<User> fansList = userService.getUserByCollection(resSet);
        return fansList;
    }

    /**
     * 查看某个人关注列表
     * @param entityId
     * @return
     */
    public List<User> getFollowPeople(Long entityId){
        String key = RedisConstant.getFollowKey(EntityType.Entity_User.getType(),entityId);
        Set<Object> set =  redisUtil.sGet(key);
        Set<Long> resSet = new HashSet<>();
        for(Object item : set){
            String[] strs = item.toString().split(":");
            if(strs.length==2){
                try{
                    Long t = Long.valueOf(strs[1]);
                    if("3".equals(strs[0].trim())){
                        resSet.add(t);
                    }
                }catch (Exception e){
                    log.error("获取关注时格式转换错误");
                }
            }
        }
        List<User> followList = userService.getUserByCollection(resSet);
        return followList;
    }

    /**
     * 取消关注
     * 成功 true
     * 失败 false
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean unFollow(Long userId,int entityType, Long entityId){
        String otherKey = RedisConstant.getFANSKey(entityType,entityId);
        String meKey = RedisConstant.getFollowKey(EntityType.Entity_User.getType(),userId);
        long res = redisUtil.setRemove(otherKey,String.valueOf(userId));
        long res1 = redisUtil.setRemove(meKey,String.valueOf(entityType+":"+entityId));
        System.out.println(res+" "+res1);
        if(res==1 && res1==1){
            return true;
        }
        return false;
    }

    /**
     * 查看用户是否关注了某个用户，或者课程等
     * 思路：
     *  从该用户的关注列表中，查看是否含有该entityType：entityId
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean getFollowStatus(Long userId,int entityType, Long entityId){
        String key = RedisConstant.getFollowKey(EntityType.Entity_User.getType(),userId);
        boolean res = redisUtil.sHasKey(key,String.valueOf(entityType+":"+entityId));
        return res;
    }
}
