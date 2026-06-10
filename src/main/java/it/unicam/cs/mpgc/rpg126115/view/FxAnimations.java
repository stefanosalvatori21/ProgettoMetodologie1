package it.unicam.cs.mpgc.rpg126115.view;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;

// animazioni comuni per le transizioni tra schermate
public final class FxAnimations {

    private static final Duration ENTRANCE = Duration.millis(340);

    private FxAnimations() {}

    public static void playSceneEntrance(Parent root) {
        if (root == null) return;
        root.setOpacity(0.0);
        root.setTranslateY(16);

        FadeTransition fade = new FadeTransition(ENTRANCE, root);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition slide = new TranslateTransition(ENTRANCE, root);
        slide.setFromY(16);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition entrance = new ParallelTransition(fade, slide);
        entrance.setOnFinished(e -> {
            root.setOpacity(1.0);
            root.setTranslateY(0);
        });
        entrance.play();
    }

    public static void fadeIn(Node node, double millis) {
        if (node == null) return;
        node.setOpacity(0.0);
        node.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(millis), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public static void fadeOut(Node node, double millis) {
        if (node == null) return;
        FadeTransition ft = new FadeTransition(Duration.millis(millis), node);
        ft.setFromValue(node.getOpacity());
        ft.setToValue(0.0);
        ft.setOnFinished(e -> {
            node.setVisible(false);
            node.setOpacity(1.0);
        });
        ft.play();
    }

    public static void pop(Node node) {
        if (node == null) return;
        ScaleTransition st = new ScaleTransition(Duration.millis(140), node);
        st.setFromX(0.94);
        st.setFromY(0.94);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setInterpolator(Interpolator.EASE_OUT);
        st.play();
    }
}
