package it.unicam.cs.mpgc.rpg126115.service.impl;

import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.entity.*;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Equipment;
import it.unicam.cs.mpgc.rpg126115.model.item.HealingPotion;
import it.unicam.cs.mpgc.rpg126115.model.item.ReiatsuDrink;
import it.unicam.cs.mpgc.rpg126115.model.item.SpiritBoost;
import it.unicam.cs.mpgc.rpg126115.service.CharacterService;
import it.unicam.cs.mpgc.rpg126115.service.GameService;
import it.unicam.cs.mpgc.rpg126115.service.LootService;

import java.util.List;
import java.util.UUID;

public class GameServiceImpl implements GameService {

    private final CharacterService characterService;
    private final LootService      lootService;

    public GameServiceImpl(CharacterService characterService, LootService lootService) {
        this.characterService = characterService;
        this.lootService      = lootService;
    }

    @Override
    public GameState newGame(String playerName, PlayerClass playerClass) {
        Player player = playerClass.createPlayer(playerName);
        player.addItem(new HealingPotion("Pozione Curativa",   40));
        player.addItem(new HealingPotion("Pozione Curativa",   40));
        player.addItem(new ReiatsuDrink("Tè dello Spirito",    30));
        player.addItem(new SpiritBoost("Erba del Combattente",  8));
        return new GameState(UUID.randomUUID().toString(), player);
    }

    @Override
    public List<Equipment> onBattleVictory(GameState gameState, Enemy defeatedEnemy) {
        int xp = characterService.calculateXpReward(defeatedEnemy);
        characterService.awardExperience(gameState.getPlayer(), xp);
        List<Equipment> loot = lootService.generateLoot(defeatedEnemy);
        loot.forEach(eq -> gameState.getPlayer().addToEquipmentBag(eq));
        return loot;
    }

    @Override
    public void onBattleDefeat(GameState gameState) {
        // game over — gestito dalla view
    }

    @Override
    public boolean isGameOver(GameState gameState) {
        return !gameState.getPlayer().isAlive();
    }
}
