package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.ui.TerminalDisplay;
import org.jline.utils.AttributedStyle;

public class IterationSection extends UiSection {
    public IterationSection(TerminalDisplay display) {
        super(display);
    }

    public void render(int iteration) {
        display.printWithStyle(
                "Iteration count: " + iteration,
                AttributedStyle.BOLD.foreground(AttributedStyle.CYAN)
        );
    }
}
