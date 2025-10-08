package com.bychenkv.simulation;

import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.core.DefaultSimulationFactory;
import com.bychenkv.simulation.core.Simulation;

public class Launcher {
    public static void main(String[] args) {
        SimulationConfig config = SimulationConfig.withDefaults();
        Simulation simulation = new DefaultSimulationFactory(config)
                .createSimulation();
        simulation.start();
    }
}
