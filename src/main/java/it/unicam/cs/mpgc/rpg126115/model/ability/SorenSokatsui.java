package it.unicam.cs.mpgc.rpg126115.model.ability;

import it.unicam.cs.mpgc.rpg126115.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;

public class SorenSokatsui implements Ability {
    private static final int COST = 50;
    private static final double MULTIPLIER = 2.8;
    private static final int MIN_LEVEL = 10;

    @Override public String getName() { return "Soren Sokatsui"; }

    @Override public String getDescription() {
        return "Due onde di energia celeste — danno ad area + WEAKENED. Richiede Shikai. (Costo: " + COST + " REI)";
    }

    @Override public int getReiatsuCost() { return COST; }

    @Override public AbilityEffect getEffect() { return AbilityEffect.AOE_DAMAGE; }

    @Override
    public boolean isAvailable(int level, TransformationState state) {
        return level >= MIN_LEVEL && state != TransformationState.NORMAL;
    }

    @Override
    public CombatResult execute(GameCharacter user, GameCharacter target) {
        int rawDamage = (int)(user.computeAttackDamage() * MULTIPLIER);
        int actualDamage = target.getStats().takeDamage(rawDamage);
        return new CombatResult(actualDamage, COST, StatusEffect.WEAKENED, !target.isAlive(),
                user.getName() + " lancia Soren Sokatsui! " + actualDamage + " danni — il nemico è INDEBOLITO!");
    }
}
