package com.example.demo.repository;

import com.example.demo.model.Role;

import java.util.List;

public interface RoleRepository {

    Role findById(long id);
    Role findByName(String name);
}

