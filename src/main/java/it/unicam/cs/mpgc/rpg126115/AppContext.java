package it.unicam.cs.mpgc.rpg126115;

import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.combat.BattleState;
import it.unicam.cs.mpgc.rpg126115.model.world.World;
import it.unicam.cs.mpgc.rpg126115.service.*;
import it.unicam.cs.mpgc.rpg126115.view.ViewManager;

public class AppContext {

    private final GameService gameService;
    private final BattleService battleService;
    private final NarrativeService narrativeService;
    private final CharacterService characterService;
    private final SaveLoadService saveLoadService;
    private final World world;
    private final ViewManager viewManager;

    private GameState currentGameState;
    private BattleState currentBattleState;

    public AppContext(GameService gameService, BattleService battleService,
                      NarrativeService narrativeService, CharacterService characterService,
                      SaveLoadService saveLoadService, World world, ViewManager viewManager) {
        this.gameService = gameService;
        this.battleService = battleService;
        this.narrativeService = narrativeService;
        this.characterService = characterService;
        this.saveLoadService = saveLoadService;
        this.world = world;
        this.viewManager = viewManager;
    }

    public GameService getGameService() { return gameService; }
    public BattleService getBattleService() { return battleService; }
    public NarrativeService getNarrativeService() { return narrativeService; }
    public CharacterService getCharacterService() { return characterService; }
    public SaveLoadService getSaveLoadService() { return saveLoadService; }
    public World getWorld() { return world; }
    public ViewManager getViewManager() { return viewManager; }

    public GameState getCurrentGameState() { return currentGameState; }
    public void setCurrentGameState(GameState state) { this.currentGameState = state; }
    public BattleState getCurrentBattleState() { return currentBattleState; }
    public void setCurrentBattleState(BattleState state) { this.currentBattleState = state; }
}
