package com.bitcamp221.didabara.Exception;


public class LoginException extends RuntimeException{
    String responseMessage;
    int statusCode;

    public LoginException(int statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }
}


