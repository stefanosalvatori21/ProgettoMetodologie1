package it.unicam.cs.mpgc.rpg126115.model.ability;

import it.unicam.cs.mpgc.rpg126115.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;

public class GetsugaTensho implements Ability {
    private static final int COST = 30;
    private static final double MULTIPLIER = 3.5;
    private static final int MIN_LEVEL = 1;

    @Override public String getName() { return "Getsuga Tensho"; }

    @Override public String getDescription() {
        return "Concentra il Reiatsu sulla lama e scatena un'onda di energia oscura. (Costo: " + COST + " REI)";
    }

    @Override public int getReiatsuCost() { return COST; }

    @Override public AbilityEffect getEffect() { return AbilityEffect.SINGLE_DAMAGE; }

    @Override
    public boolean isAvailable(int level, TransformationState state) {
        return level >= MIN_LEVEL;
    }

    @Override
    public CombatResult execute(GameCharacter user, GameCharacter target) {
        int rawDamage = (int)(user.computeAttackDamage() * MULTIPLIER);
        int actualDamage = target.getStats().takeDamage(rawDamage);
        return new CombatResult(actualDamage, COST, null, !target.isAlive(),
                user.getName() + " urla: «Getsuga... TENSHO!» — " + actualDamage + " danni!");
    }
}
