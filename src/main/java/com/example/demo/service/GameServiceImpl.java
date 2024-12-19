package com.example.demo.service;

import com.example.demo.plugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Service
public class GameServiceImpl implements GameService {

    Map<String, Game> dataGames = new HashMap<>();

    @Autowired
    GameCatalog gameCatalog;

    @Autowired
    List<GamePlugin> plugins;

    @Override
    public Game createGame(String typeGame, int playerCount, int boardSize) throws IllegalArgumentException {
        GamePlugin plugin = plugins.stream()
                .filter(p -> p.getDefaultTypeGame().equals(typeGame))
                .findFirst()
                .orElse(null);

        if (plugin == null) {
            throw new IllegalArgumentException("No plugin found for the game type: " + typeGame);
        }

        Game game = plugin.getGameFactory().createGame(plugin.getDefaultPlayerCount(), plugin.getDefaultBoardSize());
        UUID gameId = game.getId();
        String id = gameId.toString();
        dataGames.put(id, game);
        return game;
    }

    @Override
    public Game getGame(String gameId) {
        return dataGames.get(gameId);
    }

    @Override
    public void deleteGame(String gameId){
        dataGames.remove(gameId);
    }

    @Override
    public List<Game> displayGame(){
        List<Game> games = new ArrayList<>();
        games.addAll(dataGames.values());
        return games;
    }

    private static Token getTokenWithName(Game game, String tokenName) {
        return Stream.of(game.getRemainingTokens(), game.getRemovedTokens(), game.getBoard().values())
                .flatMap(Collection::stream)
                .filter(t -> t.getName().equals(tokenName))
                .filter(t -> t.canMove())
                .findFirst()
                .orElse(null);
    }

    public void move(String gameId, String tokenName, int x, int y) throws InvalidPositionException {
        Token token = getTokenWithName(dataGames.get(gameId), tokenName);
        token.moveTo(new CellPosition(x, y));
    }

    @Override
    public List<Game> currentStatus() {
        return dataGames.values().stream()
                .filter(game -> game.getStatus() == GameStatus.ONGOING)
                .collect(Collectors.toList());
    }

    public Collection<CellPosition> possibilityMoves(String gameId, String tokenName, int x, int y){
        Token token = getTokenWithName(dataGames.get(gameId), tokenName);

        return token.getAllowedMoves();
    }

}
