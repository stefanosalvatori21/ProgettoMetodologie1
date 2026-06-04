package it.unicam.cs.mpgc.rpg126115.model.item.accessory;

import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.item.Accessory;

public class ConcealmentCloak extends Accessory {
    private static final int DEF_BONUS = 8;
    private static final int ATK_PENALTY = 3;

    @Override public String getName() { return "Mantello Occultante"; }

    @Override public String getDescription() { return "+8 DEF, -3 ATK."; }

    @Override
    public void applyBonus(Stats stats) {
        stats.setDefense(stats.getDefense() + DEF_BONUS);
        stats.setAttack(stats.getAttack() - ATK_PENALTY);
    }

    @Override
    public void removeBonus(Stats stats) {
        stats.setDefense(stats.getDefense() - DEF_BONUS);
        stats.setAttack(stats.getAttack() + ATK_PENALTY);
    }
}
