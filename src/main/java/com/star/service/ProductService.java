package com.star.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.star.mapper.ProductDao;
import com.star.model.entity.Course;
import com.star.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/27
 */

public interface ProductService {

    public Product getHotestProduct();

    public PageInfo<Product> getProducts(int page, int pageSize, String sort, Long majorId, Long directId, String tag);

    PageInfo<Product> getAllProductsByPage(String name,int page,int pageSize);

}
