package it.unicam.cs.mpgc.rpg126115.model.equipment;

public abstract class Equipment {
    private final String        name;
    private final String        description;
    private final String        abilityName;
    private final String        imagePath;
    private final EquipmentSlot slot;
    private final Rarity        rarity;

    protected Equipment(String name, String description, String abilityName,
                        String imagePath, EquipmentSlot slot, Rarity rarity) {
        this.name        = name;
        this.description = description;
        this.abilityName = abilityName;
        this.imagePath   = imagePath;
        this.slot        = slot;
        this.rarity      = rarity;
    }

    public String        getName()        { return name; }
    public String        getDescription() { return description; }
    public String        getAbilityName() { return abilityName; }
    public String        getImagePath()   { return imagePath; }
    public EquipmentSlot getSlot()        { return slot; }
    public Rarity        getRarity()      { return rarity; }

    /** Short label shown on cards, e.g. "+12 ATK" or "+8 DEF  +10 HP". */
    public abstract String getStatsSummary();
}
