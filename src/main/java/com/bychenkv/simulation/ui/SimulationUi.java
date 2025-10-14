package com.bychenkv.simulation.ui;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationUi {
    private static final String INPUT_THREAD_NAME = "InputThread";
    private static final int INPUT_THREAD_JOIN_TIMEOUT_MS = 1000;

    private final Terminal terminal;
    private final PrintWriter writer;

    private final List<String> logs = new CopyOnWriteArrayList<>();
    private final int maxLogsNumber = 6;

    private final BlockingQueue<Integer> inputQueue = new LinkedBlockingQueue<>();
    private Thread inputThread;
    private volatile boolean inputListening;

    private final List<UiEventListener> eventListeners = new CopyOnWriteArrayList<>();

    public SimulationUi(Terminal terminal) {
        this.terminal = terminal;
        this.writer = terminal.writer();

        terminal.enterRawMode();
        clearScreen();
    }

    public void startInputListening() {
        if (inputListening) return;

        inputListening = true;

        inputThread = new Thread(this::listenInputNonBlocking, INPUT_THREAD_NAME);
        inputThread.setDaemon(true);
        inputThread.start();
    }

    private void listenInputNonBlocking() {
        NonBlockingReader reader = terminal.reader();

        try {
            while (inputListening) {
                int c = reader.read(10);
                if (c != -2) {
                    inputQueue.offer(c);
                    UiEvent.fromKeyCode(c)
                            .ifPresent(this::notifyEventListeners);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearScreen() {
        writer.print("\033[H\033[2J");
        writer.flush();
    }

    private void moveCursorHome() {
        writer.print("\033[H");
    }

    public void update(String renderedMap, int iteration, boolean isPaused) {
        moveCursorHome();

        printColored(
                "=== Simulation: Iteration #%d ===\n".formatted(iteration),
                AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)
        );
        writer.println();

        writer.println(renderedMap);
        writer.println();

        AttributedStyle statusStyle = isPaused ?
                AttributedStyle.BOLD.foreground(AttributedStyle.RED) :
                AttributedStyle.BOLD.foreground(AttributedStyle.GREEN);

        String status = isPaused ? "PAUSED" : "RUNNING";
        printColored(status + "\n", statusStyle);

        printColored(
                "Controls: [Space/Enter] pause/resume | [Q] quit\n",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN)
        );

        writer.println("─".repeat(60));

        printColored(
                "Recent Logs:\n",
                AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW)
        );

        List<String> recentLogs = getRecentLogs();
        if (recentLogs.isEmpty()) {
            printColored("  (no logs yet)\n", AttributedStyle.DEFAULT.faint());
        } else {
            for (String log : recentLogs) {
                writer.println("  › " + log);
            }
        }

        writer.print("\033[J");
        writer.flush();
    }

    private void printColored(String text, AttributedStyle style) {
        AttributedString as = new AttributedString(text, style);
        writer.print(as.toAnsi(terminal));
    }

    public void log(String message) {
        String timestamp = String.format("[%tT]", System.currentTimeMillis());
        logs.add(timestamp + " " + message);

        while (logs.size() > 100) {
            logs.removeFirst();
        }
    }

    private List<String> getRecentLogs() {
        int from = Math.max(0, logs.size() - maxLogsNumber);
        return new ArrayList<>(logs.subList(from, logs.size()));
    }

    public void stopInputListening() {
        inputListening = false;

        if (inputThread != null && inputThread.isAlive()) {
            inputThread.interrupt();
            try {
                inputThread.join(INPUT_THREAD_JOIN_TIMEOUT_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void addEventListener(UiEventListener listener) {
        eventListeners.add(listener);
    }

    public void removeEventListener(UiEventListener listener) {
        eventListeners.remove(listener);
    }

    private void notifyEventListeners(UiEvent event) {
        for (UiEventListener listener : eventListeners) {
            try {
                listener.onUiEventReceived(event);
            } catch (Exception e) {
                System.err.println("Listener error: " + e.getMessage());
            }
        }
    }
}
