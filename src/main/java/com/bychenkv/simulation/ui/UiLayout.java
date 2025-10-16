package com.bychenkv.simulation.ui;

import com.bychenkv.simulation.ui.section.UiSection;

import java.util.ArrayList;
import java.util.List;

public class UiLayout {
    private final TerminalDisplay display;
    private final List<UiSection> sections = new ArrayList<>();

    public UiLayout(TerminalDisplay display) {
        this.display = display;
    }

    public void addSection(UiSection section) {
        sections.add(section);
    }

    public void renderAllSections() {
        int row = 1;
        for (UiSection section : sections) {
            section.renderAt(row);
            row += section.getHeight();
        }
        display.flush();
    }

    public void renderSection(UiSection section) {
        int row = getRowForSection(section);
        section.renderAt(row);
        display.flush();
    }

    private int getRowForSection(UiSection section) {
        int row = 1;
        for (UiSection s : sections) {
            if (s == section) {
                return row;
            }
            row += s.getHeight();
        }
        return row;
    }
}
