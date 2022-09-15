package com.example.cheongju_university.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {



//  Spring security의 예외를 처리 해주는 기능
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {

    return (web -> web.ignoring().antMatchers("/**"));
  }
}
