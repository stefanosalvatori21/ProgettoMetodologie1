package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.combat.*;
import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;

public class EnemyFactory {

    private EnemyFactory() {}

    public static Enemy create(EnemyType type) {
        Stats stats = new Stats(type.getBaseHp(), type.getBaseAttack(), type.getBaseDefense(), 0);
        EnemyBehavior behavior = resolveBehavior(type.getDifficulty());
        return new Enemy(type.getDisplayName(), stats, type, type.getArea(), type.getDifficulty(), behavior);
    }

    private static EnemyBehavior resolveBehavior(DifficultyLevel difficulty) {
        return switch (difficulty) {
            case EASY   -> new AggressiveBehavior();
            case MEDIUM -> new DefensiveBehavior();
            case HARD   -> new SpecialAbilityBehavior();
            case BOSS   -> new BossBehavior();
        };
    }
}
