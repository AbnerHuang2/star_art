package com.star.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Author Abner
 * @CreateDate 2020/4/15
 */
@Table(name = "star_major")
@Data
@ToString
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;

    @Column(name = "major_name")
    String majorName;

    @Column(name = "major_direct_num")
    Integer majorDirectNum;

}
