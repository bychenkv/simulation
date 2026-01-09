package com.bychenkv.simulation.ui.sections;

import com.bychenkv.simulation.core.simulation.SimulationStatus;
import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.layout.UiLayoutPosition;
import org.jline.utils.AttributedStyle;

public class StatusSection implements UiSection {
    private static final int HEIGHT = 1;

    private SimulationStatus currentStatus;

    public StatusSection() {
        currentStatus = SimulationStatus.RUNNING;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void renderAt(UiLayoutPosition position, Display display) {
        display.clearLine(position.row());

        String text = currentStatus.getDisplayText() + "\n";
        AttributedStyle style = AttributedStyle.BOLD.foreground(currentStatus.getColorCode());

        display.printWithStyle(text, style);
    }

    public void setCurrentStatus(SimulationStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
}
