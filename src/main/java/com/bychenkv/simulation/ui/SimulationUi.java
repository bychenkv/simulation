package com.bychenkv.simulation.ui;

import com.bychenkv.simulation.core.SimulationEventListener;
import com.bychenkv.simulation.core.SimulationStatus;
import com.bychenkv.simulation.services.logger.LogMessage;
import com.bychenkv.simulation.services.logger.SimulationLogger;
import com.bychenkv.simulation.ui.section.*;

import java.io.Closeable;

public class SimulationUi implements SimulationEventListener,
                                     SimulationLogger.Listener,
                                     Closeable {
    private final InputEventBus eventBus;
    private final TerminalDisplay display;
    private final SimulationLogger logger;
    private final UiLayout layout;

    // SECTIONS

    private final IterationSection iterationSection;
    private final MapSection mapSection;
    private final StatusSection statusSection;
    private final LogSection logSection;

    public SimulationUi(TerminalDisplay display,
                        InputEventBus eventBus,
                        int mapHeight,
                        SimulationLogger logger) {
        this.display = display;
        this.eventBus = eventBus;
        this.logger = logger;
        this.logger.addListener(this);

        iterationSection = new IterationSection(display);
        mapSection = new MapSection(display, mapHeight);
        statusSection = new StatusSection(display);
        logSection = new LogSection(display);

        layout = new UiLayout(display);
        layout.addSection(new HeaderSection(display));
        layout.addSection(iterationSection);
        layout.addSection(mapSection);
        layout.addSection(statusSection);
        layout.addSection(logSection);
        layout.addSection(new CommandSection(display));
    }

    public void start() {
        eventBus.startListening();
        display.clear();
        display.hideCursor();
        layout.renderAllSections();
    }

    @Override
    public void onIterationCompleted(int iteration, String renderedMap) {
        iterationSection.setCurrentIteration(iteration);
        layout.renderSection(iterationSection);

        mapSection.setCurrentRenderedMap(renderedMap);
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
    public void onMapRendered(String renderedMap) {
        mapSection.setCurrentRenderedMap(renderedMap);
        layout.renderSection(mapSection);
    }

    @Override
    public void close() {
        eventBus.close();
        logger.removeListener(this);

        display.showCursor();
        display.clear();
    }

    public void addEventListener(UiCommandListener listener) {
        eventBus.addListener(listener);
    }

    public void removeEventListener(UiCommandListener listener) {
        eventBus.removeListener(listener);
    }
}
