package com.star.controller;

import com.github.pagehelper.PageInfo;
import com.star.model.HostHolder;
import com.star.model.api.CommonResult;
import com.star.model.entity.Comment;
import com.star.model.vo.CommentVo;
import com.star.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Abner
 * @CreateDate 2020/5/9
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @RequestMapping("/getComments")
    public CommonResult<PageInfo<CommentVo>> getComments(Long entityId, Integer entityType, String sort,
                                                         @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8")int pageSize){
        PageInfo<CommentVo> pageInfo = null;
        pageInfo = commentService.getComments(entityId,entityType,sort,page,pageSize);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"获取评论成功");
        }
        return CommonResult.failed("获取评论失败");
    }

    @RequestMapping("/addComment")
    public CommonResult<Comment> addComment(Long entityId,Integer entityType,String commentContent){
        if(hostHolder.getUser()==null){
            return CommonResult.failed("请先登录");
        }
        Comment comment = new Comment();
        comment.setUserId(hostHolder.getUser().getId());
        comment.setEntityId(entityId);
        comment.setEntityType(entityType);
        comment.setCommentContent(commentContent);
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        comment.setCommentCreatetime(df.format(date));
        comment.setCommentStatus(String.valueOf(1));
        boolean res = commentService.addComment(comment);
        if(res){
            return CommonResult.success(comment,"添加评论成功");
        }
        return CommonResult.failed("添加评论失败");
    }

    @RequestMapping("/deleteComment")
    public CommonResult<String> deleteComment(Long id){
        boolean res = commentService.deleteCommentById(id);
        if(res){
            return CommonResult.success("删除评论成功","删除评论成功");
        }
        return CommonResult.failed("删除评论失败");
    }

}
