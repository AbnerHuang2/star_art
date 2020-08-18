package com.star.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.constant.EntityType;
import com.star.mapper.CommentDao;
import com.star.model.entity.Comment;
import com.star.model.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/5/9
 */
public interface CommentService {

    public PageInfo<CommentVo> getComments(Long entityId, int entityType, String sort, int page, int pageSize);

    public boolean addComment(Comment comment);

    public boolean deleteCommentById(Long id);

}
