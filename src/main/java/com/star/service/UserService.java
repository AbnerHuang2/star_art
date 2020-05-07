package com.star.service;

import com.github.pagehelper.PageHelper;
import com.star.constant.RedisConstant;
import com.star.constant.RoleConstant;
import com.star.mapper.UserDao;
import com.star.model.entity.User;
import com.star.utils.RedisUtil;
import com.star.utils.StarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    TokenService tokenService;

    @Autowired
    RedisUtil redisUtil;

    public void setDefault(User user){
        user.setUserAvatarURL("http://pic4.zhimg.com/50/v2-425eefcba6d48b4def4032896ece331c_hd.jpg");
    }

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
    public String login(String email, String password){
        String token = null;
        User user = getUserByEmail(email);
        if(user!=null && user.getUserPassword().equals(StarUtil.MD5(password))){
            token = tokenService.generateToken(user.getId()+"");
        }

        return token;
    }

    public int register(String email, String password){
        User user = new User();
        user.setUserEmail(email);
        user.setUserPassword(StarUtil.MD5(password));
        user.setStatus(RoleConstant.Role_Student);
        setDefault(user);

        return userDao.insert(user);
    }

    public List<User> getHotUsers(){
        PageHelper.startPage(1,4);
        Example example = new Example(User.class);
        example.setOrderByClause("userFansCount desc");
        example.createCriteria().andEqualTo("status",RoleConstant.Role_Student);
        return userDao.selectByExample(example);
    }

    public User getUserById(Long id){
        return userDao.selectByPrimaryKey(id);
    }

}
