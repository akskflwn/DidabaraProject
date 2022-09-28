package com.bitcamp221.didabara.Exception;

import java.io.IOException;

public class KakaoLoginException extends IOException {
    String responseMessage;
    int statusCode;

    public KakaoLoginException(int statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }
}
