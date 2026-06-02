package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.combat.EnemyBehavior;
import it.unicam.cs.mpgc.rpg126115.model.combat.TurnAction;
import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.world.Area;

public class Enemy extends GameCharacter {
    private final EnemyType type;
    private final Area area;
    private final DifficultyLevel difficulty;
    private final EnemyBehavior behavior;

    public Enemy(String name, Stats stats, EnemyType type, Area area,
                 DifficultyLevel difficulty, EnemyBehavior behavior) {
        super(name, stats);
        this.type = type;
        this.area = area;
        this.difficulty = difficulty;
        this.behavior = behavior;
    }

    public TurnAction decideTurn(Player player, int turnNumber) {
        return behavior.decideTurn(this, player, turnNumber);
    }

    @Override
    public String getDisplayName() { return type.getDisplayName(); }

    @Override
    public int computeAttackDamage() { return getStats().getAttack(); }

    public EnemyType getType() { return type; }
    public Area getArea() { return area; }
    public DifficultyLevel getDifficulty() { return difficulty; }
}
