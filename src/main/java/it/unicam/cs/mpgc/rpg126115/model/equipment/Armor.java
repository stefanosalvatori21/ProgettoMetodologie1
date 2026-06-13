package it.unicam.cs.mpgc.rpg126115.model.equipment;

import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;

public class Armor extends Equipment {
    private final int bonusDef;
    private final int bonusMaxHp;

    public Armor(String name, String description, String abilityName,
                 String imagePath, int bonusDef, int bonusMaxHp, Rarity rarity) {
        super(name, description, abilityName, imagePath, EquipmentSlot.ARMOR, rarity);
        this.bonusDef   = bonusDef;
        this.bonusMaxHp = bonusMaxHp;
    }

    public int getBonusDef()   { return bonusDef; }
    public int getBonusMaxHp() { return bonusMaxHp; }

    @Override
    public String getStatsSummary() {
        String s = "+" + bonusDef + " DEF";
        if (bonusMaxHp > 0) s += "  +" + bonusMaxHp + " HP";
        return s;
    }

    @Override
    public void applyBonus(Stats stats) {
        stats.setDefense(stats.getDefense() + bonusDef);
        stats.setMaxHp(stats.getMaxHp() + bonusMaxHp);
        stats.setHp(stats.getHp() + bonusMaxHp);
    }

    @Override
    public void removeBonus(Stats stats) {
        stats.setDefense(Math.max(0, stats.getDefense() - bonusDef));
        int newMax = Math.max(1, stats.getMaxHp() - bonusMaxHp);
        stats.setMaxHp(newMax);
        stats.setHp(Math.min(stats.getHp(), newMax));
    }
}
