package com.star.service;

import com.star.constant.EntityType;
import com.star.constant.RedisConstant;
import com.star.mapper.FollowDao;
import com.star.model.entity.User;
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
 * @createDate 2020/5/17
 */

public interface FollowService {

    public boolean follow(Long userId,int entityType, Long entityId);
    public List<User> getFans(int entityType, Long entityId);
    public List<User> getFollowPeople(Long entityId);
    public boolean unFollow(Long userId,int entityType, Long entityId);
    public boolean getFollowStatus(Long userId,int entityType, Long entityId);
}
