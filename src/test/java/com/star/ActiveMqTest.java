package com.star;

import com.alibaba.fastjson.JSONObject;
import com.star.activemq.provider.LikeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Abner
 * @createDate 2020/5/22
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActiveMqTest {

    @Autowired
    LikeProvider likeProvider;

    @Test
    void contextLoads(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actor",1);
        likeProvider.sendMsg(jsonObject.toJSONString());
    }

}
