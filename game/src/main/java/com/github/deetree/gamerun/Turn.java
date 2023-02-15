package com.github.deetree.gamerun;

import java.util.random.RandomGenerator;

/**
 * @author Mariusz Bal
 */
public class Turn {
    private final RandomGenerator generator;
    private int playerTurn;

    public Turn(RandomGenerator generator) {
        this.generator = generator;
        this.playerTurn = generator.nextInt(2);
    }

    /**
     * Changes the turn to the other player.
     *
     * @return turn
     */
    public Turn nextTurn() {
        playerTurn++;
        playerTurn %= 2;
        return this;
    }

    /**
     * Informs which player's turn it is.
     *
     * @return current player's ordinal
     */
    public int currentPlayerTurn() {
        return playerTurn;
    }
}
