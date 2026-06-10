package it.unicam.cs.mpgc.rpg126115.view;

import it.unicam.cs.mpgc.rpg126115.App;
import it.unicam.cs.mpgc.rpg126115.AppContext;
import it.unicam.cs.mpgc.rpg126115.controller.BattleController;
import it.unicam.cs.mpgc.rpg126115.controller.CharacterSelectController;
import it.unicam.cs.mpgc.rpg126115.controller.GameController;
import it.unicam.cs.mpgc.rpg126115.controller.MainMenuController;
import it.unicam.cs.mpgc.rpg126115.controller.VictoryController;
import it.unicam.cs.mpgc.rpg126115.exception.RpgException;
import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.combat.BattleState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class FxViewManager implements ViewManager {

    private final Stage primaryStage;
    private AppContext appContext;

    public FxViewManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public void showMainMenu() {
        loadScene("fxml/main-menu.fxml", "Bleach RPG — Menu Principale");
    }

    @Override
    public void showCharacterSelect() {
        FXMLLoader loader = buildLoader("fxml/character-select.fxml");
        try {
            Scene scene = new Scene(loader.load(), 1600, 900);
            scene.getStylesheets().add(getStylesheet());
            primaryStage.setTitle("Bleach RPG — Scegli il Personaggio");
            primaryStage.setScene(scene);
            FxAnimations.playSceneEntrance(scene.getRoot());
        } catch (IOException e) {
            throw new RpgException("Impossibile caricare character-select.fxml", e);
        }
    }

    @Override
    public void showNarrativeView(GameState gameState) {
        FXMLLoader loader = buildLoader("fxml/game-view.fxml");
        try {
            Scene scene = new Scene(loader.load(), 1600, 900);
            scene.getStylesheets().add(getStylesheet());
            primaryStage.setTitle("Bleach RPG — " + gameState.getCurrentArea().getDisplayName());
            primaryStage.setScene(scene);
            FxAnimations.playSceneEntrance(scene.getRoot());
            GameController ctrl = loader.getController();
            ctrl.initializeWith(gameState);
        } catch (IOException e) {
            throw new RpgException("Impossibile caricare game-view.fxml", e);
        }
    }

    @Override
    public void showBattleView(BattleState battleState) {
        FXMLLoader loader = buildLoader("fxml/battle-view.fxml");
        try {
            Scene scene = new Scene(loader.load(), 1600, 900);
            scene.getStylesheets().add(getStylesheet());
            primaryStage.setTitle("Bleach RPG — Combattimento");
            primaryStage.setScene(scene);
            FxAnimations.playSceneEntrance(scene.getRoot());
            BattleController ctrl = loader.getController();
            ctrl.initializeWith(battleState);
        } catch (IOException e) {
            throw new RpgException("Impossibile caricare battle-view.fxml", e);
        }
    }

    @Override
    public void showVictoryView(GameState gameState) {
        FXMLLoader loader = buildLoader("fxml/victory-view.fxml");
        try {
            Scene scene = new Scene(loader.load(), 1600, 900);
            scene.getStylesheets().add(getStylesheet());
            primaryStage.setTitle("Bleach RPG — Vittoria");
            primaryStage.setScene(scene);
            FxAnimations.playSceneEntrance(scene.getRoot());
            VictoryController ctrl = loader.getController();
            ctrl.initializeWith(gameState);
        } catch (IOException e) {
            throw new RpgException("Impossibile caricare victory-view.fxml", e);
        }
    }

    @Override
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadScene(String fxmlPath, String title) {
        FXMLLoader loader = buildLoader(fxmlPath);
        try {
            Scene scene = new Scene(loader.load(), 1600, 900);
            scene.getStylesheets().add(getStylesheet());
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            FxAnimations.playSceneEntrance(scene.getRoot());
        } catch (IOException e) {
            throw new RpgException("Impossibile caricare: " + fxmlPath, e);
        }
    }

    private FXMLLoader buildLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
        loader.setControllerFactory(cls -> {
            if (cls == MainMenuController.class) return new MainMenuController(appContext);
            if (cls == GameController.class)     return new GameController(appContext);
            if (cls == BattleController.class)         return new BattleController(appContext);
            if (cls == CharacterSelectController.class) return new CharacterSelectController(appContext);
            if (cls == VictoryController.class)         return new VictoryController(appContext);
            try { return cls.getDeclaredConstructor().newInstance(); }
            catch (Exception ex) { throw new RpgException("Impossibile istanziare: " + cls, ex); }
        });
        return loader;
    }

    private String getStylesheet() {
        return App.class.getResource("css/style.css").toExternalForm();
    }
}
