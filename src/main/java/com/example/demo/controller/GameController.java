package com.example.demo.controller;

import com.example.demo.GameCatalog;
import com.example.demo.GameService;
import com.example.demo.model.GameCreationParams;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    @PostMapping("/games")
    public Game createGame(@RequestBody GameCreationParams params) {
        return gameService.createGame(params.typeGame(), params.playerCount(), params.boardSize());
    }

    @GetMapping("/games/{gameId}")
    public Object getGame(@PathVariable String gameId) {

        return gameService.getGame(gameId);
    }



}

