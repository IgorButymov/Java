package com.geekbrains.lesson14.services;


import com.geekbrains.lesson14.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}
