package com.bychenkv.simulation;

import com.bychenkv.simulation.core.Simulation;
import com.bychenkv.simulation.ui.SimulationUi;
import com.bychenkv.simulation.ui.UiCommandListener;
import com.bychenkv.simulation.ui.UiCommand;

public class SimulationLauncher implements UiCommandListener {
    private static final int SIMULATION_THREAD_JOIN_TIMEOUT_MS = 1000;
    private static final String SIMULATION_THREAD_NAME = "SimulationThread";

    private final Simulation simulation;
    private final SimulationUi ui;
    private final Thread simulationThread;

    public SimulationLauncher(Simulation simulation, SimulationUi ui) {
        this.simulation = simulation;
        simulationThread = new Thread(simulation::start, SIMULATION_THREAD_NAME);

        this.ui = ui;
        this.ui.addEventListener(this);
    }

    public void launch() {
        ui.start();
        simulationThread.start();
    }

    @Override
    public void onCommandReceived(UiCommand command) {
        switch (command) {
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
        ui.removeEventListener(this);
        ui.close();
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
