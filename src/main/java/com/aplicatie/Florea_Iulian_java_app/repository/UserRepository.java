package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User findByUsername(String username);
    List<User> findAll();
}
