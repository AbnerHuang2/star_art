package com.star.interceptor;

import com.star.model.HostHolder;
import com.star.model.entity.User;
import com.star.service.TokenService;
import com.star.service.UserService;
import com.star.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {

	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	UserService userService;
	@Autowired
	TokenService tokenService;
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//访问前的拦截器，用于验证token
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if("token".equals(cookie.getName())) {
					//通过token查询用户
					String token = cookie.getValue();
					//从redis中获取该token对应的User
					User user = tokenService.validToken(token);
					if(user!=null){
						//token有效并且没有过期
						hostHolder.setUser(user);
					}

					return true;
				}
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		hostHolder.clear();
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
	
}
