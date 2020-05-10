package com.star.controller;

import com.github.pagehelper.PageInfo;
import com.star.model.api.CommonResult;
import com.star.model.entity.Course;
import com.star.model.entity.Product;
import com.star.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Abner
 * @CreateDate 2020/4/19
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping("/getProducts")
    public CommonResult<PageInfo<Product>> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8")int pageSize,
                                                       Long majorId, Long directId,String tag){
        PageInfo<Product> pageInfo = null;
        pageInfo = productService.getProducts(page,pageSize,null,majorId,directId,tag);
        if(pageInfo!=null){
            return CommonResult.success(pageInfo,"获取作品成功");
        }
        return CommonResult.failed("获取作品失败");
    }
}
