package it.unicam.cs.mpgc.rpg126115.model.item.consumable;

import it.unicam.cs.mpgc.rpg126115.model.entity.GameCharacter;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;
import it.unicam.cs.mpgc.rpg126115.model.entity.Quincy;
import it.unicam.cs.mpgc.rpg126115.model.item.Consumable;

public class Ginto extends Consumable {
    private static final int FIXED_DAMAGE = 30;

    @Override public String getName() { return "Ginto"; }

    @Override public String getDescription() {
        return "Fiala d'argento (solo Quincy): 30 danni fissi + WEAKENED al nemico.";
    }

    @Override
    public String use(Player player, GameCharacter target) {
        if (!(player instanceof Quincy)) {
            return "Solo un Quincy può usare il Ginto!";
        }
        if (target == null || !target.isAlive()) {
            return "Nessun bersaglio valido.";
        }
        target.getStats().takeDamage(FIXED_DAMAGE);
        return "Hai lanciato il Ginto! " + FIXED_DAMAGE + " danni fissi — il nemico è indebolito!";
    }
}
