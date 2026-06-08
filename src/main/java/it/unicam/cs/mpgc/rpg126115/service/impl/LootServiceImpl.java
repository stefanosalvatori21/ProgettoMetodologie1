package it.unicam.cs.mpgc.rpg126115.service.impl;

import it.unicam.cs.mpgc.rpg126115.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg126115.model.equipment.*;
import it.unicam.cs.mpgc.rpg126115.service.LootService;

import java.util.*;

public class LootServiceImpl implements LootService {

    private final Random random = new Random();


    private static final Map<Rarity, List<Equipment>> WEAPONS = Map.of(
        Rarity.COMMON, List.of(
            new Weapon(
                "Asauchi",
                "La Zanpakuto iniziale, priva di nome e spirito. Affilata ma neutra.",
                "Lama Neutra",
                "images/equipment/asauchi.png",
                12, Rarity.COMMON),
            new Weapon(
                "Hozukimaru",
                "La lancia componibile di Ikkaku Madarame, capace di estendersi a sorpresa.",
                "Allungo",
                "images/equipment/hozukimaru.png",
                25, Rarity.COMMON)
        ),
        Rarity.UNCOMMON, List.of(
            new Weapon(
                "Wabisuke",
                "La spada pesante di Izuru Kira. Ogni colpo raddoppia il peso del nemico.",
                "Raddoppio del Peso",
                "images/equipment/wabisuke.png",
                42, Rarity.UNCOMMON),
            new Weapon(
                "Zabimaru",
                "La Zanpakuto a frusta di Renji Abarai, un serpente segmentato con zanne affilate.",
                "Morso del Serpente",
                "images/equipment/zabimaru.png",
                60, Rarity.UNCOMMON),
            new Weapon(
                "Haineko",
                "La spada di cenere di Rangiku Matsumoto. Si dissolve in polvere tagliente.",
                "Nube di Cenere",
                "images/equipment/haineko.png",
                80, Rarity.UNCOMMON)
        ),
        Rarity.RARE, List.of(
            new Weapon(
                "Sode no Shirayuki",
                "La Zanpakuto di ghiaccio di Rukia Kuchiki. I colpi critici congelano il bersaglio.",
                "Tsukishiro",
                "images/equipment/sode_no_shirayuki.png",
                105, Rarity.RARE),
            new Weapon(
                "Senbonzakura",
                "I mille petali letali di Byakuya Kuchiki. Infligge danni continui per 3 turni.",
                "Tempesta di Lame",
                "images/equipment/senbonzakura.png",
                140, Rarity.RARE),
            new Weapon(
                "Zangetsu",
                "La mastodontica lama di Ichigo Kurosaki. Sblocca il Getsuga Tenshō.",
                "Getsuga Tenshō",
                "images/equipment/zangetsu.png",
                185, Rarity.RARE)
        ),
        Rarity.LEGENDARY, List.of(
            new Weapon(
                "Hyōrinmaru",
                "Il drago di ghiaccio di Toshiro Hitsugaya. Riduce la difesa dei nemici congelati.",
                "Era Glaciale",
                "images/equipment/hyorinmaru.png",
                250, Rarity.LEGENDARY),
            new Weapon(
                "Ryūjin Jakka",
                "La letale spada di fuoco del Comandante Yamamoto. Ogni attacco incendia il nemico.",
                "Inferno del Purgatorio",
                "images/equipment/ryujin_jakka.png",
                360, Rarity.LEGENDARY)
        )
    );


    private static final Map<Rarity, List<Equipment>> ARMORS = Map.of(
        Rarity.COMMON, List.of(
            new Armor(
                "Vesti del Rukongai",
                "Semplice tessuto logoro proveniente dai sobborghi di Soul Society.",
                "—",
                "images/equipment/vesti_rukongai.png",
                3, 25, Rarity.COMMON),
            new Armor(
                "Shihakushō dell'Accademia",
                "Divisa da studente dell'Accademia Shinigami. Aumenta l'EXP ottenuta in battaglia.",
                "Disciplina",
                "images/equipment/shihakusho_accademia.png",
                9, 60, Rarity.COMMON)
        ),
        Rarity.UNCOMMON, List.of(
            new Armor(
                "Shihakushō da Shinigami",
                "La divisa ufficiale del Gotei 13. Aumenta l'evasione del 5%.",
                "Flessibilità",
                "images/equipment/shihakusho_shinigami.png",
                16, 130, Rarity.UNCOMMON),
            new Armor(
                "Armatura dell'11ª Divisione",
                "Protezione robusta usata dai guerrieri di Zaraki. Potenzia l'ATK quando i PF calano.",
                "Spirito Combattivo",
                "images/equipment/armatura_11.png",
                28, 230, Rarity.UNCOMMON),
            new Armor(
                "Divisa della 4ª Divisione",
                "Protezione medica rinforzata. Aumenta l'efficacia delle cure del 20%.",
                "Rigenerazione Fluida",
                "images/equipment/divisa_4.png",
                38, 340, Rarity.UNCOMMON)
        ),
        Rarity.RARE, List.of(
            new Armor(
                "Mantello di Urahara",
                "Il mantello nero anti-Reiatsu di Kisuke Urahara. Riduce la probabilità di essere presi di mira.",
                "Mimetismo",
                "images/equipment/mantello_urahara.png",
                54, 480, Rarity.RARE),
            new Armor(
                "Haori da Vice-Capitano",
                "Il distintivo bianco da vice-capitano. Aumenta la Reiatsu massima del 15%.",
                "Autorità",
                "images/equipment/haori_vice_capitano.png",
                75, 720, Rarity.RARE),
            new Armor(
                "Tessuto di Shutara",
                "Creato dalla Guardia Reale. Riduce i danni subiti da attacchi elementali del 15%.",
                "Tessitura Spirituale",
                "images/equipment/tessuto_shutara.png",
                100, 1080, Rarity.RARE)
        ),
        Rarity.LEGENDARY, List.of(
            new Armor(
                "Haori da Capitano",
                "Il classico mantello bianco del Gotei 13. Potenzia la difesa di tutto il team.",
                "Orgoglio del Capitano",
                "images/equipment/haori_capitano.png",
                138, 1560, Rarity.LEGENDARY),
            new Armor(
                "Mantello del Re delle Anime",
                "L'armatura suprema. Immune a tutti gli status alterati negativi.",
                "Pressione Divina",
                "images/equipment/mantello_re_anime.png",
                195, 2520, Rarity.LEGENDARY)
        )
    );


    @Override
    public List<Equipment> generateLoot(Enemy enemy) {
        List<Equipment> drops = new ArrayList<>();
        double r = random.nextDouble();

        switch (enemy.getDifficulty()) {
            case EASY -> {
                if (r < 0.30) addDrop(drops, Rarity.COMMON);
            }
            case MEDIUM -> {
                if      (r < 0.15) addDrop(drops, Rarity.UNCOMMON);
                else if (r < 0.58) addDrop(drops, Rarity.COMMON);
            }
            case HARD -> {
                if      (r < 0.10) addDrop(drops, Rarity.RARE);
                else if (r < 0.38) addDrop(drops, Rarity.UNCOMMON);
                else if (r < 0.74) addDrop(drops, Rarity.COMMON);
                if (random.nextDouble() < 0.22) addDrop(drops, Rarity.COMMON);
            }
            case BOSS -> {
                if      (r < 0.10) addDrop(drops, Rarity.LEGENDARY);
                else if (r < 0.48) addDrop(drops, Rarity.RARE);
                else               addDrop(drops, Rarity.UNCOMMON);
                if (random.nextDouble() < 0.55) addDrop(drops, Rarity.COMMON);
            }
        }
        return drops;
    }

    private void addDrop(List<Equipment> drops, Rarity rarity) {
        Equipment eq = randomFromPool(rarity);
        if (eq != null) drops.add(eq);
    }

    private Equipment randomFromPool(Rarity rarity) {
        boolean weapon    = random.nextBoolean();
        List<Equipment> primary   = weapon ? WEAPONS.getOrDefault(rarity, List.of())
                                           : ARMORS.getOrDefault(rarity, List.of());
        List<Equipment> secondary = weapon ? ARMORS.getOrDefault(rarity, List.of())
                                           : WEAPONS.getOrDefault(rarity, List.of());
        List<Equipment> pool = primary.isEmpty() ? secondary : primary;
        if (pool.isEmpty()) return null;
        return pool.get(random.nextInt(pool.size()));
    }
}
