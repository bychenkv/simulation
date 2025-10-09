package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;

import java.util.List;

public record SimulationActions(
        List<Action> init,
        List<Action> turn
) {
    public SimulationActions {
        init = List.copyOf(init);
        turn = List.copyOf(turn);
    }
}
