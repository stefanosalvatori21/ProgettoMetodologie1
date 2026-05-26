package it.unicam.cs.mpgc.rpg126115.model.ability;

import it.unicam.cs.mpgc.rpg126115.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;

public class HeiligPfeil implements Ability {
    private static final int COST = 25;
    private static final double MULTIPLIER = 3.8;

    @Override public String getName() { return "Heilig Pfeil"; }

    @Override public String getDescription() {
        return "Freccia sacra di Reishi puro. Alto danno singolo da distanza. (Costo: " + COST + " REI)";
    }

    @Override public int getReiatsuCost() { return COST; }

    @Override public AbilityEffect getEffect() { return AbilityEffect.SINGLE_DAMAGE; }

    @Override
    public boolean isAvailable(int level, TransformationState state) { return true; }

    @Override
    public CombatResult execute(GameCharacter user, GameCharacter target) {
        int rawDamage = (int)(user.computeAttackDamage() * MULTIPLIER);
        int actualDamage = target.getStats().takeDamage(rawDamage);
        return new CombatResult(actualDamage, COST, null, !target.isAlive(),
                user.getName() + " scocca l'Heilig Pfeil! " + actualDamage + " danni!");
    }
}
