package com.star.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.constant.RedisConstant;
import com.star.constant.RoleConstant;
import com.star.mapper.UserDao;
import com.star.model.entity.User;
import com.star.utils.RedisUtil;
import com.star.utils.StarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;


public interface UserService {

    public User getUserByEmail(String email);

    public String login(String email, String password);

    public User adminLogin(String email,String password);

    public int register(String email, String password);

    public List<User> getHotUsers(int page, int pageSize);

    public User getUserById(Long id);

    public List<User> getUserByCollection(Iterable values);

    public User updateUserInfo(User user);

    PageInfo<User> getAllUserByPage(int page, int pageSize);

    PageInfo<User> getNormalUserByPage(String name, int page, int pageSize);

}
