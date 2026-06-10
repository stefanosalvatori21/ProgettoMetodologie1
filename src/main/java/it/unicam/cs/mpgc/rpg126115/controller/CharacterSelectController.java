package it.unicam.cs.mpgc.rpg126115.controller;

import it.unicam.cs.mpgc.rpg126115.App;
import it.unicam.cs.mpgc.rpg126115.AppContext;
import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.entity.PlayerClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CharacterSelectController {

    @FXML private TextField  nameField;
    @FXML private VBox       shinigamiCard;
    @FXML private VBox       quincyCard;
    @FXML private VBox       arrancarCard;
    @FXML private ImageView  shinigamiImage;
    @FXML private ImageView  quincyImage;
    @FXML private ImageView  arrancarImage;
    @FXML private Button     startButton;

    private final AppContext ctx;
    private PlayerClass selectedClass = null;

    public CharacterSelectController(AppContext ctx) {
        this.ctx = ctx;
    }

    @FXML
    public void initialize() {
        loadImage(shinigamiImage, "images/chars/shinigami.png");
        loadImage(quincyImage,    "images/chars/quincy.png");
        loadImage(arrancarImage,  "images/chars/arrancar.png");
    }

    @FXML public void onSelectShinigami() { selectClass(PlayerClass.SHINIGAMI); }
    @FXML public void onSelectQuincy()    { selectClass(PlayerClass.QUINCY); }
    @FXML public void onSelectArrancar()  { selectClass(PlayerClass.ARRANCAR); }

    @FXML
    public void onStart() {
        if (selectedClass == null) {
            ctx.getViewManager().showAlert("Attenzione", "Scegli prima una classe!");
            return;
        }
        String name = nameField.getText().strip();
        if (name.isBlank()) name = "Shinigami Senza Nome";

        GameState gameState = ctx.getGameService().newGame(name, selectedClass);
        ctx.setCurrentGameState(gameState);
        ctx.getViewManager().showNarrativeView(gameState);
    }

    @FXML
    public void onBack() {
        ctx.getViewManager().showMainMenu();
    }

    private void selectClass(PlayerClass cls) {
        selectedClass = cls;

        shinigamiCard.getStyleClass().remove("class-card-selected");
        quincyCard.getStyleClass().remove("class-card-selected");
        arrancarCard.getStyleClass().remove("class-card-selected");

        VBox chosen = switch (cls) {
            case SHINIGAMI -> shinigamiCard;
            case QUINCY    -> quincyCard;
            case ARRANCAR  -> arrancarCard;
        };
        chosen.getStyleClass().add("class-card-selected");
        startButton.setDisable(false);
    }

    private void loadImage(ImageView view, String path) {
        try {
            var stream = App.class.getResourceAsStream(path);
            if (stream == null) return;
            double w = view.getFitWidth()  > 0 ? view.getFitWidth()  * 2 : 840;
            double h = view.getFitHeight() > 0 ? view.getFitHeight() * 2 : 660;
            Image raw = new Image(stream, w, h, true, true);
            view.setImage(removeBackground(raw));
            view.setSmooth(true);
        } catch (Exception ignored) {}
    }

    // rimuove lo sfondo bianco dalle immagini dei personaggi
    private Image removeBackground(Image src) {
        int w = (int) src.getWidth();
        int h = (int) src.getHeight();
        if (w == 0 || h == 0) return src;

        WritableImage dst = new WritableImage(w, h);
        PixelReader  pr  = src.getPixelReader();
        PixelWriter  pw  = dst.getPixelWriter();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color c = pr.getColor(x, y);

                if (c.getOpacity() < 0.05) { pw.setColor(x, y, Color.TRANSPARENT); continue; }

                double r = c.getRed(), g = c.getGreen(), b = c.getBlue();
                double lum = r * 0.299 + g * 0.587 + b * 0.114;
                double sat = c.getSaturation();

                if (lum > 0.82 && sat < 0.18) {
                    double alpha = Math.max(0.0, 1.0 - (lum - 0.78) / 0.22);
                    pw.setColor(x, y, new Color(r, g, b, alpha * c.getOpacity()));
                } else {
                    pw.setColor(x, y, c);
                }
            }
        }
        return dst;
    }
}
