package com.example.authentication.service;

import com.example.authentication.Repositories.RoleRepository;
import com.example.authentication.Repositories.UserRepository;
import com.example.authentication.model.Role;
import com.example.authentication.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@Transactional
@AllArgsConstructor
@Data
public class UserServiceImp implements UserService{
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(String username, String pwd) {
        User appUser=userRepo.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("This User Already Exist");

        User user= User.builder().
                username(username)
                .password(passwordEncoder.encode(pwd))
                .role(new Role("USER"))
                .build();
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(String role) {
        return roleRepo.save(Role.builder().name(role).build());
    }


    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }
    public void addRoletoUser(String username, String role) {
        User appUser=userRepo.findByUsername(username);
        Role appRole=roleRepo.findByName(role);
        appUser.setRole(appRole);
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    public boolean verifyOldPassword(String username, String oldPassword) {
        // Retrieve the user by username from your data source
        User user = getUser(username);

        // Check if the user exists
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Use BCryptPasswordEncoder to verify the old password
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
    @Override
    public void updatePassword(String username, String newPassword) {
            User user = getUser(username);
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
    }
}
