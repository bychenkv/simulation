package com.bychenkv.simulation.entity.creature;

import com.bychenkv.simulation.core.Path;
import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.utils.ResourceFinder;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Creature extends Entity {
    private final ResourceFinder resourceFinder;

    protected final int speed;
    protected final int maxHp;
    protected int currentHp;
    protected int hpRestoreRate;

    public Creature(int maxHp, int speed, ResourceFinder resourceFinder) {
        this.resourceFinder = resourceFinder;
        this.maxHp = maxHp;
        this.speed = speed;
        currentHp = maxHp;
    }

    public void makeMove(SimulationMap map, Position currentPosition) {
        Path path = resourceFinder.findPath(currentPosition, this::canConsume);

        switch (path.getLength()) {
            case 0 -> moveRandomly(map, currentPosition);
            case 1 -> {
                Position resourcePosition = path.getResourcePosition();
                consumeResourceAt(map, resourcePosition);
                restoreHp();
            }
            default -> {
                Position nextPosition = path.getNextPosition(speed);
                changePosition(map, currentPosition, nextPosition);
            }
        }
    }

    public abstract boolean canConsume(Entity entity);

    protected abstract void consumeResourceAt(SimulationMap map, Position resourcePosition);

    protected void restoreHp() {
        currentHp = Math.min(maxHp, currentHp + hpRestoreRate);
    }

    private void moveRandomly(SimulationMap map, Position currentPosition) {
        Position nextPosition = currentPosition;

        for (int i = 0; i < speed; i++) {
            List<Position> neighbors = currentPosition.getNeighborsWithinBounds(map.getHeight(), map.getWidth())
                    .stream()
                    .filter(p -> !map.isOccupied(p))
                    .toList();

            if (neighbors.isEmpty()) {
                break;
            }

            int randomIndex = ThreadLocalRandom.current().nextInt(neighbors.size());
            nextPosition = neighbors.get(randomIndex);
        }

        changePosition(map, currentPosition, nextPosition);
    }

    private void changePosition(SimulationMap map, Position currentPosition, Position nextPosition) {
        map.removeEntityAt(currentPosition);
        map.addEntity(nextPosition, this);

        System.out.println(this + " has moved from " + currentPosition + " to " + nextPosition);
    }
}
