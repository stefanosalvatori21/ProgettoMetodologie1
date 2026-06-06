package it.unicam.cs.mpgc.rpg126115.model;

import it.unicam.cs.mpgc.rpg126115.model.entity.Player;
import it.unicam.cs.mpgc.rpg126115.model.item.Accessory;
import it.unicam.cs.mpgc.rpg126115.model.item.Item;
import it.unicam.cs.mpgc.rpg126115.model.world.Area;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameState {
    private final String saveId;
    private final Player player;
    private Area currentArea;
    private String currentEventId;
    private final List<Item> inventory;
    private final List<Accessory> equippedAccessories;
    private final Set<String> completedEventIds;

    public GameState(String saveId, Player player) {
        this.saveId = saveId;
        this.player = player;
        this.currentArea = Area.KARAKURA_TOWN;
        this.currentEventId = "start";
        this.inventory = new ArrayList<>();
        this.equippedAccessories = new ArrayList<>();
        this.completedEventIds = new HashSet<>();
    }

    public void completeEvent(String eventId) {
        completedEventIds.add(eventId);
    }

    public boolean isEventCompleted(String eventId) {
        return completedEventIds.contains(eventId);
    }

    public String getSaveId() { return saveId; }
    public Player getPlayer() { return player; }
    public Area getCurrentArea() { return currentArea; }
    public void setCurrentArea(Area currentArea) { this.currentArea = currentArea; }
    public String getCurrentEventId() { return currentEventId; }
    public void setCurrentEventId(String currentEventId) { this.currentEventId = currentEventId; }
    public List<Item> getInventory() { return inventory; }
    public List<Accessory> getEquippedAccessories() { return equippedAccessories; }
    public Set<String> getCompletedEventIds() { return completedEventIds; }
}
