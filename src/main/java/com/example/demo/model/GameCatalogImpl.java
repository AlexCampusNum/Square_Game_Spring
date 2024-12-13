package com.example.demo.model;

import com.example.demo.GameCatalog;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GameCatalogImpl implements GameCatalog {
    TicTacToeGameFactory ticTacToeGameFactory;


    public GameCatalogImpl() {
        ticTacToeGameFactory = new TicTacToeGameFactory();
    }

    public Collection<String> getGameIdentifiers(){

        return List.of(ticTacToeGameFactory.getGameFactoryId());
    }

}
