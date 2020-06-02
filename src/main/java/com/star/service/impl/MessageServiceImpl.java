package com.star.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.mapper.MessageDao;
import com.star.model.entity.Message;
import com.star.service.MessageService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Abner
 * @createDate 2020/5/23
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    MessageDao messageDao;

    @Override
    public boolean addMessage(Message msg) {
        int res = messageDao.insert(msg);
        return res == 1 ? true : false;
    }

    @Override
    public Map<Long,String> getConversationMapByToId(Long toId) {
        Map<Long, String> map = new HashMap<>();
        Example example = new Example(Message.class);
        example.createCriteria()
                .orEqualTo("toId",toId);
        List<Message> list = messageDao.selectByExample(example);
        if(list.size()>0){
            for(Message msg : list){
                String res = map.get(msg.getFromId());
                if(res == null){
                    map.put(msg.getFromId(),msg.getConversationId());
                }
            }
        }
        return map;
    }


    @Override
    public Integer getUnReadCount(Long toId, String conversationId) {
        Example example = new Example(Message.class);
        example.createCriteria()
                .andEqualTo("toId",toId)
                .andEqualTo("conversationId",conversationId)
                .andEqualTo("isRead",1);
        List<Message> list = messageDao.selectByExample(example);
        return list.size();
    }

    @Override
    public Message readMessage(Message message) {
        int res =  messageDao.updateByPrimaryKey(message);
        return res==1 ? message : null;
    }

    @Override
    public PageInfo<Message> getMessageByConversionId(String conversationId, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        Example example = new Example(Message.class);
        example.createCriteria()
                .andEqualTo("conversationId",conversationId);
        example.setOrderByClause("msg_createtime desc");
        List<Message> list = messageDao.selectByExample(example);
        PageInfo<Message> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
