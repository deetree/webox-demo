package com.github.deetree.gamerun;

import com.github.deetree.Sign;

import java.util.List;
import java.util.Map;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
interface Arbiter {
    /**
     * Checks whether any of the winning sequences has been put on the board.
     *
     * @param possibleWinningSequences map of possible winning sequences
     * @param updatedIndex             index that was updated
     * @param board                    board
     * @param threshold                winning sequence length
     * @return {@code true} if the board contains winning sequence, {@code false} otherwise
     */
    default boolean isWinningSequence(Map<Integer, List<Integer>> possibleWinningSequences, int updatedIndex,
                                      Board board, Threshold threshold) {
        return possibleWinningSequences
                .entrySet()
                .stream()
                .filter(e -> e.getValue().contains(updatedIndex))
                .anyMatch(e -> board.isWinningSequence(e.getValue(), threshold.threshold()) != Sign.DEFAULT);
    }

    /**
     * Implemented by every arbiter for checking the win sequence presence,
     * according to given arbiter's map of winning sequences.
     *
     * @param board        game board
     * @param updatedIndex index of the updated cell
     * @return {@code true} if the winning sequence was found, {@code false} otherwise
     */
    boolean check(Board board, int updatedIndex);
}
