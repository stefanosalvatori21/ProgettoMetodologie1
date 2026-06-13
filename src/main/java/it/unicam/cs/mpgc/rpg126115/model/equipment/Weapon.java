package it.unicam.cs.mpgc.rpg126115.model.equipment;

import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;

public class Weapon extends Equipment {
    private final int bonusAtk;

    public Weapon(String name, String description, String abilityName,
                  String imagePath, int bonusAtk, Rarity rarity) {
        super(name, description, abilityName, imagePath, EquipmentSlot.WEAPON, rarity);
        this.bonusAtk = bonusAtk;
    }

    public int getBonusAtk() { return bonusAtk; }

    @Override
    public String getStatsSummary() { return "+" + bonusAtk + " ATK"; }

    @Override
    public void applyBonus(Stats stats) {
        stats.setAttack(stats.getAttack() + bonusAtk);
    }

    @Override
    public void removeBonus(Stats stats) {
        stats.setAttack(Math.max(1, stats.getAttack() - bonusAtk));
    }
}
