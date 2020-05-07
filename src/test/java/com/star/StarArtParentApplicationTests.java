package com.star;

import com.github.pagehelper.PageInfo;
import com.star.constant.RedisConstant;
import com.star.mapper.CourseDao;
import com.star.model.entity.*;
import com.star.model.vo.HomeStartVo;
import com.star.model.vo.NewsVo;
import com.star.service.*;
import com.star.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
class StarArtParentApplicationTests {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CourseDao courseDao;

    @Autowired
    CourseService courseService;

    @Autowired
    MajorService majorService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    ProductService productService;

    @Autowired
    NewsService newsService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    HomeService homeService;
    
    @Test
    void testHomeService(){
        List<HomeStartVo> list = homeService.getHomeStart();
        System.out.println(list);
    }
    
    @Test
    void testTeacherService(){
        List<User> list = teacherService.getRecommandTeachers(1,3);
        System.out.println(list);
    }

    @Test
    void testNewsService(){
        //PageInfo<News> pageInfo = newsService.getNewsByPage(1,5);

        PageInfo<NewsVo> pageInfo = newsService.getNews(null,1,5);

        System.out.println(pageInfo.getList());
    }

    @Test
    void testProductService(){
        PageInfo<Product> pageInfo = productService.getProducts(1,4,null,1l,null);

        System.out.println(pageInfo.getList());
    }

    @Test
    void testChapterService(){
        List<Chapter> list = chapterService.getChapterByCourseId(1l);
        System.out.println(list);
    }

    @Test
    void testMajorService(){
        Major major = majorService.getMajorByName("美术专业");
        System.out.println(major);
    }

    @Test
    void testCourseDao(){

//        PageInfo<Course> pageInfo = courseService.getCourses(1l,null,"",1,4);
//        System.out.println(pageInfo);

        List<Course> list = courseService.getCourseBySort("(follow_num*2+comment_num*3) desc");
        System.out.println(list.size());

        //Course course = courseService.getHotestCourse();
        //System.out.println(course);

//        List<Integer> directs = new ArrayList<>();
//        directs.add(1);
//        directs.add(2);
//        List<Course> list = courseService.getCourseByMajorAndDirectListAndPage(1l,directs,0,4);
//        System.out.println(list.size());

        //HomeCourseResult home = courseService.getHomeCourseResult(0,4);
        //System.out.println(home.getMusicCourseList());
    }

    @Test
    void testTkMybatis(){

//        int res = userService.register("1101964585@qq.com","123");
//        System.out.println(res);
//
//        User user = userService.getUserByEmail("1101964585@qq.com");
//        System.out.println(user);

        List<User> list = userService.getHotUsers();
        System.out.println(list);
    }

    @Test
    void testTokenService() {
//        User user = tokenService.getUserByToken("aaa");
//        System.out.println(user.getUserEmail());
        String res = tokenService.generateToken("1");
        System.out.println(res);

        tokenService.save(res,"1");
        User u = tokenService.validToken(res);
        System.out.println(u.getUserEmail());
    }

    @Test
    void contextLoads() {
        String emailAddress = "1101964585@qq.com";
        String checkCode = UUID.randomUUID().toString().substring(0,4);
        String key = RedisConstant.getCheckcodeKey(emailAddress);
        boolean res = redisUtil.set(key,checkCode,60);
        System.out.println(res);
    }

}
