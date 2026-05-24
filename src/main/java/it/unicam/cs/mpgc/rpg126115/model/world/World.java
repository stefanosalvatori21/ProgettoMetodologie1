package it.unicam.cs.mpgc.rpg126115.model.world;

import java.util.HashMap;
import java.util.Map;

public class World {
    private final Map<String, StoryEvent> events = new HashMap<>();

    public void addEvent(StoryEvent event) {
        events.put(event.getId(), event);
    }

    public StoryEvent getEvent(String id) {
        return events.get(id);
    }

    public boolean hasEvent(String id) {
        return events.containsKey(id);
    }
}
