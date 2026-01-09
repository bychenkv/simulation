package com.bychenkv.simulation.ui.layout;

import com.bychenkv.simulation.ui.sections.UiSection;

import java.util.List;
import java.util.Map;

public interface UiLayoutStrategy {
    Map<UiSection, UiLayoutPosition> calculatePositions(List<UiSection> sections);
}
