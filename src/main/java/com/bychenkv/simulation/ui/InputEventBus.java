package com.bychenkv.simulation.ui;

import org.jline.utils.NonBlockingReader;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InputEventBus implements Closeable {
    private static final String INPUT_THREAD_NAME = "InputThread";
    private static final int INPUT_THREAD_JOIN_TIMEOUT_MS = 1000;

    private final NonBlockingReader reader;
    private final List<UiCommandListener> listeners = new CopyOnWriteArrayList<>();

    private Thread inputThread;
    private volatile boolean running;

    public InputEventBus(NonBlockingReader reader) {
        this.reader = reader;
    }

    public void startListening() {
        if (running) return;

        running = true;

        inputThread = new Thread(this::runListeningLoop, INPUT_THREAD_NAME);
        inputThread.setDaemon(true);
        inputThread.start();
    }

    private void runListeningLoop() {
        try {
            while (running) {
                int keyCode = reader.read(10);
                if (keyCode != -2) {
                    UiCommand.fromKeyCode(keyCode)
                            .ifPresent(this::notifyListeners);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addListener(UiCommandListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UiCommandListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(UiCommand command) {
        for (UiCommandListener listener : listeners) {
            try {
                listener.onCommandReceived(command);
            } catch (Exception e) {
                System.err.println("Listener error: " + e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        running = false;

        if (inputThread != null && inputThread.isAlive()) {
            inputThread.interrupt();
            try {
                inputThread.join(INPUT_THREAD_JOIN_TIMEOUT_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
