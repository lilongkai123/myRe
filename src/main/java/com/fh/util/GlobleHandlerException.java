package com.fh.util;

import com.fh.common.ServerResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常捕获
@RestControllerAdvice
public class GlobleHandlerException {

    @ExceptionHandler(MyException.class)
    public ServerResponse handleMyException(Exception e){
        //e.printStackTrace();
        return ServerResponse.loginerror();
    }

    @ExceptionHandler(LoginException.class)
    public ServerResponse handleLoginException(Exception e){
         e.printStackTrace();
        return ServerResponse.loginerror();
    }
}
