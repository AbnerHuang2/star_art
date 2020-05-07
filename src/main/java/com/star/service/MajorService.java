package com.star.service;

import com.star.mapper.MajorDao;
import com.star.model.entity.Direction;
import com.star.model.entity.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/15
 */
@Service
public class MajorService {

    @Autowired
    MajorDao majorDao;

    public List<Major> getAllMajor(){
        return majorDao.selectAll();
    }

    public Major getMajorById(Long id){
        return majorDao.selectByPrimaryKey(id);
    }

    public Major getMajorByName(String name){
        Example example = new Example(Major.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("majorName",name);
        return majorDao.selectByExample(example).get(0);
    }

}
