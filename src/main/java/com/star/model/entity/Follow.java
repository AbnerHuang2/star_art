package com.star.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author Abner
 * @CreateDate 2020/5/17
 */
@Table(name = "star_follow")
@Data
@ToString
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;
    Long followingId;   //关注者
    Long followerId;    //被关注
    Integer followType;
}
