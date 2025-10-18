package com.bychenkv.simulation.services.logger;

public record LogMessage(long timestamp, LogLevel level, String message) {
    public LogMessage(LogLevel level, String message) {
        this(System.currentTimeMillis(), level, message);
    }
}