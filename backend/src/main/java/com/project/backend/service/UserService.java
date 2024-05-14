package com.project.backend.service;

import com.project.backend.model.User;

import java.util.List;

public interface UserService  {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);

    User getUserByEmail(String email);
}
