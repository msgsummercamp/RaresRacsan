package com.example.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.spring")
public class AppConfig {
    @Bean
    public HelloService helloService() {
        return new HelloService();
    }

    @Bean
    public Dog dog() {
        return new Dog();
    }
}
