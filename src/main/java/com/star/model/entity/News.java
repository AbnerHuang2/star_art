package com.star.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author Abner
 * @CreateDate 2020/4/27
 */
@Table(name = "star_news")
@Data
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;
    String newsTitle;
    String newsContent;
    Integer newsLike;
    Integer newsComment;
    Integer newsShare;
    String newsTags;
    String newsCreatetime;
    Character newsStatus;
    Long belongId;
}
