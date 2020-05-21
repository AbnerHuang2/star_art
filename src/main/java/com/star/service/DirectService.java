package com.star.service;

import com.star.mapper.DirectDao;
import com.star.model.entity.Direction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/4/15
 */
public interface DirectService {
    public List<Direction> getAllDirect();
    public Direction getDirectById(Long id);
    public List<Direction> getDirectByMajorId(Long majorId);
    public List<Direction> getUserDirects(String userDirects);
    /**
     * 获取用户没有选择的
     * @param userDirects
     * @return
     */
    public List<Direction> getRestDirects(String userDirects);
}
