package com.example.authentication.Repositories;

import com.example.authentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByName(String name);
}
