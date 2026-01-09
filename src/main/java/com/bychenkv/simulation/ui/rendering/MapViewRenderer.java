package com.bychenkv.simulation.ui.rendering;

import com.bychenkv.simulation.config.MapSectionConfig;
import com.bychenkv.simulation.core.entity.Entity;
import com.bychenkv.simulation.core.map.Position;
import com.bychenkv.simulation.core.map.SimulationMap;
import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.layout.UiLayoutPosition;

import java.util.Objects;

public class MapViewRenderer {
    private static final String CELL_BORDER = "+";
    private static final String VERTICAL_BORDER = "|";
    private static final String HORIZONTAL_BORDER = "-";

    private final MapViewport viewport;
    private final SimulationMap map;
    private final EntityRenderer entityRenderer;
    private final int cellWidth;
    private final int cellHeight;

    public MapViewRenderer(MapViewport viewport,
                           MapSectionConfig config,
                           SimulationMap map,
                           EntityRenderer entityRenderer) {
        this.viewport = Objects.requireNonNull(viewport);
        this.map = map;
        this.entityRenderer = entityRenderer;
        cellWidth = config.cellWidth();
        cellHeight = config.cellHeight();
    }

    public void render(UiLayoutPosition position, Display display) {
        clearViewportArea(position, display);

        int visibleRows = calculateVisibleRows();
        int visibleColumns = calculateVisibleColumns();

        renderColumnHeaders(visibleColumns, position, display);
        UiLayoutPosition contentPosition = new UiLayoutPosition(position.row() + 2, position.column());
        renderMapContent(visibleRows, visibleColumns, contentPosition, display);
    }

    private void clearViewportArea(UiLayoutPosition position, Display display) {
        for (int row = 0; row < viewport.getHeight(); row++) {
            display.clearLine(position.row() + row);
        }
    }

    private int calculateVisibleRows() {
        int maxRows = map.getHeight() - viewport.getOffsetY();
        int cellWithBorder = cellHeight + 1; // + 1 because of the border itself
        int availableRows = (viewport.getHeight() - cellWithBorder) / cellWithBorder;

        return Math.min(maxRows, availableRows);
    }

    private int calculateVisibleColumns() {
        int maxColumns = map.getWidth() - viewport.getOffsetX();
        int cellWithBorder = cellWidth + 1; // + 1 because of the border itself
        int availableColumns = (viewport.getWidth() - cellWithBorder) / cellWithBorder;

        return Math.min(maxColumns, availableColumns);
    }

    private void renderColumnHeaders(int visibleColumns,
                                     UiLayoutPosition position,
                                     Display display) {
        display.moveCursorTo(position);
        renderCell(display, " ");

        for (int col = 0; col < visibleColumns; col++) {
            int actualColumn = viewport.getOffsetX() + col;
            renderCell(display, String.valueOf(actualColumn));
        }

        UiLayoutPosition borderPosition = new UiLayoutPosition(position.row() + 1, position.column());
        display.moveCursorTo(borderPosition);
        renderHorizontalBorder(display, visibleColumns + 1);
    }

    private void renderMapContent(
            int visibleRows,
            int visibleColumns,
            UiLayoutPosition position,
            Display display
    ) {
        UiLayoutPosition currentPosition = position;

        for (int row = 0; row < visibleRows; row++) {
            int actualRow = row + viewport.getOffsetY();

            display.moveCursorTo(currentPosition);
            renderContentRow(actualRow, visibleColumns, display);
            currentPosition = new UiLayoutPosition(currentPosition.row() + 1, currentPosition.column());

            if (row < visibleRows - 1) {
                display.moveCursorTo(currentPosition);
                renderHorizontalBorder(display, visibleColumns + 1);
                currentPosition = new UiLayoutPosition(currentPosition.row() + 1, currentPosition.column());
            }
        }
    }

    private void renderContentRow(int row, int visibleColumns, Display display) {
        renderCell(display, String.valueOf(row));

        for (int col = 0; col < visibleColumns; col++) {
            int actualColumn = col + viewport.getOffsetX();
            String content = getCellContent(row, actualColumn);
            renderCell(display, content);
        }
    }

    private String getCellContent(int row, int column) {
        Position position = new Position(row, column);
        Entity entity = map.getEntityAt(position);

        return entity == null ? " " : entityRenderer.render(entity);
    }

    private void renderCell(Display display, String content) {
        String paddedContent = getPaddedContent(content, cellWidth);
        display.print(paddedContent + VERTICAL_BORDER);
    }

    private void renderHorizontalBorder(Display display, int cellCount) {
        String borderSegment = HORIZONTAL_BORDER.repeat(cellWidth) + CELL_BORDER;
        String border = borderSegment.repeat(cellCount);
        display.print(border);
    }

    private static String getPaddedContent(String content, int totalWidth) {
        int paddingLeft = (totalWidth - content.length()) / 2;
        int paddingRight = totalWidth - paddingLeft - content.length();

        return " ".repeat(paddingLeft) + content + " ".repeat(paddingRight);
    }
}
