package com.github.deetree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.deetree.gamerun.Turn;

import java.util.EnumMap;
import java.util.Map;

import static com.github.deetree.GameplayRole.PARTICIPANT;
import static com.github.deetree.GameplayRole.SPECTATOR;

/**
 * @author Mariusz Bal
 */
class Players {

    @JsonProperty
    private final Map<Sign, String> players = new EnumMap<>(Sign.class);

    GameplayRole assignRole(String user) {
        return players.values().stream().anyMatch(s -> s.equals(user)) ? PARTICIPANT : SPECTATOR;
    }

    Sign findPlayerSign(String player) {
        return findPlayerEntryByName(player).getKey();
    }

    boolean isPlayerTurn(String player, Turn turn) {
        return findPlayerSign(player).ordinal() == turn.currentPlayerTurn();
    }

    private Map.Entry<Sign, String> findPlayerEntryByName(String player) {
        return players
                .entrySet()
                .stream()
                .filter(e -> e.getValue().equals(player))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("The user provided is not this gameplay participant"));
    }

    String findPlayerBySign(Sign sign) {
        return players.get(sign);
    }

    String findCurrentTurnPlayer(Turn turn) {
        return findPlayerBySign(players
                .entrySet()
                .stream()
                .filter(e -> e.getKey().ordinal() == turn.currentPlayerTurn())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid turn value"))
                .getKey());
    }
}
