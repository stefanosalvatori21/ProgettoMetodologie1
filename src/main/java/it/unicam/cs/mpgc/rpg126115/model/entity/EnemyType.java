package it.unicam.cs.mpgc.rpg126115.model.entity;

import it.unicam.cs.mpgc.rpg126115.model.world.Area;

public enum EnemyType {

    // Karakura Town
    HOLLOW_MINORE(        "Hollow Minore",            Area.KARAKURA_TOWN, DifficultyLevel.EASY,     80,  22,  5, "images/enemies/hollow_minore.png"),
    PLUS_CORROTTO(        "Plus Corrotto",             Area.KARAKURA_TOWN, DifficultyLevel.EASY,     65,  19,  3, "images/enemies/plus_corrotto.png"),
    ANIMA_RIBELLE(        "Anima Ribelle",             Area.KARAKURA_TOWN, DifficultyLevel.EASY,     85,  24,  4, "images/enemies/anima_ribelle.png"),
    MENOS(                "Menos",                     Area.KARAKURA_TOWN, DifficultyLevel.EASY,    100,  28,  7, "images/enemies/menos.png"),
    SOLDATO_QUINCY(       "Soldato Quincy",            Area.KARAKURA_TOWN, DifficultyLevel.MEDIUM,  160,  38, 10, "images/enemies/soldato_quincy.png"),
    GRAND_FISHER(         "Grand Fisher",              Area.KARAKURA_TOWN, DifficultyLevel.MEDIUM,  260,  50, 14, "images/enemies/grand_fisher.png"),

    // Seireitei
    SHINIGAMI_BASSO_RANGO("Shinigami di Basso Rango", Area.SEIREITEI,     DifficultyLevel.EASY,    100,  26,  8, "images/enemies/shinigami_basso_rango.png"),
    SOUL_REAPER_GUARD(    "Guardia Soul Society",      Area.SEIREITEI,     DifficultyLevel.MEDIUM,  190,  36, 12, "images/enemies/soul_reaper_guard.png"),
    QUINCY_JAGDARMEE(     "Quincy Jagdarmee",          Area.SEIREITEI,     DifficultyLevel.MEDIUM,  175,  40, 10, "images/enemies/quincy_jagdarmee.png"),
    UFFICIALE_SEGGIO(     "Ufficiale di Seggio",       Area.SEIREITEI,     DifficultyLevel.MEDIUM,  260,  46, 16, "images/enemies/ufficiale_seggio.png"),
    MENOS_GRANDE(         "Menos Grande",              Area.SEIREITEI,     DifficultyLevel.HARD,    420,  60, 20, "images/enemies/menos_grande.png"),
    GIKONGAN_DIFETTOSO(   "Gikongan Difettoso",        Area.SEIREITEI,     DifficultyLevel.HARD,    360,  55, 18, "images/enemies/gikongan_difettoso.png"),
    VICE_CAPITANO_KIRA(   "Izuru Kira",                Area.SEIREITEI,     DifficultyLevel.BOSS,    700,  75, 28, "images/enemies/izuru_kira.png"),
    VICE_CAPITANO_RENJI(  "Renji Abarai",              Area.SEIREITEI,     DifficultyLevel.BOSS,    820,  82, 32, "images/enemies/renji_abarai.png"),
    BYAKUYA(              "Byakuya Kuchiki",           Area.SEIREITEI,     DifficultyLevel.BOSS,   1000,  90, 38, "images/enemies/byakuya.png"),
    HITSUGAYA(            "Tōshirō Hitsugaya",         Area.SEIREITEI,     DifficultyLevel.BOSS,    960,  86, 36, "images/enemies/hitsugaya.png"),
    HASCHWALTH(           "Jugram Haschwalth",         Area.SEIREITEI,     DifficultyLevel.BOSS,   1100,  92, 40, "images/enemies/haschwalth.png"),
    YAMAMOTO(             "Genryūsai Yamamoto",        Area.SEIREITEI,     DifficultyLevel.BOSS,   1400,  98, 44, "images/enemies/yamamoto.png"),
    KENPACHI(             "Kenpachi Zaraki",           Area.SEIREITEI,     DifficultyLevel.BOSS,   1300, 105, 32, "images/enemies/kenpachi.png"),

    // Hueco Mundo
    ADJUCHAS(             "Adjuchas",                  Area.HUECO_MUNDO,   DifficultyLevel.HARD,    450,  62, 22, "images/enemies/adjuchas.png"),
    FRACCION(             "Fracción",                  Area.HUECO_MUNDO,   DifficultyLevel.HARD,    550,  66, 25, "images/enemies/fraccion.png"),
    VASTO_LORDE(          "Vasto Lorde",               Area.HUECO_MUNDO,   DifficultyLevel.HARD,    700,  75, 32, "images/enemies/vasto_lorde.png"),
    STERNRITTER(          "Sternritter",               Area.HUECO_MUNDO,   DifficultyLevel.HARD,    620,  72, 28, "images/enemies/sternritter.png"),
    URYU(                 "Uryū Ishida",               Area.HUECO_MUNDO,   DifficultyLevel.BOSS,   1200, 108, 48, "images/enemies/uryu_ishida.png"),
    GRIMMJOW(             "Grimmjow Jaegerjaquez",     Area.HUECO_MUNDO,   DifficultyLevel.BOSS,   1500, 118, 55, "images/enemies/grimmjow.png"),
    ULQUIORRA(            "Ulquiorra Cifer",           Area.HUECO_MUNDO,   DifficultyLevel.BOSS,   1800, 132, 60, "images/enemies/ulquiorra.png"),
    AIZEN(                "Sosuke Aizen",              Area.HUECO_MUNDO,   DifficultyLevel.BOSS,   3000, 148, 76, "images/enemies/aizen.png"),
    YHWACH(               "Yhwach",                    Area.HUECO_MUNDO,   DifficultyLevel.BOSS,   5000, 188, 108, "images/enemies/yhwach.png");

    private final String displayName;
    private final Area area;
    private final DifficultyLevel difficulty;
    private final int baseHp;
    private final int baseAttack;
    private final int baseDefense;
    private final String imagePath;

    EnemyType(String displayName, Area area, DifficultyLevel difficulty,
              int baseHp, int baseAttack, int baseDefense, String imagePath) {
        this.displayName = displayName;
        this.area        = area;
        this.difficulty  = difficulty;
        this.baseHp      = baseHp;
        this.baseAttack  = baseAttack;
        this.baseDefense = baseDefense;
        this.imagePath   = imagePath;
    }

    public String          getDisplayName() { return displayName; }
    public Area            getArea()        { return area; }
    public DifficultyLevel getDifficulty()  { return difficulty; }
    public int             getBaseHp()      { return baseHp; }
    public int             getBaseAttack()  { return baseAttack; }
    public int             getBaseDefense() { return baseDefense; }
    public String          getImagePath()   { return imagePath; }
}
