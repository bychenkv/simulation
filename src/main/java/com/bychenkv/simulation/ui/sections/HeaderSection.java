package com.bychenkv.simulation.ui.sections;

import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.layout.UiLayoutPosition;
import org.jline.utils.AttributedStyle;

public class HeaderSection implements UiSection {
    private static final int HEIGHT = 1;

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void renderAt(UiLayoutPosition position, Display display) {
        display.clearLine(position.row());
        display.printWithStyle(
                "=== Simulation ===\n",
                AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)
        );
    }
}
