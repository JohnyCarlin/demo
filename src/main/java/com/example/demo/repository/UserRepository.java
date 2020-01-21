package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.Collection;

public interface UserRepository {
    void addNewUser(User user);
    Collection<User> getAllUsers();
    User getUserByID(Long id);
    void removeUser(Long id);
    void editExistingUser(User user);
    User getUserByName(String name);
}
