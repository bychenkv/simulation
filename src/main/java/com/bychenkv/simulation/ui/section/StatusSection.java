package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.core.SimulationStatus;
import com.bychenkv.simulation.ui.TerminalDisplay;
import org.jline.utils.AttributedStyle;

public class StatusSection extends UiSection {
    public StatusSection(TerminalDisplay display) {
        super(display);
    }

    public void render(SimulationStatus status) {
        String text = status.getDisplayText() + "\n";
        AttributedStyle style = AttributedStyle.BOLD.foreground(status.getColorCode());

        display.printWithStyle(text, style);
    }
}
