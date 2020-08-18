package com.star.service;

import com.star.constant.RedisConstant;
import com.star.mapper.UserDao;
import com.star.model.entity.User;
import com.star.utils.RedisUtil;
import com.star.utils.StarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author Abner
 * @CreateDate 2020/4/12
 */

public interface TokenService {

    public String generateToken(String data);
    /**
     * 将token，data保存到redis
     * @param token
     * @param data
     * @return
     */
    public boolean save(String token,String data);
    public void delToken(String token);
    public User validToken(String token);

}
