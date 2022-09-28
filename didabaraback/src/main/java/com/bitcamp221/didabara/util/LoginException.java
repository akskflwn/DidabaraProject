package com.bitcamp221.didabara.util;


public class LoginException extends RuntimeException{
    String responseMessage;
    int statusCode;

    public LoginException(int errorCode, String responseMessage) {
        this.responseMessage = responseMessage;
        this.statusCode = errorCode;
    }
}
