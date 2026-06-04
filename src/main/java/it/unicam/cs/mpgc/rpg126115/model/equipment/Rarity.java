package it.unicam.cs.mpgc.rpg126115.model.equipment;

public enum Rarity {
    COMMON    ("Comune",      "#a8b8d0"),
    UNCOMMON  ("Non Comune",  "#2ecc71"),
    RARE      ("Raro",        "#5b9cf6"),
    LEGENDARY ("Leggendario", "#ffd700");

    private final String displayName;
    private final String color;

    Rarity(String displayName, String color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() { return displayName; }
    public String getColor()       { return color; }
}
