package com.bychenkv.simulation;

import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.core.*;
import com.bychenkv.simulation.services.rendering.ConsoleMapRendererFactory;
import com.bychenkv.simulation.services.rendering.MapRenderer;
import com.bychenkv.simulation.services.rendering.MapRendererFactory;
import com.bychenkv.simulation.ui.InputEventBus;
import com.bychenkv.simulation.ui.SimulationUi;
import com.bychenkv.simulation.ui.TerminalDisplay;
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

        SimulationUi ui = new SimulationUi(
                new TerminalDisplay(terminal),
                new InputEventBus(terminal.reader())
        );

        Simulation simulation = new DefaultSimulationFactory(
                SimulationConfig.withDefaults(),
                new ConsoleMapRendererFactory(),
                ui
        ).createSimulation();

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
