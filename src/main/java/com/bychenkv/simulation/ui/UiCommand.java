package com.bychenkv.simulation.ui;

import java.util.Optional;

public enum UiCommand {
    TOGGLE_PAUSE("Space", "Pause/resume simulation"),
    STOP("Q", "Quit");

    private final String key;
    private final String description;

    UiCommand(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<UiCommand> fromKeyCode(int keyCode) {
        return switch (keyCode) {
            case 'q', 'Q', 27 -> Optional.of(STOP);
            case ' ', '\r', '\n', 'p', 'P' -> Optional.of(TOGGLE_PAUSE);
            default -> Optional.empty();
        };
    }
}
