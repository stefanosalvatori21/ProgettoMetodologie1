package it.unicam.cs.mpgc.rpg126115.model.item;

import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public class SpiritBoost extends Consumable {

    private final String name;
    private final int atkBonus;

    public SpiritBoost(String name, int atkBonus) {
        this.name = name;
        this.atkBonus = atkBonus;
    }

    @Override public String getName()        { return name; }
    @Override public String getDescription() { return "Aumenta ATK di " + atkBonus + " per questa battaglia."; }

    @Override
    public String use(Player player, GameCharacter target) {
        player.getStats().setAttack(player.getStats().getAttack() + atkBonus);
        return player.getName() + " usa " + name + "! ATK aumentato di " + atkBonus + "!";
    }
}
