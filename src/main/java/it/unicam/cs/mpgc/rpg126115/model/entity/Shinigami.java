package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;
import it.unicam.cs.mpgc.rpg126115.model.ability.GetsugaTensho;
import it.unicam.cs.mpgc.rpg126115.model.ability.SorenSokatsui;
import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.transformation.Transformation;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;

import java.util.List;

public class Shinigami extends Player {

    public Shinigami(String name) {
        // HP 200 · ATK 18 · DEF 12 · REI 100  — Balanced all-rounder
        super(name, new Stats(200, 18, 12, 100));
    }

    @Override
    public String getDisplayName() { return "Shinigami — " + getName(); }

    @Override
    public List<Ability> getStartingAbilities() {
        return List.of(new GetsugaTensho());
    }

    @Override
    public Transformation getPartialTransformation() {
        return new Transformation("Shikai", TransformationState.PARTIAL,
                1.4, 1.3, 1.5, List.of(new SorenSokatsui()));
    }
}
