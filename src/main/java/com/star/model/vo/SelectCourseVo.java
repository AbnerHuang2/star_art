package com.star.model.vo;

import com.star.model.entity.Course;
import com.star.model.entity.SelectCourse;
import lombok.Data;
import lombok.ToString;

/**
 * @Author Abner
 * @CreateDate 2020/5/16
 */
@Data
@ToString
public class SelectCourseVo {
    SelectCourse selectCourse;
    Course course;
}
