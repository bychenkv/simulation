package com.bychenkv.simulation.config;

public record AppConfig(
        int maxLogHistorySize,
        long iterationDelayMs,
        int shutdownTimeoutMs
) {
    private static final int DEFAULT_MAX_LOG_HISTORY_SIZE = 100;
    private static final long DEFAULT_ITERATION_DELAY_MS = 500L;
    private static final int DEFAULT_SHUTDOWN_TIMEOUT_MS = 5000;

    public static AppConfig defaults() {
        return new AppConfig(
                DEFAULT_MAX_LOG_HISTORY_SIZE,
                DEFAULT_ITERATION_DELAY_MS,
                DEFAULT_SHUTDOWN_TIMEOUT_MS
        );
    }
}
