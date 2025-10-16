package com.bychenkv.simulation.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimulationEventBus {
    private final List<SimulationEventListener> listeners = new CopyOnWriteArrayList<>();

    public void addListener(SimulationEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SimulationEventListener listener) {
        listeners.remove(listener);
    }

    public void notifyIterationCompleted(int iteration, String renderedMap) {
        for (SimulationEventListener listener : listeners) {
            listener.onIterationCompleted(iteration, renderedMap);
        }
    }

    public void notifyStatusChanged(SimulationStatus status) {
        for (SimulationEventListener listener : listeners) {
            listener.onStatusChanged(status);
        }
    }
}
