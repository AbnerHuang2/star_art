package com.star.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.mapper.CommentDao;
import com.star.model.entity.Comment;
import com.star.model.vo.CommentVo;
import com.star.service.CommentService;
import com.star.service.CourseService;
import com.star.service.NewsService;
import com.star.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/6/26
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentDao commentDao;

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @Autowired
    CourseService courseService;

    String Sort_default = "comment_createtime desc";

    public PageInfo<CommentVo> getComments(Long entityId, int entityType, String sort, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(Comment.class);
        if(sort!=null) {
            example.setOrderByClause(sort);
        }else{
            example.setOrderByClause(Sort_default); //setOrderByClause对应的是数据库的字段
        }
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",entityId);
        criteria.andEqualTo("entityType",entityType);
        criteria.andEqualTo("commentStatus",1);
        List<Comment> list = commentDao.selectByExample(example);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(list);
        //将评论总数设置到相应的entity中
        switch (entityType){
            case 1 : newsService.updateNewsComment(entityId,(int)commentPageInfo.getTotal());break;
            case 2 : courseService.updateCourseComment(entityId,(int)commentPageInfo.getTotal());break;

            default:break;
        }

        List<CommentVo> commentVos = new ArrayList<>();

        for(Comment comment : list){
            CommentVo commentVo = new CommentVo();
            commentVo.setComment(comment);
            commentVo.setUser(userService.getUserById(comment.getUserId()));

            commentVos.add(commentVo);
        }

        PageInfo<CommentVo> pageInfo = new PageInfo<>(commentVos);
        pageInfo.setTotal(commentPageInfo.getTotal());

        return pageInfo;
    }

    public boolean addComment(Comment comment){
        int res = commentDao.insert(comment);
        if(res==1){
            return true;
        }
        return false;
    }

    public boolean deleteCommentById(Long id){
        int res = commentDao.deleteByPrimaryKey(id);
        if(res==1){
            return true;
        }
        return false;
    }
}
