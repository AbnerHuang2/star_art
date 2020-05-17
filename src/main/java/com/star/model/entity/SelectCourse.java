package com.star.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author Abner
 * @CreateDate 2020/5/15
 */
@Table(name = "user_course")
@Data
@ToString
public class SelectCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;

    Long userId;
    Long courseId;
    Date selectDate;
    String userRate;
    Integer finished;
}
