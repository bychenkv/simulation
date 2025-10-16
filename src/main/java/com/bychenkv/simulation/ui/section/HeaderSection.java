package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.ui.TerminalDisplay;
import org.jline.utils.AttributedStyle;

public class HeaderSection extends UiSection {
    public HeaderSection(TerminalDisplay display) {
        super(display);
    }

    public void render() {
        display.printWithStyle(
                "=== Simulation ===\n",
                AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)
        );
    }
}
