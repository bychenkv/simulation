package com.bychenkv.simulation.rendering;

import com.bychenkv.simulation.map.MapBounds;
import com.bychenkv.simulation.map.Position;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.entity.Entity;

import java.io.PrintWriter;

public class ConsoleMapRenderer implements MapRenderer {
    private static final int CELL_WIDTH = 5;
    private static final String CELL_BORDER = "+";
    private static final String HORIZONTAL_BORDER = "-";
    private static final String VERTICAL_BORDER = "|";
    private static final String EMPTY_CELL = " ";

    private final SimulationMap map;
    private final PrintWriter writer;
    private final EntityRenderer entityRenderer;

    public ConsoleMapRenderer(SimulationMap map,
                              PrintWriter writer,
                              EntityRenderer entityRenderer) {
        this.map = map;
        this.writer = writer;
        this.entityRenderer = entityRenderer;
    }

    public void render() {
        MapBounds bounds = map.getBounds();

        for (int row = 0; row <= bounds.height(); row++) {
            for (int column = 0; column <= bounds.width(); column++) {
                printCell(row, column);
            }
            printHorizontalBorderLine();
        }
    }

    private void printHorizontalBorderLine() {
        String borderLineSegment = HORIZONTAL_BORDER.repeat(CELL_WIDTH) + CELL_BORDER;
        String horizontalBorderLine = borderLineSegment.repeat(map.getBounds().width() + 1);
        writer.format("\n%s\n", horizontalBorderLine);
    }

    private void printCell(int row, int column) {
        String content = getCellContent(row, column);
        writer.print(getPaddedContent(content) + VERTICAL_BORDER);
    }

    private String getCellContent(int row, int column) {
        if (isHeaderCorner(row, column)) {
            return EMPTY_CELL;
        }
        if (isRowHeader(column)) {
            return String.valueOf(row - 1);
        }
        if (isColumnHeader(row)) {
            return String.valueOf(column - 1);
        }
        return getMapCellContent(row - 1, column - 1);
    }

    private static boolean isHeaderCorner(int row, int column) {
        return row == 0 && column == 0;
    }

    private static boolean isRowHeader(int column) {
        return column == 0;
    }

    private static boolean isColumnHeader(int row) {
        return row == 0;
    }

    private String getMapCellContent(int x, int y) {
        Position position = new Position(x, y);
        Entity entity = map.getEntityAt(position);
        if (entity != null) {
            return entityRenderer.render(entity);
        }

        return EMPTY_CELL;
    }

    private static String getPaddedContent(String content) {
        int contentLength = content.length();

        int paddingLeft = (CELL_WIDTH - contentLength) / 2;
        int paddingRight = CELL_WIDTH - paddingLeft - contentLength;

        return " ".repeat(paddingLeft) + content + " ".repeat(paddingRight);
    }
}
