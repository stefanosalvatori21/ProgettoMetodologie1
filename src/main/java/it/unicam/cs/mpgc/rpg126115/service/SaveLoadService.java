package it.unicam.cs.mpgc.rpg126115.service;

import it.unicam.cs.mpgc.rpg126115.model.GameState;

import java.util.List;

public interface SaveLoadService {
    void save(GameState gameState);
    GameState load(String saveId);
    List<String> listSaves();
    boolean hasSave(String saveId);
}
