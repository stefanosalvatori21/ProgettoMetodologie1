package it.unicam.cs.mpgc.rpg126115.model.entity.stats;

public class Stats {
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int reiatsu;
    private int maxReiatsu;
    private int experience;
    private int level;

    public Stats(int maxHp, int attack, int defense, int maxReiatsu) {
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.reiatsu = maxReiatsu;
        this.maxReiatsu = maxReiatsu;
        this.experience = 0;
        this.level = 1;
    }

    public int takeDamage(int rawDamage) {
        // Soft mitigation: defense absorbs half its value as flat reduction
        int actualDamage = Math.max(1, rawDamage - defense / 2);
        hp = Math.max(0, hp - actualDamage);
        return actualDamage;
    }

    public int heal(int amount) {
        int actualHeal = Math.min(amount, maxHp - hp);
        hp += actualHeal;
        return actualHeal;
    }

    public boolean spendReiatsu(int amount) {
        if (reiatsu < amount) return false;
        reiatsu -= amount;
        return true;
    }

    public void restoreReiatsu(int amount) {
        reiatsu = Math.min(maxReiatsu, reiatsu + amount);
    }

    public boolean gainExperience(int xp) {
        experience += xp;
        if (experience >= experienceToNextLevel()) {
            levelUp();
            return true;
        }
        return false;
    }

    private void levelUp() {
        level++;
        experience = 0;
        maxHp += (int)(maxHp * 0.1);
        hp = maxHp;
        attack += 2;
        defense += 1;
        maxReiatsu += 5;
        reiatsu = maxReiatsu;
    }

    public int experienceToNextLevel() {
        return level * 100;
    }

    public boolean isAlive() { return hp > 0; }

    public boolean isLowHp() { return hp <= maxHp * 0.25; }

    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getReiatsu() { return reiatsu; }
    public int getMaxReiatsu() { return maxReiatsu; }
    public int getExperience() { return experience; }
    public int getLevel() { return level; }

    public void setAttack(int attack) { this.attack = Math.max(0, attack); }
    public void setDefense(int defense) { this.defense = Math.max(0, defense); }
    public void setMaxHp(int maxHp) { this.maxHp = Math.max(1, maxHp); }
    public void setMaxReiatsu(int maxReiatsu) { this.maxReiatsu = Math.max(0, maxReiatsu); }
    public void setHp(int hp) { this.hp = Math.max(0, Math.min(hp, maxHp)); }
    public void setReiatsu(int reiatsu) { this.reiatsu = Math.max(0, Math.min(reiatsu, maxReiatsu)); }
    public void setLevel(int level) { this.level = Math.max(1, level); }
    public void setExperience(int experience) { this.experience = Math.max(0, experience); }
}
