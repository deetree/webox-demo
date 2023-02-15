package com.github.deetree.gamerun;

import com.github.deetree.Dimensions;
import com.github.deetree.Event;
import com.github.deetree.GameEvent;
import com.github.deetree.Sign;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mariusz Bal
 */
public final class Board {
    private final BoardObserver boardObserver;
    private final Dimensions dimensions;
    private final Map<Integer, Sign> board = new HashMap<>();

    public Board(Dimensions dimensions, BoardObserver boardObserver) {
        this.dimensions = dimensions;
        this.boardObserver = boardObserver;
    }

    /**
     * Puts the character on the board.
     *
     * @param position  position at which the character has to be placed
     * @param character character to be placed at given position
     * @return character that had to be placed on the board,
     * or {@link Sign#DEFAULT} if the board has no more space
     */
    public Event<Sign> put(int position, Sign character) {
        validatePosition(position);
        board.putIfAbsent(position, character);
        if (boardObserver.verify(this, position))
            return new Event<>(GameEvent.WIN, character);
        else if (isBoardFilled())
            return new Event<>(GameEvent.DRAW, Sign.DEFAULT);
        return new Event<>(GameEvent.CONTINUE, character);
    }

    private boolean isBoardFilled() {
        return board.size() >= dimensions.cellCount();
    }

    private boolean validatePosition(int position) {
        return isPositionOccupied(position) && isPositionOutOfRange(position);
    }

    private boolean isPositionOutOfRange(int position) {
        if (position < 1 || position > dimensions.cellCount())
            throw new InvalidPositionException("Position out of range");
        return true;
    }

    private boolean isPositionOccupied(int position) {
        if (board.containsKey(position))
            throw new InvalidPositionException("Position occupied");
        return true;
    }

    /**
     * Checks whether cells under possible win positions have the same character placed.
     *
     * @param cellsForWin indices that are considered as a winning sequence
     * @param threshold   winning sequence length
     * @return {@link Sign#O} or {@link Sign#X} depending on which is the winning one,
     * {@link Sign#DEFAULT} if no winning sequence was found
     */
    Sign isWinningSequence(List<Integer> cellsForWin, int threshold) {
        var charactersAtPositions = cellsForWin.stream()
                .map(board::get)
                .toList();
        if (countFrequency(charactersAtPositions, Sign.O) >= threshold)
            return Sign.O;
        else if (countFrequency(charactersAtPositions, Sign.X) >= threshold)
            return Sign.X;
        return Sign.DEFAULT;
    }

    private int countFrequency(List<Sign> characters, Sign sign) {
        return Collections.frequency(characters, sign);
    }
}
