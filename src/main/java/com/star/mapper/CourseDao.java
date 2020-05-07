package com.star.mapper;

import com.star.model.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CourseDao extends Mapper<Course> {

}
