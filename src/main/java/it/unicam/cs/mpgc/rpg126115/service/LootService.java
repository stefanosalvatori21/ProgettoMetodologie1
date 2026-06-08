package it.unicam.cs.mpgc.rpg126115.service;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.equipment.Equipment;

import java.util.List;

public interface LootService {
    /** Generates equipment drops for defeating the given enemy. May return an empty list. */
    List<Equipment> generateLoot(Enemy enemy);
}
