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

    //关注业务
    public static String BIZ_FOLLOW = "STAR_FOLLOW";

    //粉丝业务
    public static String BIZ_FANS = "STAR_FANS";

    public static String getCheckcodeKey(String email){
        return BIZ_CHECKCODE+SPLIT+email;
    }

    public  static String getLikeKey(int entityType,Long entityId){
        return BIZ_LIKE+SPLIT+entityType+SPLIT+entityId;
    }

    public  static String getFollowKey(int entityType,Long entityId){
        return BIZ_FOLLOW+SPLIT+entityType+SPLIT+entityId;
    }

    public  static String getFANSKey(int entityType,Long entityId){
        return BIZ_FANS+SPLIT+entityType+SPLIT+entityId;
    }

}
