package it.unicam.cs.mpgc.rpg126115.service.impl;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;
import it.unicam.cs.mpgc.rpg126115.service.CharacterService;

public class CharacterServiceImpl implements CharacterService {

    @Override
    public boolean awardExperience(Player player, int xp) {
        return player.getStats().gainExperience(xp);
    }

    @Override
    public int calculateXpReward(Enemy enemy) {
        return switch (enemy.getDifficulty()) {
            case EASY   -> 50;
            case MEDIUM -> 90;
            case HARD   -> 160;
            case BOSS   -> 320;
        };
    }
}
