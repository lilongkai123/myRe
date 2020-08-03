package com.fh.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.order.service.order.OrderService;
import com.fh.util.Idempotent;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orderController")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("paymentOrder")
    @Idempotent
    public ServerResponse paymentOrder(String stringCart, Integer payType, Integer addressId, HttpServletRequest request){
        List<Cart> cartList = null;
        if (StringUtils.isNotEmpty(stringCart)){
            cartList = JSONObject.parseArray(stringCart, Cart.class);
        }else {
            ServerResponse.error("没有商品需要支付");
        }
        return orderService.paymentOrder(cartList,payType,addressId,request);
    }

    @RequestMapping("getToken")
    public ServerResponse getToken(){

        String metoken = UUID.randomUUID().toString();
        RedisUtil.set(metoken,metoken);
        return ServerResponse.success(metoken);
    }

}
