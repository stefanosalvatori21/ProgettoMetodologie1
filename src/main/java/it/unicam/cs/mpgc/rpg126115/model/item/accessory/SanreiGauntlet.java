package it.unicam.cs.mpgc.rpg126115.model.item.accessory;

import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.item.Accessory;

public class SanreiGauntlet extends Accessory {
    private static final int HP_PENALTY = 10;

    @Override public String getName() { return "Guanto di Sanrei"; }

    @Override public String getDescription() {
        return "Riduce il costo REI abilità del 20%, ma -10 HP max (solo Quincy).";
    }

    @Override
    public void applyBonus(Stats stats) {
        stats.setMaxHp(stats.getMaxHp() - HP_PENALTY);
    }

    @Override
    public void removeBonus(Stats stats) {
        stats.setMaxHp(stats.getMaxHp() + HP_PENALTY);
    }
}
