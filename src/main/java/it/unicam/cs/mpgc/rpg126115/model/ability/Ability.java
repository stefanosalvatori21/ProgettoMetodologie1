package it.unicam.cs.mpgc.rpg126115.model.ability;

import it.unicam.cs.mpgc.rpg126115.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;

public interface Ability {
    String getName();
    String getDescription();
    int getReiatsuCost();
    AbilityEffect getEffect();
    boolean isAvailable(int level, TransformationState state);
    CombatResult execute(GameCharacter user, GameCharacter target);
}
