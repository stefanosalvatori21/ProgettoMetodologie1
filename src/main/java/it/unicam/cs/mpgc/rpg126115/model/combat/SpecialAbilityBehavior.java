package it.unicam.cs.mpgc.rpg126115.model.combat;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public class SpecialAbilityBehavior implements EnemyBehavior {
    private static final int SPECIAL_EVERY_N_TURNS = 3;

    @Override
    public TurnAction decideTurn(Enemy self, Player target, int turnNumber) {
        if (turnNumber > 0 && turnNumber % SPECIAL_EVERY_N_TURNS == 0) {
            return new TurnAction(ActionType.USE_ABILITY, null, null);
        }
        return new TurnAction(ActionType.ATTACK, null, null);
    }
}
