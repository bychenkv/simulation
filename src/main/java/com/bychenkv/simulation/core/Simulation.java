package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.services.rendering.MapRenderer;
import com.bychenkv.simulation.ui.SimulationUi;

import java.util.concurrent.atomic.AtomicReference;

public class Simulation {
    private static final long ITERATION_DELAY_MS = 500;

    private final SimulationMap map;
    private final MapRenderer mapRenderer;
    private final SimulationActions actions;
    private final SimulationEventBus eventBus = new SimulationEventBus();

    private int iterationCount;
    private final AtomicReference<SimulationStatus> status =
            new AtomicReference<>(SimulationStatus.STOPPED);

    public Simulation(
            SimulationMap map,
            MapRenderer mapRenderer,
            SimulationActions actions,
            SimulationUi ui
    ) {
        this.map = map;
        this.mapRenderer = mapRenderer;
        this.actions = actions;

        eventBus.addListener(ui);
    }

    public void start() {
        //ui.log("Simulation started");
        try {
            initializeSimulation();

            while (status.get() != SimulationStatus.STOPPED) {
                if (!waitIfPaused()) break;
                executeIteration();
                Thread.sleep(ITERATION_DELAY_MS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void initializeSimulation() {
        //ui.log("Initialize simulation...");

        updateStatus(SimulationStatus.RUNNING);
        iterationCount = 0;

        for (Action action : actions.init()) {
            action.execute(map);
        }
    }

    private void executeIteration() {
        iterationCount++;
        String renderedMap = mapRenderer.render();
        eventBus.notifyIterationCompleted(iterationCount, renderedMap);

        for (Action action : actions.turn()) {
            if (status.get() == SimulationStatus.RUNNING) {
                action.execute(map);
            }
        }
    }

    private synchronized boolean waitIfPaused() throws InterruptedException {
        while (status.get() == SimulationStatus.PAUSED) {
            wait();
        }
        return status.get() != SimulationStatus.STOPPED;
    }

    public synchronized void pause() {
        //ui.log("Simulation paused");
        updateStatus(SimulationStatus.PAUSED);
    }

    public synchronized void resume() {
        //ui.log("Simulation resumed");
        updateStatus(SimulationStatus.RUNNING);
        notifyAll();
    }

    public synchronized void stop() {
        //ui.log("Simulation stopped");
        updateStatus(SimulationStatus.STOPPED);
        notifyAll();
    }

    public boolean isPaused() {
        return status.get() == SimulationStatus.PAUSED;
    }

    private void updateStatus(SimulationStatus newStatus) {
        status.set(newStatus);
        eventBus.notifyStatusChanged(newStatus);
    }
}
