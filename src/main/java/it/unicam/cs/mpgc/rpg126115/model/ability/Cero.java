package it.unicam.cs.mpgc.rpg126115.model.ability;

import it.unicam.cs.mpgc.rpg126115.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;

import java.util.Random;

public class Cero implements Ability {
    private static final int COST = 40;
    private static final double MULTIPLIER = 2.8;
    private static final int MIN_LEVEL = 1;
    private static final double BURN_CHANCE = 0.30;
    private static final Random RANDOM = new Random();

    @Override public String getName() { return "Cero"; }

    @Override public String getDescription() {
        return "Raggio di energia devastante ad area. 30% di probabilità di BURNING. (Costo: " + COST + " REI)";
    }

    @Override public int getReiatsuCost() { return COST; }

    @Override public AbilityEffect getEffect() { return AbilityEffect.AOE_DAMAGE; }

    @Override
    public boolean isAvailable(int level, TransformationState state) { return level >= MIN_LEVEL; }

    @Override
    public CombatResult execute(GameCharacter user, GameCharacter target) {
        int rawDamage = (int)(user.computeAttackDamage() * MULTIPLIER);
        int actualDamage = target.getStats().takeDamage(rawDamage);
        StatusEffect status = RANDOM.nextDouble() < BURN_CHANCE ? StatusEffect.BURNING : null;
        String narrative = user.getName() + " carica il Cero! " + actualDamage + " danni"
                + (status != null ? " — il nemico prende fuoco!" : "!");
        return new CombatResult(actualDamage, COST, status, !target.isAlive(), narrative);
    }
}
