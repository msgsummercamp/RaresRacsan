package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PetOwner {
    @Autowired
    @Qualifier("dog")
    private Animal pet;

    public void playWithPet() {
        if (pet != null) {
            System.out.println("Playing with my pet: " + pet.getName());
            pet.makeSound();
        } else {
            System.out.println("No pet found to play with.");
        }
    }

    private Animal anotherPet;

    @Autowired
    @Qualifier("cat")
    public void setAnotherPet(Animal pet) {
        this.anotherPet = pet;
    }

    public void playWithAnotherPet() {
        if (anotherPet != null) {
            System.out.println("Playing with my another pet: " + anotherPet.getName());
            anotherPet.makeSound();
        } else {
            System.out.println("No another pet found to play with.");
        }
    }
}
