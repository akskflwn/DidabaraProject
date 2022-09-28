package com.bitcamp221.didabara.util;

public enum UserErrorCode {


    ALREADY_EXIST_USERNAME("이미 존재하는 아이디입니다."),
    USER_UPDATE_EXCEPTION("회원정보 수정 실패"),
    USER_DELETE_EXCEPTION("회정삭제 실패"),
    DOES_NOT_EXIST_USER("존재하지 않는 회원입니다."),
    DOES_NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다"),
    DOES_NOT_MATCH_USER("회원정보가 일치하지 앖습니다.");


    private String description;

    private UserErrorCode(String description) {

        this.description = description;
    }

}
