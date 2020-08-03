package com.fh.loginregister.service;

import com.fh.common.ServerResponse;
import com.fh.loginregister.model.Login;

public interface LoginService {

    ServerResponse queryUserName(String userName);

    ServerResponse queryPhone(String phone);

    ServerResponse addLogin(Login login);

    ServerResponse Login(Login login);
}
