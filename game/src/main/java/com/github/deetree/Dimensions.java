package com.github.deetree;

/**
 * @author Mariusz Bal
 */
public record Dimensions(int rows, int cols) {
    public int cellCount() {
        return rows * cols;
    }
}
