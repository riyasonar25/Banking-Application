package com.bank.service;

import com.bank.model.User;
import com.bank.repository.UserRepository;

public class AuthService {

    private UserRepository repo = new UserRepository();

    public void register(User user) {
        try {
            repo.register(user);
            System.out.println("Registration successful!");
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
    
    
    public User login(String email, String password) {
        try {
            User user = repo.login(email, password);
            if (user == null) {
                System.out.println("Invalid email or password");
            }
            return user;
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return null;
        }
    }
}
