package com.example.authentication.service;

import com.example.authentication.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class UserDetailsImpl implements UserDetailsService {

        private final UserService userService;
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User appUser= userService.loadUserByUsername(username);
            String userRole = appUser.getRole().getName();
            UserDetails userDetails= org.springframework.security.core.userdetails.User.withUsername(appUser.getUsername())
                    .password(appUser.getPassword())
                    .roles(userRole).build();
            return userDetails;
        }
    }

