package com.github.deetree.gamerun;

import java.util.List;

/**
 * @author Mariusz Bal
 */
public final class Judge implements BoardObserver {

    private final List<Arbiter> arbiters;

    private Judge(List<Arbiter> arbiters) {this.arbiters = arbiters;}

    public static Judge of(Config config) {
        int width = config.dimensions().cols();
        int maxCells = config.dimensions().cellCount();
        Threshold threshold = config.threshold();
        return new Judge(List.of(
                HorizontalArbiter.of(maxCells, threshold, width),
                VerticalArbiter.of(maxCells, threshold, width),
                DiagonalArbiter.of(maxCells, threshold, width),
                AntiDiagonalArbiter.of(maxCells, threshold, width)));
    }

    @Override
    public boolean verify(Board board, int updatedIndex) {
        return arbiters.stream().anyMatch(a -> a.check(board, updatedIndex));
    }
}
