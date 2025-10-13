package com.bychenkv.simulation;

import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.core.DefaultSimulationFactory;
import com.bychenkv.simulation.core.Simulation;
import com.bychenkv.simulation.core.SimulationFactory;
import com.bychenkv.simulation.core.SimulationLauncher;
import com.bychenkv.simulation.rendering.ConsoleMapRendererFactory;
import com.bychenkv.simulation.rendering.MapRendererFactory;
import com.bychenkv.simulation.services.input.ConsoleUserInputHandler;
import com.bychenkv.simulation.services.input.UserInputHandler;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Terminal terminal;
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .encoding("UTF-8")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimulationConfig config = SimulationConfig.withDefaults();
        MapRendererFactory rendererFactory = new ConsoleMapRendererFactory(terminal);

        SimulationFactory simulationFactory = new DefaultSimulationFactory(config, rendererFactory);
        Simulation simulation = simulationFactory.createSimulation();

        UserInputHandler inputHandler = new ConsoleUserInputHandler();

        SimulationLauncher launcher = new SimulationLauncher(simulation, inputHandler);
        launcher.launch();
    }
}
