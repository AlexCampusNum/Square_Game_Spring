package com.example.demo.service;

import fr.le_campus_numerique.square_games.engine.Game;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface GameDao {

    @NotNull Stream<Game> findAll();

    //ok
    Optional<Game> findById(@NotNull String gameId);

    @NotNull Game upsert(@NotNull Game game);

    //ok
    void delete(@NotNull String gameId);

    Map<String, Game> getDataGames();

}
