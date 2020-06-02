package com.star.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Abner
 * @createDate 2020/5/23
 */
@Table(name = "star_msg")
@Data
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;
    Long fromId;
    Long toId;
    String msgContent;
    Integer isRead; // 0已读， 1未读
    String conversationId;  //fromId_toId的形式保存
    String msgCreatetime;
}
