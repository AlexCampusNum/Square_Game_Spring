package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserDAO userDAO;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userDAO.getUserById(id);
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userDAO.addUser(user);
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        userDAO.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userDAO.deleteUser(id);
    }

}
