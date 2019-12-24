package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("userRepository")
public interface UserRepository {
    void addNewUser(User user);
    Collection<User> getAllUsers();
    User getUserByID(Long id);
    void removeUser(Long id);
    void editExistingUser(User user);
    User getUserByName(String name);
}
