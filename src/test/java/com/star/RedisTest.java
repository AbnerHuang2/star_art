package com.star;

import com.star.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/5/1
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    RedisUtil redisUtil;

    @Test
    void TestString(){
        String key = "test1";
        redisUtil.set(key,"我是test1的值",10);
    }

    @Test
    void TestList(){
        String key = "list_test";
//        ArrayList<String> list = new ArrayList<>();
//        list.add("aa");
        //redisUtil.lSet(key,"cc");

        List list = redisUtil.lGet(key,0,10);
        System.out.println(list.get(0));
    }

    @Test
    void testSet(){
        String key = "set_test";
        long res = redisUtil.sSet(key, "aa");
        System.out.println(redisUtil.sGetSetSize(key));
    }

    @Test
    void TestHash(){
        String key = "hash_test";
//        ArrayList<String> list = new ArrayList<>();
//        list.add("aa");

        redisUtil.hset(key,"aa","aa");
        System.out.println(redisUtil.hget(key,"aa"));

    }

}
