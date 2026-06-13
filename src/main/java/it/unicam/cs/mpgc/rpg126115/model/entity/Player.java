package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;
import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Equipment;
import it.unicam.cs.mpgc.rpg126115.model.equipment.EquipmentSlot;
import it.unicam.cs.mpgc.rpg126115.model.item.Item;
import it.unicam.cs.mpgc.rpg126115.model.transformation.Transformation;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;
import it.unicam.cs.mpgc.rpg126115.model.transformation.Transformable;

import java.util.*;

public abstract class Player extends GameCharacter implements Transformable {
    private TransformationState    transformationState;
    private final List<Ability>    abilities;
    private final List<Item>       inventory;
    private final EquipmentManager equipmentManager;

    protected Player(String name, Stats stats) {
        super(name, stats);
        this.transformationState = TransformationState.NORMAL;
        this.abilities           = new ArrayList<>(getStartingAbilities());
        this.inventory           = new ArrayList<>();
        this.equipmentManager    = new EquipmentManager();
    }

    public abstract List<Ability> getStartingAbilities();
    public abstract Transformation getPartialTransformation();


    @Override public TransformationState getTransformationState() { return transformationState; }

    @Override
    public boolean canTransformPartial() {
        return transformationState == TransformationState.NORMAL;
    }

    @Override
    public void activatePartial() {
        if (!canTransformPartial()) return;
        Transformation t = getPartialTransformation();
        applyTransformationMultipliers(t);
        abilities.addAll(t.getUnlockedAbilities());
        transformationState = TransformationState.PARTIAL;
    }

    private void applyTransformationMultipliers(Transformation t) {
        Stats s = getStats();
        s.setAttack((int)(s.getAttack() * t.getAttackMult()));
        s.setDefense((int)(s.getDefense() * t.getDefenseMult()));
        s.setMaxReiatsu((int)(s.getMaxReiatsu() * t.getReiatsuMult()));
    }

    public void resetBattleState() {
        transformationState = TransformationState.NORMAL;
    }

    @Override
    public int computeAttackDamage() { return getStats().getAttack(); }


    public List<Item> getInventory()         { return inventory; }
    public void addItem(Item item)           { inventory.add(item); }
    public boolean removeItem(Item item)     { return inventory.remove(item); }


    public void equip(Equipment equipment)                       { equipmentManager.equip(equipment, getStats()); }
    public void unequip(EquipmentSlot slot)                      { equipmentManager.unequip(slot, getStats()); }
    public Equipment getEquipped(EquipmentSlot slot)             { return equipmentManager.getEquipped(slot); }
    public Map<EquipmentSlot, Equipment> getEquippedItems()      { return equipmentManager.getEquippedItems(); }
    public List<Equipment> getEquipmentBag()                     { return equipmentManager.getBag(); }
    public void addToEquipmentBag(Equipment eq)                  { equipmentManager.addToBag(eq); }


    public List<Ability> getAbilities() { return abilities; }
}
