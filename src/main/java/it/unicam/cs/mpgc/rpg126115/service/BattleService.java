package it.unicam.cs.mpgc.rpg126115.service;

import it.unicam.cs.mpgc.rpg126115.model.combat.BattleState;
import it.unicam.cs.mpgc.rpg126115.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg126115.model.combat.TurnAction;
import it.unicam.cs.mpgc.rpg126115.model.entity.EnemyType;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public interface BattleService {
    BattleState startBattle(Player player, EnemyType enemyType);
    CombatResult executePlayerAction(BattleState state, TurnAction action);
    CombatResult executeEnemyTurn(BattleState state);
}
