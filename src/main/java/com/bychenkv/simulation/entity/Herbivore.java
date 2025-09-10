package com.bychenkv.simulation.entity;

import com.bychenkv.simulation.Coordinate;
import com.bychenkv.simulation.Map;

public class Herbivore extends Creature {
    public Herbivore(int maxHp, int speed, int hpRestoreRate) {
        super(maxHp, speed);
        this.hpRestoreRate = hpRestoreRate;
    }

    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    public boolean isDead() {
        return currentHp == 0;
    }

    @Override
    public boolean canConsume(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    protected void consumeResource(Map map, Coordinate resourceCoordinate) {
        map.removeEntity(resourceCoordinate);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC07";
    }
}
