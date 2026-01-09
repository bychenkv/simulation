package com.bychenkv.simulation.ui.sections;

import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.layout.UiLayoutPosition;
import org.jline.utils.AttributedStyle;

public class IterationSection implements UiSection {
    private static final int HEIGHT = 1;
    private static final AttributedStyle STYLE =
            AttributedStyle.BOLD.foreground(AttributedStyle.CYAN);

    private int currentIteration;

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void renderAt(UiLayoutPosition position, Display display) {
        display.clearLine(position.row());
        display.printWithStyle(getIterationText(), STYLE);
    }

    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    private String getIterationText() {
        return "Iteration #%d\n".formatted(currentIteration);
    }
}
