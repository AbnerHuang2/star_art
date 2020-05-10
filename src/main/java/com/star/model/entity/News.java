package com.star.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

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
    String newsIntro;
    String newsContent;
    Integer newsLike;
    Integer newsComment;
    Integer newsShare;
    String newsTags;
    String newsCreatetime;
    Character newsStatus;
    Long belongId;
}
