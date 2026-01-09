package com.bychenkv.simulation.ui.sections;

import com.bychenkv.simulation.logger.LogMessage;
import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.layout.UiLayoutPosition;
import org.jline.utils.AttributedStyle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LogSection implements UiSection {
    private static final int MAX_DISPLAYED_LOGS = 20;
    private static final int HEIGHT = MAX_DISPLAYED_LOGS + 2;
    public static final int MAX_LENGTH = 1000;

    private final Deque<LogMessage> displayedLogs;

    public LogSection() {
        displayedLogs = new ArrayDeque<>(MAX_DISPLAYED_LOGS);
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void renderAt(UiLayoutPosition position, Display display) {
        int currentRow = position.row();

        display.clearLine(currentRow++);
        display.printWithStyle("=== Logs ===", AttributedStyle.BOLD);

        display.clearLine(currentRow++);
        display.println("─".repeat(60));

        List<LogMessage> logs = new ArrayList<>(displayedLogs);
        for (int i = 0; i < MAX_DISPLAYED_LOGS; i++) {
            display.clearLine(currentRow++);

            if (i < logs.size()) {
                LogMessage log = logs.get(i);
                display.printWithStyle(
                        formatLogMessage(log),
                        log.level().getStyle()
                );
            }
        }
    }

    public void addLog(LogMessage message) {
        displayedLogs.addLast(message);

        if (displayedLogs.size() > MAX_DISPLAYED_LOGS) {
            displayedLogs.removeFirst();
        }
    }

    private String formatLogMessage(LogMessage log) {
        String timestamp = formatTimestamp(log.timestamp());
        return String.format("%s [%s] %s",
                timestamp,
                log.level().getPrefix(),
                truncate(log.message(), MAX_LENGTH)
        );
    }

    private String formatTimestamp(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;

        if (diff < 1000) {
            return "now";
        } else if (diff < 60000) {
            return (diff / 1000) + "s ago";
        } else {
            return (diff / 60000) + "m ago";
        }
    }

    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}
