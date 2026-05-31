package it.unicam.cs.mpgc.rpg126115.model.combat;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public interface EnemyBehavior {
    TurnAction decideTurn(Enemy self, Player target, int turnNumber);
}
