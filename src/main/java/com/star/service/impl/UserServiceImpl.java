package com.star.service.impl;

import com.github.pagehelper.PageHelper;
import com.star.constant.RoleConstant;
import com.star.mapper.UserDao;
import com.star.model.entity.User;
import com.star.service.TokenService;
import com.star.service.UserService;
import com.star.utils.RedisUtil;
import com.star.utils.StarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/5/20
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;

    @Autowired
    TokenService tokenService;

    @Autowired
    RedisUtil redisUtil;

    public void setDefault(User user){
        user.setUserAvatarURL("http://pic4.zhimg.com/50/v2-425eefcba6d48b4def4032896ece331c_hd.jpg");
    }

    @Override
    public User getUserByEmail(String email){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userEmail", email);
        return userDao.selectOneByExample(example);
    }

    /**
     * 用户登录
     * 实现思路：
     * 通过email查询用户
     * 对比密码。（加盐对比，增强安全性）
     * 生成token.
     *
     * @param email
     * @param password
     * @return
     */
    @Override
    public String login(String email, String password){
        String token = null;
        User user = getUserByEmail(email);
        if(user!=null && user.getUserPassword().equals(StarUtil.MD5(password))){
            token = tokenService.generateToken(user.getId()+"");
        }

        return token;
    }
    @Override
    public int register(String email, String password){
        User user = new User();
        user.setUserEmail(email);
        user.setUserPassword(StarUtil.MD5(password));
        user.setStatus(RoleConstant.Role_Student);
        setDefault(user);

        return userDao.insert(user);
    }
    @Override
    @Cacheable(value = "hotUsers", key = "methodName")
    public List<User> getHotUsers(int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(User.class);
        example.setOrderByClause("userFansCount desc");
        //example.createCriteria().andEqualTo("status",RoleConstant.Role_Student);
        return userDao.selectByExample(example);
    }
    @Override
    @Cacheable(value = "user", key = "#id")
    public User getUserById(Long id){
        return userDao.selectByPrimaryKey(id);
    }
    @Override
    public List<User> getUserByCollection(Iterable values){
        if(values==null || !values.iterator().hasNext()){
            return null;
        }
        Example example = new Example(User.class);
        example.createCriteria().andIn("id",values);

        return userDao.selectByExample(example);
    }
    @Override
    @CacheEvict(value = "hotUsers", allEntries = true,beforeInvocation = true)
    @CachePut(value = "user",key = "#user.id")
    public User updateUserInfo(User user){
        if(user!=null){
            int res = userDao.updateByPrimaryKey(user);
            return res==1 ? user : null;
        }
        return null;
    }
}
