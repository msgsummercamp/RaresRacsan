package com.example.spring;

import org.springframework.stereotype.Component;

@Component ("cat")
public class Cat implements Animal{
    @Override
    public void makeSound() {
        System.out.println("Meow! Meow!");
    }

    @Override
    public String getName() {
        return "Bomboclat cat";
    }
}
