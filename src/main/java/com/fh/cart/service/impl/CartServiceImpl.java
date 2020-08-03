package com.fh.cart.service.impl;

import com.fh.cart.model.Cart;
import com.fh.cart.service.CartService;
import com.alibaba.fastjson.JSONObject;
import com.fh.common.ConstantCommon;
import com.fh.common.ServerResponse;
import com.fh.common.meiju;
import com.fh.loginregister.model.Login;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductService productService;

    public ServerResponse buy(Integer productId,Integer count,HttpServletRequest request){

        //判断是否有这个商品
        Product product = productService.selectProundById(productId);
        if (product == null){
            return ServerResponse.error(meiju.PRODUCT_NOT_EXIST);
        }
        //判断这个商品是否上架
        if (product.getStatus() == 0){
            return ServerResponse.error(meiju.PRODUCT_NOT_GROUNDING);
        }
        Login login = (Login)request.getSession().getAttribute(ConstantCommon.TEMPLATE_SUCCESS_PATH);
        boolean exists = RedisUtil.hExists(ConstantCommon.TEMPLATE_CART_KEY+login.getId(), productId.toString());
        if (!exists){
            Cart cart = new Cart();
            cart.setName(product.getName());
            cart.setCount(count);
            cart.setPrice(product.getPrice());
            cart.setFilePath(product.getFilePath());
            cart.setProductId(productId);
            String jsonString = JSONObject.toJSONString(cart);
            RedisUtil.hSet(ConstantCommon.TEMPLATE_CART_KEY+login.getId(),productId.toString(),jsonString);
        }else{
            String productJson = RedisUtil.hGet(ConstantCommon.TEMPLATE_CART_KEY + login.getId(), productId.toString());
            Cart cart = JSONObject.parseObject(productJson, Cart.class);
            cart.setCount(cart.getCount()+count);
            String jsonString = JSONObject.toJSONString(cart);
            RedisUtil.hSet(ConstantCommon.TEMPLATE_CART_KEY+login.getId(),productId.toString(),jsonString);
        }
        return ServerResponse.success();

    }


}
