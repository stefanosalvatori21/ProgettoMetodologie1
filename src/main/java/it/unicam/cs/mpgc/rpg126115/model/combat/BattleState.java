package it.unicam.cs.mpgc.rpg126115.model.combat;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;

public class BattleState {
    private final Player player;
    private final Enemy currentEnemy;
    private int turnNumber;
    private boolean playerTurn;
    private boolean battleOver;

    public BattleState(Player player, Enemy enemy) {
        this.player = player;
        this.currentEnemy = enemy;
        this.turnNumber = 0;
        this.playerTurn = true;
        this.battleOver = false;
    }

    public void nextTurn() {
        if (playerTurn) turnNumber++;
        playerTurn = !playerTurn;
        if (!player.isAlive() || !currentEnemy.isAlive()) {
            battleOver = true;
        }
    }

    public boolean isPlayerVictory() { return battleOver && !currentEnemy.isAlive(); }

    public boolean isPlayerDefeated() { return battleOver && !player.isAlive(); }

    public Player getPlayer() { return player; }
    public Enemy getCurrentEnemy() { return currentEnemy; }
    public int getTurnNumber() { return turnNumber; }
    public boolean isPlayerTurn() { return playerTurn; }
    public boolean isBattleOver() { return battleOver; }
}
