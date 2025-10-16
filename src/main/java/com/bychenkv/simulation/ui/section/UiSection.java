package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.ui.TerminalDisplay;

public abstract class UiSection {
    protected final TerminalDisplay display;

    public UiSection(TerminalDisplay display) {
        this.display = display;
    }
}
