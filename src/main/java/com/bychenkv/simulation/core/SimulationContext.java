package com.bychenkv.simulation.core;

import com.bychenkv.simulation.map.SimulationMap;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SimulationContext {
    private final SimulationMap map;
    private final AtomicReference<SimulationStatus> status;
    private final AtomicInteger iterationCount;

    public SimulationContext(SimulationMap map) {
        this.map = map;
        status = new AtomicReference<>(SimulationStatus.STOPPED);
        iterationCount = new AtomicInteger(0);
    }

    public SimulationMap getMap() {
        return map;
    }

    public int incrementIterationCount() {
        return iterationCount.incrementAndGet();
    }

    public void resetIterationCount() {
        iterationCount.set(0);
    }

    public boolean isPaused() {
        return status.get() == SimulationStatus.PAUSED;
    }

    public boolean isStopped() {
        return status.get() == SimulationStatus.STOPPED;
    }

    public boolean isRunning() {
        return status.get() == SimulationStatus.RUNNING;
    }

    public void setStatus(SimulationStatus newStatus) {
        status.set(newStatus);
    }

    public synchronized void waitIfPaused() throws InterruptedException {
        while (isPaused()) {
            wait();
        }
    }

    public synchronized void notifyResumed() {
        notifyAll();
    }
}
