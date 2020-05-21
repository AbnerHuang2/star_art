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
@Service
public class TokenService {

    @Autowired
    RedisUtil redisUtil;

    @Resource
    UserDao userDao;

    //生成token(格式为 STAR_TOKEN:加密的data-时间-六位随机数)(目前data是用户id)
    public String generateToken(String data){
        StringBuilder token = new StringBuilder();
        //加token:
        token.append(RedisConstant.BIZ_TOKEN+RedisConstant.SPLIT);

        //加加密的data
        token.append(StarUtil.MD5(data));
        //加时间
        //token.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "-");
        //加六位随机数111111-999999
        //token.append(new Random().nextInt((999999 - 111111 + 1)) + 111111);
        //System.out.println("token=>" + token.toString());
        //将token保存到redis中

        return token.toString();
    }

    /**
     * 将token，data保存到redis
     * @param token
     * @param data
     * @return
     */
    public boolean save(String token,String data){
        return redisUtil.set(token,data,RedisConstant.TOKEN_ExpiredTime);
    }

    public void delToken(String token){
        redisUtil.del(token);
    }

    public User validToken(String token){
        String userId = (String) redisUtil.get(token);
        if(userId==null){
            return null;
        }
        User u = new User();
        u.setId(Long.valueOf(userId));
        return userDao.selectOne(u);
    }

}
