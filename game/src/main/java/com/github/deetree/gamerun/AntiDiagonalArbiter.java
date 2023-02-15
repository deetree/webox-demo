package com.github.deetree.gamerun;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mariusz Bal
 */
final class AntiDiagonalArbiter implements Arbiter {
    private final int maxCells;
    private final Threshold threshold;
    private final Row row;
    private Map<Integer, List<Integer>> possibleAntiDiagonalWinners;

    private AntiDiagonalArbiter(int maxCells, Threshold threshold, int width) {
        this.maxCells = maxCells;
        this.threshold = threshold;
        this.row = new Row(threshold, width);
    }

    static Arbiter of(int maxCells, Threshold threshold, int width) {
        AntiDiagonalArbiter arbiter = new AntiDiagonalArbiter(maxCells, threshold, width);
        arbiter.possibleAntiDiagonalWinners = arbiter.createPossibleAntiDiagonalWinners();
        return arbiter;
    }

    @Override
    public boolean check(Board board, int updatedIndex) {
        return isWinningSequence(possibleAntiDiagonalWinners, updatedIndex, board, threshold);
    }

    private Map<Integer, List<Integer>> createPossibleAntiDiagonalWinners() {
        return findPossibleAntiDiagonalWinnerIndices()
                .stream()
                .collect(Collectors.toMap(n -> n,
                        n -> IntStream.iterate(n, a -> a <= maxCells, a -> a + row.width() - 1)
                                .boxed()
                                .limit(threshold.threshold())
                                .toList()));
    }

    private List<Integer> findPossibleAntiDiagonalWinnerIndices() {
        return IntStream.rangeClosed(1, maxCells)
                .filter(i -> countLastCellInSequence(i) <= maxCells)
                .filter(i -> row.areInNeighbourRows(i, countLastCellInSequence(i)))
                .boxed().toList();
    }

    private int countLastCellInSequence(int i) {
        return i + (threshold.threshold() - 1) * (row.width() - 1);
    }
}
