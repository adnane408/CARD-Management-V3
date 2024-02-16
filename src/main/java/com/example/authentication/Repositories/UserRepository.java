package com.example.authentication.Repositories;

import com.example.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);

}
