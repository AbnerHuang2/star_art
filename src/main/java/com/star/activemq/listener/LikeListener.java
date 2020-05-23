package com.star.activemq.listener;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author Abner
 * @createDate 2020/5/22
 */
@Component  //加上这个才会启动这个线程
public class LikeListener {
    @JmsListener(destination = "likeQueue")
    public void readMessage(Map map) {
        System.out.println("接收到消息：" + map);
    }
}
