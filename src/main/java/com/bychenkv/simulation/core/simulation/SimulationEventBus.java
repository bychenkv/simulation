package com.bychenkv.simulation.core.simulation;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimulationEventBus {
    private final List<Listener> listeners;

    public SimulationEventBus() {
        listeners = new CopyOnWriteArrayList<>();
    }

    public void notifyIterationCompleted(int iteration) {
        for (Listener listener : listeners) {
            listener.onIterationCompleted(iteration);
        }
    }

    public void notifyStatusChanged(SimulationStatus status) {
        for (Listener listener : listeners) {
            listener.onStatusChanged(status);
        }
    }

    public void notifyMapRendered() {
        for (Listener listener : listeners) {
            listener.onMapRendered();
        }
    }

    public interface Listener {
        void onIterationCompleted(int iteration);
        void onStatusChanged(SimulationStatus status);
        void onMapRendered();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}
