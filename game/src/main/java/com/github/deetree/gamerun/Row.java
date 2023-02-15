package com.github.deetree.gamerun;

/**
 * @author Mariusz Bal
 */
record Row(Threshold threshold, int width) {

    /**
     * Checks whether first and last cell of the sequence are in consecutive rows.
     *
     * @param firstCell first cell position
     * @param lastCell  last cell position
     * @return {@code true} if the cells are placed in consecutive rows,
     * {@code false} otherwise
     */
    boolean areInNeighbourRows(int firstCell, int lastCell) {
        return calculateRowNumber(lastCell) - calculateRowNumber(firstCell) == (threshold.threshold() - 1);
    }

    /**
     * Calculates cell's row
     *
     * @param cellNumber cell position on board
     * @return row number
     */
    int calculateRowNumber(int cellNumber) {
        int remainder = cellNumber % width;
        int quotient = cellNumber / width;
        if (remainder > 0)
            return quotient + 1;
        return quotient;
    }
}
