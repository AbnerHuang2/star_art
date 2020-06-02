package com.star.service;

import com.github.pagehelper.PageInfo;
import com.star.model.entity.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {

    public boolean addMessage(Message msg);

    /**
     *
     * @param toId
     * @return 返回fromId和会话Id
     */
    Map<Long,String> getConversationMapByToId(Long toId);

    Integer getUnReadCount(Long toId,String conversationId);

    Message readMessage(Message message);

    public PageInfo<Message> getMessageByConversionId(String conversationId, int page, int pageSize);
}
