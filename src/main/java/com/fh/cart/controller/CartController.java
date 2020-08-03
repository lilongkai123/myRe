package com.fh.cart.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.cart.service.CartService;
import com.fh.common.ConstantCommon;
import com.fh.common.ServerResponse;
import com.fh.loginregister.model.Login;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("CartController")
public class CartController {

    @Autowired
    private CartService cartService;

    //添加购入车到redis
    @RequestMapping("buy")
    public ServerResponse buy(Integer productId,Integer count,HttpServletRequest request){
        return cartService.buy(productId,count,request);
    }

    //查询购物车里面的数量
    @RequestMapping("queryCartProductCount")
    public ServerResponse queryCartProductCount(HttpServletRequest request){
        //获取登陆用户的信息
        Login loginSession = (Login) request.getSession().getAttribute(ConstantCommon.TEMPLATE_SUCCESS_PATH);
        //获取redis里面的值的信息
        List<String> loginList = RedisUtil.hget(ConstantCommon.TEMPLATE_CART_KEY + loginSession.getId());
        long totalCount = 0;
        //如果这个值不为空并且这个数组的长度大于0
        if (loginList !=null && loginList.size()>0){
            for (String str : loginList) {
                //将对象转换为JSON类型的对象
                Cart cart = JSONObject.parseObject(str, Cart.class);
                totalCount +=cart.getCount();
            }
        }else {
            return ServerResponse.success(0);
        }

        return ServerResponse.success(totalCount);
    }
    //查询集合
    @RequestMapping("queryList")
    public ServerResponse queryList( HttpServletRequest request){
        //获取登陆用户的信息
        Login loginSession = (Login) request.getSession().getAttribute(ConstantCommon.TEMPLATE_SUCCESS_PATH);
        //获取redis里面的值的信息
        List<String> loginList = RedisUtil.hget(ConstantCommon.TEMPLATE_CART_KEY + loginSession.getId());
        List<Cart> list = new ArrayList<>();
        if (loginList.size()>0 && loginList!=null){
            for (String s : loginList) {
                Cart cart = JSONObject.parseObject(s, Cart.class);
                list.add(cart);
            }
        }else {
            return ServerResponse.error();
        }
        return ServerResponse.success(list);
    }

    //删除
    @RequestMapping("deleteCart/{productId}")
    public ServerResponse deleteCart(HttpServletRequest request,@PathVariable("productId") Integer productId){
        //获取登陆用户的信息
        Login loginSession = (Login) request.getSession().getAttribute(ConstantCommon.TEMPLATE_SUCCESS_PATH);
        RedisUtil.hdel(ConstantCommon.TEMPLATE_CART_KEY + loginSession.getId(),productId.toString());
        return ServerResponse.success();
    }
    //批量删除
    @RequestMapping("deleteBatch")
    public ServerResponse deleteBatch(HttpServletRequest request,@RequestParam("idList") List<Integer> idList){
        //获取登陆用户的信息
        Login loginSession = (Login) request.getSession().getAttribute(ConstantCommon.TEMPLATE_SUCCESS_PATH);
        for (Integer productId : idList) {
            RedisUtil.hdel(ConstantCommon.TEMPLATE_CART_KEY +loginSession.getId(),productId.toString());
        }
        return ServerResponse.success();
    }
}
