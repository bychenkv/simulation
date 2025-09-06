package com.bychenkv.simulation;

public class Simulation {
    private final MapRenderer mapRenderer;
    private int moveCounter;

    public Simulation(int rows, int columns) {
        Map map = new Map(rows, columns);
        mapRenderer = new MapRenderer(map);
    }

    public void start() {
        System.out.println("Start simulation...");

        while (true) {
            mapRenderer.render();
            moveCounter++;
            System.out.println("Move counter: " + moveCounter);
        }
    }
}
