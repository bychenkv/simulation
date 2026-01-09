package com.bychenkv.simulation.ui.rendering;

import com.bychenkv.simulation.core.entity.Entity;
import com.bychenkv.simulation.core.entity.creature.Herbivore;
import com.bychenkv.simulation.core.entity.creature.Predator;
import com.bychenkv.simulation.core.entity.object.Grass;
import com.bychenkv.simulation.core.entity.object.Rock;
import com.bychenkv.simulation.core.entity.object.Tree;

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