package it.unicam.cs.mpgc.rpg126115.controller;

import it.unicam.cs.mpgc.rpg126115.App;
import it.unicam.cs.mpgc.rpg126115.AppContext;
import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.combat.BattleState;
import it.unicam.cs.mpgc.rpg126115.model.entity.Arrancar;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;
import it.unicam.cs.mpgc.rpg126115.model.entity.Quincy;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Equipment;
import it.unicam.cs.mpgc.rpg126115.model.equipment.EquipmentSlot;
import it.unicam.cs.mpgc.rpg126115.model.world.Area;
import it.unicam.cs.mpgc.rpg126115.model.world.NarrativeChoice;
import it.unicam.cs.mpgc.rpg126115.model.world.StoryEvent;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.List;

public class GameController {

    @FXML private Region    bgRegion;
    @FXML private Label     areaLabel;
    @FXML private Label     playerStatsLabel;
    @FXML private Label     eventTitleLabel;
    @FXML private TextArea  eventDescriptionArea;
    @FXML private ImageView eventImageView;
    @FXML private Label     eventImageCaption;
    @FXML private Button    skipBtn;
    @FXML private VBox      choicesBox;
    @FXML private VBox      equipOverlay;
    @FXML private VBox      weaponSlotCard;
    @FXML private VBox      armorSlotCard;
    @FXML private Label     equipStatsLabel;
    @FXML private FlowPane  equipBagGrid;

    private final AppContext ctx;
    private GameState gameState;
    private Timeline  typewriterTimeline;
    private String    pendingFullText = "";

    public GameController(AppContext ctx) { this.ctx = ctx; }

    public void initializeWith(GameState gameState) {
        this.gameState = gameState;
        eventDescriptionArea.setOnMouseClicked(e -> skipAnimation());
        Platform.runLater(this::fixTextAreaBackground);
        refresh();
    }

    private void fixTextAreaBackground() {
        eventDescriptionArea.applyCss();
        eventDescriptionArea.layout();
        for (String sel : new String[]{".scroll-pane", ".viewport", ".content"}) {
            Node n = eventDescriptionArea.lookup(sel);
            if (n instanceof Region r) r.setStyle("-fx-background-color: #050412;");
        }
        Node sp = eventDescriptionArea.lookup(".scroll-pane");
        if (sp instanceof Region r) r.setStyle("-fx-background-color: transparent;");
    }

    @FXML public void onSkipAnimation() { skipAnimation(); }


    private void refresh() {
        if ("victory".equals(gameState.getCurrentEventId())) {
            ctx.getViewManager().showVictoryView(gameState);
            return;
        }
        StoryEvent event = ctx.getNarrativeService()
                .getCurrentEvent(gameState, ctx.getWorld());
        if (event == null) {
            eventTitleLabel.setText("Fine della storia");
            streamText("Non ci sono altri eventi. Il tuo viaggio è terminato.");
            choicesBox.getChildren().clear();
            return;
        }
        gameState.setCurrentArea(event.getArea());
        applyAreaBackground(event.getArea());
        areaLabel.setText(event.getArea().getDisplayName());
        playerStatsLabel.setText(buildStatsText());
        eventTitleLabel.setText(event.getTitle());
        loadEventImage();
        streamText(event.getDescription());
        buildChoiceButtons(event);
    }


    private void streamText(String fullText) {
        pendingFullText = fullText;
        stopTypewriter();
        eventDescriptionArea.clear();
        skipBtn.setVisible(true);
        skipBtn.setDisable(false);

        typewriterTimeline = new Timeline();
        for (int i = 1; i <= fullText.length(); i++) {
            final String slice = fullText.substring(0, i);
            typewriterTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis((long) i * 22),
                            e -> eventDescriptionArea.setText(slice)));
        }
        typewriterTimeline.setOnFinished(e -> {
            skipBtn.setVisible(false);
            eventDescriptionArea.positionCaret(0);
        });
        typewriterTimeline.play();
    }

    private void skipAnimation() {
        stopTypewriter();
        eventDescriptionArea.setText(pendingFullText);
        eventDescriptionArea.positionCaret(0);
        skipBtn.setVisible(false);
    }

    private void stopTypewriter() {
        if (typewriterTimeline != null) { typewriterTimeline.stop(); typewriterTimeline = null; }
    }


    private void loadEventImage() {
        String imagePath = playerClassImagePath();
        String caption   = gameState.getPlayer().getDisplayName();
        try {
            var stream = App.class.getResourceAsStream(imagePath);
            if (stream != null)
                eventImageView.setImage(new Image(stream,
                        (int) eventImageView.getFitWidth() * 2,
                        (int) eventImageView.getFitHeight() * 2,
                        true, true));
        } catch (Exception ignored) {}
        eventImageCaption.setText(caption);
    }

    private String playerClassImagePath() {
        var p = gameState.getPlayer();
        if (p instanceof Quincy)   return "images/chars/quincy.png";
        if (p instanceof Arrancar) return "images/chars/arrancar.png";
        return "images/chars/shinigami.png";
    }


    private static final String[] CHOICE_VARIANTS = {"choice-btn-1","choice-btn-2","choice-btn-3","choice-btn-4"};
    private static final String[] CHOICE_PREFIXES = {"①  ","②  ","③  ","④  "};

    private void buildChoiceButtons(StoryEvent event) {
        choicesBox.getChildren().clear();

        if (event.hasCombat()) {
            Button btn = new Button("⚔  COMBATTI:  " + event.getEncounter().getDisplayName().toUpperCase());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().addAll("choice-button", "choice-btn-combat");
            btn.setOnAction(e -> startBattle(event));
            choicesBox.getChildren().add(btn);
        } else {
            var choices = event.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                NarrativeChoice choice = choices.get(i);
                Button btn = new Button(CHOICE_PREFIXES[i % CHOICE_PREFIXES.length] + choice.getText());
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.getStyleClass().addAll("choice-button", CHOICE_VARIANTS[i % CHOICE_VARIANTS.length]);
                btn.setOnAction(e -> applyChoice(choice));
                choicesBox.getChildren().add(btn);
            }
        }

        Button saveBtn = new Button("💾   Salva partita");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.getStyleClass().add("save-button");
        saveBtn.setOnAction(e -> {
            ctx.getSaveLoadService().save(gameState);
            ctx.getViewManager().showAlert("Salvataggio", "Partita salvata!");
        });
        choicesBox.getChildren().add(saveBtn);
    }

    private void applyChoice(NarrativeChoice choice) {
        ctx.getNarrativeService().applyChoice(gameState, choice);
        refresh();
    }

    private void startBattle(StoryEvent event) {
        stopTypewriter();
        BattleState battle = ctx.getBattleService()
                .startBattle(gameState.getPlayer(), event.getEncounter().getType());
        ctx.setCurrentBattleState(battle);
        ctx.setCurrentGameState(gameState);
        ctx.getViewManager().showBattleView(battle);
    }


    @FXML public void onBuild() { showEquipmentPanel(); }

    @FXML public void onEquipmentClose() {
        FadeTransition ft = new FadeTransition(Duration.millis(140), equipOverlay);
        ft.setToValue(0.0);
        ft.setOnFinished(e -> equipOverlay.setVisible(false));
        ft.play();
    }

    private void showEquipmentPanel() {
        buildEquipmentOverlay();
        equipOverlay.setOpacity(0);
        equipOverlay.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(200), equipOverlay);
        ft.setToValue(1.0);
        ft.play();
    }

    private void buildEquipmentOverlay() {
        Player player = gameState.getPlayer();

        populateSlot(weaponSlotCard, EquipmentSlot.WEAPON, player);
        populateSlot(armorSlotCard,  EquipmentSlot.ARMOR,  player);

        var s = player.getStats();
        equipStatsLabel.setText(String.format(
                "ATK  %d%nDEF  %d%nHP   %d / %d%nREI  %d / %d%nLv.  %d",
                s.getAttack(), s.getDefense(),
                s.getHp(), s.getMaxHp(),
                s.getReiatsu(), s.getMaxReiatsu(),
                s.getLevel()));

        equipBagGrid.getChildren().clear();
        List<Equipment> bag = player.getEquipmentBag();
        if (bag.isEmpty()) {
            Label empty = new Label("Nessun equipaggiamento nel zaino.");
            empty.setStyle("-fx-text-fill: rgba(180,160,120,0.55); -fx-font-style: italic; -fx-font-size: 12px;");
            equipBagGrid.getChildren().add(empty);
        } else {
            bag.forEach(eq -> equipBagGrid.getChildren().add(buildBagCard(eq)));
        }
    }

    private void populateSlot(VBox slotCard, EquipmentSlot slot, Player player) {
        slotCard.getChildren().clear();
        slotCard.setOnMouseClicked(null);
        slotCard.getStyleClass().removeAll("equip-slot-card-filled");

        Equipment eq = player.getEquipped(slot);
        if (eq == null) {
            Label icon  = new Label(slot == EquipmentSlot.WEAPON ? "⚔" : "🛡");
            icon.setStyle("-fx-font-size: 28px; -fx-text-fill: rgba(200,160,0,0.22);");
            Label lbl   = new Label("— Vuoto —");
            lbl.setStyle("-fx-font-size: 11px; -fx-text-fill: rgba(200,160,0,0.28); -fx-font-style: italic;");
            slotCard.getChildren().addAll(icon, lbl);
        } else {
            Node visual = equipmentVisual(eq, 90);

            Label name = new Label(eq.getName());
            name.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;"
                    + " -fx-text-fill: " + eq.getRarity().getColor() + ";");
            name.setWrapText(true);
            name.setMaxWidth(180);
            name.setAlignment(Pos.CENTER);

            Label stats = new Label(eq.getStatsSummary());
            stats.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #c8d4f0;");

            Label rarity = new Label("[ " + eq.getRarity().getDisplayName() + " ]");
            rarity.setStyle("-fx-font-size: 10px; -fx-font-style: italic;"
                    + " -fx-text-fill: " + eq.getRarity().getColor() + ";");

            Label ability = new Label("✦  " + eq.getAbilityName());
            ability.setStyle("-fx-font-size: 10px; -fx-font-style: italic;"
                    + " -fx-text-fill: rgba(200,220,255,0.70);");
            ability.setWrapText(true);
            ability.setMaxWidth(180);
            ability.setAlignment(Pos.CENTER);

            Label hint = new Label("✖  rimuovi");
            hint.setStyle("-fx-font-size: 9px; -fx-text-fill: rgba(220,80,80,0.65);");

            slotCard.getChildren().addAll(visual, name, stats, ability, rarity, hint);
            slotCard.getStyleClass().add("equip-slot-card-filled");
            slotCard.setOnMouseClicked(e -> { player.unequip(slot); buildEquipmentOverlay(); refresh(); });
        }
    }

    private VBox buildBagCard(Equipment eq) {
        Node visual = equipmentVisual(eq, 72);

        Label name = new Label(eq.getName());
        name.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: "
                + eq.getRarity().getColor() + ";");
        name.setWrapText(true);
        name.setMaxWidth(148);
        name.setAlignment(Pos.CENTER);

        Label stats = new Label(eq.getStatsSummary());
        stats.setStyle("-fx-font-size: 11px; -fx-text-fill: #b0c0e0;");

        Label ability = new Label("✦  " + eq.getAbilityName());
        ability.setStyle("-fx-font-size: 10px; -fx-font-style: italic;"
                + " -fx-text-fill: rgba(190,210,255,0.72);");
        ability.setWrapText(true);
        ability.setMaxWidth(148);
        ability.setAlignment(Pos.CENTER);

        Label rarity = new Label(eq.getRarity().getDisplayName());
        rarity.setStyle("-fx-font-size: 10px; -fx-font-style: italic; -fx-text-fill: " + eq.getRarity().getColor() + ";");

        VBox card = new VBox(5, visual, name, stats, ability, rarity);
        card.getStyleClass().add("equip-bag-card");
        card.setAlignment(Pos.CENTER);
        card.setOnMouseClicked(e -> { gameState.getPlayer().equip(eq); buildEquipmentOverlay(); refresh(); });
        return card;
    }

    /** Returns an ImageView with the equipment image, or an emoji Label as fallback. */
    private Node equipmentVisual(Equipment eq, double size) {
        var stream = App.class.getResourceAsStream(eq.getImagePath());
        if (stream != null) {
            try {
                ImageView iv = new ImageView(new Image(stream));
                iv.setFitWidth(size);
                iv.setFitHeight(size);
                iv.setPreserveRatio(true);
                iv.setSmooth(true);
                return iv;
            } catch (Exception ignored) {}
        }
        Label fallback = new Label(eq.getSlot() == EquipmentSlot.WEAPON ? "⚔" : "🛡");
        fallback.setStyle("-fx-font-size: 22px; -fx-text-fill: " + eq.getRarity().getColor() + ";");
        return fallback;
    }


    private void applyAreaBackground(Area area) {
        bgRegion.getStyleClass().removeIf(c -> c.startsWith("bg-"));
        bgRegion.getStyleClass().add(switch (area) {
            case KARAKURA_TOWN -> "bg-karakura";
            case SEIREITEI     -> "bg-seireitei";
            case HUECO_MUNDO   -> "bg-hueco";
        });
    }

    private String buildStatsText() {
        var s = gameState.getPlayer().getStats();
        return String.format("Lv.%d  HP %d/%d  REI %d/%d  [%s]",
                s.getLevel(), s.getHp(), s.getMaxHp(),
                s.getReiatsu(), s.getMaxReiatsu(),
                gameState.getPlayer().getDisplayName());
    }
}
