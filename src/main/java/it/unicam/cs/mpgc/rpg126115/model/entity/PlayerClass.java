package it.unicam.cs.mpgc.rpg126115.model.entity;

public enum PlayerClass {
    SHINIGAMI("Shinigami", "Bilanciato — Kido e Zanjutsu"),
    QUINCY("Quincy", "Distanza — Reishi e frecce sacre"),
    ARRANCAR("Arrancar", "Corpo a corpo — Cero e forza bruta");

    private final String displayName;
    private final String description;

    PlayerClass(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
