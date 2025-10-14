package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.services.rendering.MapRenderer;
import com.bychenkv.simulation.ui.SimulationUi;

public class Simulation {
    private static final long ITERATION_DELAY_MS = 500;

    private final SimulationMap map;
    private final MapRenderer mapRenderer;
    private final SimulationActions actions;
    private final SimulationUi ui;

    private int iterationCount;
    private volatile boolean isRunning;
    private volatile boolean isPaused;

    public Simulation(
            SimulationMap map,
            MapRenderer mapRenderer,
            SimulationActions actions,
            SimulationUi ui
    ) {
        this.map = map;
        this.mapRenderer = mapRenderer;
        this.actions = actions;
        this.ui = ui;
    }

    public void start() {
        ui.log("Simulation started");
        try {
            initializeSimulation();

            while (isRunning) {
                if (!waitIfPaused()) break;
                executeIteration();
                Thread.sleep(ITERATION_DELAY_MS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void initializeSimulation() {
        ui.log("Initialize simulation...");

        isRunning = true;
        isPaused = false;
        iterationCount = 0;

        for (Action action : actions.init()) {
            action.execute(map);
        }
    }

    private void executeIteration() {
        String rendered = mapRenderer.render();
        iterationCount++;
        ui.update(rendered, iterationCount, isPaused);

        for (Action action : actions.turn()) {
            if (isRunning && !isPaused) {
                action.execute(map);
            }
        }
    }

    private synchronized boolean waitIfPaused() throws InterruptedException {
        while (isPaused && isRunning) {
            wait();
        }
        return isRunning;
    }

    public synchronized void pause() {
        ui.log("Simulation paused");
        isPaused = true;
    }

    public synchronized void resume() {
        ui.log("Simulation resumed");
        isPaused = false;
        notifyAll();
    }

    public synchronized void stop() {
        ui.log("Simulation stopped");
        isRunning = false;
        isPaused = false;
        notifyAll();
    }

    public boolean isPaused() {
        return isPaused;
    }
}
