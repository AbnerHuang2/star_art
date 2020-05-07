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
 * @CreateDate 2020/4/27
 */
@Table(name = "star_product")
@Data
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    Long id;

    String productName;
    String productUrl;
    String productIntro;
    Integer productLike;
    String productTag;
    Long belongId;
    String productCreatedate;
    Long directId;
}
