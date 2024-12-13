package com.example.demo.controller;

import com.example.demo.GameCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class GameCatalogController {

    @Autowired
    GameCatalog gameCatalog;

    @GetMapping("/game")
    public Collection<String> game() {

        return gameCatalog.getGameIdentifiers();
    }

}
