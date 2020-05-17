package com.star.service;

import com.star.mapper.DirectDao;
import com.star.model.entity.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/15
 */
@Service
public class DirectService {
    @Resource
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

    public List<Direction> getUserDirects(String userDirects){
        if(userDirects==null){
            return null;
        }
        String[] directs_str = userDirects.split(";");
        List<Integer> directs = new ArrayList<>();
        for(String di : directs_str){
            try{
                directs.add(Integer.valueOf(di));
            }catch (Exception e){
                continue;
            }
        }
        Example example = new Example(Direction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",directs);
        return directDao.selectByExample(example);
    }

    /**
     * 获取用户没有选择的
     * @param userDirects
     * @return
     */
    public List<Direction> getRestDirects(String userDirects){
        if(userDirects==null){
            return null;
        }
        String[] directs_str = userDirects.split(";");
        List<Integer> directs = new ArrayList<>();
        for(String di : directs_str){
            try{
                directs.add(Integer.valueOf(di));
            }catch (Exception e){
                continue;
            }
        }
        Example example = new Example(Direction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotIn("id",directs);
        return directDao.selectByExample(example);
    }

}
