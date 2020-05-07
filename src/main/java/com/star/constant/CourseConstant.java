package com.star.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author Abner
 * @CreateDate 2020/4/15
 */
public enum CourseConstant {
    PUBLIC_Class(1),    //"公开课"
    FREE(2),    //,"免费课程"
    CHARGE(3);  //,"收费课程"

    private int code;

    private CourseConstant(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
