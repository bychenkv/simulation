package com.bychenkv.simulation.action;

import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.Map;

import java.util.Random;

public abstract class ArrangeEntitiesAction extends Action {
    private final int entitiesNumber;
    private final Random random;

    public ArrangeEntitiesAction(Map map, int entitiesNumber) {
        super(map);
        this.entitiesNumber = entitiesNumber;
        random = new Random();
    }

    @Override
    public void execute() {
        for (int i = 0; i < entitiesNumber; i++) {
            int x, y;
            do {
                x = random.nextInt(map.getRows());
                y = random.nextInt(map.getColumns());
            } while (map.getEntity(x, y) != null);

            map.addEntity(x, y, createEntity());
        }
    }

    public abstract Entity createEntity();
}
