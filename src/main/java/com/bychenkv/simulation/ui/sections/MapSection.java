package com.bychenkv.simulation.ui.sections;

import com.bychenkv.simulation.ui.rendering.MapViewRenderer;
import com.bychenkv.simulation.ui.rendering.MapViewport;
import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.layout.UiLayoutPosition;

public class MapSection implements UiSection {
    private final MapViewport viewport;
    private final MapViewRenderer renderer;

    public MapSection(MapViewport viewport, MapViewRenderer renderer) {
        this.viewport = viewport;
        this.renderer = renderer;
    }

    public void scrollBy(int deltaX, int deltaY) {
        viewport.scrollBy(deltaX, deltaY);
    }

    @Override
    public int getHeight() {
        return viewport.getHeight();
    }

    @Override
    public void renderAt(UiLayoutPosition position, Display display) {
        renderer.render(position, display);
    }
}
