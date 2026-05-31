package it.unicam.cs.mpgc.rpg126115.model.combat;

import it.unicam.cs.mpgc.rpg126115.model.ability.Ability;
import it.unicam.cs.mpgc.rpg126115.model.item.Item;

public record TurnAction(ActionType type, Ability ability, Item item) {}
