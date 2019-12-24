package com.example.demo.repository;

import com.example.demo.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roleRepository")
public interface RoleRepository {
//    Role findByEmail(String email);
    List<Role> findById(long id);
}

