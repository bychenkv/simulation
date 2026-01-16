package com.bychenkv.simulation.launcher;

import com.bychenkv.simulation.core.action.*;
import com.bychenkv.simulation.config.*;
import com.bychenkv.simulation.core.map.SimulationMap;
import com.bychenkv.simulation.core.simulation.SimulationController;
import com.bychenkv.simulation.core.simulation.SimulationEngine;
import com.bychenkv.simulation.core.simulation.SimulationEventBus;
import com.bychenkv.simulation.core.simulation.SimulationState;
import com.bychenkv.simulation.core.finder.BfsResourceFinder;
import com.bychenkv.simulation.logger.SimulationLogger;
import com.bychenkv.simulation.ui.rendering.ConsoleEntityRenderer;
import com.bychenkv.simulation.ui.rendering.EntityRenderer;
import com.bychenkv.simulation.ui.rendering.MapViewRenderer;
import com.bychenkv.simulation.ui.rendering.MapViewport;
import com.bychenkv.simulation.ui.command.UiCommandEventBus;
import com.bychenkv.simulation.ui.SimulationUi;
import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.display.TerminalDisplay;
import org.jline.terminal.Terminal;

import java.util.Objects;

public class SimulationLauncherFactory {
    private final SimulationConfig simulationConfig;
    private final AppConfig appConfig;
    private final MapSectionConfig mapSectionConfig;
    private final Terminal terminal;

    public SimulationLauncherFactory(SimulationConfig simulationConfig,
                                     AppConfig appConfig,
                                     MapSectionConfig mapSectionConfig,
                                     Terminal terminal) {
        this.simulationConfig = Objects.requireNonNull(simulationConfig);
        this.appConfig = Objects.requireNonNull(appConfig);
        this.mapSectionConfig = Objects.requireNonNull(mapSectionConfig);
        this.terminal = Objects.requireNonNull(terminal);
    }

    public SimulationLauncher createLauncher() {
        Dependencies dependencies = buildDependencies();

        SimulationController controller = createController(dependencies);
        SimulationUi ui = createUi(dependencies);

        dependencies.eventBus.addListener(ui);

        return new SimulationLauncher(
                controller,
                ui,
                dependencies.logger,
                appConfig.shutdownTimeoutMs()
        );
    }

    private Dependencies buildDependencies() {
        SimulationLogger logger = new SimulationLogger(appConfig.maxLogHistorySize());
        SimulationEventBus eventBus = new SimulationEventBus();
        SimulationState state = new SimulationState();
        SimulationMap map = new SimulationMap(mapSectionConfig.mapBounds());

        BfsResourceFinder resourceFinder = new BfsResourceFinder(map);
        SimulationActionsFactory actionsFactory = new SimulationActionsFactory(
                simulationConfig,
                logger,
                eventBus,
                resourceFinder
        );

        return new Dependencies(logger, eventBus, state, map, actionsFactory);
    }

    private SimulationController createController(Dependencies dependencies) {
        SimulationEngine engine = createEngine(dependencies);

        return new SimulationController(
                engine,
                dependencies.state,
                dependencies.eventBus,
                dependencies.logger,
                appConfig.iterationDelayMs()
        );
    }

    private SimulationEngine createEngine(Dependencies dependencies) {
        SimulationActions actions = dependencies.actionsFactory.createActions();

        return new SimulationEngine(
                dependencies.map,
                actions,
                dependencies.eventBus,
                dependencies.logger,
                dependencies.state
        );
    }

    private SimulationUi createUi(Dependencies dependencies) {
        Display display = new TerminalDisplay(terminal);
        UiCommandEventBus uiCommandEventBus = new UiCommandEventBus(
                terminal.reader(),
                dependencies.logger
        );

        MapViewport viewport = new MapViewport(mapSectionConfig);
        EntityRenderer entityRenderer = new ConsoleEntityRenderer();
        MapViewRenderer renderer = new MapViewRenderer(
                viewport,
                mapSectionConfig,
                dependencies.map,
                entityRenderer
        );

        return new SimulationUi(
                display,
                uiCommandEventBus,
                dependencies.logger,
                viewport,
                renderer
        );
    }

    private record Dependencies(
            SimulationLogger logger,
            SimulationEventBus eventBus,
            SimulationState state,
            SimulationMap map,
            SimulationActionsFactory actionsFactory
    ) {}
}
