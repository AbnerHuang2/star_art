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

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/29
 */
@Service
@Log4j2
public class TeacherService {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserDao userDao;

    public List<User> getRecommandTeachers(int page, int pageSize){
        User user = hostHolder.getUser();
        if(user==null){
            log.info("未登录用户推荐热门导师");
            return defaultRecommandTeachers(page, pageSize);
        }
        //登录用户推荐导师
        //TODO

        return defaultRecommandTeachers(page, pageSize);
    }

    public List<User> defaultRecommandTeachers(int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("status", RoleConstant.Role_Teacher);
        example.setOrderByClause("userFansCount desc");
        return userDao.selectByExample(example);
    }

    public PageInfo<User> getTeachers(int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(User.class);
        example.setOrderByClause("id desc");
        example.createCriteria().andEqualTo("status", RoleConstant.Role_Teacher);
        List<User> list = userDao.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    public User getTeacherById(Long id){
        return userDao.selectByPrimaryKey(id);
    }

    public List<User> getTeachersBySort(String sort){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("status", RoleConstant.Role_Teacher);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }else{
            example.setOrderByClause("id");
        }
        return userDao.selectByExample(example);
    }

}
