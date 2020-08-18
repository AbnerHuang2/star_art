package com.star.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.constant.RoleConstant;
import com.star.mapper.UserDao;
import com.star.model.HostHolder;
import com.star.model.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/29
 */
public interface TeacherService {

    public List<User> getRecommandTeachers(int page, int pageSize);
    public List<User> defaultRecommandTeachers(int page, int pageSize);
    public PageInfo<User> getTeachers(int page, int pageSize);
    public User getTeacherById(Long id);
    public List<User> getTeachersBySort(String sort);

}
