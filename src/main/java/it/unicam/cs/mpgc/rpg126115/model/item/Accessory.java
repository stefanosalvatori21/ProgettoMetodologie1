package it.unicam.cs.mpgc.rpg126115.model.item;

import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;

public abstract class Accessory implements Item {
    private boolean equipped;

    @Override
    public ItemType getType() { return ItemType.ACCESSORY; }

    public abstract void applyBonus(Stats stats);

    public abstract void removeBonus(Stats stats);

    public boolean isEquipped() { return equipped; }

    public void setEquipped(boolean equipped) { this.equipped = equipped; }
}
