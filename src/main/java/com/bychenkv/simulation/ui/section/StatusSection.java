package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.core.SimulationStatus;
import com.bychenkv.simulation.ui.TerminalDisplay;
import org.jline.utils.AttributedStyle;

public class StatusSection extends UiSection {
    private static final int HEIGHT = 1;

    private SimulationStatus currentStatus = SimulationStatus.RUNNING;

    public StatusSection(TerminalDisplay display) {
        super(display);
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void renderAt(int startRow) {
        display.clearLine(startRow);

        String text = currentStatus.getDisplayText() + "\n";
        AttributedStyle style = AttributedStyle.BOLD.foreground(currentStatus.getColorCode());

        display.printWithStyle(text, style);
    }

    public void setCurrentStatus(SimulationStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
}
