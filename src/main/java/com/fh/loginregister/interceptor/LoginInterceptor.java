package com.fh.loginregister.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.ConstantCommon;
import com.fh.loginregister.model.Login;
import com.fh.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //处理客户端传过来的自定义信息
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,mtoken,content-type");
        //处理客户端发过来 put,delete
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"PUT.POST,DELETE,GET");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method =  handlerMethod.getMethod();
        if (method.isAnnotationPresent(Ignore.class)){
            return true;
        }
        //进行登录认证
        String token =request.getHeader("x-auth");
        if (StringUtils.isEmpty(token)){
            throw new LoginException();
        }
        //验证token失效
        Boolean tokenExists = RedisUtil.exists(token);
        if (!tokenExists){
            throw new MyException();
        }
        boolean verify = JwtUtils.verify(token);
        if (verify){
            String userName = JwtUtils.getUser(token);
            String decodeName = URLDecoder.decode(userName, "utf-8");
            Login login = JSONObject.parseObject(decodeName, Login.class);
            request.getSession().setAttribute(ConstantCommon.TEMPLATE_SUCCESS_PATH,login);
            request.getSession().setAttribute(ConstantCommon.TEMPLATE_TOKEN_KEY,token);
        }else {
            throw new MyException();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
