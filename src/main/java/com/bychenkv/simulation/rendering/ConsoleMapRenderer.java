package com.bychenkv.simulation.rendering;

import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;

import java.io.PrintStream;

public class ConsoleMapRenderer implements MapRenderer {
    private static final int CELL_WIDTH = 5;

    private static final String CELL_CORNER = "+";
    private static final String CELL_HORIZONTAL_BORDER = "-";
    private static final String CELL_VERTICAL_BORDER = "|";
    private static final String EMPTY_CELL = " ";

    private final SimulationMap simulationMap;
    private final PrintStream printStream;
    private final EntityRenderer entityRenderer;

    public ConsoleMapRenderer(SimulationMap simulationMap,
                              PrintStream printStream,
                              EntityRenderer entityRenderer) {
        this.simulationMap = simulationMap;
        this.printStream = printStream;
        this.entityRenderer = entityRenderer;
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
        String rowBorder = CELL_CORNER + CELL_HORIZONTAL_BORDER.repeat(CELL_WIDTH);

        for (int column = 0; column < simulationMap.getWidth(); column++) {
            printStream.print(rowBorder);
            if (column == simulationMap.getWidth() - 1) {
                printStream.println(CELL_CORNER);
            }
        }
    }

    private void renderCell(int row, int column) {
        printStream.print(CELL_VERTICAL_BORDER);
        printStream.print(getCellContent(row, column));

        if (column == simulationMap.getWidth() - 1) {
            printStream.println(CELL_VERTICAL_BORDER);
        }
    }

    private String getCellContent(int row, int column) {
        Entity entity = simulationMap.getEntityAt(row, column);
        String sprite = entity != null ? entityRenderer.render(entity) : EMPTY_CELL;

        int paddingLeft = (CELL_WIDTH - sprite.length()) / 2;
        int paddingRight = CELL_WIDTH - paddingLeft - sprite.length();

        return " ".repeat(paddingLeft) + sprite + " ".repeat(paddingRight);
    }
}
