package com.star.activemq.provider;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Abner
 * @createDate 2020/5/22
 */
@Component
public class LikeProvider {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    private final String queue = "likeQueue";

    public void sendMsg(String msg){
        jmsMessagingTemplate.convertAndSend(queue,msg);
    }

}
