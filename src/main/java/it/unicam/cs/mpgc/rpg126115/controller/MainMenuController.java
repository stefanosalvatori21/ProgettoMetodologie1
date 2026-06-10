package it.unicam.cs.mpgc.rpg126115.controller;

import it.unicam.cs.mpgc.rpg126115.AppContext;
import it.unicam.cs.mpgc.rpg126115.model.GameState;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class MainMenuController {

    private final AppContext ctx;

    public MainMenuController(AppContext ctx) {
        this.ctx = ctx;
    }

    @FXML
    public void onNewGame() {
        ctx.getViewManager().showCharacterSelect();
    }

    @FXML
    public void onLoadGame() {
        List<String> saves = ctx.getSaveLoadService().listSaves();
        if (saves.isEmpty()) {
            ctx.getViewManager().showAlert("Carica Partita", "Nessun salvataggio disponibile.");
            return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(saves.getFirst(), saves);
        dialog.setTitle("Carica Partita");
        dialog.setHeaderText("Seleziona un salvataggio:");
        dialog.setContentText("Salvataggio:");
        Optional<String> choice = dialog.showAndWait();
        choice.ifPresent(id -> {
            GameState gameState = ctx.getSaveLoadService().load(id);
            ctx.setCurrentGameState(gameState);
            ctx.getViewManager().showNarrativeView(gameState);
        });
    }

    @FXML
    public void onQuit() {
        Stage stage = (Stage) Stage.getWindows().stream()
                .filter(w -> w instanceof Stage && ((Stage) w).isShowing())
                .findFirst().orElseThrow();
        stage.close();
    }
}
