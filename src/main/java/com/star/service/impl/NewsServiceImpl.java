package com.star.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.constant.EntityType;
import com.star.mapper.NewsDao;
import com.star.model.HostHolder;
import com.star.model.entity.News;
import com.star.model.entity.User;
import com.star.model.vo.NewsVo;
import com.star.service.LikeService;
import com.star.service.NewsService;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/6/26
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    HostHolder hostHolder;

    @Resource
    NewsDao newsDao;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

    String Sort_default = "news_createtime desc";

    public PageInfo<News> getAllNewsByPage(String name, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(News.class);
        if(!StringUtils.isEmpty(name)){
            example.createCriteria().andLike("newsTitle","%"+name+"%");
        }
        List<News> list = newsDao.selectByExample(example);
        PageInfo<News> newsTitle = new PageInfo<>(list);
        return newsTitle;
    }

    public PageInfo<NewsVo> getNews(String sort, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(News.class);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }else{
            example.setOrderByClause(Sort_default);
        }
        List<News> list = newsDao.selectByExample(example);
        PageInfo<News> newsPageInfo = new PageInfo<>(list);
        List<NewsVo> newsVos = new ArrayList<>(list.size());
        for(News news : list){
            NewsVo newsVo = new NewsVo();
            newsVo.setNews(news);
            newsVo.setUser(userService.getUserById(news.getBelongId()));
            if(hostHolder.getUser()!=null){
                User u = hostHolder.getUser();
                newsVo.setLikeStatus(likeService.getlikeStatus(u.getId(), EntityType.Entity_News.getType(),news.getId()));
            }

            newsVos.add(newsVo);
        }
        PageInfo<NewsVo> pageInfo = new PageInfo<>(newsVos);
        pageInfo.setTotal(newsPageInfo.getTotal());
        pageInfo.setPageNum(newsPageInfo.getPageNum());
        pageInfo.setPageSize(newsPageInfo.getPageSize());
        pageInfo.setHasNextPage(newsPageInfo.isHasNextPage());
        pageInfo.setIsLastPage(newsPageInfo.isIsLastPage());
        pageInfo.setPages(newsPageInfo.getPages());

        return pageInfo;
    }

    public PageInfo<News> getNewsByPage(int page,int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(News.class);
        example.setOrderByClause(Sort_default);

        List<News> list = newsDao.selectByExample(example);
        PageInfo<News> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    public List<News> getNewsBySort(String sort){
        Example example = new Example(News.class);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }else{
            example.setOrderByClause(Sort_default);
        }
        return newsDao.selectByExample(example);
    }

    public News getNewsById(Long newsId){
        return newsDao.selectByPrimaryKey(newsId);
    }

    public long like(Long newsId){
        long res = likeService.like(hostHolder.getUser().getId(),EntityType.Entity_News.getType(),newsId);
        if(res>0){
            News news = getNewsById(newsId);
            if(news != null){
                news.setNewsLike(news.getNewsLike()+1);
                int result = newsDao.updateByPrimaryKey(news);
                if(result==1){
                    return news.getNewsLike();
                }
            }
        }
        return  0;
    }

    public long unlike(Long newsId){
        boolean res = likeService.unlike(hostHolder.getUser().getId(),EntityType.Entity_News.getType(),newsId);
        if(res){
            News news = getNewsById(newsId);
            if(news != null){
                news.setNewsLike(news.getNewsLike()-1);
                int result = newsDao.updateByPrimaryKey(news);
                if(result==1){
                    return news.getNewsLike();
                }
            }
        }
        return  -1;
    }

    public boolean addNews(News news){
        int res = newsDao.insert(news);
        if(res==1){
            return true;
        }
        return false;
    }

    public boolean updateNewsComment(Long newsId,int commentNum){
        News news = getNewsById(newsId);
        if(news != null){
            news.setNewsComment(commentNum);
            int result = newsDao.updateByPrimaryKey(news);
            if(result==1){
                return true;
            }
        }
        return false;
    }

}
