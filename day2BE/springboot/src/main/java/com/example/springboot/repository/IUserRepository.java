package com.example.springboot.repository;

import com.example.springboot.model.User;

import java.util.List;

public interface IUserRepository {
    List<User> getUsers();
}
