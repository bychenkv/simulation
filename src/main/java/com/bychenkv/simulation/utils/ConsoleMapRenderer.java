package com.bychenkv.simulation.utils;

import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;

import java.util.Objects;

public class ConsoleMapRenderer implements MapRenderer {
    private static final int CELL_WIDTH = 5;

    private static final String CELL_CORNER = "+";
    private static final String CELL_HORIZONTAL_BORDER = "-";
    private static final String CELL_VERTICAL_BORDER = "|";

    private final SimulationMap simulationMap;

    public ConsoleMapRenderer(SimulationMap simulationMap) {
        this.simulationMap = simulationMap;
    }

    public void render() {
        for (int row = 0; row < simulationMap.getHeight(); row++) {
            renderRow(row);
        }
    }

    private void renderRow(int row) {
        renderRowBorder();

        for (int column = 0; column < simulationMap.getWidth(); column++) {
            renderCell(row, column);
        }

        if (row == simulationMap.getHeight() - 1) {
            renderRowBorder();
        }
    }

    private void renderRowBorder() {
        for (int y = 0; y < simulationMap.getWidth(); y++) {
            System.out.print(CELL_CORNER + CELL_HORIZONTAL_BORDER.repeat(CELL_WIDTH));
            if (y == simulationMap.getWidth() - 1) {
                System.out.println(CELL_CORNER);
            }
        }
    }

    private void renderCell(int x, int y) {
        System.out.print(CELL_VERTICAL_BORDER);

        Entity entity = simulationMap.getEntityAt(new Position(x, y));
        String content = Objects.requireNonNullElse(entity, " ").toString();
        System.out.print(getPaddedContent(content));

        if (y == simulationMap.getWidth() - 1) {
            System.out.println(CELL_VERTICAL_BORDER);
        }
    }

    private static String getPaddedContent(String content) {
        int contentLength = content.length();

        int paddingLeft = (CELL_WIDTH - contentLength) / 2;
        int paddingRight = CELL_WIDTH - paddingLeft - contentLength;

        return " ".repeat(paddingLeft) + content + " ".repeat(paddingRight);
    }
}
