package com.star.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.star.model.HostHolder;
import com.star.model.entity.Course;
import com.star.model.entity.News;
import com.star.model.entity.Product;
import com.star.model.entity.User;
import com.star.model.vo.HomeStartVo;
import com.star.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/5/21
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    TeacherService teacherService;

    @Autowired
    CourseService courseService;

    @Autowired
    ProductService productService;

    @Autowired
    NewsService newsService;

    public List<HomeStartVo> getHomeStart(){
        List<HomeStartVo> list = new ArrayList<HomeStartVo>();

        List<Course> courseList = null;
        if(hostHolder.getUser()!=null){
            courseList = courseService.getRecommandCourses(hostHolder.getUser().getId(),1,1);
            if(courseList.size()<1){
                courseList = courseService.getCourseBySort("(follow_num*2+comment_num*3) desc");
            }
        }else{
            courseList = courseService.getCourseBySort("(follow_num*2+comment_num*3) desc");
        }

        if(courseList.size()>0){
            HomeStartVo vo1 = new HomeStartVo();
            vo1.setTitle(courseList.get(0).getCourseName());
            vo1.setImgUrl(courseList.get(0).getCoverImgurl());
            vo1.setIntro("好评不断的课程");
            JSONObject json = new JSONObject();
            json.put("entity",courseList.get(0));
            vo1.setEntity(json);

            vo1.setTo("/courseDetail");
            JSONObject params = new JSONObject();
            params.put("courseId",courseList.get(0).getId());
            vo1.setParams(params);

            list.add(vo1);
        }
        List<User> teacherList = teacherService.getTeachersBySort("userFansCount desc");
        if(teacherList.size()>0){
            HomeStartVo vo1 = new HomeStartVo();
            vo1.setTitle(teacherList.get(0).getUserName());
            vo1.setImgUrl(teacherList.get(0).getUserAvatarURL());
            vo1.setIntro(teacherList.get(0).getUserIntro());

            JSONObject json = new JSONObject();
            json.put("entity",teacherList.get(0));
            vo1.setEntity(json);

            vo1.setTo("/person");
            JSONObject params = new JSONObject();
            params.put("id",teacherList.get(0).getId());
            vo1.setParams(params);

            list.add(vo1);
        }
        List<Product> productList = productService.getProducts
                (1,2,"product_like desc",null,null,null)
                .getList();
        if(productList.size()>0){
            HomeStartVo vo1 = new HomeStartVo();
            vo1.setTitle(productList.get(0).getProductName());
            vo1.setImgUrl(productList.get(0).getProductUrl());
            vo1.setIntro(productList.get(0).getProductIntro());

            JSONObject json = new JSONObject();
            json.put("entity",productList.get(0));
            vo1.setEntity(json);

            vo1.setTo("/product");
            JSONObject params = new JSONObject();
            vo1.setParams(params);

            list.add(vo1);
        }

        List<News> newsList = newsService.getNewsBySort("(news_like*2 + news_comment*4) desc");
        if(newsList.size()>0){
            HomeStartVo vo1 = new HomeStartVo();
            vo1.setTitle(newsList.get(0).getNewsTitle());
            vo1.setImgUrl("https://images.pexels.com/photos/1037995/pexels-photo-1037995.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=400");
            vo1.setIntro(newsList.get(0).getNewsContent());

            JSONObject json = new JSONObject();
            json.put("entity",newsList.get(0));
            vo1.setEntity(json);

            vo1.setTo("/newsDetail");
            JSONObject params = new JSONObject();
            params.put("newsId",newsList.get(0).getId());
            vo1.setParams(params);

            list.add(vo1);
        }

        return list;
    }
}
