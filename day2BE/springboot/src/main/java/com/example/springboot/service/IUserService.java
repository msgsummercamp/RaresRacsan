package com.example.springboot.service;

import com.example.springboot.model.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers(Integer minId);
}
