package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.ui.TerminalDisplay;
import org.jline.utils.AttributedStyle;

public class IterationSection extends UiSection {
    private static final int HEIGHT = 1;

    private int currentIteration;

    public IterationSection(TerminalDisplay display) {
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
                "Iteration #%d\n".formatted(currentIteration),
                AttributedStyle.BOLD.foreground(AttributedStyle.CYAN)
        );
        display.flush();
    }

    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }
}
