package com.star.model.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

/**
 * @Author Abner
 * @CreateDate 2020/4/30
 */
@Data
@ToString
public class HomeStartVo {

    String title;
    String imgUrl;
    String intro;
    JSONObject entity;
    String to;  //跳转地址
    JSONObject params;  //参数
}
