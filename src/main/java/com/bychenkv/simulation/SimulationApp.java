package com.bychenkv.simulation;

import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.core.*;
import com.bychenkv.simulation.services.rendering.ConsoleMapRendererFactory;
import com.bychenkv.simulation.services.rendering.MapRendererFactory;
import com.bychenkv.simulation.ui.SimulationUi;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class SimulationApp {
    public static void main(String[] args) {
        Terminal terminal;
        try {
            terminal = createTerminal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimulationConfig config = SimulationConfig.withDefaults();
        MapRendererFactory rendererFactory = new ConsoleMapRendererFactory(terminal);

        SimulationUi ui = new SimulationUi(terminal);
        Simulation simulation = new DefaultSimulationFactory(config, rendererFactory, ui)
                .createSimulation();

        SimulationLauncher launcher = new SimulationLauncher(simulation, ui);
        launcher.launch();
    }

    private static Terminal createTerminal() throws IOException {
        return TerminalBuilder.builder()
                .system(true)
                .encoding("UTF-8")
                .build();
    }
}
