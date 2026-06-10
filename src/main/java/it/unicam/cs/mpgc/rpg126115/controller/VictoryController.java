package it.unicam.cs.mpgc.rpg126115.controller;

import it.unicam.cs.mpgc.rpg126115.AppContext;
import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.entity.Arrancar;
import it.unicam.cs.mpgc.rpg126115.model.entity.Quincy;
import it.unicam.cs.mpgc.rpg126115.model.world.StoryEvent;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class VictoryController {

    @FXML private VBox  rootVBox;
    @FXML private Label victoryTitleLabel;
    @FXML private Label playerInfoLabel;
    @FXML private TextArea storyTextArea;

    private final AppContext ctx;

    public VictoryController(AppContext ctx) {
        this.ctx = ctx;
    }

    public void initializeWith(GameState gameState) {
        var player = gameState.getPlayer();
        var stats  = player.getStats();
        String cls = player instanceof Quincy   ? "Quincy"
                   : player instanceof Arrancar ? "Arrancar"
                   : "Shinigami";
        playerInfoLabel.setText(player.getName() + "  ·  " + cls + "  ·  Lv. " + stats.getLevel());

        StoryEvent victory = ctx.getWorld().getEvent("victory");
        if (victory != null) storyTextArea.setText(victory.getDescription());

        Platform.runLater(this::fixTextAreaBackground);
        playEntrance();
    }

    private void fixTextAreaBackground() {
        storyTextArea.applyCss();
        storyTextArea.layout();
        for (String sel : new String[]{".scroll-pane", ".viewport", ".content"}) {
            Node n = storyTextArea.lookup(sel);
            if (n instanceof Region r) r.setStyle("-fx-background-color: transparent;");
        }
    }

    private void playEntrance() {
        rootVBox.setOpacity(0);
        rootVBox.setScaleX(0.94);
        rootVBox.setScaleY(0.94);

        FadeTransition  fade  = new FadeTransition(Duration.millis(900), rootVBox);
        ScaleTransition scale = new ScaleTransition(Duration.millis(900), rootVBox);
        fade.setToValue(1.0);
        scale.setToX(1.0);
        scale.setToY(1.0);
        new ParallelTransition(fade, scale).play();
    }

    @FXML public void onNewGame()   { ctx.getViewManager().showCharacterSelect(); }
    @FXML public void onMainMenu()  { ctx.getViewManager().showMainMenu(); }
}
