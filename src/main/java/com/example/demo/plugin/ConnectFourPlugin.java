package com.example.demo.plugin;

import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ConnectFourPlugin implements GamePlugin {

    private final GameFactory connectFourGameFactory = new ConnectFourGameFactory();

    @Value("${game.connectfour.default-type-game}")
    private String defaultTypeGame;

    @Value("${game.connectfour.default-player-count}")
    private int defaultPlayerCount;

    @Value("${game.connectfour.default-board-size}")
    private int defaultBoardSize;

    @Autowired
    private MessageSource messageSource;

    public String getGameName(Locale locale) {
        return messageSource.getMessage("game.connectfour.name", null, locale);
    }

    public String getDefaultTypeGame(){
        return defaultTypeGame;
    }

    public int getDefaultPlayerCount() {
        return defaultPlayerCount;
    }

    public GameFactory getGameFactory() {
        return connectFourGameFactory;
    }

    public int getDefaultBoardSize() {
        return defaultBoardSize;
    }
}
