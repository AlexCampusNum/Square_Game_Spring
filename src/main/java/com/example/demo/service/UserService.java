package com.example.demo.service;

import com.example.demo.entity.GameUser;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<GameUser> getAllUsers() {
        return userRepository.findAll();
    }

    public GameUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public GameUser saveUser(GameUser gameUser) {
        return userRepository.save(gameUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
