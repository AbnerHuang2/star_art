package com.star.model.vo;

import com.star.model.entity.Course;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 显示首页的课程相关信息
 * @Author Abner
 * @CreateDate 2020/4/15
 */
@Data
@ToString
public class HomeCourseResult {
    List<Course> pubCourseList; //公开课
    List<Course> artCourseList; //美术专业
    List<Course> musicCourseList;  //音乐专业
}
