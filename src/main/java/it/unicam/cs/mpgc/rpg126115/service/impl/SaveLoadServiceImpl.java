package it.unicam.cs.mpgc.rpg126115.service.impl;

import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.repository.GameStateRepository;
import it.unicam.cs.mpgc.rpg126115.service.SaveLoadService;

import java.util.List;

public class SaveLoadServiceImpl implements SaveLoadService {

    private final GameStateRepository repository;

    public SaveLoadServiceImpl(GameStateRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(GameState gameState) {
        repository.save(gameState);
    }

    @Override
    public GameState load(String saveId) {
        return repository.load(saveId);
    }

    @Override
    public List<String> listSaves() {
        return repository.listAllIds();
    }

    @Override
    public boolean hasSave(String saveId) {
        return repository.exists(saveId);
    }
}
