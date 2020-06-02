package com.star.controller;

import com.alibaba.fastjson.JSONObject;
import com.star.model.HostHolder;
import com.star.model.api.CommonResult;
import com.star.model.entity.Message;
import com.star.model.entity.User;
import com.star.service.MessageService;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Abner
 * @createDate 2020/5/25
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping("/addMessage")
    public CommonResult<Boolean> addMessage(String msgContent,Long fromId,Long toId){
        System.out.println(msgContent+" : "+fromId+" : "+toId);
        if(StringUtils.isEmpty(msgContent) || fromId==null || toId==null){
            return CommonResult.failed("私信发送失败");
        }
        Message msg = new Message();
        msg.setMsgContent(msgContent);
        msg.setFromId(fromId);
        msg.setToId(toId);
        msg.setIsRead(1);
        msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId,toId) :
                String.format("%d_%d", toId,fromId));   //会话Id,小的在前大的在后。
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        msg.setMsgCreatetime(sdf.format(date));
        boolean res = messageService.addMessage(msg);
        if(res){
            return CommonResult.success(res,"私信发送成功");
        }
        return CommonResult.failed("私信发送失败");
    }



}
