package com.example.demo.controller;

import com.example.demo.entity.GameUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Map<String, Object> getAllUsers() {
    List<Map<String, Object>> users = userService.getAllUsers().stream()
            .map(gameUser -> Map.of(
                    "type", "users",
                    "id", gameUser.getId().toString(),
                    "attributes", Map.of(
                            "name", gameUser.getName(),
                            "email", gameUser.getEmail()
                    )
            ))
            .toList();
        return Map.of("data", users);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable Long id) {
        GameUser gameUser = userService.getUserById(id);
        return Map.of(
                "data", Map.of(
                        "type", "users",
                        "id", gameUser.getId().toString(),
                        "attributes", Map.of(
                                "name", gameUser.getName(),
                                "email", gameUser.getEmail()
                        )
                )
        );
    }

    @PostMapping
    public Map<String, Object> createUser(@RequestBody Map<String, Object> request) {
        Map<String, Object> data = (Map<String, Object>) request.get("data");
        Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
        GameUser gameUser = new GameUser();
        gameUser.setName((String) attributes.get("name"));
        gameUser.setEmail((String) attributes.get("email"));
        GameUser savedGameUser = userService.saveUser(gameUser);
        return Map.of(
                "data", Map.of(
                        "type", "users",
                        "id", savedGameUser.getId().toString(),
                        "attributes", Map.of(
                                "name", savedGameUser.getName(),
                                "email", savedGameUser.getEmail()
                        )
                )
        );
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Map<String, Object> data = (Map<String, Object>) request.get("data");
        Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");

        GameUser existingGameUser = userService.getUserById(id);

        existingGameUser.setName((String) attributes.get("name"));
        existingGameUser.setEmail((String) attributes.get("email"));

        GameUser updatedGameUser = userService.saveUser(existingGameUser);

        return Map.of(
                "data", Map.of(
                        "type", "users",
                        "id", updatedGameUser.getId().toString(),
                        "attributes", Map.of(
                                "name", updatedGameUser.getName(),
                                "email", updatedGameUser.getEmail()
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Map.of("meta", Map.of("message", "Utilisateur supprimé avec succès"));
    }

}