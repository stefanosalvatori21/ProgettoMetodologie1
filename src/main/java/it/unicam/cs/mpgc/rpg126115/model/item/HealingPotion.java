package it.unicam.cs.mpgc.rpg126115.model.item;

import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public class HealingPotion extends Consumable {

    private final String name;
    private final int healAmount;

    public HealingPotion(String name, int healAmount) {
        this.name = name;
        this.healAmount = healAmount;
    }

    @Override public String getName()        { return name; }
    @Override public String getDescription() { return "Ripristina " + healAmount + " HP."; }

    @Override
    public String use(Player player, GameCharacter target) {
        int actual = player.getStats().heal(healAmount);
        return player.getName() + " usa " + name + " e recupera " + actual + " HP!";
    }
}
