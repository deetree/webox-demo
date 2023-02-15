package com.github.deetree;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Mariusz Bal
 */
class Players {
    @JsonProperty
    private final Map<Sign, String> players = new EnumMap<>(Sign.class) {{
        put(Sign.O, "");
        put(Sign.X, "");
    }};

    private String update(Sign sign, String currentPlayer, String newPlayer) {
        if (players.replace(sign, currentPlayer, newPlayer))
            return newPlayer;
        return currentPlayer;
    }

    String placePlayer(Sign sign, String player) {
        update(sign, "", player);
        return player;
    }

    String removePlayer(Sign sign, String player) {
        update(sign, player, "");
        return player;
    }

    Map<Sign, String> peekPlayers() {
        return players;
    }

    boolean areBothPlayersCollected() {
        return freeSeatsCount() == 0;
    }

    int freeSeatsCount() {
        return (int) players.values().stream().filter(String::isBlank).count();
    }
}
