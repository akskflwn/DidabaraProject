package com.bitcamp221.didabara.util;

import com.bitcamp221.didabara.dto.DefaultRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserErrorHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<DefaultRes> handleException(LoginException e){
        return ResponseEntity.ok().body(DefaultRes.res(e.statusCode,e.responseMessage));

    }
}
