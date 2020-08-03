package com.fh.loginregister.interceptor;

import com.fh.util.Idempotent;
import com.fh.util.MyException;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

public class IdempotentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method =  handlerMethod.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)){
            return true;
        }

        String metoken = request.getHeader("metoken");
        if (StringUtils.isEmpty(metoken)){
            throw new MyException("没有metoken");
        }

        Boolean existss = RedisUtil.exists(metoken);
        if (!existss){
            throw new MyException("metoken失效");
        }
        Long del = RedisUtil.del(metoken);
        if (del == 0){
            throw new MyException("重复下单");
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
