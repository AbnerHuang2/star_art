package com.star.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.star.model.entity.Message;
import com.star.service.MessageService;
import com.star.service.UserService;
import com.star.utils.EmojiFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Abner
 * @createDate 2020/5/23
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer {

    // 这里使用静态，让 service 属于类
    private static MessageService messageService;
    // 注入的时候，给类的 service 注入
    @Autowired
    public void setMessageService(MessageService messageService) {
        WebSocketServer.messageService = messageService;
    }

    // 这里使用静态，让 service 属于类
    private static UserService userService;
    // 注入的时候，给类的 service 注入
    @Autowired
    public void setUserService(UserService userService) {
        WebSocketServer.userService = userService;
    }

    private static volatile long onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<Long, WebSocketServer> webSocketSet = new ConcurrentHashMap<Long, WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session WebSocketsession;
    //当前发消息的人员编号
    private Long userno = 0l;

    /**
     * 建立连接调用的方法，成员加入
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session,  @PathParam("userId") Long userId) {
        userno = userId;//接收到发送消息的人员编号
        this.WebSocketsession = session;
        webSocketSet.put(userId, this);//加入map中
        addOnlineCount();     //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

        //发送会话名单（包含未读消息）给该用户
        //messageType 1代表上线 2代表下线 3代表会话名单  4代表普通消息
        Map<Long,String> map = messageService.getConversationMapByToId(userId);
        JSONArray resList = new JSONArray();
        for(Map.Entry<Long,String> entry : map.entrySet()){
            JSONObject json = new JSONObject();
            json.put("user",userService.getUserById(entry.getKey()));
            json.put("conversationId",entry.getValue());
            json.put("unReadCount",messageService.getUnReadCount(userId,entry.getValue()));
            resList.add(json);
        }
        JSONObject data = new JSONObject();
        data.put("resList",resList);
        data.put("messageType",3);

        try {
            webSocketSet.get(userId).send(data.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 收到消息调用的方法，群成员发送消息
     *
     * @param userId：用户id
     * @param message：发送消息
     */
    @OnMessage
    public void onMessage( @PathParam("userId") Long userId, String message) {
        try {
            log.info("来自客户端消息：" + message+"客户端的id是："+userId);
            JSONObject jsonObject = JSON.parseObject(message);
            Integer messageType = jsonObject.getInteger("messageType");
            //messageType 1代表上线 2代表会话的聊天记录 3代表会话名单  4代表普通消息
            if(messageType == 2 ){  //获取会话的聊天记录
                String conversationId = jsonObject.getString("conversationId");
                PageInfo<Message> msgList = messageService.getMessageByConversionId(conversationId,1,20);
                //将消息列表中的未读消息设置为已读。
                for(Message msg : msgList.getList()){
                    if(msg.getIsRead()==1){
                        msg.setIsRead(0);
                        messageService.readMessage(msg);
                    }
                }
                try {
                    JSONObject json = new JSONObject();
                    json.put("messageType",2);
                    json.put("conversationId",conversationId);
                    json.put("msgList",msgList);
                    webSocketSet.get(userId).send(json.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(messageType == 4){
                String content = jsonObject.getString("msgContent");
                Long fromId = jsonObject.getLong("fromId");
                Long toId = jsonObject.getLong("toId");
                //如果不是发给所有，那么就发给某一个人
                JSONObject data = new JSONObject();
                data.put("messageType",4);
                data.put("msgContent",content);
                data.put("fromId",fromId);
                data.put("toId",toId);
                sendMsg(data);
            }

        }
        catch (Exception e){
            log.info("onMessage执行出错了");
        }
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void send(String message) throws IOException {
        this.WebSocketsession.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public void sendMsg(JSONObject data) {
        Long fromId = data.getLong("fromId");
        // 发给谁
        Long toId = data.getLong("toId");
        String content = data.getString("msgContent");
        //过滤输入法输入的表情
        content= EmojiFilter.filterEmoji(content);
        //消息保存数据库
        Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setConversationId(fromId < toId ? String.format("%d_%d", fromId,toId) :
                String.format("%d_%d", toId,fromId));   //会话Id,小的在前大的在后。
        message.setIsRead(1);
        message.setMsgContent(content);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        message.setMsgCreatetime(sdf.format(date));
        messageService.addMessage(message);

        try {
            //如果用户在线，直接发送给用户
            if (webSocketSet.get(toId) != null) {
                webSocketSet.get(toId).send(JSON.toJSONString(message));
            }
        } catch (IOException e) {
            log.error("消息发送失败");
            e.printStackTrace();
        }

    }

    /**
     * 关闭连接调用的方法，成员退出
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") Long userId) {
        if (userno>0) {
            webSocketSet.remove(userno); //从set中删除
            subOnlineCount();     //在线数减1
            System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 传输消息错误调用的方法
     *
     * @param error
     */
    @OnError
    public void OnError(Throwable error) {
        log.info("Connection error");
    }

    public static void addOnlineCount(){
        synchronized (WebSocketServer.class){
            WebSocketServer.onlineCount++;
        }
    }
    public static void subOnlineCount(){
        synchronized (WebSocketServer.class){
            WebSocketServer.onlineCount--;
        }
    }
    public static Long getOnlineCount(){
        synchronized (WebSocketServer.class){
            return WebSocketServer.onlineCount;
        }
    }
}

