package com.bychenkv.simulation;

import com.bychenkv.simulation.config.AppConfig;
import com.bychenkv.simulation.config.MapSectionConfig;
import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.launcher.SimulationLauncher;
import com.bychenkv.simulation.launcher.SimulationLauncherFactory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SimulationApp {
    public static void main(String[] args) {
        int statusCode = runApp(args);
        System.exit(statusCode);
    }

    private static int runApp(String[] ignoredArgs) {
        try (Terminal terminal = createTerminal()) {
            try (SimulationLauncher launcher = createLauncherFactory(terminal).createLauncher()) {
                launcher.launch();
                launcher.awaitTermination();
                return 0;
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize terminal: " + e.getMessage());
            return 1;
        } catch (Exception e) {
            System.err.println("Simulation app failed: " + e.getMessage());
            return 2;
        }
    }

    private static Terminal createTerminal() throws IOException {
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .encoding(StandardCharsets.UTF_8)
                .build();

        terminal.enterRawMode();
        return terminal;
    }

    private static SimulationLauncherFactory createLauncherFactory(Terminal terminal) {
        return new SimulationLauncherFactory(
                SimulationConfig.defaults(),
                AppConfig.defaults(),
                MapSectionConfig.defaults(),
                terminal
        );
    }
}
