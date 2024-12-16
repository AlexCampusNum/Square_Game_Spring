package com.example.demo.model;

import com.example.demo.GameCatalog;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GameCatalogImpl implements GameCatalog {
    GameFactory ticTacToeGameFactory;
    GameFactory taquinGameFactory;
    GameFactory connectFourGameFactory;


    public GameCatalogImpl() {
        ticTacToeGameFactory = new TicTacToeGameFactory();
        taquinGameFactory = new TaquinGameFactory();
        connectFourGameFactory = new ConnectFourGameFactory();
    }

    public Collection<String> getGameIdentifiers(){
        return List.of(ticTacToeGameFactory.getGameFactoryId());
    }

    public GameFactory getGameFactory(String typeGame){
        GameFactory gameFactory = switch (typeGame) {
            case "tictactoe" -> ticTacToeGameFactory;
            case "taquin" -> taquinGameFactory;
            case "connectfour" -> connectFourGameFactory;
            default -> null;
        };

        return gameFactory;
    }

}
