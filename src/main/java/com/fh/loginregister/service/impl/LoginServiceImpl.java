package com.fh.loginregister.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ConstantCommon;
import com.fh.common.ServerResponse;
import com.fh.loginregister.mapper.LoginMapper;
import com.fh.loginregister.model.Login;
import com.fh.loginregister.service.LoginService;
import com.fh.util.JwtUtils;
import com.fh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public ServerResponse queryUserName(String userName) {
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName", userName);
        Login login = loginMapper.selectOne(queryWrapper);
        if (login == null){
            return ServerResponse.success();
        }
        return ServerResponse.error("该用户已存在");
    }

    @Override
    public ServerResponse queryPhone(String phone){
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        Login login = loginMapper.selectOne(queryWrapper);
        if (login == null){
            return ServerResponse.success();
        }
        return ServerResponse.error("手机号已存在");
    }

    @Override
    public ServerResponse addLogin(Login login) {
        String redisCode = RedisUtil.get(login.getPhone());
        if (!redisCode.equals(login.getCode())){
            return ServerResponse.error();
        }
        loginMapper.insert(login);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse Login(Login login) {
        QueryWrapper<Login> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName", login.getUserName());
        queryWrapper.or();
        queryWrapper.eq("phone", login.getUserName());
        Login loginDB = loginMapper.selectOne(queryWrapper);
        if (loginDB == null){
            return ServerResponse.error("用户名或者密码不存在");
        }
        if (!login.getPassword().equals(loginDB.getPassword())){
            return ServerResponse.error("密码不正确");
        }
        String token = null;
        try {
            String loginDBString = JSONObject.toJSONString(loginDB);
            token = JwtUtils.sign(loginDBString);
            RedisUtil.set(token,token, ConstantCommon.TEMPLATE_TOKEN_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.success(token);
    }
}
