package com.example.demo.service;

import com.example.demo.entity.GameEntity;
import com.example.demo.entity.GameTokenEntity;
import com.example.demo.plugin.GamePlugin;
import com.example.demo.repository.GameRepository;
import fr.le_campus_numerique.square_games.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;



@Service
public class JpaGameDao implements GameDao{

    @Autowired
    GameRepository gameRepository;
    List<GamePlugin> plugins;

    @Override
    public Optional<Game> findById(String gameId) {

        return Optional.empty();
    }

    @Override
    public void delete(String gameId) {

    }

    public Map<String, Game> getDataGames(){
        List<GameEntity> result = gameRepository.findAll();

        Map<String, Game> gamesMap = new HashMap<>();

        for(GameEntity gameEntity : result){
            String factoryId = gameEntity.getFactoryId();

            GameFactory gameFactory = null;
            String gameFactoryStringId = gameFactory.getGameFactoryId();
            UUID gameFactoryUUIDId = gameFactoryStringId != null ? UUID.fromString(gameFactoryStringId) : null;


            GamePlugin plugin = plugins.stream()
                    .filter(p -> p.getDefaultTypeGame().equals(factoryId))
                    .findFirst()
                    .orElse(null);

            if (plugin == null) {
                throw new IllegalArgumentException("No plugin found for the game type: " + factoryId);
            }

            int boardSize = gameEntity.getBoardSize();

            String player = gameEntity.getPlayerIds();
            List<UUID> players = jpp(player);

            List<GameTokenEntity> tokens = gameEntity.getTokens();
            var removedTokensEntity = tokens.stream().filter(GameTokenEntity::isRemoved).toList();//2 facon d'écrire la même chose, par contre l'un est le contriare de l'autre !
            var borderTokensEntity = tokens.stream().filter(t -> !t.isRemoved()).toList();//2 facon d'écrire la même chose, par contre l'un est le contriare de l'autre !
            Collection<TokenPosition<UUID>> removedTokens = new ArrayList<>();
            removedTokensEntity.forEach(gameTokenEntity -> removedTokens.add(mapToken(gameTokenEntity)));
            Collection<TokenPosition<UUID>> borderTokens = new ArrayList<>();
            borderTokensEntity.forEach(gameTokenEntity -> borderTokens.add(mapToken(gameTokenEntity)));


            Game game = gameFactory.createGameWithIds(gameFactoryUUIDId, boardSize, players, removedTokens, borderTokens);
            UUID gameId = game.getId();
            String id = gameId.toString();
            gamesMap.put(id, game);

        }

        return gamesMap;
    }

    private List<UUID> jpp (String player){
        List<UUID> players = new ArrayList<>();
        UUID uuid = UUID.fromString(player);
        players.add(uuid);
    }

    @Override
    public void save(String id, Game game) {
        gameRepository.save(map(game));
    }

    private static GameEntity map(Game game) {
        GameEntity gameEntity = new GameEntity();

        gameEntity.setFactoryId(game.getFactoryId());
        gameEntity.setBoardSize(game.getBoardSize());
        gameEntity.setId(game.getId().toString());

        List<GameTokenEntity> tokens = new ArrayList();
        game.getRemovedTokens().forEach(token -> tokens.add(mapToken(token)));
        game.getBoard().values().stream().map(JpaGameDao::mapToken).forEach(tokens::add);


        gameEntity.setTokens(tokens);

        String playerIds = game.getPlayerIds().stream()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
        gameEntity.setPlayerIds(playerIds);

        return gameEntity;
    }

    private static GameTokenEntity mapToken(Token token) {
        GameTokenEntity gameTokenEntity = new GameTokenEntity();
        token.getOwnerId().ifPresent(ownerId->gameTokenEntity.setOwnerId(ownerId.toString()));
        gameTokenEntity.setName(token.getName());
        gameTokenEntity.setRemoved(token.canMove());
        gameTokenEntity.setX(token.getPosition().x());
        gameTokenEntity.setY(token.getPosition().y());

        return null;
    }

    private static TokenPosition<UUID> mapToken(GameTokenEntity gameTokenEntity) {

        TokenPosition<UUID> tokenPosition = new TokenPosition<>(UUID.fromString(gameTokenEntity.getOwnerId()), gameTokenEntity.getName(), gameTokenEntity.getX(), gameTokenEntity.getY());

        return tokenPosition;
    }


}
