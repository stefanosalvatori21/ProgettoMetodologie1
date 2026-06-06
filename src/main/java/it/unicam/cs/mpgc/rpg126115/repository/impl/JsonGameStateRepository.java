package it.unicam.cs.mpgc.rpg126115.repository.impl;

import it.unicam.cs.mpgc.rpg126115.exception.GameStateNotFoundException;
import it.unicam.cs.mpgc.rpg126115.exception.PersistenceException;
import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.entity.*;
import it.unicam.cs.mpgc.rpg126115.model.entity.stats.Stats;
import it.unicam.cs.mpgc.rpg126115.model.world.Area;
import it.unicam.cs.mpgc.rpg126115.repository.GameStateRepository;
import it.unicam.cs.mpgc.rpg126115.util.FilePathUtil;
import it.unicam.cs.mpgc.rpg126115.util.JsonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class JsonGameStateRepository implements GameStateRepository {

    @Override
    public void save(GameState gameState) {
        SaveData dto = toDto(gameState);
        String json = JsonUtil.toJson(dto);
        Path file = FilePathUtil.getSaveFile(gameState.getSaveId());
        try {
            Files.writeString(file, json);
        } catch (IOException e) {
            throw new PersistenceException("Errore nel salvataggio: " + file, e);
        }
    }

    @Override
    public GameState load(String saveId) {
        Path file = FilePathUtil.getSaveFile(saveId);
        if (!Files.exists(file)) throw new GameStateNotFoundException("Salvataggio non trovato: " + saveId);
        try {
            String json = Files.readString(file);
            SaveData dto = JsonUtil.fromJson(json, SaveData.class);
            return fromDto(dto);
        } catch (IOException e) {
            throw new PersistenceException("Errore nel caricamento: " + file, e);
        }
    }

    @Override
    public List<String> listAllIds() {
        try (Stream<Path> files = Files.list(FilePathUtil.getSavesDirectory())) {
            return files
                    .filter(p -> p.toString().endsWith(".json"))
                    .map(p -> p.getFileName().toString().replace(".json", ""))
                    .toList();
        } catch (IOException e) {
            throw new PersistenceException("Errore nell'elenco salvataggi", e);
        }
    }

    @Override
    public void delete(String saveId) {
        try {
            Files.deleteIfExists(FilePathUtil.getSaveFile(saveId));
        } catch (IOException e) {
            throw new PersistenceException("Errore nella cancellazione: " + saveId, e);
        }
    }

    @Override
    public boolean exists(String saveId) {
        return Files.exists(FilePathUtil.getSaveFile(saveId));
    }


    private static SaveData toDto(GameState gs) {
        Player p = gs.getPlayer();
        Stats s = p.getStats();
        SaveData dto = new SaveData();
        dto.saveId         = gs.getSaveId();
        dto.playerName     = p.getName();
        dto.playerClass    = p instanceof Shinigami ? "SHINIGAMI"
                           : p instanceof Quincy    ? "QUINCY"
                                                    : "ARRANCAR";
        dto.hp             = s.getHp();
        dto.maxHp          = s.getMaxHp();
        dto.attack         = s.getAttack();
        dto.defense        = s.getDefense();
        dto.reiatsu        = s.getReiatsu();
        dto.maxReiatsu     = s.getMaxReiatsu();
        dto.experience     = s.getExperience();
        dto.level          = s.getLevel();
        dto.currentArea    = gs.getCurrentArea().name();
        dto.currentEventId = gs.getCurrentEventId();
        dto.completedEvents = gs.getCompletedEventIds().stream().toList();
        return dto;
    }

    private static GameState fromDto(SaveData dto) {
        Player player = switch (dto.playerClass) {
            case "QUINCY"   -> new Quincy(dto.playerName);
            case "ARRANCAR" -> new Arrancar(dto.playerName);
            default         -> new Shinigami(dto.playerName);
        };
        Stats s = player.getStats();
        s.setMaxHp(dto.maxHp);
        s.setHp(dto.hp);
        s.setAttack(dto.attack);
        s.setDefense(dto.defense);
        s.setMaxReiatsu(dto.maxReiatsu);
        s.setReiatsu(dto.reiatsu);
        s.setLevel(dto.level);
        s.setExperience(dto.experience);

        GameState gs = new GameState(dto.saveId, player);
        gs.setCurrentArea(Area.valueOf(dto.currentArea));
        gs.setCurrentEventId(dto.currentEventId);
        if (dto.completedEvents != null) dto.completedEvents.forEach(gs::completeEvent);
        return gs;
    }

    private static class SaveData {
        String saveId, playerName, playerClass;
        int hp, maxHp, attack, defense, reiatsu, maxReiatsu, experience, level;
        String currentArea, currentEventId;
        List<String> completedEvents;
    }
}
