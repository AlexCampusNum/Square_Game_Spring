package com.example.demo.service;

import com.example.demo.plugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Service
public class GameCatalogImpl implements GameCatalog {
    @Autowired
    Collection<GamePlugin> gamePlugins;

    public Collection<String> getGameIdentifiers(){
        return gamePlugins.stream()
                .map(plugin -> plugin.getGameName(getLocale()))
                .toList();
    }

}
