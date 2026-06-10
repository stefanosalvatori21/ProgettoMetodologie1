package it.unicam.cs.mpgc.rpg126115;

import it.unicam.cs.mpgc.rpg126115.repository.impl.JsonGameStateRepository;
import it.unicam.cs.mpgc.rpg126115.service.*;
import it.unicam.cs.mpgc.rpg126115.service.impl.*;
import it.unicam.cs.mpgc.rpg126115.view.FxViewManager;
import it.unicam.cs.mpgc.rpg126115.model.world.World;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        JsonGameStateRepository repository = new JsonGameStateRepository();

        CharacterService characterService = new CharacterServiceImpl();
        LootService      lootService      = new LootServiceImpl();
        GameService      gameService      = new GameServiceImpl(characterService, lootService);
        BattleService    battleService    = new BattleServiceImpl();
        NarrativeService narrativeService = new NarrativeServiceImpl();
        SaveLoadService  saveLoadService  = new SaveLoadServiceImpl(repository);

        World world = WorldBuilder.buildWorld();

        FxViewManager viewManager = new FxViewManager(primaryStage);

        AppContext appContext = new AppContext(gameService, battleService, narrativeService,
                characterService, saveLoadService, world, viewManager);
        viewManager.setAppContext(appContext);

        primaryStage.setMinWidth(1600);
        primaryStage.setMinHeight(900);
        primaryStage.setResizable(true);
        viewManager.showMainMenu();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
