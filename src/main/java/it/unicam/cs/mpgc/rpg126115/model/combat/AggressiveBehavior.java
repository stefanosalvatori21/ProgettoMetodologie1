package it.unicam.cs.mpgc.rpg126115.model.combat;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public class AggressiveBehavior implements EnemyBehavior {

    @Override
    public TurnAction decideTurn(Enemy self, Player target, int turnNumber) {
        return new TurnAction(ActionType.ATTACK, null, null);
    }
}
