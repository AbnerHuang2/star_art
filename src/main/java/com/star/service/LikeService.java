package com.star.service;

import com.star.constant.RedisConstant;
import com.star.model.HostHolder;
import com.star.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author Abner
 * @CreateDate 2020/5/1
 */

public interface LikeService {

    public long like(Long userId,int entityType, Long entityId);
    public boolean unlike(Long userId,int entityType, Long entityId);
    public int getlikeStatus(Long userId,int entityType, Long entityId);
}
