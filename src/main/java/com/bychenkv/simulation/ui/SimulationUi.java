package com.bychenkv.simulation.ui;

import com.bychenkv.simulation.core.SimulationEventListener;
import com.bychenkv.simulation.core.SimulationStatus;
import com.bychenkv.simulation.ui.section.*;

import java.io.Closeable;

public class SimulationUi implements SimulationEventListener, Closeable {
    private final InputEventBus eventBus;
    private final TerminalDisplay display;

    private final HeaderSection headerSection;
    private final MapSection mapSection;
    private final StatusSection statusSection;
    private final CommandsSection commandsSection;
    private final IterationSection iterationSection;

    public SimulationUi(TerminalDisplay display, InputEventBus eventBus) {
        this.display = display;
        this.eventBus = eventBus;

        headerSection = new HeaderSection(display);
        mapSection = new MapSection(display);
        statusSection = new StatusSection(display);
        commandsSection = new CommandsSection(display);
        iterationSection = new IterationSection(display);
    }

    public void start() {
        eventBus.startListening();

        display.clear();
        headerSection.render();
        commandsSection.render();
    }

    @Override
    public void onIterationCompleted(int iteration, String renderedMap) {
        iterationSection.render(iteration);
        mapSection.render(renderedMap);
    }

    @Override
    public void onStatusChanged(SimulationStatus status) {
        statusSection.render(status);
    }

    @Override
    public void close() {
        eventBus.close();
    }

    public void addEventListener(UiCommandListener listener) {
        eventBus.addListener(listener);
    }

    public void removeEventListener(UiCommandListener listener) {
        eventBus.removeListener(listener);
    }
}
