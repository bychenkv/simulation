package com.bychenkv.simulation.logs;

import java.time.LocalDateTime;

public record LogEntry(LocalDateTime timestamp, LogLevel level, String message) {
}
