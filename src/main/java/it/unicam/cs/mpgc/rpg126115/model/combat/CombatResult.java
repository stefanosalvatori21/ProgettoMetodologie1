package it.unicam.cs.mpgc.rpg126115.model.combat;

import it.unicam.cs.mpgc.rpg126115.model.ability.StatusEffect;

public record CombatResult(
        int damageDealt,
        int reiatsuUsed,
        StatusEffect appliedStatus,
        boolean targetDefeated,
        String narrative
) {}
