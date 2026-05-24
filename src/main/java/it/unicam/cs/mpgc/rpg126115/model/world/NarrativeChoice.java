package it.unicam.cs.mpgc.rpg126115.model.world;

import it.unicam.cs.mpgc.rpg126115.model.item.Item;

public class NarrativeChoice {
    private final String text;
    private final String nextEventId;
    private final Item reward;

    public NarrativeChoice(String text, String nextEventId, Item reward) {
        this.text = text;
        this.nextEventId = nextEventId;
        this.reward = reward;
    }

    public String getText() { return text; }
    public String getNextEventId() { return nextEventId; }
    public Item getReward() { return reward; }
    public boolean hasReward() { return reward != null; }
}
