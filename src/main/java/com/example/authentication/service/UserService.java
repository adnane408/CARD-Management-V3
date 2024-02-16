package com.example.authentication.service;

import com.example.authentication.model.Role;
import com.example.authentication.model.User;

import java.sql.Struct;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(String username, String pwd);
    Role saveRole(String role);
    User getUser(String username);
    public void addRoletoUser(String username, String role);
    User loadUserByUsername(String username);
    List<User> getUsers();
    public boolean verifyOldPassword(String username, String oldPassword);
    public void updatePassword(String username, String newPassword);
}
