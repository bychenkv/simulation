package com.bychenkv.simulation.utils;

import com.bychenkv.simulation.Coordinate;
import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.entity.Entity;

import java.util.Objects;

public class MapRenderer {
    private static final int CELL_WIDTH = 5;

    private static final String CELL_CORNER = "+";
    private static final String CELL_HORIZONTAL_BORDER = "-";
    private static final String CELL_VERTICAL_BORDER = "|";

    private final Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    public void render() {
        for (int row = 0; row < map.getRows(); row++) {
            renderRow(row);
        }
    }

    private void renderRow(int row) {
        renderRowBorder();

        for (int column = 0; column < map.getColumns(); column++) {
            renderCell(row, column);
        }

        if (row == map.getRows() - 1) {
            renderRowBorder();
        }
    }

    private void renderRowBorder() {
        for (int y = 0; y < map.getColumns(); y++) {
            System.out.print(CELL_CORNER + CELL_HORIZONTAL_BORDER.repeat(CELL_WIDTH));
            if (y == map.getColumns() - 1) {
                System.out.println(CELL_CORNER);
            }
        }
    }

    private void renderCell(int x, int y) {
        System.out.print(CELL_VERTICAL_BORDER);

        Entity entity = map.getEntity(new Coordinate(x, y));
        String content = Objects.requireNonNullElse(entity, " ").toString();
        System.out.print(getPaddedContent(content));

        if (y == map.getColumns() - 1) {
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
