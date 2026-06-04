package it.unicam.cs.mpgc.rpg126115.model.equipment;

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
}
