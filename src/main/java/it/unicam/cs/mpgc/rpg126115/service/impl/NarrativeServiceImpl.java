package it.unicam.cs.mpgc.rpg126115.service.impl;

import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.world.NarrativeChoice;
import it.unicam.cs.mpgc.rpg126115.model.world.StoryEvent;
import it.unicam.cs.mpgc.rpg126115.model.world.World;
import it.unicam.cs.mpgc.rpg126115.service.NarrativeService;

public class NarrativeServiceImpl implements NarrativeService {

    @Override
    public StoryEvent getCurrentEvent(GameState state, World world) {
        return world.getEvent(state.getCurrentEventId());
    }

    @Override
    public void applyChoice(GameState state, NarrativeChoice choice) {
        state.completeEvent(state.getCurrentEventId());
        state.setCurrentEventId(choice.getNextEventId());
        if (choice.hasReward()) {
            state.getInventory().add(choice.getReward());
        }
    }
}
