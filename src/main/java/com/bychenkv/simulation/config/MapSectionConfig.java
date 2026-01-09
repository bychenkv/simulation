package com.bychenkv.simulation.config;

import com.bychenkv.simulation.core.map.MapBounds;

public record MapSectionConfig(
        int cellHeight,
        int cellWidth,
        int rowsToDisplay,
        int columnsToDisplay,
        MapBounds mapBounds
) {
    private static final int DEFAULT_CELL_HEIGHT = 1;
    private static final int DEFAULT_CELL_WIDTH = 5;
    private static final int DEFAULT_ROWS_TO_DISPLAY = 10;
    private static final int DEFAULT_COLUMNS_TO_DISPLAY = 10;
    private static final MapBounds DEFAULT_MAP_BOUNDS = MapBounds.withDefaults();

    public static MapSectionConfig defaults() {
        return new MapSectionConfig(
                DEFAULT_CELL_HEIGHT,
                DEFAULT_CELL_WIDTH,
                DEFAULT_ROWS_TO_DISPLAY,
                DEFAULT_COLUMNS_TO_DISPLAY,
                DEFAULT_MAP_BOUNDS
        );
    }

    public int getViewHeight() {
        return rowsToDisplay * (cellHeight + 1);
    }

    public int getViewWidth() {
        return columnsToDisplay * (cellWidth + 1);
    }
}
