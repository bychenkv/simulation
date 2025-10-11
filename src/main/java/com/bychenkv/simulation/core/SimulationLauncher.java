package com.bychenkv.simulation.core;

import com.bychenkv.simulation.services.input.UserInputEvent;
import com.bychenkv.simulation.services.input.UserInputEventListener;
import com.bychenkv.simulation.services.input.UserInputHandler;

public class SimulationLauncher implements UserInputEventListener {
    private static final int SIMULATION_THREAD_JOIN_TIMEOUT_MS = 1000;
    private static final String SIMULATION_THREAD_NAME = "SimulationThread";

    private final Simulation simulation;
    private final UserInputHandler inputHandler;
    private final Thread simulationThread;

    public SimulationLauncher(Simulation simulation, UserInputHandler inputHandler) {
        this.simulation = simulation;
        simulationThread = new Thread(simulation::start, SIMULATION_THREAD_NAME);

        this.inputHandler = inputHandler;
        this.inputHandler.addEventListener(this);

    }

    public void launch() {
        simulationThread.start();
        inputHandler.startListen();
    }

    @Override
    public void onInputEventReceived(UserInputEvent event) {
        switch (event) {
            case STOP -> stop();
            case TOGGLE_PAUSE -> togglePause();
        }
    }

    private void togglePause() {
        if (simulation.isPaused()) {
            simulation.resume();
        } else {
            simulation.pause();
        }
    }

    private void stop() {
        inputHandler.stopListen();
        simulation.stop();

        if (simulationThread != null && simulationThread.isAlive()) {
            simulationThread.interrupt();
            try {
                simulationThread.join(SIMULATION_THREAD_JOIN_TIMEOUT_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
