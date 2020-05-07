package com.star.model.vo;

import com.star.model.entity.News;
import com.star.model.entity.User;
import lombok.Data;
import lombok.ToString;

/**
 * @Author Abner
 * @CreateDate 2020/4/30
 */
@Data
@ToString
public class NewsVo implements java.io.Serializable{

    User user;
    News news;
    int likeStatus;
}
