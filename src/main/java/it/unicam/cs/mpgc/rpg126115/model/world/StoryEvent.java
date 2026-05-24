package it.unicam.cs.mpgc.rpg126115.model.world;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;

import java.util.List;

public class StoryEvent {
    private final String id;
    private final String title;
    private final String description;
    private final Area area;
    private final List<NarrativeChoice> choices;
    private final Enemy encounter;

    public StoryEvent(String id, String title, String description, Area area,
                      List<NarrativeChoice> choices, Enemy encounter) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.area = area;
        this.choices = List.copyOf(choices);
        this.encounter = encounter;
    }

    public boolean hasCombat() { return encounter != null; }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Area getArea() { return area; }
    public List<NarrativeChoice> getChoices() { return choices; }
    public Enemy getEncounter() { return encounter; }
}
