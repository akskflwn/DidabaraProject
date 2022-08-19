package com.bitcamp221.didabara;

import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DidabaraApplication {

  public static void main(String[] args) {
    SpringApplication.run(DidabaraApplication.class, args);
  }

}
