package com.example.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

// for xml configuration
// import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
public class Main {
    public static void main(String[] args) {
        // Using Java-based configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloService helloService = context.getBean(HelloService.class);
        helloService.sayHelloFromConfig();

        /* This code was for "applicationContext.xml" configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloService helloService = (HelloService) context.getBean("helloService");
        helloService.sayHello();
        */

        PetOwner owner = context.getBean(PetOwner.class);
        owner.playWithPet();    // Cat and dog use @Component, PetOwner uses @Autowired and @Qualifier
        owner.playWithAnotherPet();
    }
}