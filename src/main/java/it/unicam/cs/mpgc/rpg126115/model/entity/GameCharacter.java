package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;

public abstract class GameCharacter {
    private final String name;
    private Stats stats;

    protected GameCharacter(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
    }

    public abstract String getDisplayName();

    public abstract int computeAttackDamage();

    public String getName() { return name; }

    public Stats getStats() { return stats; }

    protected void setStats(Stats stats) { this.stats = stats; }

    public boolean isAlive() { return stats.isAlive(); }
}
