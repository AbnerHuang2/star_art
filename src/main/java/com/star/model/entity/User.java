package com.star.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="star_user")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;

    @Column(name = "userEmail")
    String userEmail;

    @Column(name = "userName")
    String userName;

    @Column(name = "userPassword")
    String userPassword;

    @Column(name = "userNickName")
    String userNickName;

    @Column(name = "userAvatarURL")
    String userAvatarURL;

    @Column(name = "userWechat")
    String userWechat;

    @Column(name = "userSex")
    String userSex;

    @Column(name = "userBirth")
    String userBirth;

    @Column(name = "userIntro")
    String userIntro;

    @Column(name = "userMajor")
    String userMajor;

    @Column(name = "user_directions")
    String userDirections;

    @Column(name = "userTags")
    String userTags;

    @Column(name = "userFollowCount")
    Integer userFollowCount;

    @Column(name = "userFansCount")
    Integer userFansCount;

    @Column(name = "userCommentCount")
    Integer userCommentCount;

    @Column(name = "userSelectedCourseCount")
    Integer userSelectedCourseCount;

    @Column(name = "userProductCount")
    Integer userProductCount;

    @Column(name = "userOnlineFlag")
    Character userOnlineFlag;

    @Column(name = "status")
    Character status;

}
