package com.example.demo.model;

public record GameCreationParams(
        String typeGame,
        int playerCount,
        int boardSize
) {
}
