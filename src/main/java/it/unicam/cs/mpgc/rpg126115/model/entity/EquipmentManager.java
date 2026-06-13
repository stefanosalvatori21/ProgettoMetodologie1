package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Equipment;
import it.unicam.cs.mpgc.rpg126115.model.equipment.EquipmentSlot;

import java.util.*;

public class EquipmentManager {

    private final Map<EquipmentSlot, Equipment> equipped = new EnumMap<>(EquipmentSlot.class);
    private final List<Equipment> bag = new ArrayList<>();

    public void equip(Equipment equipment, Stats stats) {
        Equipment current = equipped.get(equipment.getSlot());
        if (current != null) {
            current.removeBonus(stats);
            bag.add(current);
        }
        equipped.put(equipment.getSlot(), equipment);
        equipment.applyBonus(stats);
        bag.remove(equipment);
    }

    public void unequip(EquipmentSlot slot, Stats stats) {
        Equipment current = equipped.remove(slot);
        if (current != null) {
            current.removeBonus(stats);
            bag.add(current);
        }
    }

    public Equipment getEquipped(EquipmentSlot slot)            { return equipped.get(slot); }
    public Map<EquipmentSlot, Equipment> getEquippedItems()     { return Collections.unmodifiableMap(equipped); }
    public List<Equipment> getBag()                             { return Collections.unmodifiableList(bag); }
    public void addToBag(Equipment eq)                          { bag.add(eq); }
}
