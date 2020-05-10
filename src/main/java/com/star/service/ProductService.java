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
@Service
public class ProductService {
    @Autowired
    ProductDao productDao;

    String Sort_hot = "product_like desc";

    public Product getHotestProduct(){
        Example example = new Example(Product.class);
        example.setOrderByClause(Sort_hot);
        List<Product> list = productDao.selectByExample(example);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public PageInfo<Product> getProducts(int page, int pageSize,String sort,Long majorId,Long directId,String tag){
        PageHelper.startPage(page,pageSize);
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        if(majorId!=null){
            criteria.andCondition("direct_id in (select id from major_direct where major_id="+majorId+")");
        }
        if(directId!=null){
            criteria.andEqualTo("directId",directId);
        }
        if(tag != null){
            criteria.andLike("productTag","%"+tag+"%");
        }

        //设置排序方式
        if(!StringUtils.isEmpty(sort)) {
            example.setOrderByClause(sort);
        }else{
            example.setOrderByClause("product_createdate desc");
        }

        List<Product> list = productDao.selectByExample(example);
        PageInfo<Product> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

}
