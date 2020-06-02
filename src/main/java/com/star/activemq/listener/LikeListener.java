package com.star.activemq.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.star.constant.EntityType;
import com.star.model.entity.Message;
import com.star.model.entity.News;
import com.star.model.entity.User;
import com.star.service.MessageService;
import com.star.service.NewsService;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author Abner
 * @createDate 2020/5/22
 */
@Component  //加上这个才会启动这个线程
public class LikeListener {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @JmsListener(destination = "likeQueue")
    public void readMessage(String msg) {
        JSONObject jsonObject = JSON.parseObject(msg);
        Long userId = jsonObject.getLong("userId");
        Integer entityType = jsonObject.getInteger("entityType");
        Long entityId = jsonObject.getLong("entityId");
        Long toId = 0l;


        Long fromId = 1l; //用官方人员账号给用户发消息
        Message message = new Message();
        message.setFromId(fromId);

        if(entityType == EntityType.Entity_News.getType()){
            News news = newsService.getNewsById(entityId);
            toId = news.getBelongId();

            message.setToId(toId);
            User from = userService.getUserById(userId);
            message.setMsgContent(from.getUserNickName()+" 点赞了你发布的的说说 "+news.getNewsTitle());
        }


        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        message.setMsgCreatetime(sdf.format(date));
        message.setIsRead(1);
        message.setConversationId(fromId < toId ? String.format("%d_%d", fromId,toId) :
                String.format("%d_%d", toId,fromId));

        messageService.addMessage(message);

    }
}
