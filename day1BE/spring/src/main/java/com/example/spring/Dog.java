package com.example.spring;

import org.springframework.stereotype.Component;

@Component("dog")
public class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof! Woof!");
    }

    @Override
    public String getName() {
        return "Dodo dog";
    }
}
