package com.star.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.constant.EntityType;
import com.star.mapper.NewsDao;
import com.star.model.HostHolder;
import com.star.model.entity.News;
import com.star.model.entity.User;
import com.star.model.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/27
 */

public interface NewsService {
    public PageInfo<News> getAllNewsByPage(String name, int page, int pageSize);
    public PageInfo<NewsVo> getNews(String sort, int page, int pageSize);
    public PageInfo<News> getNewsByPage(int page,int pageSize);
    public List<News> getNewsBySort(String sort);
    public News getNewsById(Long newsId);
    public long like(Long newsId);
    public long unlike(Long newsId);
    public boolean addNews(News news);
    public boolean updateNewsComment(Long newsId,int commentNum);
}
