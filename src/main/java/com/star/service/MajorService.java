package com.star.service;

import com.star.mapper.MajorDao;
import com.star.model.entity.Direction;
import com.star.model.entity.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/15
 */
public interface MajorService {

    public List<Major> getAllMajor();

    public Major getMajorById(Long id);

    public Major getMajorByName(String name);

}
