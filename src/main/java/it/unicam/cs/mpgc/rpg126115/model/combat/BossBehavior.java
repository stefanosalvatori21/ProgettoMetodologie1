package it.unicam.cs.mpgc.rpg126115.model.combat;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public class BossBehavior implements EnemyBehavior {

    @Override
    public TurnAction decideTurn(Enemy self, Player target, int turnNumber) {
        if (self.getStats().isLowHp() && turnNumber % 2 == 0) {
            return new TurnAction(ActionType.USE_ABILITY, null, null);
        }
        if (turnNumber > 0 && turnNumber % 4 == 0) {
            return new TurnAction(ActionType.USE_ABILITY, null, null);
        }
        return new TurnAction(ActionType.ATTACK, null, null);
    }
}
