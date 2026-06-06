package it.unicam.cs.mpgc.rpg126115.repository;

import it.unicam.cs.mpgc.rpg126115.model.GameState;

import java.util.List;

public interface GameStateRepository {
    void save(GameState gameState);
    GameState load(String saveId);
    List<String> listAllIds();
    void delete(String saveId);
    boolean exists(String saveId);
}
