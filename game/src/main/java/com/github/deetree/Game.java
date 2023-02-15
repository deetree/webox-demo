package com.github.deetree;

import com.github.deetree.gamerun.Board;
import com.github.deetree.gamerun.Turn;

/**
 * @author Mariusz Bal
 */
record Game(String uuid, Room gameRoom, Board board, Turn turn) {

    boolean isGivenPlayerTurn(String player) {
        return gameRoom.players().isPlayerTurn(player, turn);
    }

    Sign findPlayerSign(String player) {
        return gameRoom.players().findPlayerSign(player);
    }

    String switchTurn() {
        return gameRoom.players().findCurrentTurnPlayer(turn.nextTurn());
    }
}
