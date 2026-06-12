package it.unicam.cs.mpgc.rpg126115.repository;

import it.unicam.cs.mpgc.rpg126115.exception.GameStateNotFoundException;
import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.entity.*;
import it.unicam.cs.mpgc.rpg126115.model.world.Area;
import it.unicam.cs.mpgc.rpg126115.repository.impl.JsonGameStateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonGameStateRepositoryTest {

    private static final String ID = "test-repo-roundtrip";
    private final JsonGameStateRepository repo = new JsonGameStateRepository();

    @AfterEach
    void cleanup() {
        repo.delete(ID);
    }


    private GameState shinigamiState() {
        return new GameState(ID, new Shinigami("Ichigo"));
    }


    @Test
    void roundTrip_preservesPlayerName() {
        repo.save(shinigamiState());
        assertEquals("Ichigo", repo.load(ID).getPlayer().getName());
    }

    @Test
    void roundTrip_shinigami_preservesPlayerClass() {
        repo.save(shinigamiState());
        assertInstanceOf(Shinigami.class, repo.load(ID).getPlayer());
    }

    @Test
    void roundTrip_quincy_preservesPlayerClass() {
        repo.save(new GameState(ID, new Quincy("Uryu")));
        assertInstanceOf(Quincy.class, repo.load(ID).getPlayer());
    }

    @Test
    void roundTrip_arrancar_preservesPlayerClass() {
        repo.save(new GameState(ID, new Arrancar("Grimmjow")));
        assertInstanceOf(Arrancar.class, repo.load(ID).getPlayer());
    }

    @Test
    void roundTrip_preservesCurrentHp() {
        GameState gs = shinigamiState();
        gs.getPlayer().getStats().takeDamage(30);
        int expectedHp = gs.getPlayer().getStats().getHp();
        repo.save(gs);
        assertEquals(expectedHp, repo.load(ID).getPlayer().getStats().getHp());
    }

    @Test
    void roundTrip_preservesLevel() {
        GameState gs = shinigamiState();
        // Force a level-up by granting enough XP
        gs.getPlayer().getStats().gainExperience(200);
        int expectedLevel = gs.getPlayer().getStats().getLevel();
        repo.save(gs);
        assertEquals(expectedLevel, repo.load(ID).getPlayer().getStats().getLevel());
    }

    @Test
    void roundTrip_preservesCurrentArea() {
        GameState gs = shinigamiState();
        gs.setCurrentArea(Area.HUECO_MUNDO);
        repo.save(gs);
        assertEquals(Area.HUECO_MUNDO, repo.load(ID).getCurrentArea());
    }

    @Test
    void roundTrip_preservesCurrentEventId() {
        GameState gs = shinigamiState();
        gs.setCurrentEventId("hueco_mundo_gate");
        repo.save(gs);
        assertEquals("hueco_mundo_gate", repo.load(ID).getCurrentEventId());
    }

    @Test
    void roundTrip_preservesCompletedEvents() {
        GameState gs = shinigamiState();
        gs.completeEvent("start");
        gs.completeEvent("battle_menos");
        repo.save(gs);
        GameState loaded = repo.load(ID);
        assertTrue(loaded.isEventCompleted("start"));
        assertTrue(loaded.isEventCompleted("battle_menos"));
        assertFalse(loaded.isEventCompleted("battle_grimmjow"));
    }

    @Test
    void roundTrip_preservesSaveId() {
        repo.save(shinigamiState());
        assertEquals(ID, repo.load(ID).getSaveId());
    }


    @Test
    void load_nonExistentSave_throwsGameStateNotFoundException() {
        assertThrows(GameStateNotFoundException.class,
                () -> repo.load("non-existent-save-id-xyz"));
    }


    @Test
    void exists_beforeSave_returnsFalse() {
        assertFalse(repo.exists("never-saved-id-xyz"));
    }

    @Test
    void exists_afterSave_returnsTrue() {
        repo.save(shinigamiState());
        assertTrue(repo.exists(ID));
    }

    @Test
    void exists_afterDelete_returnsFalse() {
        repo.save(shinigamiState());
        repo.delete(ID);
        assertFalse(repo.exists(ID));
    }


    @Test
    void listAllIds_afterSave_containsSaveId() {
        repo.save(shinigamiState());
        assertTrue(repo.listAllIds().contains(ID));
    }

    @Test
    void listAllIds_afterDelete_doesNotContainSaveId() {
        repo.save(shinigamiState());
        repo.delete(ID);
        assertFalse(repo.listAllIds().contains(ID));
    }
}
