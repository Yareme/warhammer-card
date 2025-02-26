PRAGMA foreign_keys = ON;

CREATE TABLE "wyposarzenie" (
    "id"              INTEGER PRIMARY KEY,
    "nazwa"           TEXT NOT NULL,
    "obciążenie"      INTEGER NOT NULL
);

CREATE TABLE "typ_pancerza" (
    "id"              INTEGER PRIMARY KEY,
    "nazwa"           TEXT NOT NULL
);

CREATE TABLE "lokalizacja_pancerza" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL
);

CREATE TABLE "pancerz" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL,
    "cena"                         TEXT NOT NULL,


    "kara"                         TEXT,
    "lokalizacja_pancerza"         TEXT,

    "punkty_pancerza"              INTEGER NOT NULL,

    "zalety"                       TEXT,
    "wady"                         TEXT,

    "dostępność_id"                   INTEGER NOT NULL,
    "typ_pancerza_id"              INTEGER NOT NULL,

     "opciążenie"                   INTEGER,


    FOREIGN KEY ("dostępność_id") REFERENCES "dostępność_id"("id") ON UPDATE CASCADE ON DELETE CASCADE
    FOREIGN KEY ("typ_pancerza_id") REFERENCES "typ_pancerza"("id") ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE "dostępność" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL,
    "wioska"                         TEXT,
    "miasteczko"                         TEXT,
    "miasto"                         TEXT
);

CREATE TABLE "zalety_pancerz" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL,
    "opis"                         TEXT NOT NULL
);

CREATE TABLE "wady_pancerz" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL,
    "opis"                         TEXT NOT NULL
);

CREATE TABLE "status" (
    "id"        INTEGER PRIMARY KEY,
    "nazwa"     TEXT NOT NULL
);

CREATE TABLE "rasa" (
    "id"          INTEGER PRIMARY KEY,
    "name"        TEXT,

    "punkty_przeznaczenia" INTEGER,
    "punkty_bohatera" INTEGER,
    "punkty_dodatkowe" INTEGER,

    "wartość_min" INTEGER,
    "wartość_max" INTEGER
);

CREATE TABLE "imiona" (
    "id"           INTEGER PRIMARY KEY,
    "imię"         TEXT NOT NULL,

    "rasa_id"      INTEGER NOT NULL,

    FOREIGN KEY ("rasa_id") REFERENCES "rasa"("id")  ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "klasa" (
    "id"      INTEGER PRIMARY KEY,
    "nazwa"   TEXT NOT NULL
);

CREATE TABLE "profesja" (
    "id"             INTEGER PRIMARY KEY,
    "nazwa"          TEXT,
    "ścieżka_profesji" TEXT,

    "klasa_id"        INTEGER,

    FOREIGN KEY ("klasa_id") REFERENCES "klasa"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "poziom" (
    "id"                   INTEGER PRIMARY KEY,
    "nazwa"                TEXT ,

    "schemat_cech"          TEXT ,
    "schemat_umiejetnosci"          TEXT ,
    "schemat_talentow"          TEXT ,



    "profesja_id"          INTEGER ,
    "status_id"            INTEGER ,

    FOREIGN KEY ("profesja_id") REFERENCES "profesja"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("status_id") REFERENCES "status"("id") ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "typ_broni" (
    "id"            INTEGER PRIMARY KEY,
    "nazwa"         TEXT NOT NULL,
    "czy_zaciągowa" INTEGER NOT NULL
);

CREATE TABLE "broń" (
    "id"                     INTEGER PRIMARY KEY,
    "nazwa"                  TEXT NOT NULL,
    "czy_dwuręczna"          INTEGER NOT NULL,
    "cena"                   TEXT NOT NULL,
    "obciążenie"             INTEGER NOT NULL,

    "zasięg"                 TEXT NOT NULL,
    "obrażenia"              TEXT NOT NULL,
    "zalety"                 TEXT NOT NULL,
    "wady"                   TEXT NOT NULL,
    "kategoria"              TEXT NOT NULL,
    "cechy_broni"                  TEXT,

    "czy_własna"             INTEGER NOT NULL,

    "typ_broni_id"           INTEGER NOT NULL,
    "dostępność_id"                   INTEGER NOT NULL,

    FOREIGN KEY ("typ_broni_id") REFERENCES "typ_broni"("id") ON UPDATE CASCADE ON DELETE CASCADE
     FOREIGN KEY ("dostępność_id") REFERENCES "dostępność_id"("id") ON UPDATE CASCADE ON DELETE CASCADE


);

CREATE TABLE "rasa_profesja" (
    "id"                   INTEGER PRIMARY KEY,
    "wartość_min"          INTEGER NOT NULL,
    "wartość_max"          INTEGER NOT NULL,

    "rasa_id"              INTEGER NOT NULL,
    "profesja_id"          INTEGER NOT NULL,

    FOREIGN KEY ("rasa_id") REFERENCES "rasa"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("profesja_id") REFERENCES "profesja"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "cechy" (
    "id"              INTEGER PRIMARY KEY,
    "nazwa_calkowita" TEXT NOT NULL,
    "nazwa_krótka"    TEXT NOT NULL
);

CREATE TABLE "kampania" (
    "id"              INTEGER PRIMARY KEY,
    "nazwa"           TEXT NOT NULL,
    "data_utworzenia" INTEGER NOT NULL
);

CREATE TABLE "karta" (
    "id"                                 INTEGER PRIMARY KEY,

    "imie"                               TEXT,
    "wiek"                               TEXT,
    "wzrost"                             TEXT,
    "wlosy"                              TEXT,
    "oczy"                               TEXT,


    "punkty_przeznaczenia"               INTEGER,
    "punkty_szczescia"                   INTEGER,

    "punkty_bohatera"           I        INTEGER,
    "punkty_determinacji"                INTEGER,

    "punkty_dodatkowe"                   INTEGER,

     "szybkosc"                          INTEGER,
     "xp_aktualny"                       INTEGER,
     "xp_wydany"                         INTEGER,

    "pensy"                              INTEGER,
    "sreblo"                             INTEGER,
    "złota_korona"                       INTEGER,

    "psyhologia"                         TEXT,
    "zepsucie_i_mutacje"                 TEXT,
    "punkty_grzechu"                     INTEGER,

    "ambicja_krótkoterminowa"            TEXT,
    "ambicja_długoterminowa"             TEXT,

    "nazwa_drużyny"                      TEXT,
    "ambicja_drużynowa_krótkoterm"       TEXT,
    "ambicja_drużynowa_długoterm"        TEXT,
    "członkowie_drużyny"                 TEXT,

    "motywacja"                          TEXT,

    "kampania_id"                        INTEGER,
    "rasa_id"                            INTEGER,
    "profesja_id"                        INTEGER,
    "poziom_id"                          INTEGER,

    FOREIGN KEY ("kampania_id") REFERENCES "kampania"("id") ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY ("rasa_id") REFERENCES "rasa"("id") ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY ("profesja_id") REFERENCES "profesja"("id") ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY ("poziom_id") REFERENCES "poziom"("id") ON UPDATE CASCADE ON DELETE CASCADE


);

CREATE TABLE "talent" (
    "id"                 INTEGER PRIMARY KEY,
    "nazwa"              TEXT NOT NULL,
    "maksimum"           TEXT NOT NULL,

    "testy"              TEXT,
    "opis"               TEXT NOT NULL,

    "cechy_id"           INTEGER

);

CREATE TABLE "umiejętności" (
    "id"              INTEGER PRIMARY KEY,
    "nazwa"           TEXT NOT NULL,
    "czy_zaawansowana"    INTEGER NOT NULL,
    "opis"            TEXT NOT NULL,

    "cechy_id"  INTEGER NOT NULL,

    FOREIGN KEY ("cechy_id") REFERENCES "cechy"("id") ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "karta_cecha" (

    "karta_id"           INTEGER NOT NULL,
    "cechy_id"           INTEGER,
    "lvl_up"             INTEGER,
    "wartość_pociątkowa" INTEGER NOT NULL,
    "rozwój"             INTEGER NOT NULL,

    FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("cechy_id") REFERENCES "cechy"("id") ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "karta_broń"(
"karta_id"      INTEGER NOT NULL,
"broń_id"       INTEGER NOT NULL,
FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY ("broń_id") REFERENCES "broń"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "karta_pancerz" (

    "karta_id"           INTEGER NOT NULL,
    "pancerz_id"         INTEGER NOT NULL,
    "czy_zalożony"       INTEGER,

    FOREIGN KEY ("karta_id") REFERENCES "karta"("id")  ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("pancerz_id") REFERENCES "pancerz"("id")  ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "karta_talent" (

    "karta_id"          INTEGER NOT NULL,
    "talent_id"         INTEGER NOT NULL,
    "poziom"            INTEGER NOT NULL,
     "lvl_up"             INTEGER,

    FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("talent_id") REFERENCES "talent"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "karta_umiętność" (

    "karta_id"                     INTEGER NOT NULL,
    "umiejętności_id"              INTEGER NOT NULL,
    "rozwój"                       INTEGER,
     "lvl_up"             INTEGER,

     FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("umiejętności_id") REFERENCES "umiejętności"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "karta_wypasarzenie" (

    "karta_id"                     INTEGER NOT NULL,
    "wyposarzenie_id"             INTEGER NOT NULL,

     FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("wyposarzenie_id") REFERENCES "wyposarzenie"("id") ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "tradycja" (
    "id"          INTEGER NOT NULL,
    "nazwa"       TEXT NOT NULL,
    "opis"        TEXT NOT NULL
);

CREATE TABLE "zaklęcia" (
    "id"                   INTEGER PRIMARY KEY,
    "nazwa"                TEXT NOT NULL,
    "poziom_zaklęcia"      INTEGER NOT NULL,
    "zacięg"               TEXT NOT NULL,
    "cel"                  TEXT NOT NULL,
    "czas"                 TEXT NOT NULL,
    "opis"                 TEXT NOT NULL,

    "tradycja_id"          INTEGER NOT NULL,

    FOREIGN KEY ("tradycja_id") REFERENCES "tradycja"("id")  ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "kolor_oczu"(

    "id" INTEGER PRIMARY KEY,
    "kolor" TEXT,
    "rasa_id" INTEGER

);

CREATE TABLE "kolor_wlosy"(

    "id" INTEGER PRIMARY KEY,
    "kolor" TEXT,
    "rasa_id" INTEGER

);

-- Dodanie ras
INSERT INTO "Rasa" ("name", "wartość_min", "wartość_max")
VALUES
  ("Człowiek",  1, 90),
  ("Nieziołek",  91, 94),
  ("Krasnolud",  95, 98),
  ("Wysoki elf",  99, 99),
  ("Leśny elf",  100, 100);


  INSERT INTO "kolor_oczu"("kolor","rasa_id")
    VALUES
        ("Zielony",1),
        ("Błękitny",1),
        ("Niebieski",1),
        ("Niebieski",1),
        ("Szary",1),
        ("Brązowy",1),
        ("Orzechowy",1),
        ("Ciemnobrązowy",1),
        ("Czarny",1),

        ("Jasnoszary",2),
        ("Szary",2),
        ("Błękitny",2),
        ("Niebieski",2),
        ("Zielony",2),
        ("Orzechowy",2),
        ("Brązowy",2),
        ("Miedziany",2),
        ("Ciemnobrązowy",2),
        ("Ciemnobrązowy",2),

        ("Czarny jak węgiel",3),
        ("Szary jak ołów",3),
        ("Stalowoszary",3),
        ("Niebieski",3),
        ("Brązowy jak ziemia",3),
        ("Ciemnobrązowy",3),
        ("Orzechowy",3),
        ("Zielony",3),
        ("Ciemny miedziany",3),
        ("Złoty",3),

        ("Czarny jak smoła",4),
        ("Ametystowy",4),
        ("Akwamaryna",4),
        ("Szafirowy",4),
        ("Turkusowy",4),
        ("Szmaragdowy",4),
        ("Bursztynowy",4),
        ("Miedziany",4),
        ("Cytrynowożółty",4),
        ("Złoty",4),

        ("Koloru kości słoniowej",5),
        ("Antracyt",5),
        ("Zielony jak bluszcz",5),
        ("Zielony jak mech",5),
        ("Kasztanowy",5),
        ("Kasztanowy",5),
        ("Ciemnobrązowy",5),
        ("Jasnobrązowy",5),
        ("Złotobrązowy",5),
        ("Fiołkowy",5);





 INSERT INTO "kolor_wlosy"("kolor","rasa_id")
    VALUES
        ("Biały",1),
        ("Złoty blond",1),
        ("Rudoblond",1),
        ("Złoty",1),
        ("Jasny brąz",1),
        ("Ciemny brąz",1),
        ("Czarny",1),
        ("Kasztanowy",1),
        ("Rudy",1),
        ("Siwy",1),

        ("Biały",2),
        ("Lniany",2),
        ("Rudawy",2),
        ("Złoty",2),
        ("Kasztanowy",2),
        ("Rudy",2),
        ("Musztardowy",2),
        ("Migdałowy",2),
        ("Czekoladowy",2),
        ("Lukrecjowy",2),

        ("Blond",3),
        ("Siwy",3),
        ("Jasny blond",3),
        ("Brązowy",3),
        ("Miedziany",3),
        ("Brąz",3),
        ("Ciemny brąz",3),
        ("Rudy brąz",3),
        ("Czarny",3),

        ("Srebrnosiwy",4),
        ("Popielaty",4),
        ("Jasny blond",4),
        ("Miodowoblond",4),
        ("Żółty",4),
        ("Miedziany blond",4),
        ("Blond",4),
        ("Migdałowy",4),
        ("Czerwony",4),
        ("Czarny",4),

        ("Brzozowobiały",5),
        ("Blond",5),
        ("Różowe złoto",5),
        ("Miodowoblond",5),
        ("Brązowy",5),
        ("Mahoniowy",5),
        ("Ciemny brąz",5),
        ("Sjena",5),
        ("Hebanowy",5),
        ("Niebiesko-czarny",5);

-- Dodanie imion
-- Człowiek
INSERT INTO "Imiona" ("imię", "rasa_id")
VALUES
  ("Lurinian Menudilius", 1),
  ("Eduanis Famidenius", 1),
  ("Silulcabor Idolione", 1),
  ("Gaiuvus Haynonius", 1),
  ("Barollus Darad", 1),
  ("Afescus Merarus", 1),
  ("Pusentius Sialitian", 1),
  ("Vlacanius Turilchiotus", 1),
  ("Grateas Desinnius", 1),
  ("Minitunian Brolecus", 1),
  ("Kasilias Jiracia", 1),
  ("Iululin Neranis", 1),
  ("Segondeius Philone", 1),
  ("Faduberius Wavonicus", 1),
  ("Rufich Nirentanus", 1),
  ("Dexihat Tragicci", 1),
  ("Nostanni Culatius", 1),
  ("Nigitus Concicci", 1),
  ("Dyusunius Felilia", 1),
  ("Eduatav Fulance", 1);

-- Nioziołek
INSERT INTO "Imiona" ("imię", "rasa_id")
VALUES
  ("Cornan Cherrykettle", 2),
  ("Arhace Hogfingers", 2),
  ("Marzu Reedlade", 2),
  ("Orios Greenbridge", 2),
  ("Osace Cleareye", 2),
  ("Belras Thornbelly", 2),
  ("Anras Bronzebrand", 2),
  ("Davbin Mildfound", 2),
  ("Sharry Roseflow", 2),
  ("Davhace Laughingeyes", 2),
  ("Conos Rosebottle", 2),
  ("Finmo Swiftwoods", 2),
  ("Dangin Stouthill", 2),
  ("Janrin Forespell", 2),
  ("Korzor Grandbelly", 2),
  ("Zenlos Cindermeadow", 2),
  ("Sanver Wildgrove", 2),
  ("Zenace Quickberry", 2),
  ("Norhorn Cloudmouse", 2),
  ("Meramin Fatstride", 2);

-- Krasnolud
INSERT INTO "Imiona" ("imię", "rasa_id")
VALUES
  ("Ozmed Merrybrew", 3),
  ("Dhukgrin Bloodbrand", 3),
  ("Sarbatum Undertank", 3),
  ("Jaldrad Lightmane", 3),
  ("Sadrorli Goldshield", 3),
  ("Saseag Barrelaxe", 3),
  ("Vameath Heavyhand", 3),
  ("Thigromli Wyvernriver", 3),
  ("Yovred Pebblebuckle", 3),
  ("Doundoc Leatherbreaker", 3),
  ("Jotrean Opalbane", 3),
  ("Jotgrumli Heavybow", 3),
  ("Darezmotir Oakfury", 3),
  ("Gamnaet Flintfury", 3),
  ("Groogrous Bonetoe", 3),
  ("Skakholim Trollstone", 3),
  ("Gasic Steeldigger", 3),
  ("Folgraic Hammerhand", 3),
  ("Nutmout Greatdigger", 3),
  ("Lodgrel Hammerbraids", 3);

-- Wysoki elf
INSERT INTO "imiona" ("imię", "rasa_id")
VALUES
  ("Eraalcalin Camthaer",4),
  ("Angrmowe Larethaen",4),
  ("Undionl Jaerahl",4),
  ("Ingaenare Thromaire",4),
  ("Moraenendor Thromifeth",4),
  ("Henaldormo Charmaine",4),
  ("Henrilelion Aedus",4),
  ("Molrionion Faelock",4),
  ("Moroonilmo Thramihre",4),
  ("Arkil Sillonuseus",4),
  ("Falcyon Loraethire",4),
  ("Peloniril Alkinaine",4),
  ("Caralmoormo Larethifeth",4),
  ("Uulentilrmo Thromfaere",4),
  ("Yaronilmo Thilinihle",4),
  ("Ocatnilianranir Camiath",4),
  ("Ohtimarcoindil Caemious",4),
  ("Ocatianhnilian Jaereus",4),
  ("Hennarenquarien Stormaerith",4),
  ("Enrnmir Laemire",4);

-- Leśny elf
INSERT INTO "imiona" ("imię", "rasa_id")
VALUES
  ("Dhidri",5),
  ("Dozli",5),
  ("Iozkollith",5),
  ("Tarcudro",5),
  ("Thakaerni",5),
  ("Dhaluci",5),
  ("Duscaltun",5),
  ("Goko",5),
  ("Ziorlo",5),
  ("Zallioc",5),
  ("Caerton",5),
  ("Vulco",5),
  ("Gakorcoth",5),
  ("Khokiolvot",5),
  ("Dorcaekkaeth",5),
  ("Taaltioca",5),
  ("Strothuzka",5),
  ("Scuka",5),
  ("Stradaen",5),
  ("Scaadia",5);





--Dodanie klas
INSERT INTO "klasa"("nazwa")
VALUES
    ("UCZENI"),
    ("MIESZCZANIE"),
    ("DWORZANIE"),
    ("POSPÓLSTWO"),
    ("WĘDROWCY"),
    ("WODNIACY"),
    ("ŁOTRY"),
    ("WOJOWNICY");





--Dodanie profesji
INSERT INTO "profesja"("nazwa","klasa_id")
VALUES
  ("Aptekarka", 1),
  ("Czarodziej", 1),
  ("Inżynier", 1),
  ("Kapłan", 1),
  ("Medyczka", 1),
  ("Mniszka", 1),
  ("Prawniczka", 1),
  ("Uczony", 1),
  ("Agitator", 2),
  ("Kupiec", 2),
  ("Mieszczka", 2),
  ("Rzemieślniczka", 2),
  ("Strażnik", 2),
  ("Szczurołap", 2),
  ("Śledczy", 2),
  ("Żebrak", 2),
  ("Artystka", 3),
  ("Doradca", 3),
  ("Namiestnik", 3),
  ("Poseł", 3),
  ("Służąca", 3),
  ("Szlachcic", 3),
  ("Szpieg", 3),
  ("Zwadźca", 3),
  ("Chłopka", 4),
  ("Górnik", 4),
  ("Guślarz", 4),
  ("Łowczyni", 4),
  ("Mistyczka", 4),
  ("Zarządca", 4),
  ("Zielarka", 4),
  ("Zwiadowca", 4),
  ("Biczownik", 5),
  ("Domokrążca", 5),
  ("Kuglarka", 5),
  ("Łowca Czarownic", 5),
  ("Łowczyni Nagród", 5),
  ("Posłaniec", 5),
  ("Strażniczka Dróg", 5),
  ("Woźnica", 5),
  ("Doker", 6),
  ("Flisak", 6),
  ("Pilotka Rzeczna", 6),
  ("Pirat Rzeczny", 6),
  ("Przemytniczka", 6),
  ("Przewoźnik", 6),
  ("Strażnik Rzeczny", 6),
  ("Żeglarz", 6),
  ("Banita", 7),
  ("Czarownica", 7),
  ("Cmentarna", 7),
  ("Paser", 7),
  ("Rajfur", 7),
  ("Rekietierka", 7),
  ("Szarlatan", 7),
  ("Złodziej", 7),
  ("Gladiator", 8),
  ("Kapłan Bitewny", 8),
  ("Kawalerzysta", 8),
  ("Ochroniarz", 8),
  ("Oprych", 8),
  ("Rycerz", 8),
  ("Zabójca", 8),
  ("Żołnierz", 8);


INSERT INTO "rasa_profesja"("profesja_id","rasa_id","wartość_min","wartość_max")
VALUES
--ludzi
("1","1","1","1"),
("2","1","2","2"),
("3","1","3","3"),
("4","1","4","8"),
("5","1","9","9"),
("6","1","10","11"),
("7","1","12","12"),
("8","1","13","14"),
("9","1","15","15"),
("10","1","16","16"),
("11","1","17","19"),
("12","1","20","21"),
("13","1","22","22"),
("14","1","23","24"),
("15","1","25","25"),
("16","1","26","27"),
("17","1","28","28"),
("18","1","29","29"),
("19","1","30","30"),
("20","1","31","31"),
("21","1","32","34"),
("22","1","35","35"),
("23","1","36","36"),
("24","1","37","37"),
("25","1","38","42"),
("26","1","43","43"),
("27","1","44","44"),
("28","1","45","46"),
("29","1","47","47"),
("30","1","48","48"),
("31","1","49","49"),
("32","1","50","50"),
("33","1","51","52"),
("34","1","53","53"),
("35","1","54","55"),
("36","1","56","56"),
("37","1","57","57"),
("38","1","58","58"),
("39","1","59","59"),
("40","1","60","60"),
("41","1","61","62"),
("42","1","63","65"),
("43","1","66","66"),
("44","1","67","67"),
("45","1","68","68"),
("46","1","69","70"),
("47","1","71","72"),
("48","1","73","74"),
("49","1","75","78"),
("50","1","79","79"),
("51","1","81","81"),
("52","1","80","80"),
("53","1","82","83"),
("54","1","84","84"),
("55","1","85","85"),
("56","1","86","88"),
("57","1","89","89"),
("58","1","90","90"),
("59","1","91","92"),
("60","1","93","94"),
("61","1","95","95"),
("62","1","96","96"),
("64","1","97","100"),

--Krasnoludy
("1","3","1","1"),
("3","3","2","4"),
("5","3","5","5"),
("7","3","6","7"),
("8","3","8","9"),
("9","3","10","11"),
("10","3","12","15"),
("11","3","16","21"),
("12","3","22","27"),
("13","3","28","30"),
("14","3","31","31"),
("15","3","32","33"),
("16","3","34","34"),
("17","3","35","35"),
("18","3","36","37"),
("19","3","38","39"),
("20","3","40","41"),
("21","3","42","42"),
("22","3","43","43"),
("23","3","44","44"),
("24","3","45","45"),
("25","3","46","46"),
("26","3","47","51"),
("28","3","52","53"),
("30","3","54","55"),
("32","3","56","56"),
("34","3","57","58"),
("35","3","59","60"),
("37","3","61","64"),
("38","3","65","66"),
("40","3","67","67"),
("41","3","68","69"),
("42","3","70","71"),
("43","3","72","72"),
("44","3","73","73"),
("45","3","74","75"),
("46","3","76","77"),
("48","3","78","78"),
("59","3","79","81"),
("51","3","82","82"),
("54","3","83","83"),
("56","3","84","84"),
("57","3","85","87"),
("60","3","88","90"),
("61","3","91","93"),
("63","3","94","97"),
("64","3","98","100"),

--Nieziolki
("1", "2","1","1"),
("3", "2","2","2"),
("5", "2","3","4"),
("7", "2","5","6"),
("8", "2","7","8"),
("9", "2","9","10"),
("10", "2","11","14"),
("11", "2","15","17"),
("12", "2","18","22"),
("13", "2","23","24"),
("14", "2","25","27"),
("15", "2","28","29"),
("16", "2","30","33"),
("17", "2","34","35"),
("18", "2","36","36"),
("19", "2","37","38"),
("20", "2","39","39"),
("21", "2","40","45"),
("23", "2","46","46"),
("25", "2","47","49"),
("26", "2","50","50"),
("28", "2","51","52"),
("30", "2","53","53"),
("31", "2","54","56"),
("32", "2","57","57"),
("34", "2","58","59"),
("35", "2","60","62"),
("37", "2","63","63"),
("38", "2","64","65"),
("39", "2","66","66"),
("40", "2","67","68"),
("41", "2","69","71"),
("42", "2","72","74"),
("43", "2","75","75"),
("45", "2","76","79"),
("46", "2","80","80"),
("47", "2","81","81"),
("48", "2","82","82"),
("49", "2","83","83"),
("51", "2","85","85"),
("52", "2","84","84"),
("53", "2","86","88"),
("54", "2","89","89"),
("55", "2","90","90"),
("56", "2","91","94"),
("57", "2","95","95"),
("60", "2","96","97"),
("64", "2","98","100"),

--WysokieLeśny
("1", "4", "1", "2"),
("2", "4", "3", "6"),
("5", "4", "7", "8"),
("7", "4", "9", "12"),
("8", "4", "13", "16"),
("10", "4", "17", "21"),
("11", "4", "22", "23"),
("12", "4", "24", "26"),
("13", "4", "27", "27"),
("15", "4", "28", "29"),
("17", "4", "30", "30"),
("18", "4", "31", "32"),
("19", "4", "33", "34"),
("20", "4", "35", "37"),
("22", "4", "38", "40"),
("23", "4", "41", "43"),
("24", "4", "44", "45"),
("28", "4", "46", "48"),
("31", "4", "49", "50"),
("32", "4", "51", "56"),
("35", "4", "57", "59"),
("37", "4", "60", "62"),
("38", "4", "63", "63"),
("45", "4", "64", "64"),
("46", "4", "65", "65"),
("48", "4", "66", "80"),
("49", "4", "81", "83"),
("53", "4", "84", "85"),
("55", "4", "86", "88"),
("57", "4", "89", "90"),
("59", "4", "91", "94"),
("60", "4", "95", "96"),
("61", "4", "97", "97"),
("62", "4", "98", "98"),
("64", "4", "99", "100"),

--Leśne elfy
("2", "5", "1", "4"),
("8", "5", "5", "5"),
("11", "5", "6", "10"),
("12", "5", "6", "10"),
("15", "5", "11", "14"),
("16", "5", "11", "14"),
("17", "5", "15", "18"),
("18", "5", "15", "18"),
("20", "5", "19", "25"),
("22", "5", "26", "31"),
("23", "5", "32", "35"),
("26", "5", "36", "45"),
("27", "5", "36", "45"),
("28", "5", "36", "45"),
("29", "5", "46", "50"),
("31", "5", "51", "57"),
("32", "5", "58", "68"),
("35", "5", "69", "73"),
("37", "5", "74", "75"),
("38", "5", "76", "78"),
("44", "5", "79", "79"),
("49", "5", "80", "85"),
("57", "5", "86", "87"),
("59", "5", "88", "92"),
("62", "5", "95", "96"),
("64", "5", "97", "100");

INSERT INTO "cechy" ("nazwa_calkowita", "nazwa_krótka")
VALUES
    ("Walka Wręcz","WW"),
    ("Umiejętności Strzeleckie","US"),
    ("Siła","S"),
    ("Wytrzymałość","Wt"),
    ("Inicjatywa","I"),
    ("Zwinność","Zw"),
    ("Zręczność","Zr"),
    ("Inteligencja","Int"),
    ("Siła Woli","SW"),
    ("Ogłada","Ogd");


   INSERT INTO "umiejętności" ("nazwa", "czy_zaawansowana","cechy_id","opis")
   VALUES
        ("Atletyka",0,6," OPIS"),

        ("Broń Biała (Podstawowa)",0,1," OPIS"),

        ("Charyzma",0,10," OPIS"),
        ("Dowodzenie",0,10," OPIS"),
        ("Hazard",0,8," OPIS"),
        ("Intuicja",0,5," OPIS"),

        ("Jeździectwo(Konie)",0,6," OPIS"),
        ("Mocna głowa",0,4," OPIS"),

        ("Nawigacja",0,5," OPIS"),
        ("Odporność",0,4," OPIS"),
        ("Opanowanie",0,9," OPIS"),


        ("Oswajanie",0,9," OPIS"),

        ("Percepcja",0,5," OPIS"),
        ("Plotkowanie",0,10," OPIS"),

        ("Powożenie",0,6," OPIS"),
        ("Przekupstwo",0,10," OPIS"),

        ("Skradanie",0,6," OPIS"),
        ("Sztuka",0,7," OPIS"),
        ("Sztuka Przetrwania",0,8," OPIS"),
        ("Targowanie",0,10," OPIS"),
        ("Unik",0,6," OPIS"),
        ("Wioślarstwo",0,3," OPIS"),
        ("Wspinaczka",0,3," OPIS"),
        ("Występy",0,10," OPIS"),
        ("Zastraszanie",0,3," OPIS"),


        ("Badania Naukowe",1,8," OPIS"),

        ("Broń Biała(Bijatyka)",1,1," OPIS"),
        ("Broń Biała(Drzewcowa)",1,1," OPIS"),
        ("Broń Biała(Dwuręczna)",1,1," OPIS"),
        ("Broń Biała(Kawaleryjska)",1,1," OPIS"),
        ("Broń Biała(Korbacze)",1,1," OPIS"),
        ("Broń Biała(Parująca)",1,1," OPIS"),
        ("Broń Biała(Szermiercza)",1,1," OPIS"),


        ("Broń Zasięgowa(Broń Eksperymentalna)",1,2," OPIS"),
        ("Broń Zasięgowa(Broń Miotana)",1,2," OPIS"),
        ("Broń Zasięgowa(Broń Oplątująca)",1,2," OPIS"),
        ("Broń Zasięgowa(Broń Prochowa)",1,2," OPIS"),
        ("Broń Zasięgowa(Kusze)",1,2," OPIS"),
        ("Broń Zasięgowa(Łuki)",1,2," OPIS"),
        ("Broń Zasięgowa(Materiały Wybuchowe)",1,2," OPIS"),
        ("Broń Zasięgowa(Proce)",1,2," OPIS"),



        ("Jeździectwo(Gryfy)",1,6," OPIS"),
        ("Jeździectwo(Pegazy)",1,6," OPIS"),
        ("Jeździectwo(Półgryfy)",1,6," OPIS"),
        ("Jeździectwo(Wielkie Wilki)",1,6," OPIS"),


        ("Język(Bitewny)",1,8,"OPIS"),
        ("Język(Bretoński)",1,8," OPIS"),
        ("Język(Klasyczny)",1,8," OPIS"),
        ("Język(Gildii)",1,8," OPIS"),
        ("Język(Khazalid)",1,8," OPIS"),
        ("Język(Magiczny)",1,8," OPIS"),
        ("Język(Złodziei)",1,8," OPIS"),
        ("Język(Tileański)",1,8," OPIS"),


        ("Kuglarstwo(Akrobatyka)",1,6," OPIS"),
        ("Kuglarstwo(Chodzenie po Linie)",1,6," OPIS"),
        ("Kuglarstwo(Komedianctwo)",1,6," OPIS"),
        ("Kuglarstwo(Mimika)",1,6," OPIS"),
        ("Kuglarstwo(Połykanie Ognia)",1,6," OPIS"),
        ("Kuglarstwo(Taniec)",1,6," OPIS"),
        ("Kuglarstwo(Żonglowanie)",1,6," OPIS"),


        ("Leczenie",1,8," OPIS"),


        ("Modlitwa",1,10," OPIS"),


        ("Muzyka(Dudy)",1,7," OPIS"),
        ("Muzyka(Harfa)",1,7," OPIS"),
        ("Muzyka(Lutnia)",1,7," OPIS"),
        ("Muzyka(Obój)",1,7," OPIS"),
        ("Muzyka(Skrzypce)",1,7," OPIS"),




        ("Opieka nad Zwierzętami",1,9," OPIS"),


        ("Otwieranie Zamków",1,7," OPIS"),



        ("Pływanie",1,3," OPIS"),


        ("Rzemiosło(Aptekarstwo)",1,7," OPIS"),
        ("Rzemiosło(Balsamowanie)",1,7," OPIS"),
        ("Rzemiosło(Garbarstwo)",1,7," OPIS"),
        ("Rzemiosło(Gotowanie)",1,7," OPIS"),
        ("Rzemiosło(Kaligrafia)",1,7," OPIS"),
        ("Rzemiosło(Kowalstwo)",1,7," OPIS"),
        ("Rzemiosło(Świecarstwo)",1,7," OPIS"),


        ("Sekretne Znaki(Gildii)",1,8," OPIS"),
        ("Sekretne Znaki(Kolegium Cienia)",1,8," OPIS"),
        ("Sekretne Znaki(Łowców)",1,8," OPIS"),
        ("Sekretne Znaki(Włóczęgów)",1,8," OPIS"),
        ("Sekretne Znaki(Złodziei)",1,8," OPIS"),
        ("Sekretne Znaki(Zwiadowców)",1,8," OPIS"),



        ("Skradanie(Podziemia)",1,6," OPIS"),
        ("Skradanie(Miasto)",1,6," OPIS"),
        ("Skradanie(Wieś)",1,6," OPIS"),


        ("Splatanie Magii(Aqshy)",1,9," OPIS"),
        ("Splatanie Magii(Azyr)",1,9," OPIS"),
        ("Splatanie Magii(Chamon)",1,9," OPIS"),
        ("Splatanie Magii(Dhar)",1,9," OPIS"),
        ("Splatanie Magii(Ghur)",1,9," OPIS"),
        ("Splatanie Magii(Ghyran)",1,9," OPIS"),
        ("Splatanie Magii(Hysh)",1,9," OPIS"),
        ("Splatanie Magii(Shyish)",1,9," OPIS"),
        ("Splatanie Magii(Ulgu)",1,9," OPIS"),


        ("Sztuka(Grawerstwo)",1,7," OPIS"),
        ("Sztuka(Kartografia)",1,7," OPIS"),
        ("Sztuka(Malarstwo)",1,7," OPIS"),
        ("Sztuka(Rzeźbiarstwo)",1,7," OPIS"),
        ("Sztuka(Tatuowanie)",1,7," OPIS"),
        ("Sztuka(Tkactwo)",1,7," OPIS"),
        ("Sztuka(Układanie Mozaiki)",1,7," OPIS"),




        ("Tresura(Gołębie)",1,8," OPIS"),
        ("Tresura(Konie)",1,8," OPIS"),
        ("Tresura(Pegazy)",1,8," OPIS"),
        ("Tresura(Półgryfy)",1,8," OPIS"),
        ("Tresura(Psy)",1,8," OPIS"),

        ("Tropienie",1,5," OPIS"),


        ("Wiedza(Chemia)",1,8," OPIS"),
        ("Wiedza(Geologia)",1,8," OPIS"),
        ("Wiedza(Heraldyka)",1,8," OPIS"),
        ("Wiedza(Historia)",1,8," OPIS"),
        ("Wiedza(Inżynieria)",1,8," OPIS"),
        ("Wiedza(Medycyna)",1,8," OPIS"),
        ("Wiedza(Magia)",1,8," OPIS"),
        ("Wiedza(Metalurgia)",1,8," OPIS"),
        ("Wiedza(Nauka)",1,8," OPIS"),
        ("Wiedza(Prawo)",1,8," OPIS"),
        ("Wiedza(Rośliny)",1,8," OPIS"),
        ("Wiedza(Teologia)",1,8," OPIS"),


        ("Wycena",1,8," OPIS"),

        ("Występy(Aktorstwo)",1,10," OPIS"),
        ("Występy(Gawędziarstwo)",1,10," OPIS"),
        ("Występy(Komedianctwo)",1,10," OPIS"),
        ("Występy(Śpiewanie)",1,10," OPIS"),

        ("Zastawianie Pułapek",1,7," OPIS"),


        ("Zwinne Palce",1,7," OPIS"),


        ("Żeglarstwo(Barki)",1,6," OPIS"),
        ("Żeglarstwo(Karawele)",1,6," OPIS"),
        ("Żeglarstwo(Kogi)",1,6," OPIS"),
        ("Żeglarstwo(Fregaty)",1,6," OPIS"),
        ("Żeglarstwo(Wilcze Statki)",1,6," OPIS");


--Dodanie statusu
INSERT INTO "status"("nazwa")
VALUES
    ("BRĄZ 1"),
    ("BRĄZ 2"),
    ("BRĄZ 3"),
    ("BRĄZ 4"),
    ("BRĄZ 5"),
    ("SREBRO 1"),
    ("SREBRO 2"),
    ("SREBRO 3"),
    ("SREBRO 4"),
    ("SREBRO 5"),
    ("ZŁOTO 1"),
    ("ZŁOTO 2"),
    ("ZŁOTO 3"),
    ("ZŁOTO 4"),
    ("ZŁOTO 5"),

    ("ZŁOTO 7"),
    ("BRĄZ 0");

   INSERT INTO "poziom" ("nazwa", "profesja_id","status_id","schemat_cech","schemat_umiejetnosci" )
   VALUES
        ("Uczennica Aptekarki", 1,3,"4,7,8","48,61,8,71,109,114,119"),
        ("Aptekarka", 1,6,"4,7,8,10","48,61,8,71,109,114,119,3,49,13,14,20,117"),
        ("Farmaceutka", 1, 8,"4,5,7,8,10","26,4,6,48,61,8,71,109,114,119,3,49,13,14,20,117"),
        ("Mistrzyni Aptekarstwa", 1, 1,"4,5,7,8,9,10","7,25,26,4,6,48,61,8,71,109,114,119,3,49,13,14,20,117"),

        ("Uczeń Czarodzieja", 2, 3,"1,8,9","48,61,8,71,109,114,119"),
        ("Czarodziej", 2, 8,"1,6,8,9","48,61,8,71,109,114,119"),
        ("Mistrz Magii", 2, 11,"1,5,6,8,9","48,61,8,71,109,114,119"),
        ("Arcymag", 2, 12,"1,5,6,8,9,10","48,61,8,71,109,114,119"),

        ("Student Inżynierii", 3, 4,"2,7,8","48,61,8,71,109,114,119"),
        ("Inżynier", 3, 7,"2,5,7,8","48,61,8,71,109,114,119"),
        ("Renomowany Inżynier", 3, 9,"2,4,5,7,8","48,61,8,71,109,114,119"),
        ("Mistrz Inżynierii", 3, 12,"2,4,5,7,8,9","48,61,8,71,109,114,119"),

        ("Kleryk", 4, 2,"4,6,9","48,61,8,71,109,114,119"),
        ("Kapłan", 4, 6,"4,6,9,10","48,61,8,71,109,114,119"),
        ("Arcykapłan", 4, 11,"4,6,8,9,10","48,61,8,71,109,114,119"),
        ("Lektor", 4, 12,"4,5,6,8,9,10","48,61,8,71,109,114,119"),

       ("Studentka Medycyny", 5, 4,"7,8,9","48,61,8,71,109,114,119"),
       ("Medyczka", 5, 8,"7,8,9,10","48,61,8,71,109,114,119"),
       ("Doktor",5 , 10,"5,7,8,9,10","48,61,8,71,109,114,119"),
       ("Nadworna Medyczka", 5, 11,"5,6,7,8,9,10","48,61,8,71,109,114,119"),

       ("Nowicjuszka", 6,1 ,"7,8,10","48,61,8,71,109,114,119"),
       ("Mniszka", 6, 4,"7,8,9,10","48,61,8,71,109,114,119"),
       ("Przeorysza", 6,7 ,"5,7,8,9,10","48,61,8,71,109,114,119"),
       ("Matka Przełożona", 6, 10,"4,5,7,8,9,10","48,61,8,71,109,114,119"),

       ("Studentka Prawa", 7, 4,"5,7,8","48,61,8,71,109,114,119"),
       ("Prawniczka", 7, 8,"5,7,8,10","48,61,8,71,109,114,119"),
       ("Obrońca", 7, 11,"5,7,8,9,10","48,61,8,71,109,114,119"),
       ("Sędzia", 7, 12,"4,5,7,8,9,10","48,61,8,71,109,114,119"),

       ("Student", 8, 3,"4,8,9","48,61,8,71,109,114,119"),
       ("Uczony", 8, 7,"4,5,8,9","48,61,8,71,109,114,119"),
       ("Wykładowca", 8, 10,"4,5,8,9,10","48,61,8,71,109,114,119"),
       ("Profesor", 8,11 ,"4,5,7,8,9,10","48,61,8,71,109,114,119"),

       ("Pamflecista", 9,1 ,"2,8,10","48,61,8,71,109,114,119"),
       ("Agitator", 9, 2,"2,6,8,10","48,61,8,71,109,114,119"),
       ("Podżegacz", 9, 3,"1,2,6,8,10","48,61,8,71,109,114,119"),
       ("Demagog", 9, 5,"1,2,5,6,8,10","48,61,8,71,109,114,119"),

       ("Handlarz", 10, 7,"6,9,10","48,61,8,71,109,114,119"),
       ("Kupiec", 10, 10,"6,8,9,10","48,61,8,71,109,114,119"),
       ("Mistrz Kupiectwa", 10, 11,"5,6,8,9,10","48,61,8,71,109,114,119"),
       ("Patrycjusz", 10,13,"1,5,6,8,9,10" ,"48,61,8,71,109,114,119"),

       ("Przekupka", 11,6 ,"6,8,10","48,61,8,71,109,114,119"),
       ("Mieszczka", 11,7 ,"5,6,8,10","48,61,8,71,109,114,119"),
       ("Rajczyni Miejska", 11,10 ,"5,6,7,8,10","48,61,8,71,109,114,119"),
       ("Burmistrz", 11, 11,"5,6,7,8,9,10","48,61,8,71,109,114,119"),

       ("Czeladniczka", 12, 2,"3,4,7","48,61,8,71,109,114,119"),
       ("Rzemieślniczka", 12,6 ,"3,4,7,10","48,61,8,71,109,114,119"),
       ("Mistrzyni Rzemiosła", 12,8 ,"3,4,7,9,10","48,61,8,71,109,114,119"),
       ("Mistrzyni Cechu", 12,11 ,"3,4,7,8,9,10","48,61,8,71,109,114,119"),

       ("Rekrut Straży", 13, 3 ,"1,3,10","48,61,8,71,109,114,119"),
       ("Strażnik", 13, 6 ,"1,3,9,10","48,61,8,71,109,114,119"),
       ("Sierżant Straży", 13, 8 ,"1,3,5,9,10","48,61,8,71,109,114,119"),
       ("Kapitan Straży", 13, 11 ,"1,3,5,8,9,10","48,61,8,71,109,114,119"),

       ("Uczeń Szczurołapa", 14,3 ,"1,2,9","48,61,8,71,109,114,119"),
       ("Szczurołap", 14, 6 ,"1,2,4,9","48,61,8,71,109,114,119"),
       ("Strażnik Kanałów", 14,7 ,"1,2,4,5,9","48,61,8,71,109,114,119"),
       ("Tępiciel", 14, 8, "1,2,3,4,5,9","48,61,8,71,109,114,119"),

       ("Szpicel", 15, 6 ,"5,6,8","48,61,8,71,109,114,119"),
       ("Śledczy", 15, 7 ,"5,6,8,10","48,61,8,71,109,114,119"),
       ("Starszy Śledczy", 15, 8 ,"5,6,7,8,10","48,61,8,71,109,114,119"),
       ("Detektyw", 15,10 ,"5,6,7,8,9,10","48,61,8,71,109,114,119"),

       ("Biedak", 16, 17 ,"4,6,10","48,61,8,71,109,114,119"),
       ("Żebrak", 16, 2 ,"4,6,9,10","48,61,8,71,109,114,119"),
       ("Mistrz Żebraków", 16,4 ,"1,4,6,9,10","48,61,8,71,109,114,119"),
       ("Król Żebraków", 16, 7,"1,4,5,6,9,10","48,61,8,71,109,114,119") ,

       ("Uczennica Artysty", 17, 6,"3,5,7","48,61,8,71,109,114,119"),
       ("Artystka", 17, 8,"3,5,7,10","48,61,8,71,109,114,119"),
       ("Mistrzyni Sztuki", 17,10,"3,5,7,9,10","48,61,8,71,109,114,119"),
       ("Maestro", 17,12 ,"3,5,7,8,9,10","48,61,8,71,109,114,119"),

       ("Asystent", 18,7 ,"4,5,6","48,61,8,71,109,114,119"),
       ("Doradca", 18,9 ,"4,5,6,10","48,61,8,71,109,114,119"),
       ("Radca", 18,11 ,"4,5,6,8,10","48,61,8,71,109,114,119"),
       ("Kanclerz", 18, 13 ,"4,5,6,8,9,10","48,61,8,71,109,114,119"),

       ("Nadzorca", 19, 6,"3,4,9","48,61,8,71,109,114,119"),
       ("Namiestnik", 19, 8,"1,3,4,9","48,61,8,71,109,114,119"),
       ("Seneszal", 19, 11 ,"1,3,4,9,10","48,61,8,71,109,114,119"),
       ("Gubernator", 19, 13 ,"1,3,4,8,9,10","48,61,8,71,109,114,119"),

       ("Herold", 20, 7 ,"4,6,10","48,61,8,71,109,114,119"),
       ("Poseł", 20,9 ,"4,6,8,10","48,61,8,71,109,114,119"),
       ("Dyplomata", 20, 12,"4,5,6,8,10","48,61,8,71,109,114,119"),
       ("Ambasador", 20, 15,"4,5,6,8,9,10","48,61,8,71,109,114,119"),

       ("Posługaczka", 21,6 ,"3,4,6","48,61,8,71,109,114,119"),
       ("Służąca", 21, 8 ,"3,4,5,6","48,61,8,71,109,114,119"),
       ("Pokojowa", 21,10 ,"3,4,5,6,8","48,61,8,71,109,114,119"),
       ("Ochmistrzyni", 21,11 ,"3,4,5,6,8,10","48,61,8,71,109,114,119"),

       ("Dziedzic", 22, 11 ,"1,5,7","48,61,8,71,109,114,119"),
       ("Szlachcic", 22, 13 ,"1,5,7,10","48,61,8,71,109,114,119"),
       ("Magnat", 22, 15 ,"1,5,7,8,10","48,61,8,71,109,114,119"),
       ("Lord", 22, 16 ,"1,5,7,8,9,10","48,61,8,71,109,114,119"),

       ("Informator", 23, 3 ,"6,9,10","48,61,8,71,109,114,119"),
       ("Szpieg", 23, 8 ,"1,6,9,10","48,61,8,71,109,114,119"),
       ("Agent", 23, 11 ,"1,5,6,9,10","48,61,8,71,109,114,119"),
       ("Mistrz Szpiegów", 23, 14,"1,5,6,8,9,10","48,61,8,71,109,114,119"),

       ("Szermierz", 24,8 ,"1,5,6","48,61,8,71,109,114,119"),
       ("Zwadźca", 24,10 ,"1,2,5,6","48,61,8,71,109,114,119"),
       ("Mistrz Pojedynków", 24,11,"1,2,3,5,6","48,61,8,71,109,114,119"),
       ("Szampierz Sądowy", 24,13 ,"1,2,3,5,6,9","48,61,8,71,109,114,119"),

       ("Wyrobnica", 25, 2,"3,4,6","48,61,8,71,109,114,119"),
       ("Chłopka", 25, 3,"1,3,4,6","48,61,8,71,109,114,119"),
       ("Gospodyni", 25, 4,"1,3,4,6,10","48,61,8,71,109,114,119"),
       ("Starsza Wioski", 25, 7,"1,3,4,6,8,10","48,61,8,71,109,114,119"),

       ("Poszukiwacz", 26, 2,"3,4,9","48,61,8,71,109,114,119"),
       ("Górnik", 26, 4,"1,3,4,9","48,61,8,71,109,114,119"),
       ("Sztygar", 26, 5,"1,3,4,5,9","48,61,8,71,109,114,119"),
       ("Mistrz Górnictwa", 26, 9,"1,3,4,5,9,10","48,61,8,71,109,114,119"),

       ("Uczeń Guślarza", 27, 1,"4,5,7","48,61,8,71,109,114,119"),
       ("Guślarz", 27, 2,"4,5,7,8","48,61,8,71,109,114,119"),
       ("Starszy Guślarzy", 27,3 ,"4,5,7,8,10","48,61,8,71,109,114,119"),
       ("Wiedzący", 27, 5,"4,5,7,8,9,10","48,61,8,71,109,114,119"),

       ("Traperka", 28, 2,"3,4,7","48,61,8,71,109,114,119"),
       ("Łowczyni", 28,4 ,"2,3,4,7","48,61,8,71,109,114,119"),
       ("Tropicielka", 28, 6,"2,3,4,5,7","48,61,8,71,109,114,119"),
       ("Nadworna Łowczyni", 28, 8,"2,3,4,5,7,8","48,61,8,71,109,114,119"),

       ("Wróżbiarka", 29, 1,"5,7,10","48,61,8,71,109,114,119"),
       ("Mistyczka", 29, 2,"5,7,9,10","48,61,8,71,109,114,119"),
       ("Widząca", 29, 3,"5,6,7,9,10","48,61,8,71,109,114,119"),
       ("Prorokini", 29, 4,"5,6,7,8,9,10","48,61,8,71,109,114,119"),

       ("Poborca Podatków", 30,6 ,"1,5,9","48,61,8,71,109,114,119"),
       ("Zarządca", 30,10 ,"1,5,9,10","48,61,8,71,109,114,119"),
       ("Włodarz", 30,11,"1,5,6,9,10","48,61,8,71,109,114,119"),
       ("Komornik", 30, 13,"1,5,6,8,9,10","48,61,8,71,109,114,119"),

       ("Zbieraczka Ziół", 31, 2,"4,5,6","48,61,8,71,109,114,119"),
       ("Zielarka", 31, 4,"4,5,6,7","48,61,8,71,109,114,119"),
       ("Mistrzyni Ziołolecznictwa", 31, 6,"4,5,6,7,10","48,61,8,71,109,114,119"),
       ("Arcyzielarka", 31,8,"4,5,6,7,8,10","48,61,8,71,109,114,119"),

       ("Przewodnik", 32, 3,"4,5,6","48,61,8,71,109,114,119"),
       ("Zwiadowca", 32,5 ,"2,4,5,6","48,61,8,71,109,114,119"),
       ("Przepatrywacz", 32, 6,"2,4,5,6,8","48,61,8,71,109,114,119"),
       ("Odkrywca", 32,10 ,"2,4,5,6,7,8","48,61,8,71,109,114,119"),

       ("Gorliwiec", 33, 17,"1,3,4","48,61,8,71,109,114,119"),
       ("Biczownik", 33, 17,"1,3,4,9","48,61,8,71,109,114,119"),
       ("Pokutnik", 33,17 ,"1,3,4,5,9","48,61,8,71,109,114,119"),
       ("Piewca Zagłady", 33,17 ,"1,3,4,5,9,10","48,61,8,71,109,114,119"),

       ("Powsinoga", 34, 1,"4,7,9","48,61,8,71,109,114,119"),
       ("Domokrążca", 34, 4,"4,7,9,10","48,61,8,71,109,114,119"),
       ("Doświadczony Domokrążca", 34,6 ,"4,5,7,9,10","48,61,8,71,109,114,119"),
       ("Wędrowny Handlarz", 34, 8,"4,5,7,8,9,10","48,61,8,71,109,114,119"),

       ("Muzykantka", 35, 3,"5,6,10","48,61,8,71,109,114,119"),
       ("Kuglarka", 35, 5,"1,5,6,10","48,61,8,71,109,114,119"),
       ("Trubadurka", 35, 8,"1,2,5,6,10","48,61,8,71,109,114,119"),
       ("Przywódczyni Trupy", 35, 11,"1,2,4,5,6,10","48,61,8,71,109,114,119"),

       ("Oprawca", 36,6,"1,4,9","48,61,8,71,109,114,119"),
       ("Łowca Czarownic", 36,8 ,"1,2,4,9","48,61,8,71,109,114,119"),
       ("Inkwizytor", 36, 10,"1,2,4,9,10","48,61,8,71,109,114,119"),
       ("Generał Łowców Czarownic", 36,11 ,"1,2,4,8,9,10","48,61,8,71,109,114,119"),

       ("Pogromczyni Złodziei", 37, 6,"1,4,6","48,61,8,71,109,114,119"),
       ("Łowczyni Nagród", 37,8 ,"1,2,4,6","48,61,8,71,109,114,119"),
       ("Doświadczona Łowczyni Nagród", 37,10,"1,2,3,4,6","48,61,8,71,109,114,119"),
       ("Legendarna Łowczyni Nagród", 37,11,"1,2,3,4,6,8","48,61,8,71,109,114,119"),

       ("Goniec", 38, 3,"4,5,6","48,61,8,71,109,114,119"),
       ("Posłaniec", 38, 6,"1,4,5,6","48,61,8,71,109,114,119"),
       ("Kurier", 38, 8,"1,4,5,6,9","48,61,8,71,109,114,119"),
       ("Kapitan Kurierów", 38,10,"1,4,5,6,9,10","48,61,8,71,109,114,119"),

       ("Mytniczka", 39,5 ,"2,4,5","48,61,8,71,109,114,119"),
       ("Strażniczka Dróg", 39, 7,"1,2,4,5","48,61,8,71,109,114,119"),
       ("Sierżant Strażników Dróg", 39, 9,"1,2,4,5,10","48,61,8,71,109,114,119"),
       ("Kapitan Strażników Dróg", 39, 11,"1,2,4,5,8,10","48,61,8,71,109,114,119"),

       ("Foryś", 40, 6,"2,4,9","48,61,8,71,109,114,119"),
       ("Woźnica", 40,7 ,"2,4,6,9","48,61,8,71,109,114,119"),
       ("Mistrz Woźniców", 40,8,"1,2,4,6,9","48,61,8,71,109,114,119"),
       ("Mistrz Szlaków", 40,10 ,"1,2,4,5,6,9","48,61,8,71,109,114,119"),

       ("Pomocnik Dokera", 41,3,"1,4,5","48,61,8,71,109,114,119"),
       ("Doker", 41, 6,"1,3,4,5","48,61,8,71,109,114,119"),
       ("Brygadzista", 41, 8,"1,3,4,5,9","48,61,8,71,109,114,119"),
       ("Mistrz Dokerów", 41,10 ,"1,3,4,5,8,9","48,61,8,71,109,114,119"),

       ("Rybak", 42, 2,"4,6,7","48,61,8,71,109,114,119"),
       ("Flisak", 42, 3,"1,4,6,7","48,61,8,71,109,114,119"),
       ("Znawca Rzeki", 42, 5,"1,4,5,6,7","48,61,8,71,109,114,119"),
       ("Starszy Rzeczny", 42, 7,"1,4,5,6,7,10","48,61,8,71,109,114,119"),

       ("Przewodniczka Rzeczna", 43,4,"1,4,5","48,61,8,71,109,114,119"),
       ("Pilotka Rzeczna", 43, 6,"1,4,5,9","48,61,8,71,109,114,119"),
       ("Starsza Pilotka", 43,8 ,"1,4,5,8,9","48,61,8,71,109,114,119"),
       ("Legendarna Pilotka", 43,10,"1,4,5,8,9,10","48,61,8,71,109,114,119"),

       ("Szabrownik", 44, 2,"1,3,5","48,61,8,71,109,114,119"),
       ("Pirat Rzeczny", 44, 3,"1,3,5,9","48,61,8,71,109,114,119"),
       ("Łupieżca", 44, 5,"1,2,3,5,9","48,61,8,71,109,114,119"),
       ("Kapitan Łupieżców", 44, 7,"1,2,3,5,9,10","48,61,8,71,109,114,119"),

       ("Szmuglerka Rzeczna", 45, 2,"6,7,9","48,61,8,71,109,114,119"),
       ("Przemytniczka", 45,3 ,"5,6,7,9","48,61,8,71,109,114,119"),
       ("Doświadczona Przemytniczka", 45, 5,"5,6,7,8,9","48,61,8,71,109,114,119"),
       ("Królowa Przemytników", 45,7,"5,6,7,8,9,10","48,61,8,71,109,114,119" ),

       ("Chłopiec Pokładowy", 46, 6,"3,4,6","48,61,8,71,109,114,119"),
       ("Przewoźnik", 46, 7,"3,4,5,6","48,61,8,71,109,114,119"),
       ("Sternik", 46, 8,"3,4,5,6,7","48,61,8,71,109,114,119"),
       ("Kapitan Barki", 46, 10,"3,4,5,6,7,8","48,61,8,71,109,114,119"),

       ("Rekrut Rzeczny", 47, 6,"2,3,10","48,61,8,71,109,114,119"),
       ("Strażnik Rzeczny", 47, 7,"1,2,3,10","48,61,8,71,109,114,119"),
       ("Żołnierz Okrętowy", 47,9 ,"1,2,3,5,10","48,61,8,71,109,114,119"),
       ("Mistrz Żołnierzy Okrętowych", 47, 11,"1,2,3,5,8,10","48,61,8,71,109,114,119"),

       ("Szczur Lądowy", 48, 6,"6,7,10","48,61,8,71,109,114,119"),
       ("Żeglarz", 48, 8,"1,6,7,10","48,61,8,71,109,114,119"),
       ("Bosman", 48, 10,"1,5,6,7,10","48,61,8,71,109,114,119"),
       ("Kapitan Statku", 48,12 ,"1,5,6,7,8,10","48,61,8,71,109,114,119"),

       ("Zbój", 49, 1,"1,3,4","48,61,8,71,109,114,119"),
       ("Banita", 49, 2,"1,2,3,4","48,61,8,71,109,114,119"),
       ("Herszt Banitów", 49,4,"1,2,3,4,5","48,61,8,71,109,114,119"),
       ("Król Banitów", 49,7,"1,2,3,4,5,10","48,61,8,71,109,114,119"),

       ("Szeptucha", 50,1 ,"1,4,9","48,61,8,71,109,114,119"),
       ("Czarownica", 50, 2,"1,4,5,9","48,61,8,71,109,114,119"),
       ("Wiedźma", 50, 3,"1,4,5,9,10","48,61,8,71,109,114,119"),
       ("Arcyczarownica", 50, 5,"1,4,5,8,9,10","48,61,8,71,109,114,119"),

       ("Porywaczka Zwłok", 51,2,"3,5,9","48,61,8,71,109,114,119"),
       ("Hiena Cmentarna", 51, 3,"1,3,5,9","48,61,8,71,109,114,119"),
       ("Rabuś Grobowców", 51,6 ,"1,3,5,7,9","48,61,8,71,109,114,119"),
       ("Łowczyni Skarbów", 51, 10,"1,3,5,7,8,9","48,61,8,71,109,114,119"),

       ("Pośrednik", 52,6 ,"5,6,10","48,61,8,71,109,114,119"),
       ("Paser", 52, 7,"5,6,7,10","48,61,8,71,109,114,119"),
       ("Mistrz Paserów", 52, 8,"5,6,7,8,10","48,61,8,71,109,114,119"),
       ("Broker Informacji", 52, 9,"5,6,7,8,9,10","48,61,8,71,109,114,119"),

       ("Naganiacz", 53,1 ,"6,7,10","48,61,8,71,109,114,119"),
       ("Rajfur", 53, 3,"5,6,7,10","48,61,8,71,109,114,119"),
       ("Stręczyciel", 53,6 ,"5,6,7,9,10","48,61,8,71,109,114,119"),
       ("Herszt Rajfurów", 53, 8,"5,6,7,8,9,10","48,61,8,71,109,114,119"),

       ("Zakapiorka", 54,3,"1,3,4" ,"48,61,8,71,109,114,119"),
       ("Rekietierka", 54,5,"1,3,4,10" ,"48,61,8,71,109,114,119"),
       ("Głowa Gangu", 54,8 ,"1,3,4,9,10","48,61,8,71,109,114,119"),
       ("Królowa Podziemia", 54,10,"1,3,4,8,9,10","48,61,8,71,109,114,119" ),

       ("Kanciarz", 55,3 ,"5,7,10","48,61,8,71,109,114,119"),
       ("Szarlatan", 55, 5,"5,7,9,10","48,61,8,71,109,114,119"),
       ("Oszust", 55, 7,"5,6,7,9,10","48,61,8,71,109,114,119"),
       ("Łajdak", 55, 9,"5,6,7,8,9,10","48,61,8,71,109,114,119"),

       ("Bandzior", 56, 1,"5,6,9","48,61,8,71,109,114,119"),
       ("Złodziej", 56, 3,"5,6,7,9","48,61,8,71,109,114,119"),
       ("Mistrz Złodziejski", 56,5,"3,5,6,7,9","48,61,8,71,109,114,119"),
       ("Włamywacz", 56, 8,"3,5,6,7,9,10","48,61,8,71,109,114,119"),

       ("Pięściarz", 57, 4,"1,3,4","48,61,8,71,109,114,119"),
       ("Gladiator", 57,7,"1,3,4,5" ,"48,61,8,71,109,114,119"),
       ("Mistrz Areny", 57, 10,"1,3,4,5,6","48,61,8,71,109,114,119"),
       ("Legenda Areny", 57,12 ,"1,3,4,5,6,10","48,61,8,71,109,114,119"),

       ("Nowicjusz Kapłanów Bitewnych", 58,2 ,"1,4,9","48,61,8,71,109,114,119"),
       ("Kapłan Bitewny", 58, 7,"1,3,4,9","48,61,8,71,109,114,119"),
       ("Kapłan-sierżant", 58, 8,"1,3,4,5,9","48,61,8,71,109,114,119"),
       ("Kapłan-kapitan", 58, 9,"1,3,4,5,9,10","48,61,8,71,109,114,119"),

       ("Jeździec", 59, 7,"1,3,6","48,61,8,71,109,114,119"),
       ("Kawalerzysta", 59,9 ,"1,2,3,6","48,61,8,71,109,114,119"),
       ("Sierżant Kawalerii", 59,11,"1,2,3,5,6" ,"48,61,8,71,109,114,119"),
       ("Oficer Kawalerii", 59, 12,"1,2,3,5,6,10","48,61,8,71,109,114,119"),

       ("Stróż", 60, 6,"1,4,6","48,61,8,71,109,114,119"),
       ("Ochroniarz", 60,7 ,"1,4,5,6","48,61,8,71,109,114,119"),
       ("Gwardzista", 60,8 ,"1,3,4,5,6","48,61,8,71,109,114,119"),
       ("Oficer Gwardii", 60, 10,"1,3,4,5,6,8","48,61,8,71,109,114,119"),

       ("Tani Drań", 61, 2,"1,4,6","48,61,8,71,109,114,119"),
       ("Oprych", 61,6 ,"1,4,5,6","48,61,8,71,109,114,119"),
       ("Płatny Morderca", 61,9,"1,2,4,5,6" ,"48,61,8,71,109,114,119"),
       ("Skrytobójca", 61,11,"1,2,4,5,6,10" ,"48,61,8,71,109,114,119"),

       ("Giermek", 62,8,"3,5,6" ,"48,61,8,71,109,114,119"),
       ("Rycerz", 62, 10,"1,3,5,6","48,61,8,71,109,114,119"),
       ("Pierwszy Rycerz", 62,12 ,"1,3,5,6,9","48,61,8,71,109,114,119"),
       ("Rycerz Wewnętrznego Kręgu", 62,14,"1,3,5,6,9,10" ,"48,61,8,71,109,114,119"),

       ("Zabójca Trolli", 63,2 ,"1,3,9","48,61,8,71,109,114,119"),
       ("Zabójca Olbrzymów", 63,2 ,"1,3,4,9","48,61,8,71,109,114,119"),
       ("Zabójca Smoków", 63, 2 ,"1,3,4,6,9","48,61,8,71,109,114,119"),
       ("Zabójca Demonów", 63, 2 ,"1,3,4,5,6,9","48,61,8,71,109,114,119"),

       ("Rekrut", 64,6 ,"1,4,9","48,61,8,71,109,114,119"),
       ("Żołnierz", 64, 8,"1,2,4,9","48,61,8,71,109,114,119"),
       ("Sierżant", 64,10 ,"1,2,4,5,9","48,61,8,71,109,114,119"),
       ("Oficer", 64,11 ,"1,2,4,5,9,10","48,61,8,71,109,114,119");


  INSERT INTO "dostępność"("nazwa","wioska","miasteczko","miasto")
    VALUES
        ('Powszechna',"W sprzedaży!","W sprzedaży!","W sprzedaży!"),
        ('Ograniczona',"30%","60%","90%"),
        ('Rzadka',"15%","30%","45%"),
        ('Egzotyczna',"Nie ma","Nie ma","Nie ma");


    INSERT INTO "zalety_pancerz"("nazwa","opis")
       VALUES
        ('Giętki',"Giętki pancerz możesz nosić pod warstwą innego pancerza (niepo- siadającego tej Zalety). W takim przypadku uzyskujesz korzyści obu pancerzy"),
        ('Nieprzebijalny',"Taki pancerz jest wyjątkowo odporny, co sprawia, że większość ata- ków nie jest w stanie go przebić. Wszystkie Rany Krytyczne wynikłe na skutek nieparzystego wyniku rzutu na trafienie (na przykład 11 lub 33) są ignorowane.");


    INSERT INTO "wady_pancerz"("nazwa","opis")
        VALUES
         ('Częściowy',"Pancerz nie okrywa całego Miejsca Trafienia. Przeciwnik, który uzy- ska parzysty wynik rzutu na trafienie albo wyrzuci Trafienie Krytycz- ne, ignoruje PP Częściowego pancerza."),
         ('Wrażliwe punkty',"Pancerz ma niewielkie miejsca, w które może wślizgnąć się ostrze, jeśli przeciwnik ma wystarczające umiejętności lub dość szczęścia. Jeśli wróg posługuje się bronią Nadziewającą i uzyska Trafienie Kry- tyczne, wszystkie PP tego pancerza są ignorowane.");


  INSERT INTO "typ_pancerza"("nazwa")
    VALUES
        ('MIĘKKA SKÓRA'),
        ('SKÓRA HARTOWANA'),
        ('KOLCZUGI'),
        ('PŁYTOWE'),
        ('INNE');

  INSERT INTO "lokalizacja_pancerza"("nazwa")
    VALUES
        ('ramiona'),
        ('korpus'),
        ('nogi'),
        ('głowa'),
        ('wszystko');

  INSERT INTO "pancerz"("nazwa","cena","kara","lokalizacja_pancerza","punkty_pancerza","zalety","wady","dostępność_id","typ_pancerza_id")
    VALUES
        ('Skórzana kurta','12s',NULL,'1,2',1,'','',1,1),
        ('Czepiec kolczy','1 ZK','-10 do Percepcji','1',2,'1','1',2,1),
        ('Nagolenniki płytowe','10 ZK','-10 do Skradania','3', 2,'1,2','1,2',3,4);



   INSERT INTO "talent" ("nazwa", "maksimum","testy","opis","cechy_id" )
       VALUES
       ('Aptekarz','Bonus z Inteligencj','Rzemiosło (Aptekarstwo)','Jesteś świetnym aptekarzem i lepiej od innych wyrabiasz pigułki, maści, smarowidła, olejki, kremy i im podobne. Możesz odwrócić ko- lejność kości nieudanego Testu Rzemiosła (Aptekarstwa), jeśli nowy wynik pozwoli ci odnieść sukces.',8),
       ('Arcydzieło','brak','','Jesteś niekwestionowanym mistrzem w swojej dziedzinie, two- rzącym dzieła tak złożone, że inni mogą je tylko podziwiać, zachwycając się twoim geniuszem. Za każdym razem, gdy wy- kupujesz ten Talent, tworzysz niezwykłe dzieło, wykorzystując Umiejętność Sztuka lub Rzemiosło. Nie ma ono sobie równych, będzie po wieki inspirowało, zadziwiało i budziło zachwyt swoją wyjątkowością. MG określa premie, które ci przysługują z tej ra- cji. Zwykle wpływają one na Testy Ogłady w kontaktach z tymi, którzy podziwiają twoją sztukę. Sprzedaż dzieła powinna dać ci przynajmniej dziesięciokrotną wartość zwykłej ceny, a czasami nawet więcej.',0),

       ('Artylerzysta','Bonus ze Zręcznośc','','Z łatwością przeładowujesz broń prochową. Dodaj PS równe liczbie wykupień tego Talentu do każdego Wydłużonego Testu związanego z przeładowaniem broni prochowej.',0),

       ('Nazwa','maksimum','testy(kiedy dziala)','OPIS ',0),


       ('Artylerzysta','Bonus ze Zręcznośc','','OPIS  ',0);





