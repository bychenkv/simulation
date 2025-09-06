package com.bychenkv.simulation;

public class Startup {
    private static final int DEFAULT_ROWS = 10;
    private static final int DEFAULT_COLUMNS = 10;

    public static void main(String[] args) {
        Simulation simulation = new Simulation(DEFAULT_ROWS, DEFAULT_COLUMNS);
        simulation.start();
    }
}
