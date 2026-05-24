package it.unicam.cs.mpgc.rpg126115.model.world;

public enum Area {
    KARAKURA_TOWN("Karakura Town", "Il mondo dei vivi, dove le anime dei morti transitano."),
    SEIREITEI("Seireitei", "La fortezza della Soul Society, sede dei Gotei 13."),
    HUECO_MUNDO("Hueco Mundo", "Il regno eterno degli Hollow, avvolto nell'oscurità.");

    private final String displayName;
    private final String description;

    Area(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
