package com.example.demo.plugin;

import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TaquinPlugin implements GamePlugin {

    private final GameFactory taquinGameFactory = new TaquinGameFactory();

    @Value("${game.taquin.default-type-game}")
    private String defaultTypeGame;

    @Value("${game.taquin.default-player-count}")
    private int defaultPlayerCount;

    @Value("${game.taquin.default-board-size}")
    private int defaultBoardSize;

    @Autowired
    private MessageSource messageSource;

    public String getGameName(Locale locale) {
        return messageSource.getMessage("game.taquin.name", null, locale);
    }

    public String getDefaultTypeGame(){
        return defaultTypeGame;
    }

    public int getDefaultPlayerCount() {
        return defaultPlayerCount;
    }

    public GameFactory getGameFactory() {
        return taquinGameFactory;
    }

    public int getDefaultBoardSize() {
        return defaultBoardSize;
    }
}
