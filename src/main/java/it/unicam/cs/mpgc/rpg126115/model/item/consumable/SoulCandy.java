package it.unicam.cs.mpgc.rpg126115.model.item.consumable;

import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;
import it.unicam.cs.mpgc.rpg126115.model.item.Consumable;

public class SoulCandy extends Consumable {
    private static final int HEAL_AMOUNT = 50;

    @Override public String getName() { return "Soul Candy"; }

    @Override public String getDescription() { return "+50 HP, rimuove tutti gli effetti negativi."; }

    @Override
    public String use(Player player, GameCharacter target) {
        int healed = player.getStats().heal(HEAL_AMOUNT);
        return "Hai usato una Soul Candy! Ripristinati " + healed + " HP.";
    }
}
