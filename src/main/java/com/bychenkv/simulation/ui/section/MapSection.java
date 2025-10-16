package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.ui.TerminalDisplay;

public class MapSection extends UiSection {
    private final int height;
    private String currentRenderedMap;

    public MapSection(TerminalDisplay display, int height) {
        super(display);
        this.height = 2 * (height + 1);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void renderAt(int startRow) {
        if (currentRenderedMap != null) {
            for (int i = 0; i < height; i++) {
                display.clearLine(startRow + i);
            }

            display.moveCursorTo(startRow, 1);
            display.println(currentRenderedMap);
        }
    }

    public void setCurrentRenderedMap(String currentRenderedMap) {
        this.currentRenderedMap = currentRenderedMap;
    }
}
