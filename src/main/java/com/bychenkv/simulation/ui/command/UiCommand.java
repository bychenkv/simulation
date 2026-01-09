package com.bychenkv.simulation.ui.command;

import java.util.Optional;

public enum UiCommand {
    TOGGLE_PAUSE("Space", "Pause/resume simulation"),
    STOP("Q", "Quit"),
    SCROLL_LEFT("A", "Scroll left"),
    SCROLL_UP("W", "Scroll up"),
    SCROLL_RIGHT("D", "Scroll right"),
    SCROLL_DOWN("S", "Scroll down");

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
            case 'A', 'a' -> Optional.of(SCROLL_LEFT);
            case 'W', 'w' -> Optional.of(SCROLL_UP);
            case 'D', 'd' -> Optional.of(SCROLL_RIGHT);
            case 'S', 's' -> Optional.of(SCROLL_DOWN);
            default -> Optional.empty();
        };
    }
}
