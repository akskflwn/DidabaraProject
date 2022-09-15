package com.example.cheongju_university.security;

import com.example.cheongju_university.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@Component
public class TokenProvider {

//  토큰 생성에 사용될 시크릿 키
  private static final String SECRET_KEY = "auewiogerietognjbkbgjioj5490j9e5itBSJTIJGODFJBG945gujirgjrgwg5";

  Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

//  사용자 정보를 받아서 JWT 토큰을 생성.
  public String create(final User user) {
//    토큰 유호 기한 설정
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

//    JWT 토큰 생성
    return Jwts.builder()
//            해더(header)에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
            .signWith(key, SignatureAlgorithm.HS256)
//            페이로드(payload)에 들어갈 내용
            .setSubject(user.getId().toString())
//            공부하기
            .setIssuer("cheongju_university")
//            현재 시간을 셋팅
            .setIssuedAt(new Date())
//            토큰 생성이 된 후 적용된 시간과 비교할 만료 시간
            .setExpiration(expiryDate)
            .compact();
  }

//  사용자로부터 토큰을 받아와 해당 토큰을 가진 사용자의 id값을 추출.
//  토큰을 디코딩 및 파싱하여 토큰의 위조 여뷰를 파악
  public String validateAndGetUserId(final String token) {
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody();

    return claims.getSubject();
  }
}
