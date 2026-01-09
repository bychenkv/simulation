package com.bychenkv.simulation.ui.layout;

public record UiLayoutPosition(int row, int column) {
    public UiLayoutPosition(int row) {
        this(row, 1);
    }
}
