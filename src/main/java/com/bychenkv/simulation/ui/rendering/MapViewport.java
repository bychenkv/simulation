package com.bychenkv.simulation.ui.rendering;

import com.bychenkv.simulation.config.MapSectionConfig;

public class MapViewport {
    private final int width;
    private final int height;

    private final int maxOffsetX;
    private final int maxOffsetY;

    private int offsetX;
    private int offsetY;

    public MapViewport(MapSectionConfig config) {
        if (config.getViewWidth() <= 0 || config.getViewHeight() <= 0) {
            throw new IllegalArgumentException("Viewport dimensions must be positive");
        }

        this.width = config.getViewWidth();
        this.height = config.getViewHeight();

        maxOffsetX = Math.max(0, config.mapBounds().width() - config.columnsToDisplay() + 1);
        maxOffsetY = Math.max(0, config.mapBounds().height() - config.rowsToDisplay() + 1);

        offsetX = 0;
        offsetY = 0;
    }

    public void scrollBy(int deltaX, int deltaY) {
        offsetX = Math.max(0, Math.min(maxOffsetX, offsetX + deltaX));
        offsetY = Math.max(0, Math.min(maxOffsetY, offsetY + deltaY));
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
