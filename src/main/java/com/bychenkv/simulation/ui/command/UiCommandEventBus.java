package com.bychenkv.simulation.ui.command;

import com.bychenkv.simulation.logger.SimulationLogger;
import org.jline.utils.NonBlockingReader;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UiCommandEventBus implements Closeable {
    private static final String INPUT_THREAD_NAME = "InputThread";
    private static final long SHUTDOWN_TIMEOUT_MS = 5000L;
    private static final long READ_TIMEOUT_MS = 10L;
    private static final int NO_INPUT_SIGNAL = -2;

    private final NonBlockingReader reader;
    private final SimulationLogger logger;

    private final List<Listener> listeners;
    private final ExecutorService executor;
    private volatile boolean running;

    public UiCommandEventBus(NonBlockingReader reader, SimulationLogger logger) {
        this.reader = reader;
        this.logger = logger;

        listeners = new CopyOnWriteArrayList<>();
        executor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, INPUT_THREAD_NAME);
            t.setDaemon(true);
            return t;
        });
    }

    public void startListening() {
        if (running) {
            logger.warn("InputEventBus already running");
            return;
        }
        running = true;
        executor.submit(this::runListeningLoop);
    }

    private void runListeningLoop() {
        while (running) {
            try {
                int keyCode = reader.read(READ_TIMEOUT_MS);
                if (keyCode != NO_INPUT_SIGNAL) {
                    UiCommand.fromKeyCode(keyCode)
                            .ifPresent(this::notifyListeners);
                }
            } catch (IOException e) {
                logger.error("IO error: " + e.getMessage());
                break;
            } catch (Exception e) {
                logger.error("Unexpected error in input: " + e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        running = false;
        executor.shutdown();
        try {
            if (!executor.awaitTermination(SHUTDOWN_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public interface Listener {
        void onCommandReceived(UiCommand command);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(UiCommand command) {
        for (Listener listener : listeners) {
            try {
                listener.onCommandReceived(command);
            } catch (Exception e) {
                logger.error("Listener error: " + e.getMessage());
            }
        }
    }
}
