package it.unicam.cs.mpgc.rpg126115.model.item.accessory;

import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.item.Accessory;

public class ShinigamiBadge extends Accessory {
    private static final int ATK_BONUS = 5;

    @Override public String getName() { return "Badge da Shinigami"; }

    @Override public String getDescription() {
        return "+5 ATK e abilita le trasformazioni in battaglia.";
    }

    @Override
    public void applyBonus(Stats stats) {
        stats.setAttack(stats.getAttack() + ATK_BONUS);
    }

    @Override
    public void removeBonus(Stats stats) {
        stats.setAttack(stats.getAttack() - ATK_BONUS);
    }
}
