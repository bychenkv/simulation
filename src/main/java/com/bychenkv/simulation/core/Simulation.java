package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.rendering.MapRenderer;

import java.util.List;

public class Simulation {
    private int iterationCount;
    private final SimulationMap map;
    private final MapRenderer mapRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    private Simulation(Builder builder) {
        this.map = builder.map;
        this.mapRenderer = builder.mapRenderer;
        this.initActions = builder.initActions;
        this.turnActions = builder.turnActions;
    }

    public static class Builder {
        private SimulationMap map;
        private MapRenderer mapRenderer;
        private List<Action> initActions;
        private List<Action> turnActions;

        public Builder withMap(SimulationMap map) {
            this.map = map;
            return this;
        }

        public Builder withRenderer(MapRenderer mapRenderer) {
            this.mapRenderer = mapRenderer;
            return this;
        }

        public Builder withInitActions(List<Action> initActions) {
            this.initActions = initActions;
            return this;
        }

        public Builder withTurnActions(List<Action> turnActions) {
            this.turnActions = turnActions;
            return this;
        }

        public Simulation build() {
            return new Simulation(this);
        }
    }

    public void start() {
        System.out.println("Start simulation...");

        for (Action initAction : initActions) {
            initAction.execute(map);
        }

        while (iterationCount < 10) {
            System.out.println("Move counter: " + iterationCount);
            mapRenderer.render();

            for (Action turnAction : turnActions) {
                turnAction.execute(map);
            }
            iterationCount++;
        }
    }
}
