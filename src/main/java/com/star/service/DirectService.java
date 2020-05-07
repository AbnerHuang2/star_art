package com.star.service;

import com.star.mapper.DirectDao;
import com.star.model.entity.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/15
 */
@Service
public class DirectService {
    @Autowired
    DirectDao directDao;

    public List<Direction> getAllDirect(){
        return directDao.selectAll();
    }

    public Direction getDirectById(Long id){
        return directDao.selectByPrimaryKey(id);
    }

    public List<Direction> getDirectByMajorId(Long majorId){
        Example example = new Example(Direction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("majorId",majorId);
        return directDao.selectByExample(example);
    }

}
