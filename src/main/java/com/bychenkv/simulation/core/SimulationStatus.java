package com.bychenkv.simulation.core;

import org.jline.utils.AttributedStyle;

public enum SimulationStatus {
    RUNNING("▶ RUNNING", AttributedStyle.GREEN),
    PAUSED("⏸ PAUSED", AttributedStyle.YELLOW),
    STOPPED("■ STOPPED", AttributedStyle.RED);

    private final String displayText;
    private final int colorCode;

    SimulationStatus(String displayText, int colorCode) {
        this.displayText = displayText;
        this.colorCode = colorCode;
    }

    public String getDisplayText() {
        return displayText;
    }

    public int getColorCode() {
        return colorCode;
    }
}
