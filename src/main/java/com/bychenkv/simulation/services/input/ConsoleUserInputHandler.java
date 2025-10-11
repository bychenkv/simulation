package com.bychenkv.simulation.services.input;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConsoleUserInputHandler implements UserInputHandler {
    private static final String LISTENER_THREAD_NAME = "InputHandlerThread";
    private static final int LISTENER_THREAD_JOIN_TIMEOUT_MS = 1000;

    private Scanner scanner;
    private Thread listenerThread;
    private volatile boolean isListening;
    private final List<UserInputEventListener> eventListeners = new CopyOnWriteArrayList<>();

    @Override
    public void startListen() {
         if (isListening) return;

        isListening = true;
        scanner = new Scanner(System.in);

        listenerThread = new Thread(this::listen, LISTENER_THREAD_NAME);
        listenerThread.start();
    }

    private void listen() {
        while (isListening && !Thread.currentThread().isInterrupted()) {
            try {
                String input = scanner.nextLine().trim();
                UserInputEvent.fromInput(input).ifPresent(this::notifyEventListeners);
            } catch (NoSuchElementException | IllegalStateException e) {
                break;
            }
        }
    }

    @Override
    public void stopListen() {
        isListening = false;

        if (listenerThread != null && listenerThread.isAlive()) {
            listenerThread.interrupt();
            try {
                listenerThread.join(LISTENER_THREAD_JOIN_TIMEOUT_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void addEventListener(UserInputEventListener listener) {
        eventListeners.add(listener);
    }

    @Override
    public void removeEventListener(UserInputEventListener listener) {
        eventListeners.remove(listener);
    }

    private void notifyEventListeners(UserInputEvent event) {
        for (UserInputEventListener listener : eventListeners) {
            try {
                listener.onInputEventReceived(event);
            } catch (Exception e) {
                System.err.println("Listener error: " + e.getMessage());
            }
        }
    }
}
