package com.bychenkv.simulation.core.action;

import java.util.List;

public record SimulationActions(List<Action> init, List<Action> turn) {
    public SimulationActions {
        init = List.copyOf(init);
        turn = List.copyOf(turn);
    }
}
