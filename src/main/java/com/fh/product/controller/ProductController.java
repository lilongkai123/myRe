package com.fh.product.controller;

import com.fh.common.ServerResponse;
import com.fh.product.service.ProductService;
import com.fh.util.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ProductController")
public class ProductController {

    @Autowired
    private ProductService productService;

    //查询热销商品的图片
    @RequestMapping("queryFileNameList")
    @Ignore
    public ServerResponse queryFileNameList(){
        return productService.queryFileNameList();
    }

    //查询所有的数据
    @RequestMapping("queryList")
    @Ignore
    public ServerResponse queryList(){
        return productService.queryList();
    }

    //查询分页所有数据
    @RequestMapping("queryProductListPage")
    @Ignore
    public ServerResponse queryProductListPage(long currentPage ,long pageSize){
        return productService.queryProductListPage(currentPage,pageSize);
    }

}
