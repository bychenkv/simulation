package com.bychenkv.simulation.rendering;

import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.Herbivore;
import com.bychenkv.simulation.entity.creature.Predator;
import com.bychenkv.simulation.entity.object.Grass;
import com.bychenkv.simulation.entity.object.Rock;
import com.bychenkv.simulation.entity.object.Tree;

import java.util.Map;

public class ConsoleEntityRenderer implements EntityRenderer {
    private static final Map<Class<? extends Entity>, String> sprites = Map.of(
        Rock.class, "🪨",
        Tree.class, "🌳",
        Grass.class, "🌱",
        Herbivore.class, "🐇",
        Predator.class, "🐺"
    );

    @Override
    public String render(Entity entity) {
        String sprite = sprites.get(entity.getClass());
        if (sprite == null) {
            throw new IllegalArgumentException("Unsupported entity type");
        }
        return sprite;
    }
}