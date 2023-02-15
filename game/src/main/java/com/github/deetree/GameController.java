package com.github.deetree;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mariusz Bal
 */
@RestController
@RequestMapping("/api/games")
class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    ResponseEntity<String> createGame(@RequestBody Room room, HttpServletRequest request) {
        try {
            String uri = gameService.createGame(room, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(uri);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{gameplayId}")
    ResponseEntity<GameDto> initializeGame(@PathVariable String gameplayId, @RequestBody String user) {
        try {
            return ResponseEntity.ok(gameService.loadGameData(gameplayId, user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
