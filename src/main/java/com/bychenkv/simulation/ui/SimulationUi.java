package com.bychenkv.simulation.ui;

import com.bychenkv.simulation.core.SimulationEventListener;
import com.bychenkv.simulation.core.SimulationStatus;
import com.bychenkv.simulation.ui.section.*;

import java.io.Closeable;

public class SimulationUi implements SimulationEventListener, Closeable {
    private final InputEventBus eventBus;
    private final TerminalDisplay display;
    private final UiLayout layout;

    private final HeaderSection headerSection;
    private final MapSection mapSection;
    private final StatusSection statusSection;
    private final CommandsSection commandsSection;
    private final IterationSection iterationSection;

    public SimulationUi(TerminalDisplay display, InputEventBus eventBus, int mapHeight) {
        this.display = display;
        this.eventBus = eventBus;
        layout = new UiLayout(display);

        headerSection = new HeaderSection(display);
        mapSection = new MapSection(display, mapHeight);
        statusSection = new StatusSection(display);
        commandsSection = new CommandsSection(display);
        iterationSection = new IterationSection(display);

        layout.addSection(headerSection);
        layout.addSection(iterationSection);
        layout.addSection(mapSection);
        layout.addSection(statusSection);
        layout.addSection(commandsSection);
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
        mapSection.setCurrentRenderedMap(renderedMap);

        layout.renderSection(iterationSection);
        layout.renderSection(mapSection);
    }

    @Override
    public void onStatusChanged(SimulationStatus status) {
        statusSection.setCurrentStatus(status);
        layout.renderSection(statusSection);
    }

    @Override
    public void close() {
        display.showCursor();
        eventBus.close();
    }

    public void addEventListener(UiCommandListener listener) {
        eventBus.addListener(listener);
    }

    public void removeEventListener(UiCommandListener listener) {
        eventBus.removeListener(listener);
    }
}
