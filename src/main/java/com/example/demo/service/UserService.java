package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Collection;

public interface UserService {
    User findUserByName(String name);
    void addNewUser(User user);
    Collection<User> getAllUsers();
    User getUserByID(Long id);
    void removeUser(Long id);
    void editExistingUser(User user);
}
