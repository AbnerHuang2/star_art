package com.star.controller;

import com.github.pagehelper.PageInfo;
import com.star.constant.EntityType;
import com.star.model.HostHolder;
import com.star.model.api.CommonResult;
import com.star.model.entity.News;
import com.star.model.entity.Product;
import com.star.model.entity.User;
import com.star.model.vo.NewsVo;
import com.star.service.LikeService;
import com.star.service.NewsService;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Abner
 * @CreateDate 2020/4/27
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    String Sort_hot = "(news_like+news_comment*3) desc";

    @RequestMapping("/getNews")
    public CommonResult<PageInfo<NewsVo>> getNews(@RequestParam(defaultValue = "news_createtime desc")String sort,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8")int pageSize){
        PageInfo<NewsVo> pageInfo = null;
        switch (sort){
            case "hot" : sort = Sort_hot;break;
            default: sort = null; break;
        }
        pageInfo = newsService.getNews(sort,page,pageSize);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"获取信息成功");
        }
        return CommonResult.failed("获取信息失败");
    }

    @RequestMapping("/getNewsById")
    public CommonResult<NewsVo> getNewsById(Long id){
        NewsVo newsVo = new NewsVo();
        News news = newsService.getNewsById(id);
        newsVo.setNews(news);
        newsVo.setUser(userService.getUserById(news.getBelongId()));

        if(news!=null){
            return CommonResult.success(newsVo,"获取信息成功");
        }
        return CommonResult.failed("获取信息失败");
    }

    @RequestMapping("/newsLike")
    public CommonResult<String> newsLike(@RequestParam Long newsId){
        long res = newsService.like(newsId);
        if(res>0){
            return CommonResult.success("点赞成功","点赞成功");
        }
        return CommonResult.failed("点赞失败");
    }
    @RequestMapping("/newsUnLike")
    public CommonResult<String> newsUnLike(@RequestParam Long newsId){
        long res = newsService.unlike(newsId);
        if(res>=0){
            return CommonResult.success("取消点赞成功","取消点赞成功");
        }
        return CommonResult.failed("取消点赞失败");
    }

    @RequestMapping("/addNews")
    public CommonResult<News> addNews(String newsTitle, @RequestParam("newsContent") String newsContent,String newsIntro){
        if(hostHolder.getUser()==null){
            return CommonResult.failed("请先登录");
        }
        News news = new News();
        news.setNewsTitle(newsTitle);
        news.setNewsContent(newsContent);

        if(StringUtils.isEmpty(newsIntro)){
            news.setNewsIntro(newsContent);
        }else{
            news.setNewsIntro(newsIntro);
        }

        news.setNewsLike(0);
        news.setNewsComment(0);
        news.setNewsShare(0);
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        news.setNewsCreatetime(df.format(date));
        news.setBelongId(hostHolder.getUser().getId());
        news.setNewsStatus('1');
        boolean res = newsService.addNews(news);
        if(res){
            return CommonResult.success(news,"信息发布成功");
        }
        return CommonResult.failed("信息发布失败");
    }

}
