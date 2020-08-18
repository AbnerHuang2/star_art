package com.star.service.impl;

import com.star.constant.RedisConstant;
import com.star.service.LikeService;
import com.star.service.NewsService;
import com.star.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Abner
 * @createDate 2020/6/26
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    NewsService newsService;

    /**
     * 点赞业务 ,返回点赞数
     * @param userId
     * @param entityType
     * @param entityId
     */
    public long like(Long userId,int entityType, Long entityId){
        String key = RedisConstant.getLikeKey(entityType,entityId);
        long res = redisUtil.sSet(key,String.valueOf(userId));
        if(res==1){
            return redisUtil.sGetSetSize(key);
        }
        return 0;
    }

    public boolean unlike(Long userId,int entityType, Long entityId){
        String key = RedisConstant.getLikeKey(entityType,entityId);
        long res = redisUtil.setRemove(key,String.valueOf(userId));
        if(res==1){
            return true;
        }
        return false;
    }

    /**
     * 判断用户对某个entity是否喜欢
     * true为喜欢   false为无表示
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getlikeStatus(Long userId,int entityType, Long entityId){
        String key = RedisConstant.getLikeKey(entityType,entityId);
        boolean res = redisUtil.sHasKey(key,String.valueOf(userId));
        return res ? 1 : 0;
    }
}
