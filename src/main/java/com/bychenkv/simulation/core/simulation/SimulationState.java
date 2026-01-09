package com.bychenkv.simulation.core.simulation;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationState {
    private final Lock lock;
    private final Condition pausedCondition;
    private volatile SimulationStatus status;
    private final AtomicInteger iterationCount;

    public SimulationState() {
        lock = new ReentrantLock();
        pausedCondition = lock.newCondition();
        status = SimulationStatus.STOPPED;
        iterationCount = new AtomicInteger(0);
    }

    public void setStatus(SimulationStatus status) {
        lock.lock();
        try {
            this.status = status;
            if (status != SimulationStatus.PAUSED) {
                pausedCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean isPaused() {
        return status == SimulationStatus.PAUSED;
    }

    public boolean isStopped() {
        return status == SimulationStatus.STOPPED;
    }

    public boolean isRunning() {
        return status == SimulationStatus.RUNNING;
    }

    public int incrementIterationCount() {
        return iterationCount.incrementAndGet();
    }

    public void resetIterationCount() {
        iterationCount.set(0);
    }

    public void waitIfPaused() throws InterruptedException {
        lock.lock();
        try {
            while (isPaused()) {
                pausedCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }
}
