package com.github.deetree.gamerun;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
interface BoardObserver {
    /**
     * Informs about board change.
     *
     * @param board    board
     * @param position position changed
     * @return {@code true} if there was a winning sequence in any direction,
     * {@code false} otherwise
     */
    boolean verify(Board board, int position);
}
