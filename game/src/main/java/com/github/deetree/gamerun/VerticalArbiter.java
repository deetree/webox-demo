package com.github.deetree.gamerun;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mariusz Bal
 */
final class VerticalArbiter implements Arbiter {
    private final int maxCells;
    private final Threshold threshold;
    private final Row row;
    private Map<Integer, List<Integer>> possibleVerticalWinners;

    private VerticalArbiter(int maxCells, Threshold threshold, int width) {
        this.maxCells = maxCells;
        this.threshold = threshold;
        this.row = new Row(threshold, width);
    }

    static Arbiter of(int maxCells, Threshold threshold, int width) {
        VerticalArbiter arbiter = new VerticalArbiter(maxCells, threshold, width);
        arbiter.possibleVerticalWinners = arbiter.createPossibleVerticalWinners();
        return arbiter;
    }

    @Override
    public boolean check(Board board, int updatedIndex) {
        return isWinningSequence(possibleVerticalWinners, updatedIndex, board, threshold);
    }

    private Map<Integer, List<Integer>> createPossibleVerticalWinners() {
        return findPossibleVerticalWinnerIndices()
                .stream()
                .collect(Collectors.toMap(n -> n, n -> IntStream.iterate(n, a -> a <= maxCells, a -> a + row.width())
                        .boxed()
                        .limit(threshold.threshold())
                        .toList()));
    }

    private List<Integer> findPossibleVerticalWinnerIndices() {
        return IntStream.rangeClosed(1, maxCells)
                .filter(i -> i + (threshold.threshold() - 1) * row.width() <= maxCells)
                .boxed().toList();
    }
}
