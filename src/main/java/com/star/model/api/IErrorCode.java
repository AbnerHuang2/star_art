package com.star.model.api;

/**
 * 封装API的错误码
 * Created by abner on 2019/4/19.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
