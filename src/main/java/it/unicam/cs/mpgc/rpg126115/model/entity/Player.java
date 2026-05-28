package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;
import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Armor;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Equipment;
import it.unicam.cs.mpgc.rpg126115.model.equipment.EquipmentSlot;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Weapon;
import it.unicam.cs.mpgc.rpg126115.model.item.Item;
import it.unicam.cs.mpgc.rpg126115.model.transformation.Transformation;
import it.unicam.cs.mpgc.rpg126115.model.transformation.TransformationState;
import it.unicam.cs.mpgc.rpg126115.model.transformation.Transformable;

import java.util.*;

public abstract class Player extends GameCharacter implements Transformable {
    private TransformationState transformationState;
    private final List<Ability>                    abilities;
    private final List<Item>                       inventory;
    private final Map<EquipmentSlot, Equipment>    equipped;
    private final List<Equipment>                  equipmentBag;

    protected Player(String name, Stats stats) {
        super(name, stats);
        this.transformationState = TransformationState.NORMAL;
        this.abilities           = new ArrayList<>(getStartingAbilities());
        this.inventory           = new ArrayList<>();
        this.equipped            = new EnumMap<>(EquipmentSlot.class);
        this.equipmentBag        = new ArrayList<>();
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


    public void equip(Equipment equipment) {
        Equipment current = equipped.get(equipment.getSlot());
        if (current != null) {
            removeEquipmentBonus(current);
            equipmentBag.add(current);
        }
        equipped.put(equipment.getSlot(), equipment);
        applyEquipmentBonus(equipment);
        equipmentBag.remove(equipment);
    }

    public void unequip(EquipmentSlot slot) {
        Equipment current = equipped.remove(slot);
        if (current != null) {
            removeEquipmentBonus(current);
            equipmentBag.add(current);
        }
    }

    private void applyEquipmentBonus(Equipment eq) {
        if (eq instanceof Weapon w) {
            getStats().setAttack(getStats().getAttack() + w.getBonusAtk());
        } else if (eq instanceof Armor a) {
            getStats().setDefense(getStats().getDefense() + a.getBonusDef());
            getStats().setMaxHp(getStats().getMaxHp() + a.getBonusMaxHp());
            getStats().setHp(getStats().getHp() + a.getBonusMaxHp());
        }
    }

    private void removeEquipmentBonus(Equipment eq) {
        if (eq instanceof Weapon w) {
            getStats().setAttack(Math.max(1, getStats().getAttack() - w.getBonusAtk()));
        } else if (eq instanceof Armor a) {
            getStats().setDefense(Math.max(0, getStats().getDefense() - a.getBonusDef()));
            int newMax = Math.max(1, getStats().getMaxHp() - a.getBonusMaxHp());
            getStats().setMaxHp(newMax);
            getStats().setHp(Math.min(getStats().getHp(), newMax));
        }
    }

    public Equipment              getEquipped(EquipmentSlot slot) { return equipped.get(slot); }
    public Map<EquipmentSlot, Equipment> getEquippedItems()       { return Collections.unmodifiableMap(equipped); }
    public List<Equipment>        getEquipmentBag()               { return Collections.unmodifiableList(equipmentBag); }
    public void addToEquipmentBag(Equipment eq)                   { equipmentBag.add(eq); }


    public List<Ability> getAbilities() { return abilities; }
}
