package com.github.deetree;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mariusz Bal
 */
@Component
class Games {

    private final Collection<Game> games = new ArrayList<>();

    Game create(Game game) {
        if (gameDoesNotExist(game.uuid())) {
            games.add(game);
            return game;
        }
        throw new IllegalArgumentException("Cannot create the gameplay");
    }

    private boolean gameDoesNotExist(String uuid) {
        return games.stream().map(Game::uuid).noneMatch(g -> g.equals(uuid));
    }

    Game findGame(String gameplayId) {
        return games
                .stream()
                .filter(g -> g.uuid().equals(gameplayId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid gameplayId"));
    }

    Event<Sign> play(Game game, CellClickAction action) {
        String player = action.playerName();
        if (game.isGivenPlayerTurn(player))
            return game.board().put(action.cellId(), game.findPlayerSign(player));
        else
            throw new IllegalArgumentException("This is not %s's turn".formatted(player));
    }

    String switchTurn(Game game) {
        return game.switchTurn();
    }
}
