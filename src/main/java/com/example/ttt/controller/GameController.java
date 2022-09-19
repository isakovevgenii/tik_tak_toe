package com.example.ttt.controller;

import com.example.ttt.entity.model.Game;
import com.example.ttt.entity.model.GameUpdating;
import com.example.ttt.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controllers class. Contains all endpoints of the application.
 */
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Endpoint that returns all games.
     *
     * @return json with all games
     */
    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames() {
        return gameService.getAllGames();
    }

    /**
     * Endpoint through which you can create a new game
     *
     * @param newGame The json string which present new game object
     * @return json with new game
     */
    @PostMapping("/add")
    public ResponseEntity<Game> createGame(@RequestBody Game newGame) {
        return gameService.saveGame(newGame);
    }

    /**
     * Updates the state of the game
     *
     * @param gameUpdating Game update information
     * @return json with updated game
     */
    @PutMapping("/update")
    public ResponseEntity<Game> updateGame(@RequestBody GameUpdating gameUpdating) {
        return gameService.updateGame(gameUpdating);
    }
}