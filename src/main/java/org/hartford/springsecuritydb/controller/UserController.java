package org.hartford.springsecuritydb.controller;

import org.hartford.springsecuritydb.entity.User;
import org.hartford.springsecuritydb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello from public endpoint!";
    }
    
    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello User!";
    }
    
    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello Admin!";
    }
    
    @PostMapping("/public/register")
    public String registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }
}
