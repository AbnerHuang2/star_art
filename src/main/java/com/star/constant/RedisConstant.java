package com.star.constant;

/**
 * 在这里定义一些redis业务的key
 */
public class RedisConstant {
    //定义一些业务
    public final static String SPLIT = "-";
    //注册时候的验证码set的key
    public static String BIZ_CHECKCODE = "STAR_CHECKCODE";

    //登录token业务
    public static String BIZ_TOKEN = "STAR_TOKEN";
    public static long TOKEN_ExpiredTime = 3600*24*7;

    //Like业务
    public static String BIZ_LIKE = "STAR_LIKE";

    public static String getCheckcodeKey(String email){
        return BIZ_CHECKCODE+SPLIT+email;
    }

    public static String getTokenKey(Long userId){
        return BIZ_TOKEN+SPLIT+userId;
    }

    public  static String getLikeKey(int entityType,Long entityId){
        return BIZ_LIKE+SPLIT+entityType+SPLIT+entityId;
    }

}
