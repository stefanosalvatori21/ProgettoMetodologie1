package it.unicam.cs.mpgc.rpg126115.view;

import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.combat.BattleState;

public interface ViewManager {
    void showMainMenu();
    void showCharacterSelect();
    void showNarrativeView(GameState gameState);
    void showBattleView(BattleState battleState);
    void showVictoryView(GameState gameState);
    void showAlert(String title, String message);
}
