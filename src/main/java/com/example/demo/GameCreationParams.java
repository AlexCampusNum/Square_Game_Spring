package com.example.demo;

import org.springframework.web.bind.annotation.RequestBody;

public class GameCreationParams {
    String typeGame;
    int playerCount;
    int boardSize;

    @PostMapping("/games")
    public String createGame(@RequestBody GameCreationParams params) {
// TODO - actually create a new game
        return UUID.randomUUID().toString();
    }
}
