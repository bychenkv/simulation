package com.bychenkv.simulation.core;

public interface SimulationEventListener {
    void onIterationCompleted(int iteration, String renderedMap);
    void onStatusChanged(SimulationStatus status);
}
