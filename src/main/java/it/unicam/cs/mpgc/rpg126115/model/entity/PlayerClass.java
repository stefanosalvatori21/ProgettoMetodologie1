package it.unicam.cs.mpgc.rpg126115.model.entity;

public enum PlayerClass {
    SHINIGAMI("Shinigami", "Bilanciato — Kido e Zanjutsu") {
        @Override public Player createPlayer(String name) { return new Shinigami(name); }
    },
    QUINCY("Quincy", "Distanza — Reishi e frecce sacre") {
        @Override public Player createPlayer(String name) { return new Quincy(name); }
    },
    ARRANCAR("Arrancar", "Corpo a corpo — Cero e forza bruta") {
        @Override public Player createPlayer(String name) { return new Arrancar(name); }
    };

    private final String displayName;
    private final String description;

    PlayerClass(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public abstract Player createPlayer(String name);

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
