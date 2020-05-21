package com.star.service.impl;

import com.star.mapper.MajorDao;
import com.star.model.entity.Major;
import com.star.service.MajorService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Abner
 * @createDate 2020/5/21
 */
@Service
public class MajorServiceImpl implements MajorService {
    @Resource
    MajorDao majorDao;

    @Override
    @Cacheable(value = "allMajor")
    public List<Major> getAllMajor(){
        return majorDao.selectAll();
    }
    @Override
    @Cacheable(value = "major",key = "#id")
    public Major getMajorById(Long id){
        return majorDao.selectByPrimaryKey(id);
    }
    @Override
    public Major getMajorByName(String name){
        Example example = new Example(Major.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("majorName",name);
        return majorDao.selectByExample(example).get(0);
    }
}
