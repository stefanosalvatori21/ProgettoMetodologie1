package it.unicam.cs.mpgc.rpg126115.model.item;

import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public class ReiatsuDrink extends Consumable {

    private final String name;
    private final int restoreAmount;

    public ReiatsuDrink(String name, int restoreAmount) {
        this.name = name;
        this.restoreAmount = restoreAmount;
    }

    @Override public String getName()        { return name; }
    @Override public String getDescription() { return "Ripristina " + restoreAmount + " Reiatsu."; }

    @Override
    public String use(Player player, GameCharacter target) {
        player.getStats().restoreReiatsu(restoreAmount);
        return player.getName() + " usa " + name + " e recupera " + restoreAmount + " Reiatsu!";
    }
}
