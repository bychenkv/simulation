package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.rendering.MapRenderer;

public class Simulation {
    private static final long ITERATION_DELAY_MS = 500;

    private final SimulationMap map;
    private final MapRenderer mapRenderer;
    private final SimulationActions actions;

    private int iterationCount;
    private volatile boolean isRunning;
    private volatile boolean isPaused;

    public Simulation(
            SimulationMap map,
            MapRenderer mapRenderer,
            SimulationActions actions
    ) {
        this.map = map;
        this.mapRenderer = mapRenderer;
        this.actions = actions;
    }

    public void start() {
        System.out.println("Simulation started");
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
        System.out.println("Initialize simulation...");

        isRunning = true;
        isPaused = false;
        iterationCount = 0;

        for (Action action : actions.init()) {
            action.execute(map);
        }
    }

    private void executeIteration() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.format("Iteration #%d%n", ++iterationCount);
        mapRenderer.render();
        System.out.println("Press Enter to pause/resume, 'q' + Enter to quit");

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
        System.out.println("Simulation paused");
        isPaused = true;
    }

    public synchronized void resume() {
        System.out.println("Simulation resumed");
        isPaused = false;
        notifyAll();
    }

    public synchronized void stop() {
        System.out.println("Simulation stopped");
        isRunning = false;
        isPaused = false;
        notifyAll();
    }

    public boolean isPaused() {
        return isPaused;
    }
}
