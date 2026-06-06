package it.unicam.cs.mpgc.rpg126115.util;

import it.unicam.cs.mpgc.rpg126115.exception.PersistenceException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilePathUtil {

    private static final String SAVES_DIR = "saves";

    private FilePathUtil() {}

    public static Path getSavesDirectory() {
        Path dir = Path.of(SAVES_DIR);
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new PersistenceException("Impossibile creare la cartella saves", e);
            }
        }
        return dir;
    }

    public static Path getSaveFile(String saveId) {
        return getSavesDirectory().resolve(saveId + ".json");
    }
}
