package com.bychenkv.simulation;

public class MapRenderer {
    private final Map map;

    public MapRenderer(Map map) {
        this.map = map;
    }

    public void render() {
        for (int x = 0; x < map.getRows(); x++) {
            for (int y = 0; y < map.getColumns(); y++) {
                Entity entity = map.getEntity(x, y);
                if (entity != null) {
                    System.out.print(entity);
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
    }
}
