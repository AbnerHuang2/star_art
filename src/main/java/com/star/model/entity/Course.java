package com.star.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Table(name = "star_course")
@Data
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;

    String courseName;
    String coverImgurl;
    @Column(name = "course_type")
    String courseType;
    @Column(name = "teacher_id")
    Long teacherId;
    String intro;
    @Column(name = "cost")
    float cost;
    @Column(name = "chapter_num")
    int  chapterNum;
    int totalHours;
    @Column(name = "pub_time")
    String pubTime;
    @Column(name = "stu_num")
    int stuNum;
    @Column(name = "follow_num")
    int followNum;
    @Column(name = "comment_num")
    int commentNum;
    String tags;
    @Column(name = "direct_id")
    Long directId;
}
