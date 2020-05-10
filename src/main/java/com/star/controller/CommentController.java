package com.star.controller;

import com.github.pagehelper.PageInfo;
import com.star.model.api.CommonResult;
import com.star.model.entity.Comment;
import com.star.model.vo.CommentVo;
import com.star.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Abner
 * @CreateDate 2020/5/9
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
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

}
