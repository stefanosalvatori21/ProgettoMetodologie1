package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;
import it.unicam.cs.mpgc.rpg126115.model.ability.HeiligPfeil;
import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.transformation.Transformation;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;

import java.util.List;

public class Quincy extends Player {

    public Quincy(String name) {
        // HP 160 · ATK 25 · DEF 7 · REI 140  — Glass cannon: highest ATK, lowest HP/DEF
        super(name, new Stats(160, 25, 7, 140));
    }

    @Override
    public String getDisplayName() { return "Quincy — " + getName(); }

    @Override
    public List<Ability> getStartingAbilities() {
        return List.of(new HeiligPfeil());
    }

    @Override
    public Transformation getPartialTransformation() {
        return new Transformation("Vollständig", TransformationState.PARTIAL,
                1.4, 1.1, 2.0, List.of());
    }
}
