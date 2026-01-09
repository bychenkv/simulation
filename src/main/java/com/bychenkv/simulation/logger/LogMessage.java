package com.bychenkv.simulation.logger;

public record LogMessage(long timestamp, LogLevel level, String message) {
    public LogMessage(LogLevel level, String message) {
        this(System.currentTimeMillis(), level, message);
    }
}