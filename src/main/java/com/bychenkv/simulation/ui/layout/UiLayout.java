package com.bychenkv.simulation.ui.layout;

import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.sections.UiSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UiLayout {
    private final Display display;
    private final UiLayoutStrategy strategy;
    private final List<UiSection> sections;
    private Map<UiSection, UiLayoutPosition> positions;

    public UiLayout(Display display, UiLayoutStrategy strategy) {
        this.display = display;
        this.strategy = strategy;
        sections = new ArrayList<>();
        positions = new HashMap<>();
    }

    public void addSection(UiSection section) {
        sections.add(section);
    }

    public synchronized void renderAllSections() {
        if (positions.isEmpty()) {
            positions = strategy.calculatePositions(sections);
        }

        for (Map.Entry<UiSection, UiLayoutPosition> entry : positions.entrySet()) {
            UiSection section = entry.getKey();
            UiLayoutPosition position = entry.getValue();

            section.renderAt(position, display);
        }
    }

    public synchronized void renderSection(UiSection section) {
        if (positions.isEmpty()) {
            positions = strategy.calculatePositions(sections);
        }

        UiLayoutPosition sectionPosition = positions.get(section);
        if (sectionPosition == null) {
            throw new RuntimeException("Error rendering section: " + section);
        }

        section.renderAt(sectionPosition, display);
    }
}
