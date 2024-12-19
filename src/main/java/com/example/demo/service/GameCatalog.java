package com.example.demo.service;

import fr.le_campus_numerique.square_games.engine.GameFactory;

import java.util.Collection;

public interface GameCatalog {
    public Collection<String> getGameIdentifiers();
}
