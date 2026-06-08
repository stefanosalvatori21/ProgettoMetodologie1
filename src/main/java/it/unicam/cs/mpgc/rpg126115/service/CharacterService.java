package it.unicam.cs.mpgc.rpg126115.service;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public interface CharacterService {
    boolean awardExperience(Player player, int xp);
    int calculateXpReward(Enemy enemy);
}
