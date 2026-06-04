package it.unicam.cs.mpgc.rpg126115.model.item.consumable;

import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;
import it.unicam.cs.mpgc.rpg126115.model.item.Consumable;

public class SeireiteiSake extends Consumable {
    private static final int REIATSU_RESTORE = 40;
    private static final int ATTACK_BUFF = 5;

    @Override public String getName() { return "Sake di Seireitei"; }

    @Override public String getDescription() { return "+40 Reiatsu e +5 ATK temporaneo per 3 turni."; }

    @Override
    public String use(Player player, GameCharacter target) {
        player.getStats().restoreReiatsu(REIATSU_RESTORE);
        player.getStats().setAttack(player.getStats().getAttack() + ATTACK_BUFF);
        return "Hai bevuto il Sake di Seireitei! +40 Reiatsu e +5 ATK.";
    }
}
