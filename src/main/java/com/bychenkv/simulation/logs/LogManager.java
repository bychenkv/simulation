package com.bychenkv.simulation.logs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class LogManager {
    private final Deque<LogEntry> logs;
    private final int maxSize;
    private final DateTimeFormatter timeFormatter;
    private volatile boolean enableTimestamps;

    public LogManager(int maxSize) {
        this.maxSize = maxSize;
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        enableTimestamps = true;
        logs = new ConcurrentLinkedDeque<>();
    }

    public void log(String message) {
        log(LogLevel.INFO, message);
    }

    public void log(LogLevel level, String message) {
        LogEntry logEntry = new LogEntry(LocalDateTime.now(), level, message);
        logs.addLast(logEntry);

        while (logs.size() > maxSize) {
            logs.pollFirst();
        }
    }

    public List<String> getRecentLogs(int count) {
        return logs.stream()
                .skip(Math.max(0, logs.size() - count))
                .map(this::formatLogEntry)
                .toList();
    }

    private String formatLogEntry(LogEntry entry) {
        StringBuilder sb = new StringBuilder();
        if (enableTimestamps) {
            sb.append("[")
                    .append(entry.timestamp().format(timeFormatter))
                    .append("] ");
        }
        if (entry.level() != LogLevel.INFO) {
            sb.append("[")
                    .append(entry.level())
                    .append("] ");
        }
        sb.append(entry.message());
        return sb.toString();
    }

    public void clear() {
        logs.clear();
        log(LogLevel.INFO, "Logs cleared");
    }

    public void setEnableTimestamps(boolean enable) {
        this.enableTimestamps = enable;
    }
}
