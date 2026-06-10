package it.unicam.cs.mpgc.rpg126115;

import it.unicam.cs.mpgc.rpg126115.model.entity.EnemyFactory;
import it.unicam.cs.mpgc.rpg126115.model.entity.EnemyType;
import it.unicam.cs.mpgc.rpg126115.model.world.Area;
import it.unicam.cs.mpgc.rpg126115.model.world.NarrativeChoice;
import it.unicam.cs.mpgc.rpg126115.model.world.StoryEvent;
import it.unicam.cs.mpgc.rpg126115.model.world.World;

import java.util.List;

public class WorldBuilder {

    private WorldBuilder() {}

    private static StoryEvent story(String id, String title, String description,
                                    Area area, List<NarrativeChoice> choices) {
        return new StoryEvent(id, title, description, area, choices, null);
    }

    private static StoryEvent combat(String id, String title, String description,
                                     Area area, EnemyType enemy, String afterVictory) {
        return new StoryEvent(id, title, description, area,
                List.of(new NarrativeChoice("Continua", afterVictory, null)),
                EnemyFactory.create(enemy));
    }

    private static NarrativeChoice go(String text, String id) {
        return new NarrativeChoice(text, id, null);
    }

    public static World buildWorld() {
        World w = new World();
        addAct1(w);
        addAct2(w);
        addAct3(w);
        addAct4(w);
        return w;
    }

    // ATTO 1 - KARAKURA TOWN
    private static void addAct1(World w) {

        w.addEvent(story("start",
                "La Frattura dei Tre Mondi",
                "Il cielo sopra Karakura Town è lacerato da incrinature invisibili all'occhio " +
                "umano ma vivide come sangue alla tua percezione spirituale. Le anime dei " +
                "defunti svaniscono prima di raggiungere il Seireitei — qualcosa le consuma " +
                "dall'interno delle fratture dimensionali. Sei arrivato in tempo, ma ogni " +
                "secondo conta. Un Hollow si aggira tra i civili ignari mentre in lontananza " +
                "percepisci un'energia oscura ancora più minacciosa che avanza dai vicoli.",
                Area.KARAKURA_TOWN,
                List.of(
                    go("Affronta direttamente l'Hollow — ogni secondo conta",
                       "a1_hollow"),
                    go("Segui le tracce di energia oscura verso i vicoli popolari",
                       "a1_plus"))));

        w.addEvent(combat("a1_hollow",
                "Il Divoratore di Anime",
                "L'Hollow si volta: la maschera crepata rivela occhi privi di anima. Emette " +
                "un urlo gutturale e si avventa su di te con la bocca spalancata. Dietro di " +
                "lui intravedi un'anima terrorizzata accovacciata nell'angolo di un vicolo. " +
                "Non puoi lasciargli divorare nemmeno un'altra anima. La tua energia " +
                "spirituale risponde al pericolo.",
                Area.KARAKURA_TOWN, EnemyType.HOLLOW_MINORE, "a1_urahara"));

        w.addEvent(combat("a1_plus",
                "L'Anima sul Precipizio",
                "Seguendo le tracce trovi un Plus Corrotto — un'anima umana sull'orlo della " +
                "trasformazione in Hollow. I suoi occhi oscillano tra la lucidità disperata " +
                "e l'oscurità animale. La scintilla di coscienza si sta spegnendo " +
                "velocemente. Devi fermarlo prima che la trasformazione sia irreversibile " +
                "e l'anima vada perduta per sempre.",
                Area.KARAKURA_TOWN, EnemyType.PLUS_CORROTTO, "a1_urahara"));

        w.addEvent(story("a1_urahara",
                "Il Negoziante di Segreti",
                "Kisuke Urahara emerge dall'ombra con il cappello abbassato sugli occhi, " +
                "una ventola in mano e un'espressione insolitamente seria. «Due minacce in " +
                "arrivo simultanee,» dice senza alzare lo sguardo. «Un branco di Menos " +
                "scende dai distretti est mentre una pattuglia di Soldati Quincy avanza da " +
                "ovest. Probabilmente non è una coincidenza.» Si gira verso di te con " +
                "quell'espressione a metà tra un sorriso e una minaccia. «Quale vuoi " +
                "fermare tu?»",
                Area.KARAKURA_TOWN,
                List.of(
                    go("Elimina il Menos prima che divori altri Pluses",
                       "a1_menos"),
                    go("Intercetta il Soldato Quincy che si avvicina da ovest",
                       "a1_quincy"))));

        w.addEvent(combat("a1_menos",
                "Il Colosso del Rukongai",
                "Il Menos è un colosso avvolto in una veste nera che assorbe la luce " +
                "circostante. I suoi occhi gialli ti fissano dall'alto con indifferenza — " +
                "per lui sei poco più di un ostacolo. Il suo Cero si sta accumulando: una " +
                "sfera di energia oscura pulsante che, se rilasciata, cancellerebbe l'intero " +
                "isolato. Non puoi lasciarglielo sparare.",
                Area.KARAKURA_TOWN, EnemyType.MENOS, "a1_grand_fisher"));

        w.addEvent(combat("a1_quincy",
                "L'Arciere della Luce Sacra",
                "Il Soldato Quincy in uniforme bianca punta l'arco spirituale verso di te " +
                "con tecnica impeccabile. «Avversario spirituale identificato. Protocollo " +
                "di eliminazione attivato.» Le sue Heilig Pfeil sfrecciano nell'oscurità " +
                "come fulmini azzurri. Ogni freccia è precisa, fredda, letale — portavoce " +
                "di un ordine militare che non conosce pietà.",
                Area.KARAKURA_TOWN, EnemyType.SOLDATO_QUINCY, "a1_grand_fisher"));

        w.addEvent(combat("a1_grand_fisher",
                "Grand Fisher — Il Cacciatore Antico",
                "La sua presenza domina l'area come un'oppressione fisica: Grand Fisher, " +
                "il leggendario Hollow che si nutre di anime ad alto potenziale spirituale " +
                "da decenni. I suoi tentacoli si dispiegano nell'aria come dita di nebbia. " +
                "La sua esca illusoria — una figura umana che prende le forme di chi ami di " +
                "più — cerca di inchiodare la tua attenzione. Ma tu vedi attraverso " +
                "l'inganno. E la tua rabbia è un'arma più tagliente di qualsiasi lama.",
                Area.KARAKURA_TOWN, EnemyType.GRAND_FISHER, "a2_arrival"));
    }

    // ATTO 2 - SEIREITEI
    private static void addAct2(World w) {

        w.addEvent(story("a2_arrival",
                "L'Assedio del Seireitei",
                "Il Senkaimon ti inghiotte e ti espelle all'interno del Seireitei in mezzo " +
                "al caos totale. Le mura di Sekkiseki bianco risuonano degli scontri che si " +
                "combattono simultaneamente su ogni fronte. Un Menos Grande sfonda la Porta " +
                "Est mentre la Jagdarmee del Wandenreich si infiltra dai passaggi " +
                "sotterranei. Le divisioni del Gotei 13 reagiscono, ma l'assalto coordinato " +
                "le sovraccarica. Devi scegliere dove il tuo intervento è più critico.",
                Area.SEIREITEI,
                List.of(
                    go("Rinforza la Porta Est — ferma il Menos Grande prima che entri",
                       "a2_east"),
                    go("Proteggi i laboratori della 12ª Divisione dall'incursione nemica",
                       "a2_labs"))));

        w.addEvent(combat("a2_east",
                "Il Gigante alla Porta Est",
                "Il Menos Grande sfonda il portale con la forza bruta, proiettando frammenti " +
                "di pietra di Sekkiseki in ogni direzione. La sua statura oscura il cielo " +
                "del Seireitei come una torre vivente. Zaraki Kenpachi potrebbe spezzarlo " +
                "con un sorriso, ma lui è altrove. Sei tu il più vicino. " +
                "Sei tu il baluardo.",
                Area.SEIREITEI, EnemyType.MENOS_GRANDE, "a2_bridge1"));

        w.addEvent(combat("a2_labs",
                "Il Costrutto Impazzito",
                "I laboratori della 12ª Divisione nascondono esperimenti classificati che " +
                "non compaiono in nessun registro ufficiale. Appena entri, una capsula di " +
                "contenimento si frantuma: un Gikongan Difettoso — Mod Soul imprigionato in " +
                "un corpo artificialmente potenziato — si avventa verso di te con forza " +
                "sovrumana. Gli occhi privi di coscienza riflettono solo violenza pura.",
                Area.SEIREITEI, EnemyType.GIKONGAN_DIFETTOSO, "a2_bridge1"));

        w.addEvent(story("a2_bridge1",
                "La Breccia nelle Mura Interiori",
                "La prima linea regge, ma l'intelligence ti raggiunge con notizie " +
                "allarmanti: la Quincy Jagdarmee ha già violato le mura interiori e si " +
                "dirige verso gli archivi storici del Gotei 13, dove sono custoditi i " +
                "sigilli delle Zanpakutō confiscate e le chiavi dimensionali che danno " +
                "accesso al Palazzo del Re delle Anime. Non puoi permettere che cadano " +
                "in mani nemiche.",
                Area.SEIREITEI,
                List.of(
                    go("Intercetta la Jagdarmee prima che raggiunga gli archivi",
                       "a2_jagd"),
                    go("Difendi l'Ufficiale di Seggio incaricato di custodire i sigilli segreti",
                       "a2_ufficiale"))));

        w.addEvent(combat("a2_jagd",
                "La Jagdarmee nel Cuore del Seireitei",
                "I soldati Quincy si muovono in formazione tattica nei corridoi del " +
                "Seireitei con la precisione di chi conosce ogni angolo — troppa precisione " +
                "per un assalto improvvisato. Evidentemente avevano informatori interni. " +
                "L'ufficiale che guida il plotone ti punta l'arco spirituale con occhi " +
                "gelidi. La sua formazione è irreprensibile. La tua reazione non lo è.",
                Area.SEIREITEI, EnemyType.QUINCY_JAGDARMEE, "a2_bridge2"));

        w.addEvent(combat("a2_ufficiale",
                "L'Alleato Tradito",
                "L'Ufficiale di Seggio che proteggeva i sigilli è già stato raggiunto " +
                "dall'illusione di Aizen — Kyōka Suigetsu ha trasformato l'alleato in uno " +
                "strumento del nemico. I suoi occhi sono velati da una nebbia grigia. " +
                "Non c'è modo di ragionare con chi non può distinguere realtà da visione. " +
                "L'unico modo per liberarlo è spezzare l'illusione attraverso il combattimento.",
                Area.SEIREITEI, EnemyType.UFFICIALE_SEGGIO, "a2_bridge2"));

        w.addEvent(story("a2_bridge2",
                "I Vice-Capitani sotto Illusione",
                "L'estensione del Kyōka Suigetsu si rivela più vasta del previsto. Due " +
                "Vice-Capitani — tra i guerrieri più affidabili del Gotei 13 — sono stati " +
                "raggiunti dall'illusione di Aizen e ora bloccano il passaggio verso il " +
                "Comando Centrale come sentinelle inconsapevoli. L'unico modo per spezzare " +
                "un'illusione così profonda è attraverso la realtà fisica: un combattimento " +
                "abbastanza intenso da rompere la presa di Kyōka Suigetsu sulla mente.",
                Area.SEIREITEI,
                List.of(
                    go("Affronta Izuru Kira — spezza l'illusione prima che sia troppo tardi",
                       "a2_kira"),
                    go("Risveglia Renji Abarai attraverso il combattimento diretto",
                       "a2_renji"))));

        w.addEvent(combat("a2_kira",
                "Il Peso di Wabisuke",
                "Izuru Kira ti guarda con occhi vuoti in cui non riconosci la persona che " +
                "era. Wabisuke è già attivata: ogni colpo che infligge raddoppia il peso " +
                "fisico del bersaglio. La sensazione è quella di portare il mondo sulle " +
                "spalle. Ma i suoi occhi, sotto lo strato grigio dell'illusione, tremano " +
                "ancora — una parte di lui combatte dall'interno. " +
                "Combattilo abbastanza forte da far vincere quella parte.",
                Area.SEIREITEI, EnemyType.VICE_CAPITANO_KIRA, "a2_bridge3"));

        w.addEvent(combat("a2_renji",
                "I Denti di Zabimaru",
                "Renji Abarai si lancia verso di te con un grido di guerra che riecheggia " +
                "tra le mura bianche del Seireitei. Zabimaru si distende come un serpente " +
                "segmentato, guadagnando metro dopo metro di portata. I suoi occhi sono " +
                "accesi — ma non di rabbia propria. È la furia di Aizen a guidarlo. " +
                "Sotto ogni colpo senti la resistenza di un uomo che non vuole combattere " +
                "ma non può farne a meno. Ferma la sua mano.",
                Area.SEIREITEI, EnemyType.VICE_CAPITANO_RENJI, "a2_bridge3"));

        w.addEvent(story("a2_bridge3",
                "L'Ombra di Aizen",
                "Con i Vice-Capitani liberati dall'illusione, il quadro strategico si " +
                "completa. Le informazioni recuperate dagli archivi confermano l'orrore: " +
                "Sosuke Aizen non è nella sua prigione. Si è liberato, ha potenziato " +
                "l'Hōgyoku, e ha stretto un patto con Yhwach dell'Impero Quincy. " +
                "I due minacciano di fondere i Tre Mondi in uno solo — cancellando la " +
                "distinzione tra vita, morte e vuoto. Per fermarli serve superare uno dei " +
                "guardiani del Seireitei e raggiungere il Garganta verso Hueco Mundo.",
                Area.SEIREITEI,
                List.of(
                    go("Sfida Byakuya Kuchiki — recupera i sigilli dimensionali dalla 6ª Divisione",
                       "a2_byakuya"),
                    go("Affronta Tōshirō Hitsugaya — distruggi il cristallo di controllo della 10ª Divisione",
                       "a2_hitsugaya"))));

        w.addEvent(combat("a2_byakuya",
                "I Mille Petali di Senbonzakura",
                "Byakuya Kuchiki non ti sbarrerà la strada per ostilità — lo fa per senso " +
                "del dovere assoluto, la stessa forza che lo ha reso il Capitano più " +
                "rispettato e temuto del Gotei 13. I petali di Senbonzakura si disperdono " +
                "nell'aria come una tempesta d'argento silenziosa. Ogni frammento è una " +
                "lama. Mille lame. E nessuna di esse perdona chi non è abbastanza forte.",
                Area.SEIREITEI, EnemyType.BYAKUYA, "a3_arrival"));

        w.addEvent(combat("a2_hitsugaya",
                "Il Drago di Ghiaccio",
                "Tōshirō Hitsugaya è il più giovane Capitano del Gotei 13, ma la sua " +
                "Zanpakutō Hyōrinmaru — il drago di ghiaccio — è tra le più potenti mai " +
                "esistite. Il freddo si propaga dal suo corpo come un'onda che cristallizza " +
                "l'aria. Gli occhi color turchese ti fissano con determinazione gelida. " +
                "«Non ti lascerò passare finché non dimostri di essere il più forte.»",
                Area.SEIREITEI, EnemyType.HITSUGAYA, "a3_arrival"));
    }

    // ATTO 3 - HUECO MUNDO
    private static void addAct3(World w) {

        w.addEvent(story("a3_arrival",
                "Il Deserto di Las Noches",
                "Il Garganta si chiude alle tue spalle con un suono simile a un sospiro " +
                "del mondo. Il deserto di Hueco Mundo si estende all'orizzonte come un " +
                "oceano immobile di sabbia bianca, illuminato da una luna perenne che non " +
                "conosce né alba né tramonto. Lontano, le torri di Las Noches si stagliano " +
                "come ossa gigantesche nel cielo viola. Il percorso verso la fortezza " +
                "passa attraverso territorio selvaggio — scegli come avanzare.",
                Area.HUECO_MUNDO,
                List.of(
                    go("Attraversa il deserto aperto — diretto e rischioso",
                       "a3_adjuchas"),
                    go("Infiltrati dalle rovine sotterranee che costeggiano Las Noches",
                       "a3_fraccion"))));

        w.addEvent(combat("a3_adjuchas",
                "La Bestia del Deserto Bianco",
                "La creatura emerge dalla sabbia senza preavviso — un Adjuchas dalla forma " +
                "vagamente felina con tre maschere sovrapposte che testimoniano la sua " +
                "evoluzione plurisecolare. La sua velocità è oltre il visibile. I suoi " +
                "attacchi sono istintivi, spietati, calibrati per massimizzare il dolore. " +
                "E la sua rigenerazione è dannatamente rapida.",
                Area.HUECO_MUNDO, EnemyType.ADJUCHAS, "a3_noches_gate"));

        w.addEvent(combat("a3_fraccion",
                "Il Guardiano delle Rovine",
                "Le rovine sotterranee pullulano di Arrancar di rango inferiore che " +
                "presidiano gli accessi secondari a Las Noches. Un membro della Fracción " +
                "ti intercetta nell'oscurità dei tunnel: la sua Resurrección è già " +
                "parzialmente attivata, rivelando una forma ibrida tra umano e bestia. " +
                "Parla con la cadenza precisa di un soldato che ha ricevuto ordini chiari: " +
                "nessuno entra. Nessuno esce.",
                Area.HUECO_MUNDO, EnemyType.FRACCION, "a3_noches_gate"));

        w.addEvent(story("a3_noches_gate",
                "I Cancelli dell'Assoluto",
                "Le porte di Las Noches si ergono davanti a te come una parete bianca " +
                "senza fine — costruite per impressionare, per schiacciare, per ricordare " +
                "a chiunque le avvicini quanto sia piccolo di fronte a ciò che le abita. " +
                "La difesa esterna è affidata a una creatura di potenza ancestrale. " +
                "Il tuo istinto ti dice che esistono due modi per superarla.",
                Area.HUECO_MUNDO,
                List.of(
                    go("Affronta il Vasto Lorde in campo aperto — forza contro forza",
                       "a3_vasto"),
                    go("Distrai il guardiano e sabota il portale con un attacco a sorpresa",
                       "a3_sternritter"))));

        w.addEvent(combat("a3_vasto",
                "Il Re Non Incoronato",
                "Il Vasto Lorde ti fissa con un'intelligenza fredda e antica che appartiene " +
                "a chi ha attraversato secoli di sopravvivenza nel deserto bianco. La sua " +
                "pressione spirituale è opprimente: persino l'aria si fa densa, come se il " +
                "vuoto stesso si rifiutasse di condurti avanti. Ogni suo movimento è " +
                "economia allo stato puro — nessuno spreco, nessuna esitazione, " +
                "nessuna misericordia.",
                Area.HUECO_MUNDO, EnemyType.VASTO_LORDE, "a3_inside"));

        w.addEvent(combat("a3_sternritter",
                "Lo Sternritter in Agguato",
                "Il tuo piano di sabotaggio era quasi perfetto — ma uno Sternritter del " +
                "Wandenreich presidia il punto esatto dove pensavi di agire. La lettera " +
                "del suo potere brilla sul petto dell'uniforme bianca. «Infiltrato " +
                "rilevato,» dice con un sorriso che non è un sorriso. «L'Imperatore ha " +
                "previsto anche questo.» Il suo Vollständig si attiva in un'esplosione " +
                "di luce sacra.",
                Area.HUECO_MUNDO, EnemyType.STERNRITTER, "a3_inside"));

        w.addEvent(story("a3_inside",
                "Nei Corridoi di Las Noches",
                "Il bianco assoluto dei corridoi di Las Noches è quasi accecante dopo il " +
                "deserto. L'architettura è impossibile — spazi senza senso, porte che " +
                "portano al nulla, stanze che cambiano proporzione a seconda di chi le " +
                "guarda. Ma senti la direzione giusta con ogni fibra della tua energia " +
                "spirituale. Il nucleo della frattura dimensionale è al centro della " +
                "fortezza. Due guardie dell'Espada si frappongono fra te e l'obiettivo.",
                Area.HUECO_MUNDO,
                List.of(
                    go("Sfida Grimmjow Jaegerjaquez — forza bruta contro forza bruta",
                       "a3_grimmjow"),
                    go("Affronta Ulquiorra Cifer — la via più pericolosa, ma la più diretta",
                       "a3_ulquiorra"))));

        w.addEvent(combat("a3_grimmjow",
                "La Rabbia di Grimmjow",
                "Grimmjow Jaegerjaquez è tutto istinto e superbia compressa in una forma " +
                "umana che fatica a contenere la bestia dentro. I suoi occhi turchese " +
                "brillano di quella luce che precede la violenza pura. «Finalmente " +
                "qualcuno che vale la pena di distruggere,» ringhia. La Resurrección " +
                "Pantera si scatena in un'esplosione di forza animale che fa tremare " +
                "i muri di Las Noches.",
                Area.HUECO_MUNDO, EnemyType.GRIMMJOW, "a3_bridge_final"));

        w.addEvent(combat("a3_ulquiorra",
                "Il Vuoto Assoluto — Ulquiorra Cifer",
                "Ulquiorra Cifer ti fissa con occhi verde-vuoto che non contengono né " +
                "odio né pietà — solo la constatazione fredda della tua esistenza. " +
                "«Emozioni. Cuore. Cose che non comprendo.» La sua voce è quasi curiosa. " +
                "«Ma tu le usi per combattere. Dimostrami il loro valore contro il nulla " +
                "assoluto.» Murcielago si dispiega nell'oscurità come due ali di pece " +
                "che oscurano persino la luna di Hueco Mundo.",
                Area.HUECO_MUNDO, EnemyType.ULQUIORRA, "a3_bridge_final"));

        w.addEvent(story("a3_bridge_final",
                "Il Segreto al Centro di Las Noches",
                "Al centro di Las Noches trovi ciò che cercavi — e molto di più. La " +
                "frattura dimensionale non è un incidente né un'arma. È un portale " +
                "intenzionale aperto da Aizen e Yhwach simultaneamente, usando l'energia " +
                "dell'Hōgyoku moltiplicata dal potere di The Almighty. Il loro obiettivo " +
                "finale: risvegliare il Re delle Anime assorbendo la forza vitale dei Tre " +
                "Mondi in un colpo solo. Se ci riuscissero, l'esistenza si smonterebbe " +
                "in meno di un'ora. Il ritorno al Seireitei è l'unica opzione.",
                Area.HUECO_MUNDO,
                List.of(go("Torna al Seireitei per la battaglia finale", "a4_intro"))));
    }

    // ATTO 4 - LA CONVERGENZA
    private static void addAct4(World w) {

        w.addEvent(story("a4_intro",
                "La Convergenza dei Tre Mondi",
                "Il Seireitei è il punto di convergenza. Le tre fazioni — Shinigami, " +
                "Arrancar, Quincy — combattono in ogni vicolo e su ogni tetto. Ma il " +
                "vero scontro non è tra loro: è contro chi ha orchestrato tutto questo " +
                "dall'ombra. Per arrivare ad Aizen e Yhwach devi prima superare i " +
                "guardiani che difendono l'accesso alla Camera del Trono.",
                Area.SEIREITEI,
                List.of(
                    go("Affronta Kenpachi Zaraki — il berserker che blocca qualsiasi avanzata",
                       "a4_kenpachi"),
                    go("Cerca di superare Genryūsai Yamamoto — ma lui non si fida di nessuno",
                       "a4_yamamoto"))));

        w.addEvent(combat("a4_kenpachi",
                "Il Berserker del Gotei 13",
                "Kenpachi Zaraki non difende la Camera del Trono per dovere — lo fa perché " +
                "qualsiasi cosa stia succedendo promette il miglior combattimento della sua " +
                "vita. L'occhio coperto dalla benda brilla nell'oscurità. «Finalmente,» " +
                "ride lui con voce che fa tremare il suolo, «qualcosa che vale davvero la " +
                "pena di uccidere.» La sua pressione spirituale sale senza limite visibile. " +
                "Nessun Bankai. Nessun Kidō. Solo istinto di distruzione puro.",
                Area.SEIREITEI, EnemyType.KENPACHI, "a4_haschwalth"));

        w.addEvent(combat("a4_yamamoto",
                "Il Vecchio Fuoco",
                "Genryūsai Shigekuni Yamamoto non cede la strada — nemmeno a un potenziale " +
                "alleato. «Chiunque tu sia: la responsabilità di questo momento ricade sulle " +
                "spalle di chi ha vissuto abbastanza a lungo da capire il peso del " +
                "sacrificio.» Ryūjin Jakka si risveglierà dal fodero e il calore che ne " +
                "emana fa evaporare l'aria nel raggio di un chilometro. " +
                "Dimostragli che sei degno di procedere.",
                Area.SEIREITEI, EnemyType.YAMAMOTO, "a4_haschwalth"));

        w.addEvent(combat("a4_haschwalth",
                "Il Braccio Destro — Jugram Haschwalth",
                "Jugram Haschwalth ti aspetta davanti alla Camera del Trono con la calma " +
                "di chi ha già deciso come finirà. Il suo potere — The Balance — trasferisce " +
                "la sfortuna di Yhwach su chiunque lo minacci, e ridistribuisce la fortuna " +
                "degli avversari agli alleati dell'Imperatore. «Il futuro è già stato " +
                "scritto,» dice. Poi una pausa. «...Ma non necessariamente da chi credi.»",
                Area.SEIREITEI, EnemyType.HASCHWALTH, "a4_aizen"));

        w.addEvent(combat("a4_aizen",
                "Il Dio Autoproclamato — Sōsuke Aizen",
                "Sōsuke Aizen siede sul trono che si è costruito sulla menzogna e " +
                "sull'intelligenza, con un sorriso che non ha mai smesso di contenere il " +
                "disprezzo per tutto ciò che non è lui. L'Hōgyoku pulsa nel suo petto " +
                "come un secondo cuore. «Ho previsto ogni tua mossa dall'inizio,» dice " +
                "con voce quasi stanca. «La tua vittoria eventuale... anche quella fa " +
                "parte del piano.» Kyōka Suigetsu si dissolve nell'aria. La realtà si " +
                "incrina attorno a te. Ma qualcosa dentro di te sa dove è il vero Aizen. " +
                "E colpisci.",
                Area.HUECO_MUNDO, EnemyType.AIZEN, "a4_yhwach"));

        w.addEvent(combat("a4_yhwach",
                "L'Imperatore dell'Oscurità — Yhwach",
                "Yhwach assorbe la sconfitta di Aizen come combustibile per la propria " +
                "ascesa, bevendone il potere residuo con un sorriso colossale. The Almighty " +
                "si attiva: nella sua visione vede tutti i futuri possibili, ogni tua " +
                "scelta già catalogata, ogni mossa già contromossa. «Ho visto questo " +
                "momento da prima che nascessi,» dice, la voce che risuona nell'etere " +
                "stesso. «Ho visto anche la mia sconfitta. La differenza è che io... " +
                "ho scelto di stare qui lo stesso.» Il futuro che vede è quello che " +
                "decidi tu. Combatti.",
                Area.HUECO_MUNDO, EnemyType.YHWACH, "victory"));

        w.addEvent(story("victory",
                "L'Alba dei Tre Mondi",
                "Yhwach cade. Il suo potere di vedere il futuro non ha potuto prevedere " +
                "una sola cosa: la tua volontà assoluta di cambiare il destino. " +
                "La frattura dimensionale si chiude come una ferita che finalmente smette " +
                "di sanguinare. I Tre Mondi si separano con dolcezza, tornando " +
                "all'equilibrio che ha sostenuto l'esistenza per millenni. " +
                "Le tre fazioni osservano in silenzio dall'alto delle loro posizioni — " +
                "Shinigami, Arrancar, Quincy — tutte ugualmente consapevoli che oggi " +
                "la loro sopravvivenza porta il tuo nome. " +
                "La storia del mondo spirituale non verrà riscritta. " +
                "Ma continuerà a esistere — e questo, scopri, " +
                "è la più grande delle vittorie.",
                Area.SEIREITEI,
                List.of(go("Ricomincia una nuova avventura", "start"))));
    }
}
