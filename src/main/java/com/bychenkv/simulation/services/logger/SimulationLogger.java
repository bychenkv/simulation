package com.bychenkv.simulation.services.logger;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimulationLogger {
    private final List<Listener> listeners;
    private final Deque<LogMessage> history;
    private final int maxHistorySize;

    public SimulationLogger(int maxHistorySize) {
        this.maxHistorySize = maxHistorySize;

        history = new ConcurrentLinkedDeque<>();
        listeners = new CopyOnWriteArrayList<>();
    }

    public void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public void info(String message) {
        log(message, LogLevel.INFO);
    }

    public void warn(String message) {
        log(message, LogLevel.WARNING);
    }

    public void error(String message) {
        log(message, LogLevel.ERROR);
    }

    private void log(String message, LogLevel level) {
        LogMessage logMessage = new LogMessage(level, message);

        history.addLast(logMessage);
        if (history.size() > maxHistorySize) {
            history.pollFirst();
        }

        notifyListeners(logMessage);
    }

    private void notifyListeners(LogMessage message) {
        for (Listener listener : listeners) {
            try {
                listener.onLogMessage(message);
            } catch (Exception e) {
                System.err.println("Notifying log listener failed: " + e.getMessage());
            }
        }
    }

    public interface Listener {
        void onLogMessage(LogMessage message);

    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}
