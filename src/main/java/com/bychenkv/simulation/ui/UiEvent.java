package com.bychenkv.simulation.ui;

import java.util.Arrays;
import java.util.Optional;

public enum UiEvent {
    STOP("q"),
    TOGGLE_PAUSE("");

    private final String input;

    UiEvent(String input) {
        this.input = input;
    }

    public static Optional<UiEvent> fromInput(String input) {
        return Arrays.stream(values())
                .filter(event -> event.input.equalsIgnoreCase(input))
                .findFirst();
    }

    static Optional<UiEvent> fromKeyCode(int keyCode) {
        return switch (keyCode) {
            case 'q', 'Q', 27 -> Optional.of(STOP);
            case ' ', '\r', '\n', 'p', 'P' -> Optional.of(TOGGLE_PAUSE);
            default -> Optional.empty();
        };
    }
}
