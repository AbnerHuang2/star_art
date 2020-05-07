package com.star.model.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author Abner
 * @CreateDate 2020/4/15
 */
@Table(name = "major_direct")
@Data
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;

    String directName;

    Long majorId;

    String tags;

}
