package it.unicam.cs.mpgc.rpg126115.model.item;

import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public abstract class Consumable implements Item {

    @Override
    public ItemType getType() { return ItemType.CONSUMABLE; }

    public abstract String use(Player player, GameCharacter target);
}
