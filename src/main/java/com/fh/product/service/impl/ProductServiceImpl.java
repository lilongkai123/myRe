package com.fh.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ServerResponse;
import com.fh.product.mapper.ProductMapper;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public ServerResponse queryFileNameList() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isHot",1);
        List<Product> productsFileNameList = productMapper.selectList(queryWrapper);
        return ServerResponse.success(productsFileNameList);
    }

    @Override
    public ServerResponse queryList() {
        List<Product> productsList = productMapper.selectList(null);
        return ServerResponse.success(productsList);
    }

    @Override
    public ServerResponse queryProductListPage(long currentPage, long pageSize) {
        //当前页数-1 乘于条数
        long start = (currentPage-1)*pageSize;
        //查询总条数
        long totalCount = productMapper.queryTotalCount();
        List<Product> list = productMapper.queryList(start,pageSize);
        long totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        Map map = new HashMap<>();
        map.put("list",list);
        map.put("totalPage",totalPage);
        return ServerResponse.success(map);
    }

    @Override
    public Product selectProundById(Integer productId){

        Product product = productMapper.selectById(productId);

        return product;
    }

    @Override
    public Long updateStock(int id, int count) {
        return productMapper.updateStock(id,count);
    }
}
