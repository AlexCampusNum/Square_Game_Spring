package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Map<String, Object> getAllUsers() {
    List<Map<String, Object>> users = userService.getAllUsers().stream()
            .map(user -> Map.of(
                    "type", "users",
                    "id", user.getId().toString(),
                    "attributes", Map.of(
                            "name", user.getName(),
                            "email", user.getEmail()
                    )
            ))
            .toList();
        return Map.of("data", users);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return Map.of(
                "data", Map.of(
                        "type", "users",
                        "id", user.getId().toString(),
                        "attributes", Map.of(
                                "name", user.getName(),
                                "email", user.getEmail()
                        )
                )
        );
    }

    @PostMapping
    public Map<String, Object> createUser(@RequestBody Map<String, Object> request) {
        Map<String, Object> data = (Map<String, Object>) request.get("data");
        Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
        User user = new User();
        user.setName((String) attributes.get("name"));
        user.setEmail((String) attributes.get("email"));
        User savedUser = userService.saveUser(user);
        return Map.of(
                "data", Map.of(
                        "type", "users",
                        "id", savedUser.getId().toString(),
                        "attributes", Map.of(
                                "name", savedUser.getName(),
                                "email", savedUser.getEmail()
                        )
                )
        );
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Map.of("meta", Map.of("message", "Utilisateur supprimé avec succès"));
    }

}