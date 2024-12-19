package com.example.demo.controller;

import com.example.demo.service.GameMoveParam;
import com.example.demo.service.GameService;
import com.example.demo.service.GameCreationParams;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;


@RestController
public class GameController {

    @Autowired
    GameService gameService;

    //Créer une partie
    @PostMapping("/games")
    public Game createGame(@RequestBody GameCreationParams params) throws IllegalArgumentException {
        return gameService.createGame(params.typeGame(), params.playerCount(), params.boardSize());
    }

    //Afficher une partie par son Id
    @GetMapping("/games/{gameId}")
    public Object getGame(@PathVariable String gameId) {
        return gameService.getGame(gameId);
    }

    //Supprimer une partie
    @DeleteMapping("/delete/{gameId}")
    public void deleteGame(@PathVariable String gameId) {
        gameService.deleteGame(gameId);
    }

    //Afficher toutes les parties
    @GetMapping("/games")
    public List<Game> getGames() {
        return gameService.displayGame();
    }

    //Jouer un coup
    @PutMapping("/games/{gameId}/token/{tokenName}/position")
    public void moveToken(@PathVariable String gameId, @PathVariable String tokenName, @RequestBody GameMoveParam param) {
        try {
            gameService.move(gameId, tokenName, param.x(), param.y());
        } catch (InvalidPositionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //Ressortir les parties en cours
    @GetMapping("/games/filter")
    public List<Game> filterGames() {
        return gameService.currentStatus();
    }

    //Connaître la liste des coups possibles
    @GetMapping("/games/{gameId}/token/{tokenName}/possibility")
    public Collection<CellPosition> possibilityMoves(@PathVariable String gameId, @PathVariable String tokenName, @RequestBody GameMoveParam param) {
        return gameService.possibilityMoves(gameId, tokenName, param.x(), param.y());
    }


}

