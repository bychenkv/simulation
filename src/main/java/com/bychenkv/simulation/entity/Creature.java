package com.bychenkv.simulation.entity;

import com.bychenkv.simulation.Coordinate;
import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.utils.ResourceFinder;

import java.util.List;

public abstract class Creature extends Entity {
    protected final int speed;
    protected final int maxHp;

    protected int currentHp;
    protected int hpRestoreRate;

    public Creature(int maxHp, int speed) {
        this.maxHp = maxHp;
        this.speed = speed;
        currentHp = maxHp;
    }

    public abstract boolean canConsume(Entity entity);

    public void makeMove(Map map, Coordinate currentCoordinate) {
        Coordinate resourceCoordinate = findResource(map, currentCoordinate);

        if (resourceCoordinate != null) {
            consumeResource(map, resourceCoordinate);
            restoreHp();
        } else {
            ResourceFinder resourceFinder = new ResourceFinder(map);
            List<Coordinate> path = resourceFinder.findPath(currentCoordinate);
            moveAlongPath(map, path);
        }
    }

    protected abstract void consumeResource(Map map, Coordinate resourceCoordinate);

    protected void restoreHp() {
        currentHp = Math.min(maxHp, currentHp + hpRestoreRate);
    }

    private Coordinate findResource(Map map, Coordinate currentCoordinate) {
        Coordinate resourceCoordinate = null;

        for (Coordinate neighbor : currentCoordinate.getNeighbors()) {
            if (neighbor.withinMap(map)) {
                Entity entity = map.getEntity(neighbor);
                if (entity != null && canConsume(entity)) {
                    resourceCoordinate = neighbor;
                    break;
                }
            }
        }

        return resourceCoordinate;
    }

    private void moveAlongPath(Map map, List<Coordinate> path) {
        Coordinate nextCoordinate = speed >= path.size() - 1
                ? path.get(path.size() - 2)
                : path.get(speed);

        map.removeEntity(path.getFirst());
        map.addEntity(nextCoordinate, this);
    }
}
