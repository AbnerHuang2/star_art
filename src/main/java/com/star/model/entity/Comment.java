package com.star.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author Abner
 * @CreateDate 2020/5/9
 */
@Table(name = "star_comment")
@Data
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;
    Long userId;
    Long entityId;
    Integer entityType;
    String commentContent;
    String commentCreatetime;
    String commentStatus;
}
