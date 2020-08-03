package com.fh.product.service;

import com.fh.common.ServerResponse;
import com.fh.product.model.Product;

public interface ProductService {

    ServerResponse queryFileNameList();

    ServerResponse queryList();

    ServerResponse queryProductListPage(long currentPage, long pageSize);

    Product selectProundById(Integer productId);

    Long updateStock(int id, int count);

}
