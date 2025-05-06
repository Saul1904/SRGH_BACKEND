package com.ssma.sgrh.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.User;
import com.ssma.sgrh.repository.UserRepository;



@Service
public class UserService {

    
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        return userRepository.findByUsername(username)
            .filter(user -> user.getPassword().equals(password))
            .orElseThrow(() -> new RuntimeException("⚠️ Credenciales incorrectas."));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public int getTotalEmpleados() {
        return userRepository.countEmpleados();
    }
    
}
