package com.star.service;

import com.alibaba.fastjson.JSONObject;
import com.star.model.HostHolder;
import com.star.model.entity.Course;
import com.star.model.entity.News;
import com.star.model.entity.Product;
import com.star.model.entity.User;
import com.star.model.vo.HomeStartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/30
 */

public interface HomeService {

    public List<HomeStartVo> getHomeStart();

}
