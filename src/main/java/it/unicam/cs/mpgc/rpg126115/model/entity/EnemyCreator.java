package it.unicam.cs.mpgc.rpg126115.model.entity;

@FunctionalInterface
public interface EnemyCreator {
    Enemy create(EnemyType type);
}
