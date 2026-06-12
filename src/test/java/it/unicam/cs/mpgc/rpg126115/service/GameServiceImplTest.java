package it.unicam.cs.mpgc.rpg126115.service;

import it.unicam.cs.mpgc.rpg126115.model.GameState;
import it.unicam.cs.mpgc.rpg126115.model.entity.*;
import it.unicam.cs.mpgc.rpg126115.model.world.Area;
import it.unicam.cs.mpgc.rpg126115.service.impl.CharacterServiceImpl;
import it.unicam.cs.mpgc.rpg126115.service.impl.GameServiceImpl;
import it.unicam.cs.mpgc.rpg126115.service.impl.LootServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceImplTest {

    private GameService service;

    @BeforeEach
    void setUp() {
        service = new GameServiceImpl(new CharacterServiceImpl(), new LootServiceImpl());
    }


    @Test
    void newGame_withShinigami_createsShinigami() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        assertInstanceOf(Shinigami.class, gs.getPlayer());
    }

    @Test
    void newGame_withQuincy_createsQuincy() {
        GameState gs = service.newGame("Uryu", PlayerClass.QUINCY);
        assertInstanceOf(Quincy.class, gs.getPlayer());
    }

    @Test
    void newGame_withArrancar_createsArrancar() {
        GameState gs = service.newGame("Grimmjow", PlayerClass.ARRANCAR);
        assertInstanceOf(Arrancar.class, gs.getPlayer());
    }

    @Test
    void newGame_setsPlayerName() {
        GameState gs = service.newGame("Rukia", PlayerClass.SHINIGAMI);
        assertEquals("Rukia", gs.getPlayer().getName());
    }

    @Test
    void newGame_startsAtKarakuraTown() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        assertEquals(Area.KARAKURA_TOWN, gs.getCurrentArea());
    }

    @Test
    void newGame_allClassesStartAtUnifiedStart() {
        assertEquals("start", service.newGame("Ichigo",   PlayerClass.SHINIGAMI).getCurrentEventId());
        assertEquals("start", service.newGame("Grimmjow", PlayerClass.ARRANCAR) .getCurrentEventId());
        assertEquals("start", service.newGame("Uryu",     PlayerClass.QUINCY)   .getCurrentEventId());
    }

    @Test
    void newGame_generatesDifferentSaveIds() {
        String id1 = service.newGame("A", PlayerClass.SHINIGAMI).getSaveId();
        String id2 = service.newGame("B", PlayerClass.SHINIGAMI).getSaveId();
        assertNotEquals(id1, id2);
    }

    @Test
    void newGame_playerStartsAtFullHp() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        var stats = gs.getPlayer().getStats();
        assertTrue(gs.getPlayer().isAlive());
        assertEquals(stats.getMaxHp(), stats.getHp());
    }

    @Test
    void newGame_playerStartsAtLevelOne() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        assertEquals(1, gs.getPlayer().getStats().getLevel());
    }


    @Test
    void onBattleVictory_awardsXpToPlayer() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        int xpBefore = gs.getPlayer().getStats().getExperience();
        service.onBattleVictory(gs, EnemyFactory.create(EnemyType.MENOS));
        assertTrue(gs.getPlayer().getStats().getExperience() > xpBefore);
    }

    @Test
    void onBattleVictory_easyEnemy_awards50Xp() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        service.onBattleVictory(gs, EnemyFactory.create(EnemyType.MENOS));
        // EASY enemy → 50 XP; player starts at 0
        assertEquals(50, gs.getPlayer().getStats().getExperience());
    }

    @Test
    void onBattleVictory_withSufficientXp_causesLevelUp() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        // Level 1 needs 100 XP; pre-fill 51 so the next EASY victory (50 XP) pushes past the threshold
        gs.getPlayer().getStats().gainExperience(51);
        int levelBefore = gs.getPlayer().getStats().getLevel();
        service.onBattleVictory(gs, EnemyFactory.create(EnemyType.MENOS));
        assertTrue(gs.getPlayer().getStats().getLevel() > levelBefore);
    }

    @Test
    void onBattleVictory_bossEnemy_causesLevelUp() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        int levelBefore = gs.getPlayer().getStats().getLevel();
        // BOSS awards 320 XP which exceeds the level-1 threshold (100); level-up is guaranteed
        service.onBattleVictory(gs, EnemyFactory.create(EnemyType.GRIMMJOW));
        assertTrue(gs.getPlayer().getStats().getLevel() > levelBefore);
    }


    @Test
    void isGameOver_whenPlayerAlive_returnsFalse() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        assertFalse(service.isGameOver(gs));
    }

    @Test
    void isGameOver_whenPlayerDead_returnsTrue() {
        GameState gs = service.newGame("Ichigo", PlayerClass.SHINIGAMI);
        gs.getPlayer().getStats().takeDamage(9999);
        assertTrue(service.isGameOver(gs));
    }
}
