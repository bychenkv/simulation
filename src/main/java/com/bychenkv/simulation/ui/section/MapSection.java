package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.ui.TerminalDisplay;

public class MapSection extends UiSection {
    public MapSection(TerminalDisplay display) {
        super(display);
    }

    public void render(String renderedMap) {
        display.println(renderedMap);
    }
}
