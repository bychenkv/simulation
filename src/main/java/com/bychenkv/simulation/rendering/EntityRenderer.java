package com.bychenkv.simulation.rendering;

import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.Herbivore;
import com.bychenkv.simulation.entity.object.Grass;
import com.bychenkv.simulation.entity.object.Rock;
import com.bychenkv.simulation.entity.object.Tree;

public interface EntityRenderer {
    String render(Entity entity);
}
