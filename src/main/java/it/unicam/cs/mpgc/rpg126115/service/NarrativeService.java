package it.unicam.cs.mpgc.rpg126115.service;

import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.world.NarrativeChoice;
import it.unicam.cs.mpgc.rpg126115.model.world.StoryEvent;
import it.unicam.cs.mpgc.rpg126115.model.world.World;

public interface NarrativeService {
    StoryEvent getCurrentEvent(GameState state, World world);
    void applyChoice(GameState state, NarrativeChoice choice);
}
