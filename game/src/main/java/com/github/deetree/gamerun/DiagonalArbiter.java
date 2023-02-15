package com.github.deetree.gamerun;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mariusz Bal
 */
final class DiagonalArbiter implements Arbiter {
    private final int maxCells;
    private final Threshold threshold;
    private final Row row;
    private Map<Integer, List<Integer>> possibleDiagonalWinners;

    private DiagonalArbiter(int maxCells, Threshold threshold, int width) {
        this.maxCells = maxCells;
        this.threshold = threshold;
        this.row = new Row(threshold, width);
    }

    static Arbiter of(int maxCells, Threshold threshold, int width) {
        DiagonalArbiter arbiter = new DiagonalArbiter(maxCells, threshold, width);
        arbiter.possibleDiagonalWinners = arbiter.createPossibleDiagonalWinners();
        return arbiter;
    }

    @Override
    public boolean check(Board board, int updatedIndex) {
        return isWinningSequence(possibleDiagonalWinners, updatedIndex, board, threshold);
    }

    private Map<Integer, List<Integer>> createPossibleDiagonalWinners() {
        return findPossibleDiagonalWinnerIndices()
                .stream()
                .collect(Collectors.toMap(n -> n,
                        n -> IntStream.iterate(n, a -> a <= maxCells, a -> a + row.width() + 1)
                                .boxed()
                                .limit(threshold.threshold())
                                .toList()));
    }

    private List<Integer> findPossibleDiagonalWinnerIndices() {
        return IntStream.rangeClosed(1, maxCells)
                .filter(i -> countLastCellInSequence(i) <= maxCells)
                .filter(i -> row.areInNeighbourRows(i, countLastCellInSequence(i)))
                .boxed().toList();
    }

    private int countLastCellInSequence(int startCell) {
        return startCell + (threshold.threshold() - 1) * (row.width() + 1);
    }
}
