package com.fh.loginregister.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fh.common.ConstantCommon;
import com.fh.common.ServerResponse;
import com.fh.loginregister.model.Login;
import com.fh.loginregister.service.LoginService;
import com.fh.util.Ignore;
import com.fh.util.MessageVerifyUtils;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    //查询用户名是否存在
    @RequestMapping("queryUserName")
    @Ignore
    public ServerResponse queryUserName(String userName){
        return loginService.queryUserName(userName);
    }

    //查询手机号是否存在
    @RequestMapping("queryPhone")
    @Ignore
    public ServerResponse queryPhone(String phone){
        return loginService.queryPhone(phone);
    }

    //调用短信工具类发送短信 放入redis中
    @RequestMapping("code")
    @Ignore
    public ServerResponse code(String phone){
        ServerResponse serverResponse = loginService.queryPhone(phone);
        if(serverResponse.getCode() == 200){
            try {
                String newcode = MessageVerifyUtils.getNewcode();
                MessageVerifyUtils.sendSms(phone,newcode);
                RedisUtil.set(phone,newcode);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }else {
            return ServerResponse.error();
        }
        return ServerResponse.success();
    }

    //添加用户信息
    @RequestMapping("register")
    @Ignore
    public ServerResponse addLogin(Login login){
        return loginService.addLogin(login);
    }

    //登录
    @RequestMapping("Login")
    @Ignore
    public ServerResponse Login(Login login){
        return loginService.Login(login);
    }

    //退出
    @RequestMapping("out")
    @Ignore
    public ServerResponse out(HttpServletRequest request){
        //删除redis中token的信息
        String token = (String) request.getSession().getAttribute(ConstantCommon.TEMPLATE_TOKEN_KEY);
        RedisUtil.del(token);
        request.getSession().removeAttribute(ConstantCommon.TEMPLATE_TOKEN_KEY);
        //删除session中用户的信息
        request.getSession().removeAttribute(ConstantCommon.TEMPLATE_SUCCESS_PATH);
        return ServerResponse.success();
    }

    @RequestMapping("checkLogin")
    @Ignore
    public ServerResponse checkLogin(HttpServletRequest request){
        Login login = (Login) request.getSession().getAttribute(ConstantCommon.TEMPLATE_SUCCESS_PATH);
        if (login == null){
            return ServerResponse.error();
        }
        return ServerResponse.success();
    }

}
