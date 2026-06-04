package it.unicam.cs.mpgc.rpg126115.model.equipment;

public enum EquipmentSlot {
    WEAPON("Arma"),
    ARMOR("Armatura");

    private final String displayName;

    EquipmentSlot(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }
}
