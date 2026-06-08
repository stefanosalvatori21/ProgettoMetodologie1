package it.unicam.cs.mpgc.rpg126115.service;

import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.PlayerClass;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Equipment;

import java.util.List;

public interface GameService {
    GameState newGame(String playerName, PlayerClass playerClass);
    /** Awards XP, generates loot drops and adds them to the player's bag. Returns the dropped items. */
    List<Equipment> onBattleVictory(GameState gameState, Enemy defeatedEnemy);
    void onBattleDefeat(GameState gameState);
    boolean isGameOver(GameState gameState);
}
