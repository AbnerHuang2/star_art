package com.star.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.mapper.CommentDao;
import com.star.model.entity.Comment;
import com.star.model.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/5/9
 */
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserService userService;

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

        List<Comment> list = commentDao.selectByExample(example);
        List<CommentVo> commentVos = new ArrayList<>();

        for(Comment comment : list){
            CommentVo commentVo = new CommentVo();
            commentVo.setComment(comment);
            commentVo.setUser(userService.getUserById(comment.getUserId()));

            commentVos.add(commentVo);
        }

        PageInfo<CommentVo> pageInfo = new PageInfo<>(commentVos);
        return pageInfo;
    }

}
