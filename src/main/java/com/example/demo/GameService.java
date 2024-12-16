package com.example.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;

public interface GameService {

    Game createGame(String typeGame, int playerCount, int boardSize);

    Game getGame(String gameId);


}
