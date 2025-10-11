package com.bychenkv.simulation.services.input;

import java.util.Arrays;
import java.util.Optional;

public enum UserInputEvent {
    STOP("q"),
    TOGGLE_PAUSE("");

    private final String input;

    UserInputEvent(String input) {
        this.input = input;
    }

    public static Optional<UserInputEvent> fromInput(String input) {
        return Arrays.stream(values())
                .filter(event -> event.input.equalsIgnoreCase(input))
                .findFirst();
    }
}
