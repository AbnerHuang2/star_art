package com.star.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.mapper.SelectCourseDao;
import com.star.model.entity.SelectCourse;
import com.star.model.vo.SelectCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/5/15
 */

public interface SelectCourseService {

    public PageInfo<SelectCourseVo> getSelectCourseByUserId(Long userId, int page, int pageSize);
    public boolean hasLearn(Long userId,Long courseId);


}
