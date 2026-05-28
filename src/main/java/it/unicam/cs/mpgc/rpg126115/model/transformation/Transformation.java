package it.unicam.cs.mpgc.rpg126115.model.transformation;

import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;

import java.util.List;

public class Transformation {
    private final String name;
    private final TransformationState state;
    private final double attackMult;
    private final double defenseMult;
    private final double reiatsuMult;
    private final List<Ability> unlockedAbilities;

    public Transformation(String name, TransformationState state,
                          double attackMult, double defenseMult, double reiatsuMult,
                          List<Ability> unlockedAbilities) {
        this.name = name;
        this.state = state;
        this.attackMult = attackMult;
        this.defenseMult = defenseMult;
        this.reiatsuMult = reiatsuMult;
        this.unlockedAbilities = List.copyOf(unlockedAbilities);
    }

    public String getName() { return name; }
    public TransformationState getState() { return state; }
    public double getAttackMult() { return attackMult; }
    public double getDefenseMult() { return defenseMult; }
    public double getReiatsuMult() { return reiatsuMult; }
    public List<Ability> getUnlockedAbilities() { return unlockedAbilities; }
}
