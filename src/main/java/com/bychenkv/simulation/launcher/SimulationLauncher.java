package com.bychenkv.simulation.launcher;

import com.bychenkv.simulation.core.simulation.SimulationController;
import com.bychenkv.simulation.logger.SimulationLogger;
import com.bychenkv.simulation.ui.SimulationUi;
import com.bychenkv.simulation.ui.command.UiCommandEventBus;
import com.bychenkv.simulation.ui.command.UiCommand;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationLauncher implements UiCommandEventBus.Listener, AutoCloseable {
    private static final String SIMULATION_THREAD_NAME = "SimulationThread";

    private final SimulationController controller;
    private final SimulationUi ui;
    private final SimulationLogger logger;
    private final int shutdownTimeoutMs;

    private final ExecutorService executorService;
    private final CountDownLatch terminationLatch;

    public SimulationLauncher(
            SimulationController controller,
            SimulationUi ui,
            SimulationLogger logger,
            int shutdownTimeoutMs
    ) {
        this.controller = controller;
        this.ui = ui;
        this.logger = logger;
        this.shutdownTimeoutMs = shutdownTimeoutMs;

        executorService = Executors.newSingleThreadExecutor(
                r -> new Thread(r, SIMULATION_THREAD_NAME)
        );
        terminationLatch = new CountDownLatch(1);
    }

    public void launch() {
        ui.start();
        ui.addEventListener(this);

        executorService.submit(() -> {
            try {
                controller.start();
            } catch (Exception e) {
                logger.error("Fatal simulation error: " + e.getMessage());
            }
        });
    }

    public void awaitTermination() throws InterruptedException {
        terminationLatch.await();
    }

    @Override
    public void close() {
        ui.removeEventListener(this);
        ui.close();

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(shutdownTimeoutMs, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onCommandReceived(UiCommand command) {
        switch (command) {
            case STOP -> stop();
            case TOGGLE_PAUSE -> controller.togglePause();
            case SCROLL_LEFT -> ui.scrollMap(-1, 0);
            case SCROLL_UP -> ui.scrollMap(0, -1);
            case SCROLL_RIGHT -> ui.scrollMap(1, 0);
            case SCROLL_DOWN -> ui.scrollMap(0, 1);
        }
    }

    private void stop() {
        controller.stop();
        close();
        terminationLatch.countDown();
    }
}
