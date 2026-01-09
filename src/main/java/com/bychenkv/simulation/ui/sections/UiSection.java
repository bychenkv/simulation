package com.bychenkv.simulation.ui.sections;

import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.layout.UiLayoutPosition;

public interface UiSection {
    int getHeight();
    void renderAt(UiLayoutPosition position, Display display);
}
