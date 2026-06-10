package it.unicam.cs.mpgc.rpg126115.controller;

import it.unicam.cs.mpgc.rpg126115.App;
import it.unicam.cs.mpgc.rpg126115.AppContext;
import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;
import it.unicam.cs.mpgc.rpg126115.model.combat.ActionType;
import it.unicam.cs.mpgc.rpg126115.model.combat.BattleState;
import it.unicam.cs.mpgc.rpg126115.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg126115.model.combat.TurnAction;
import it.unicam.cs.mpgc.rpg126115.model.entity.Arrancar;
import it.unicam.cs.mpgc.rpg126115.model.entity.Player;
import it.unicam.cs.mpgc.rpg126115.model.entity.Quincy;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Equipment;
import it.unicam.cs.mpgc.rpg126115.model.item.HealingPotion;
import it.unicam.cs.mpgc.rpg126115.model.item.Item;
import it.unicam.cs.mpgc.rpg126115.model.item.ReiatsuDrink;
import it.unicam.cs.mpgc.rpg126115.model.item.SpiritBoost;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.List;

public class BattleController {

    @FXML private Region      bgRegion;
    @FXML private StackPane   playerAvatarBox;
    @FXML private ImageView   playerImageView;
    @FXML private Region      playerHitOverlay;
    @FXML private StackPane   enemyAvatarBox;
    @FXML private ImageView   enemyImageView;
    @FXML private Region      enemyHitOverlay;
    @FXML private Label       playerNameLabel;
    @FXML private Label       playerStatsLabel;
    @FXML private Label       transformStateLabel;
    @FXML private ProgressBar playerHpBar;
    @FXML private ProgressBar playerReiBar;
    @FXML private Label       enemyNameLabel;
    @FXML private Label       enemyStatsLabel;
    @FXML private Label       difficultyLabel;
    @FXML private ProgressBar enemyHpBar;
    @FXML private TextArea    battleLogArea;
    @FXML private Button      transformBtn;
    @FXML private Button      abilitiesBtn;
    @FXML private Button      itemsBtn;
    @FXML private HBox        actionBar;
    @FXML private VBox        selectionOverlay;
    @FXML private Label       selectionTitle;
    @FXML private Label       selectionSubtitle;
    @FXML private FlowPane    selectionGrid;
    @FXML private VBox        endOverlay;
    @FXML private VBox        endPanel;
    @FXML private Label       endTitleLabel;
    @FXML private Label       endSubtitleLabel;
    @FXML private Label       endDetailsLabel;
    @FXML private Button      endConfirmBtn;

    private final AppContext ctx;
    private BattleState battleState;
    private Runnable    endAction;

    public BattleController(AppContext ctx) {
        this.ctx = ctx;
    }

    public void initializeWith(BattleState battleState) {
        this.battleState = battleState;
        bgRegion.getStyleClass().add("bg-battle");
        loadCharacterImage();
        loadEnemyImage();
        updateUI();
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log("⚔  SCONTRO INIZIATO");
        log("   " + battleState.getPlayer().getDisplayName()
                + "  VS  " + battleState.getCurrentEnemy().getDisplayName());
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Platform.runLater(this::applyBarColors);
    }

    private void applyBarColors() {
        applyBarColor(playerHpBar,  "#1a8040", "#2ecc71");
        applyBarColor(playerReiBar, "#503090", "#9b59b6");
        applyBarColor(enemyHpBar,   "#700000", "#e74c3c");
    }

    private void applyBarColor(ProgressBar bar, String from, String to) {
        bar.applyCss();
        bar.layout();
        Node barNode = bar.lookup(".bar");
        if (barNode != null)
            barNode.setStyle("-fx-background-color: linear-gradient(to right," + from + "," + to + ");"
                    + "-fx-background-radius: 4px;");
    }


    @FXML public void onAttack() {
        executeAction(new TurnAction(ActionType.ATTACK, null, null));
    }

    @FXML public void onDefend() {
        executeAction(new TurnAction(ActionType.DEFEND, null, null));
    }

    @FXML public void onAbilities() { showAbilitiesPanel(); }

    @FXML public void onTransform() {
        if (battleState.getPlayer().canTransformPartial()) {
            executeAction(new TurnAction(ActionType.TRANSFORM_PARTIAL, null, null));
        } else {
            ctx.getViewManager().showAlert("Trasformazione", "Nessuna trasformazione disponibile ora.");
        }
    }

    @FXML public void onItems() { showItemsPanel(); }

    @FXML public void onSelectionCancel() { hideSelectionPanel(); }


    private void showAbilitiesPanel() {
        Player player = battleState.getPlayer();
        List<Ability> available = player.getAbilities().stream()
                .filter(a -> a.isAvailable(player.getStats().getLevel(),
                        player.getTransformationState()))
                .toList();
        selectionTitle.setText("✨  Abilità Disponibili");
        selectionSubtitle.setText("Reiatsu:  " + player.getStats().getReiatsu()
                + " / " + player.getStats().getMaxReiatsu());
        selectionGrid.getChildren().clear();
        if (available.isEmpty()) {
            Label msg = new Label("Nessuna abilità disponibile in questa forma.");
            msg.getStyleClass().add("selection-subtitle");
            selectionGrid.getChildren().add(msg);
        } else {
            available.forEach(a -> selectionGrid.getChildren().add(buildAbilityCard(a)));
        }
        revealPanel();
    }

    private void showItemsPanel() {
        Player player = battleState.getPlayer();
        List<Item> inv = player.getInventory();
        selectionTitle.setText("🎒  Inventario");
        selectionSubtitle.setText("HP: " + player.getStats().getHp() + "/" + player.getStats().getMaxHp()
                + "    REI: " + player.getStats().getReiatsu() + "/" + player.getStats().getMaxReiatsu());
        selectionGrid.getChildren().clear();
        if (inv.isEmpty()) {
            Label msg = new Label("L'inventario è vuoto.");
            msg.getStyleClass().add("selection-subtitle");
            selectionGrid.getChildren().add(msg);
        } else {
            inv.forEach(item -> selectionGrid.getChildren().add(buildItemCard(item)));
        }
        revealPanel();
    }

    private void revealPanel() {
        selectionOverlay.setOpacity(0);
        selectionOverlay.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(180), selectionOverlay);
        ft.setToValue(1.0);
        ft.play();
    }

    private void hideSelectionPanel() {
        FadeTransition ft = new FadeTransition(Duration.millis(140), selectionOverlay);
        ft.setToValue(0.0);
        ft.setOnFinished(e -> selectionOverlay.setVisible(false));
        ft.play();
    }

    private VBox buildAbilityCard(Ability ability) {
        Label icon = new Label("✨");
        icon.getStyleClass().add("selection-card-icon");

        Label name = new Label(ability.getName());
        name.getStyleClass().add("selection-card-name");
        name.setWrapText(true);
        name.setMaxWidth(165);
        name.setAlignment(Pos.CENTER);

        Label cost = new Label("REI: " + ability.getReiatsuCost());
        cost.getStyleClass().add("selection-card-detail");

        VBox card = new VBox(7, icon, name, cost);
        card.getStyleClass().add("selection-card");
        card.setAlignment(Pos.CENTER);

        if (battleState.getPlayer().getStats().getReiatsu() < ability.getReiatsuCost()) {
            Label warn = new Label("Reiatsu insufficiente");
            warn.getStyleClass().add("selection-card-detail");
            warn.setStyle("-fx-text-fill: #e74c3c;");
            card.getChildren().add(warn);
            card.setOpacity(0.38);
            card.setMouseTransparent(true);
        } else {
            card.setOnMouseClicked(e -> {
                hideSelectionPanel();
                executeAction(new TurnAction(ActionType.USE_ABILITY, ability, null));
            });
        }
        return card;
    }

    private VBox buildItemCard(Item item) {
        String icon = item instanceof HealingPotion ? "🧪"
                    : item instanceof ReiatsuDrink   ? "💜"
                    : item instanceof SpiritBoost    ? "⚡"
                    : "🎁";

        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("selection-card-icon");

        Label name = new Label(item.getName());
        name.getStyleClass().add("selection-card-name");
        name.setWrapText(true);
        name.setMaxWidth(165);
        name.setAlignment(Pos.CENTER);

        Label desc = new Label(item.getDescription());
        desc.getStyleClass().add("selection-card-detail");
        desc.setWrapText(true);
        desc.setMaxWidth(165);

        VBox card = new VBox(7, iconLabel, name, desc);
        card.getStyleClass().add("selection-card");
        card.setAlignment(Pos.CENTER);
        card.setOnMouseClicked(e -> {
            hideSelectionPanel();
            executeAction(new TurnAction(ActionType.USE_ITEM, null, item));
        });
        return card;
    }


    private void executeAction(TurnAction action) {
        if (battleState.isBattleOver()) return;
        actionBar.setDisable(true);

        CombatResult pr = ctx.getBattleService().executePlayerAction(battleState, action);
        log("  ➤  " + pr.narrative());

        Animation phase1 = switch (action.type()) {
            case TRANSFORM_PARTIAL -> buildTransformEffect();
            case DEFEND                                 -> buildDefendAnimation();
            case USE_ITEM                               -> buildItemAnimation(action.item());
            default                                     -> buildAttackAnimation(pr.damageDealt() > 0);
        };

        phase1.setOnFinished(e -> {
            if (battleState.isBattleOver()) { handleBattleEnd(); return; }

            CombatResult er = ctx.getBattleService().executeEnemyTurn(battleState);
            log("  ◆  " + er.narrative());

            Animation phase2 = buildDamageAnimation(er.damageDealt() > 0);
            phase2.setOnFinished(e2 -> {
                updateUI();
                actionBar.setDisable(false);
                if (battleState.isBattleOver()) handleBattleEnd();
            });
            phase2.play();
        });
        phase1.play();
    }

    private Animation buildAttackAnimation(boolean hitsEnemy) {
        TranslateTransition lunge   = new TranslateTransition(Duration.millis(90), playerAvatarBox);
        lunge.setByX(18);
        TranslateTransition retreat = new TranslateTransition(Duration.millis(110), playerAvatarBox);
        retreat.setByX(-18);
        SequentialTransition playerMove = new SequentialTransition(lunge, retreat);

        if (!hitsEnemy) return playerMove;

        enemyHitOverlay.setStyle("-fx-background-color: rgba(255,40,40,0.62); -fx-background-radius: 6;");
        enemyHitOverlay.setOpacity(1.0);
        FadeTransition enemyFlash = new FadeTransition(Duration.millis(380), enemyHitOverlay);
        enemyFlash.setFromValue(1.0);
        enemyFlash.setToValue(0.0);
        return new ParallelTransition(playerMove, buildShake(enemyAvatarBox, 4, 9, 28), enemyFlash);
    }

    private Animation buildDamageAnimation(boolean hitsPlayer) {
        if (!hitsPlayer) return new PauseTransition(Duration.millis(120));

        playerHitOverlay.setStyle("-fx-background-color: rgba(255,20,20,0.62); -fx-background-radius: 6;");
        playerHitOverlay.setOpacity(1.0);
        FadeTransition playerFlash = new FadeTransition(Duration.millis(350), playerHitOverlay);
        playerFlash.setFromValue(1.0);
        playerFlash.setToValue(0.0);
        return new ParallelTransition(buildShake(playerAvatarBox, 4, 8, 28), playerFlash);
    }

    private Animation buildTransformEffect() {
        playerHitOverlay.setStyle("-fx-background-color: rgba(255,200,30,0.72); -fx-background-radius: 6;");
        playerHitOverlay.setOpacity(1.0);
        ScaleTransition pulse = new ScaleTransition(Duration.millis(180), playerAvatarBox);
        pulse.setFromX(1.0); pulse.setFromY(1.0);
        pulse.setToX(1.07);  pulse.setToY(1.07);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(2);
        FadeTransition goldFlash = new FadeTransition(Duration.millis(500), playerHitOverlay);
        goldFlash.setFromValue(1.0);
        goldFlash.setToValue(0.0);
        return new ParallelTransition(pulse, goldFlash);
    }

    private Animation buildDefendAnimation() {
        playerHitOverlay.setStyle("-fx-background-color: rgba(70,130,200,0.52); -fx-background-radius: 6;");
        playerHitOverlay.setOpacity(1.0);
        FadeTransition blueFlash = new FadeTransition(Duration.millis(400), playerHitOverlay);
        blueFlash.setFromValue(1.0);
        blueFlash.setToValue(0.0);
        ScaleTransition brace = new ScaleTransition(Duration.millis(150), playerAvatarBox);
        brace.setFromX(1.0); brace.setFromY(1.0);
        brace.setToX(0.96);  brace.setToY(0.96);
        brace.setAutoReverse(true);
        brace.setCycleCount(2);
        return new ParallelTransition(blueFlash, brace);
    }

    private Animation buildItemAnimation(Item item) {
        String color;
        if      (item instanceof HealingPotion) color = "rgba(50,220,80,0.72)";
        else if (item instanceof ReiatsuDrink)  color = "rgba(160,80,255,0.72)";
        else                                    color = "rgba(255,200,30,0.72)";

        playerHitOverlay.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 6;");
        playerHitOverlay.setOpacity(1.0);
        ScaleTransition pulse = new ScaleTransition(Duration.millis(200), playerAvatarBox);
        pulse.setFromX(1.0); pulse.setFromY(1.0);
        pulse.setToX(1.06);  pulse.setToY(1.06);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(2);
        FadeTransition flash = new FadeTransition(Duration.millis(520), playerHitOverlay);
        flash.setFromValue(1.0);
        flash.setToValue(0.0);
        return new ParallelTransition(pulse, flash);
    }

    private SequentialTransition buildShake(Node node, int reps, double dist, int stepMs) {
        SequentialTransition seq = new SequentialTransition();
        for (int i = 0; i < reps; i++) {
            TranslateTransition left  = new TranslateTransition(Duration.millis(stepMs), node);
            left.setByX(i % 2 == 0 ? -dist : dist);
            TranslateTransition right = new TranslateTransition(Duration.millis(stepMs), node);
            right.setByX(i % 2 == 0 ? dist : -dist);
            seq.getChildren().addAll(left, right);
        }
        return seq;
    }

    private void handleBattleEnd() {
        updateUI();
        if (battleState.isPlayerVictory()) {
            int xp              = ctx.getCharacterService().calculateXpReward(battleState.getCurrentEnemy());
            int levelBefore     = battleState.getPlayer().getStats().getLevel();
            List<Equipment> loot = ctx.getGameService().onBattleVictory(
                    ctx.getCurrentGameState(), battleState.getCurrentEnemy());
            int levelAfter      = battleState.getPlayer().getStats().getLevel();
            boolean lv          = levelAfter > levelBefore;
            log("");
            log("  ✔  VITTORIA! +" + xp + " XP" + (lv ? "  ⬆ LEVEL UP!" : ""));
            loot.forEach(eq -> log("  🎁  " + eq.getName() + "  (" + eq.getStatsSummary() + ")"));

            animateEnemyDeath(() -> {
                String current = ctx.getCurrentGameState().getCurrentEventId();
                String next    = resolvePostBattleEvent();
                ctx.getCurrentGameState().completeEvent(current);
                ctx.getCurrentGameState().setCurrentEventId(next);
                String details = "+" + xp + " EXP";
                if (lv) details += "\n⬆  LEVEL UP!   →   Lv. " + levelAfter;
                if (!loot.isEmpty()) {
                    details += "\n\n🎁  Bottino:";
                    for (Equipment eq : loot)
                        details += "\n  · " + eq.getName() + "  " + eq.getStatsSummary();
                }
                showEndOverlay(true,
                        "✔  VITTORIA!",
                        "Hai sconfitto " + battleState.getCurrentEnemy().getDisplayName() + "!",
                        details,
                        "▶  Continua l'avventura",
                        () -> ctx.getViewManager().showNarrativeView(ctx.getCurrentGameState()));
            });
        } else {
            log("  ✘  SCONFITTA.");
            showEndOverlay(false,
                    "✘  SCONFITTA",
                    "Sei stato sconfitto dal tuo avversario.",
                    "Il coraggio non è sufficiente.\nRicomincia dal menu principale.",
                    "◀  Menu Principale",
                    () -> ctx.getViewManager().showMainMenu());
        }
    }

    private void animateEnemyDeath(Runnable onDone) {
        enemyHitOverlay.setStyle("-fx-background-color: rgba(255,255,255,0.90); -fx-background-radius: 6;");
        enemyHitOverlay.setOpacity(1.0);
        FadeTransition whiteFlash = new FadeTransition(Duration.millis(110), enemyHitOverlay);
        whiteFlash.setFromValue(1.0);
        whiteFlash.setToValue(0.0);
        FadeTransition enemyFade = new FadeTransition(Duration.millis(520), enemyAvatarBox);
        enemyFade.setFromValue(1.0);
        enemyFade.setToValue(0.0);
        SequentialTransition death = new SequentialTransition(
                whiteFlash, new PauseTransition(Duration.millis(80)), enemyFade);
        death.setOnFinished(e -> onDone.run());
        death.play();
    }

    private String resolvePostBattleEvent() {
        var event = ctx.getNarrativeService()
                .getCurrentEvent(ctx.getCurrentGameState(), ctx.getWorld());
        if (event != null && !event.getChoices().isEmpty()) {
            return event.getChoices().get(0).getNextEventId();
        }
        return ctx.getCurrentGameState().getCurrentEventId();
    }

    private void showEndOverlay(boolean victory, String title, String subtitle,
                                 String details, String btnText, Runnable onConfirm) {
        endAction = onConfirm;
        endTitleLabel.setText(title);
        endSubtitleLabel.setText(subtitle);
        endDetailsLabel.setText(details);
        endConfirmBtn.setText(btnText);

        endTitleLabel.getStyleClass().removeAll("end-title-victory", "end-title-defeat");
        endPanel.getStyleClass().removeAll("end-panel-victory", "end-panel-defeat");
        endTitleLabel.getStyleClass().add(victory ? "end-title-victory" : "end-title-defeat");
        endPanel.getStyleClass().add(victory ? "end-panel-victory" : "end-panel-defeat");

        endPanel.setScaleX(0.65);
        endPanel.setScaleY(0.65);
        endOverlay.setOpacity(0);
        endOverlay.setVisible(true);

        ScaleTransition scale = new ScaleTransition(Duration.millis(340), endPanel);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(300), endOverlay);
        fade.setToValue(1.0);

        new ParallelTransition(scale, fade).play();
    }

    @FXML public void onEndConfirm() {
        if (endAction != null) endAction.run();
    }

    private void updateUI() {
        Player p  = battleState.getPlayer();
        var    ps = p.getStats();
        var    es = battleState.getCurrentEnemy().getStats();

        playerNameLabel.setText(p.getName());
        transformStateLabel.setText("[ " + transformLabel(p.getTransformationState()) + " ]");
        playerStatsLabel.setText(String.format("Lv.%d  ATK %d  DEF %d",
                ps.getLevel(), ps.getAttack(), ps.getDefense()));
        playerHpBar.setProgress(ps.getMaxHp() > 0 ? (double) ps.getHp() / ps.getMaxHp() : 0);
        playerReiBar.setProgress(ps.getMaxReiatsu() > 0
                ? (double) ps.getReiatsu() / ps.getMaxReiatsu() : 0);

        enemyNameLabel.setText(battleState.getCurrentEnemy().getDisplayName());
        enemyStatsLabel.setText(String.format("HP  %d / %d", es.getHp(), es.getMaxHp()));
        enemyHpBar.setProgress(es.getMaxHp() > 0 ? (double) es.getHp() / es.getMaxHp() : 0);
        difficultyLabel.setText("Difficoltà: " + battleState.getCurrentEnemy().getDifficulty().name());

        transformBtn.setText("🔥  Trasforma");
        transformBtn.setDisable(!p.canTransformPartial());
        abilitiesBtn.setText("✨  Abilità (" + p.getAbilities().size() + ")");
        itemsBtn.setText("🎒  Oggetti (" + p.getInventory().size() + ")");
    }

    private void loadCharacterImage() {
        Player p = battleState.getPlayer();
        String path;
        if (p instanceof Quincy)        path = "images/chars/quincy.png";
        else if (p instanceof Arrancar) path = "images/chars/arrancar.png";
        else                            path = "images/chars/shinigami.png";
        loadImage(playerImageView, path, null);
    }

    private void loadEnemyImage() {
        String path = battleState.getCurrentEnemy().getType().getImagePath();
        loadImage(enemyImageView, path, null);
        enemyImageView.setScaleX(-1); // specchio per guardare il giocatore
    }

    private void loadImage(ImageView view, String resourcePath, ColorAdjust effect) {
        try {
            var stream = App.class.getResourceAsStream(resourcePath);
            if (stream != null) {
                view.setImage(new Image(stream));
                if (effect != null) view.setEffect(effect);
            }
        } catch (Exception ignored) {}
    }

    private String transformLabel(TransformationState s) {
        return switch (s) {
            case NORMAL  -> "Forma Base";
            case PARTIAL -> "⚡ Forma Parziale";
        };
    }

    private void log(String text) {
        battleLogArea.appendText(text + "\n");
    }
}
