package com.example.demo.repository;

import com.example.demo.model.Role;

import java.util.List;

public interface RoleRepository {
//    Role findByEmail(String email);
    List<Role> findById(long id);
}

