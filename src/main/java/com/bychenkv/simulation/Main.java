package com.bychenkv.simulation;

import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.core.DefaultSimulationFactory;
import com.bychenkv.simulation.core.Simulation;
import com.bychenkv.simulation.core.SimulationFactory;
import com.bychenkv.simulation.core.SimulationLauncher;
import com.bychenkv.simulation.services.input.ConsoleUserInputHandler;
import com.bychenkv.simulation.services.input.UserInputHandler;

public class Main {
    public static void main(String[] args) {
        SimulationConfig config = SimulationConfig.withDefaults();

        SimulationFactory simulationFactory = new DefaultSimulationFactory(config);
        Simulation simulation = simulationFactory.createSimulation();

        UserInputHandler inputHandler = new ConsoleUserInputHandler();

        SimulationLauncher launcher = new SimulationLauncher(simulation, inputHandler);
        launcher.launch();
    }
}
