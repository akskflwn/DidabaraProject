package com.bitcamp221.didabara;


import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestController {

    @Autowired
    UserRepository userRepository;


    @Test
    public void saveUser() {
        UserEntity user = new UserEntity();
        user.setUsername("username1@gmail.com");
        user.setNickname("aaa");
        user.setPassword("12345");
        userRepository.save(user);
    }

}
