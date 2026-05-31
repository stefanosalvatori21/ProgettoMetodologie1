package it.unicam.cs.mpgc.rpg126115.model.combat;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public class DefensiveBehavior implements EnemyBehavior {

    @Override
    public TurnAction decideTurn(Enemy self, Player target, int turnNumber) {
        if (self.getStats().isLowHp()) {
            return new TurnAction(ActionType.DEFEND, null, null);
        }
        return new TurnAction(ActionType.ATTACK, null, null);
    }
}
