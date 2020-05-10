package com.star.model.vo;

import com.star.model.entity.Comment;
import com.star.model.entity.User;
import lombok.Data;
import lombok.ToString;

/**
 * @Author Abner
 * @CreateDate 2020/5/9
 */
@Data
@ToString
public class CommentVo {
    Comment comment;
    User user;
}
