package com.star.controller;

import com.star.constant.RedisConstant;
import com.star.model.HostHolder;
import com.star.model.api.CommonResult;
import com.star.model.entity.User;
import com.star.service.MailService;
import com.star.service.TokenService;
import com.star.service.UserService;
import com.star.utils.RedisUtil;
import com.star.utils.StarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(("/member"))
public class LoginController {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @Autowired
    HostHolder hostHolder;

    @PostMapping("/login")
    public CommonResult<User> login(String email, String passwd,
                                    HttpServletResponse response){
        String token = userService.login(email,passwd);
        if(token!=null){
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setMaxAge((int)RedisConstant.TOKEN_ExpiredTime);
            response.addCookie(cookie);

            //获取用户信息，然后保存(token,userId)到redis
            User user = userService.getUserByEmail(email);
            //将token,userId保存到redis
            tokenService.save(token,user.getId()+"");

            return CommonResult.success(user,"登录成功");


        }else {
            return CommonResult.failed("邮箱或密码错误");
        }

    }

    @RequestMapping("/adminLogin")
    public CommonResult<User> adminLogin(String email,String password){
        User user = userService.adminLogin(email,password);
        if(user!=null){
            return CommonResult.success(user,"登录成功");
        }else{
            return CommonResult.failed("登录失败");
        }
    }

    @RequestMapping("/logout")
    public CommonResult<String> logout(){
        //思路：让token失效
        User user = hostHolder.getUser();
        if(user!=null){
            String token = tokenService.generateToken(user.getId()+"");
            tokenService.delToken(token);
            return CommonResult.success("已退出");
        }
        return CommonResult.failed("操作失败");
    }

    @RequestMapping("/reg/checkEmail")
    public CommonResult<String> check(String mailAddress){
        //先检查邮箱是否正确
        if(!StarUtil.isEmail(mailAddress)){
            return CommonResult.failed("非法邮箱");
        }
        //可以用redis保存这个checkcode（键为邮箱地址，值为checkcode）
        String checkCode = UUID.randomUUID().toString().substring(0,4);
        String key = RedisConstant.getCheckcodeKey(mailAddress);
        boolean res = redisUtil.set(key,checkCode,60*5);
        try {
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("username", mailAddress);
            paramMap.put("checkCode", checkCode);
            mailService.sendTemplateMail(mailAddress,"star-art注册验证",paramMap,"check");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("邮件发送失败");
        }
        return CommonResult.success(checkCode,"获取验证码成功");
    }

    @PostMapping("/register")
    public CommonResult<Boolean> register(String mailAddress,String passwd,String checkCode){
        //先检查验证码
        String key = RedisConstant.getCheckcodeKey(mailAddress);
        String redisCheckCode = redisUtil.get(key).toString();
        if(redisCheckCode == null || !redisCheckCode.equals(checkCode)){
            return CommonResult.failed("验证码错误或者失效");
        }
        //检测用户是否存在
        if(userService.getUserByEmail(mailAddress)!=null){
            return CommonResult.failed("该邮箱已被注册");
        }
        int res = userService.register(mailAddress,passwd);
        if(res==1) {
            return CommonResult.success(true, "注册成功");
        } else{
            return CommonResult.failed();
        }
    }

}
