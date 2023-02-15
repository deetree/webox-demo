package com.github.deetree.gamerun;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mariusz Bal
 */
final class HorizontalArbiter implements Arbiter {
    private final int maxCells;
    private final Threshold threshold;
    private final Row row;
    private Map<Integer, List<Integer>> possibleHorizontalWinners;

    private HorizontalArbiter(int maxCells, Threshold threshold, int width) {
        this.maxCells = maxCells;
        this.threshold = threshold;
        this.row = new Row(threshold, width);
    }

    static Arbiter of(int maxCells, Threshold threshold, int width) {
        HorizontalArbiter arbiter = new HorizontalArbiter(maxCells, threshold, width);
        arbiter.possibleHorizontalWinners = arbiter.createPossibleHorizontalWinners();
        return arbiter;
    }

    @Override
    public boolean check(Board board, int updatedIndex) {
        return isWinningSequence(possibleHorizontalWinners, updatedIndex, board, threshold);
    }

    private Map<Integer, List<Integer>> createPossibleHorizontalWinners() {
        return findPossibleHorizontalWinnerIndices()
                .stream()
                .collect(Collectors.toMap(n -> n, n -> IntStream.rangeClosed(n, n + threshold.threshold() - 1)
                        .boxed()
                        .toList()));
    }

    private List<Integer> findPossibleHorizontalWinnerIndices() {
        return IntStream.rangeClosed(1, maxCells)
                .filter(i -> row.calculateRowNumber(i) == row.calculateRowNumber(i + threshold.threshold() - 1))
                .boxed().toList();
    }
}
