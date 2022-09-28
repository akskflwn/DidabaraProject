package com.bitcamp221.didabara.Exception;

import com.bitcamp221.didabara.Exception.LoginException;
import com.bitcamp221.didabara.dto.DefaultRes;
import com.bitcamp221.didabara.util.ResponseMessage;
import com.bitcamp221.didabara.util.StatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class UserErrorHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity handleLoginException(LoginException e){
        return ResponseEntity.ok().body(DefaultRes.res(e.statusCode,e.responseMessage));

    }
    @ExceptionHandler(KakaoLoginException.class)
    public ResponseEntity handleKakaoLoginException(KakaoLoginException e){
        return ResponseEntity.ok().body(DefaultRes.res(e.statusCode,e.responseMessage));
    }

}
