package com.example.demo.service;


//DTO
public record GameCreationParams(
        String typeGame,
        int playerCount,
        int boardSize
) {
}
