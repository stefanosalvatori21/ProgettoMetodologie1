package it.unicam.cs.mpgc.rpg126115.service.impl;

import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;
import it.unicam.cs.mpgc.rpg126115.model.combat.*;
import it.unicam.cs.mpgc.rpg126115.model.entity.*;
import it.unicam.cs.mpgc.rpg126115.model.item.Consumable;
import it.unicam.cs.mpgc.rpg126115.model.item.Item;
import it.unicam.cs.mpgc.rpg126115.service.BattleService;

public class BattleServiceImpl implements BattleService {

    private final EnemyCreator enemyCreator;

    public BattleServiceImpl(EnemyCreator enemyCreator) {
        this.enemyCreator = enemyCreator;
    }

    @Override
    public BattleState startBattle(Player player, EnemyType enemyType) {
        Enemy enemy = enemyCreator.create(enemyType);
        player.resetBattleState();
        return new BattleState(player, enemy);
    }

    @Override
    public CombatResult executePlayerAction(BattleState state, TurnAction action) {
        Player player = state.getPlayer();
        Enemy enemy = state.getCurrentEnemy();

        CombatResult result = switch (action.type()) {
            case ATTACK            -> basicAttack(player, enemy);
            case USE_ABILITY       -> useAbility(player, enemy, action.ability());
            case USE_ITEM          -> useItem(player, enemy, action.item());
            case DEFEND            -> new CombatResult(0, 0, null, false,
                                            player.getName() + " si mette in guardia.");
            case TRANSFORM_PARTIAL -> transform(player);
            case FLEE              -> new CombatResult(0, 0, null, false,
                                            player.getName() + " tenta la fuga...");
        };

        state.nextTurn();
        return result;
    }

    @Override
    public CombatResult executeEnemyTurn(BattleState state) {
        Enemy enemy = state.getCurrentEnemy();
        Player player = state.getPlayer();
        TurnAction action = enemy.decideTurn(player, state.getTurnNumber());

        CombatResult result = switch (action.type()) {
            case USE_ABILITY -> specialAttack(enemy, player);
            case DEFEND      -> new CombatResult(0, 0, null, false,
                                    enemy.getDisplayName() + " si prepara...");
            default          -> basicAttack(enemy, player);
        };

        state.nextTurn();
        return result;
    }

    private CombatResult specialAttack(GameCharacter attacker, GameCharacter defender) {
        int rawDamage = (int) (attacker.computeAttackDamage() * 1.8);
        int actualDamage = defender.getStats().takeDamage(rawDamage);
        return new CombatResult(actualDamage, 0, null, !defender.isAlive(),
                attacker.getDisplayName() + " scatena un attacco speciale! " + actualDamage + " danni!");
    }

    private CombatResult basicAttack(GameCharacter attacker, GameCharacter defender) {
        int actualDamage = defender.getStats().takeDamage(attacker.computeAttackDamage());
        return new CombatResult(actualDamage, 0, null, !defender.isAlive(),
                attacker.getDisplayName() + " attacca! " + actualDamage + " danni.");
    }

    private CombatResult useAbility(Player player, Enemy enemy, Ability ability) {
        if (ability == null) return basicAttack(player, enemy);
        if (!ability.isAvailable(player.getStats().getLevel(), player.getTransformationState())) {
            return new CombatResult(0, 0, null, false, "Abilità non disponibile in questa forma.");
        }
        if (!player.getStats().spendReiatsu(ability.getReiatsuCost())) {
            return new CombatResult(0, 0, null, false, "Reiatsu insufficiente!");
        }
        return ability.execute(player, enemy);
    }

    private CombatResult useItem(Player player, GameCharacter target, Item item) {
        if (item instanceof Consumable consumable) {
            String narrative = consumable.use(player, target);
            player.removeItem(item);
            return new CombatResult(0, 0, null, false, narrative);
        }
        return new CombatResult(0, 0, null, false, "Non puoi usare questo oggetto in battaglia.");
    }

    private CombatResult transform(Player player) {
        if (player.canTransformPartial()) {
            String name = player.getPartialTransformation().getName();
            player.activatePartial();
            return new CombatResult(0, 0, null, false,
                    "«" + name + "!» — " + player.getName() + " si trasforma!");
        }
        return new CombatResult(0, 0, null, false, "Trasformazione non disponibile.");
    }
}
