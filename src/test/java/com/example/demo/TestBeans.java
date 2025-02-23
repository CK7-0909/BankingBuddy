package com.example.demo;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestBeans {

    @Bean
    public UserServiceTest userServiceTest() {
        return Mockito.mock(UserServiceTest.class);
    };
}
