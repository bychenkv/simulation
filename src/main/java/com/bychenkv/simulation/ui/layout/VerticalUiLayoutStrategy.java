package com.bychenkv.simulation.ui.layout;

import com.bychenkv.simulation.ui.sections.UiSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerticalUiLayoutStrategy implements UiLayoutStrategy {
    @Override
    public Map<UiSection, UiLayoutPosition> calculatePositions(List<UiSection> sections) {
        Map<UiSection, UiLayoutPosition> positions = new HashMap<>();
        int row = 1;

        for (UiSection section : sections) {
            positions.put(section, new UiLayoutPosition(row));
            row += section.getHeight();
        }

        return positions;
    }
}
