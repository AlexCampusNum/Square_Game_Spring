package com.example.demo.service;

public record GameCreationParams(
        String typeGame,
        int playerCount,
        int boardSize
) {
}
