package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.ui.TerminalDisplay;
import org.jline.utils.AttributedStyle;

public class HeaderSection extends UiSection {
    private static final int HEIGHT = 1;

    public HeaderSection(TerminalDisplay display) {
        super(display);
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void renderAt(int startRow) {
        display.clearLine(startRow);
        display.printWithStyle(
                "=== Simulation ===\n",
                AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)
        );
    }
}
