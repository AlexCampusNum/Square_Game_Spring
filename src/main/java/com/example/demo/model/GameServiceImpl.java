package com.example.demo.model;

import com.example.demo.GameCatalog;
import com.example.demo.GameService;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    Map<String, Game> dataGames = new HashMap<>();

    @Autowired
    GameCatalog gameCatalog;

    @Override
    public Game createGame(String typeGame, int playerCount, int boardSize) {
        Game game = gameCatalog.getGameFactory(typeGame).createGame(playerCount, boardSize);
        UUID gameId = game.getId();
        String id = gameId.toString();
        dataGames.put(id, game);
        return game;
    }

    @Override
    public Game getGame(String gameId) {
        return dataGames.get(gameId);
    }
}
