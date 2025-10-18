package com.bychenkv.simulation;

import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.core.*;
import com.bychenkv.simulation.services.logger.SimulationLogger;
import com.bychenkv.simulation.ui.InputEventBus;
import com.bychenkv.simulation.ui.SimulationUi;
import com.bychenkv.simulation.ui.TerminalDisplay;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class SimulationApp {
    private static final int MAX_LOG_HISTORY_SIZE = 100;

    public static void main(String[] args) {
        Terminal terminal;
        try {
            terminal = createTerminal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimulationConfig config = SimulationConfig.withDefaults();

        SimulationLogger logger = new SimulationLogger(MAX_LOG_HISTORY_SIZE);

        SimulationUi ui = new SimulationUi(
                new TerminalDisplay(terminal),
                new InputEventBus(terminal.reader()),
                config.mapBounds().height(),
                logger
        );
        SimulationEventBus eventBus = new SimulationEventBus();
        eventBus.addListener(ui);

        Simulation simulation = new DefaultSimulationFactory(config, eventBus, logger)
                .createSimulation();

        SimulationLauncher launcher = new SimulationLauncher(simulation, ui);
        launcher.launch();
    }

    private static Terminal createTerminal() throws IOException {
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .encoding("UTF-8")
                .build();

        terminal.enterRawMode();

        return terminal;
    }
}
