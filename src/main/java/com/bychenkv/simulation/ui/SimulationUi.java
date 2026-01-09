package com.bychenkv.simulation.ui;

import com.bychenkv.simulation.core.simulation.SimulationEventBus;
import com.bychenkv.simulation.core.simulation.SimulationStatus;
import com.bychenkv.simulation.logger.LogMessage;
import com.bychenkv.simulation.logger.SimulationLogger;
import com.bychenkv.simulation.ui.rendering.MapViewRenderer;
import com.bychenkv.simulation.ui.rendering.MapViewport;
import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.command.UiCommandEventBus;
import com.bychenkv.simulation.ui.layout.UiLayout;
import com.bychenkv.simulation.ui.layout.VerticalUiLayoutStrategy;
import com.bychenkv.simulation.ui.sections.*;

import java.io.Closeable;

public class SimulationUi implements SimulationEventBus.Listener,
                                     SimulationLogger.Listener,
                                     Closeable {
    private final UiCommandEventBus eventBus;
    private final Display display;
    private final SimulationLogger logger;
    private final UiLayout layout;

    private final IterationSection iterationSection;
    private final MapSection mapSection;
    private final StatusSection statusSection;
    private final LogSection logSection;

    public SimulationUi(Display display,
                        UiCommandEventBus eventBus,
                        SimulationLogger logger,
                        MapViewport viewport,
                        MapViewRenderer renderer) {
        this.display = display;
        this.eventBus = eventBus;
        this.logger = logger;

        iterationSection = new IterationSection();
        mapSection = new MapSection(viewport, renderer);
        statusSection = new StatusSection();
        logSection = new LogSection();

        layout = new UiLayout(display, new VerticalUiLayoutStrategy());
        layout.addSection(new HeaderSection());
        layout.addSection(iterationSection);
        layout.addSection(mapSection);
        layout.addSection(statusSection);
        layout.addSection(logSection);
        layout.addSection(new CommandSection());
    }

    public void start() {
        logger.addListener(this);
        eventBus.startListening();
        display.clear();
        display.hideCursor();
        layout.renderAllSections();
    }

    public void scrollMap(int deltaX, int deltaY) {
        mapSection.scrollBy(deltaX, deltaY);
        layout.renderSection(mapSection);
    }

    @Override
    public void onIterationCompleted(int iteration) {
        iterationSection.setCurrentIteration(iteration);
        layout.renderSection(iterationSection);
        layout.renderSection(mapSection);
    }

    @Override
    public void onStatusChanged(SimulationStatus status) {
        statusSection.setCurrentStatus(status);
        layout.renderSection(statusSection);
    }

    @Override
    public void onLogMessage(LogMessage message) {
        logSection.addLog(message);
        layout.renderSection(logSection);
    }

    @Override
    public void onMapRendered() {
        layout.renderSection(mapSection);
    }

    @Override
    public void close() {
        eventBus.close();
        logger.removeListener(this);
        display.showCursor();
        display.clear();
    }

    public void addEventListener(UiCommandEventBus.Listener listener) {
        eventBus.addListener(listener);
    }

    public void removeEventListener(UiCommandEventBus.Listener listener) {
        eventBus.removeListener(listener);
    }
}
