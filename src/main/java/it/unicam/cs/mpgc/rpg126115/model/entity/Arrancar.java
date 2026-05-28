package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;
import it.unicam.cs.mpgc.rpg126115.model.ability.Cero;
import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.transformation.Transformation;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;

import java.util.List;

public class Arrancar extends Player {

    public Arrancar(String name) {
        // HP 250 · ATK 20 · DEF 18 · REI 50  — Tank: highest HP/DEF, moderate ATK
        super(name, new Stats(250, 20, 18, 50));
    }

    @Override
    public String getDisplayName() { return "Arrancar — " + getName(); }

    @Override
    public List<Ability> getStartingAbilities() {
        return List.of(new Cero());
    }

    @Override
    public Transformation getPartialTransformation() {
        return new Transformation("Resurrección", TransformationState.PARTIAL,
                1.8, 1.5, 1.5, List.of());
    }
}
