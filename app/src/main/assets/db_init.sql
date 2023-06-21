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

CREATE TABLE "pancerz" (
    "id"                           INTEGER NOT NULL,
    "nazwa"                        TEXT NOT NULL,
    "cena"                         TEXT NOT NULL,
    "opciążenie"                   INTEGER NOT NULL,
    "dostęp"                       TEXT NOT NULL,
    "kara"                         TEXT NOT NULL,
    "lokacja"                      TEXT,
    "punkty_pancerza"              INTEGER NOT NULL,
    "zalety"                       TEXT NOT NULL,
    "wady"                         TEXT NOT NULL,

    "typ_pancerza_id"              INTEGER NOT NULL,

    FOREIGN KEY ("typ_pancerza_id") REFERENCES "typ_pancerza"("id") ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "status" (
    "id"        INTEGER PRIMARY KEY,
    "nazwa"     TEXT NOT NULL
);

CREATE TABLE "rasa" (
    "id"          INTEGER PRIMARY KEY,
    "name"        TEXT,
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


CREATE TABLE "poziom_profesji" (
    "id"                   INTEGER PRIMARY KEY,
    "lvl"                  INTEGER ,
    "nazwa"                TEXT ,

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
    "dostęp"                 TEXT NOT NULL,
    "zasięg"                 TEXT NOT NULL,
    "obrażenia"              TEXT NOT NULL,
    "zalety"                 TEXT NOT NULL,
    "wady"                   TEXT NOT NULL,
    "kategoria"              TEXT NOT NULL,
    "cechy"                  TEXT,
    "czy_własna"             INTEGER NOT NULL,

    "typ_broni_id"           INTEGER NOT NULL,
    FOREIGN KEY ("typ_broni_id") REFERENCES "typ_broni"("id") ON UPDATE CASCADE ON DELETE CASCADE

);


CREATE TABLE "sugestia_broni" (

    "poziom_profesji_id"                 INTEGER NOT NULL,
    "broń_id"                           INTEGER NOT NULL,

    FOREIGN KEY ("poziom_profesji_id") REFERENCES "poziom_profesji"("id")  ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("broń_id") REFERENCES "broń"("id") ON UPDATE CASCADE ON DELETE CASCADE

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

    "kampania_id"                        INTEGER,
    "rasa_id"                            INTEGER,
    "profesja_id"                        INTEGER,
    "poziom_profesji_id"                 INTEGER,

    FOREIGN KEY ("kampania_id") REFERENCES "kampania"("id") ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY ("rasa_id") REFERENCES "rasa"("id") ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY ("profesja_id") REFERENCES "profesja"("id") ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY ("poziom_profesji_id") REFERENCES "poziom_profesji"("id") ON UPDATE CASCADE ON DELETE CASCADE


);


   CREATE TABLE "talent" (
    "id"                 INTEGER NOT NULL,
    "nazwa"              TEXT NOT NULL,
    "opis"               TEXT NOT NULL,
    "maksymalny_poziom"  INTEGER,
    "maksymalna_wartość" INTEGER,
    "czy_ma_maksimum"    INTEGER NOT NULL,

    "cechy_id"           INTEGER NOT NULL,

    FOREIGN KEY ("cechy_id") REFERENCES "cechy"("id") ON UPDATE CASCADE ON DELETE CASCADE
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

    "wartość_pociątkowa" INTEGER NOT NULL,
    "rozwój"             INTEGER NOT NULL,

    FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("cechy_id") REFERENCES "cechy"("id") ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "konfiguracja_rozwój" (

    "cechy_id"                           INTEGER NOT NULL,
    "poziom_profesji_id"                 INTEGER NOT NULL,

    FOREIGN KEY ("cechy_id") REFERENCES "cechy"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("poziom_profesji_id") REFERENCES "poziom_profesji_id"("id")   ON UPDATE CASCADE ON DELETE CASCADE

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

    FOREIGN KEY ("karta_id") REFERENCES "karta"("id")  ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("pancerz_id") REFERENCES "pancerz"("id")  ON UPDATE CASCADE ON DELETE CASCADE



);

CREATE TABLE "karta_talent" (

    "karta_id"          INTEGER NOT NULL,
    "talent_id"         INTEGER NOT NULL,
    "poziom"            INTEGER NOT NULL,

    FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("talent_id") REFERENCES "talent"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "karta_umiętność" (

    "karta_id"                     INTEGER NOT NULL,
    "umiejętności_id"              INTEGER NOT NULL,
    "rozwój"                       INTEGER,

     FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("umiejętności_id") REFERENCES "umiejętności"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "karta_wypasarzenie" (

    "karta_id"                     INTEGER NOT NULL,
    "wyposarzenie_id"             INTEGER NOT NULL,

     FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("wyposarzenie_id") REFERENCES "wyposarzenie"("id") ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "konfiguracja_umijętności" (
    "umiejętności_id"                    INTEGER NOT NULL,
    "poziom_profesji_id"                 INTEGER NOT NULL,

     FOREIGN KEY ("umiejętności_id") REFERENCES "umiejętności"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("poziom_profesji_id") REFERENCES "poziom_profesji"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "sugestia_wypozażenia" (

    "wyposarzenie_id"           INTEGER NOT NULL,
    "poziom_profesji_id"        INTEGER NOT NULL,

    FOREIGN KEY ("wyposarzenie_id") REFERENCES "wyposarzenie"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("poziom_profesji_id") REFERENCES "poziom_profesji"("id") ON UPDATE CASCADE ON DELETE CASCADE
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
        ("Broń Biała (Inna)",0,1," OPIS"),
        ("Charyzma",0,10," OPIS"),
        ("Dowodzenie",0,10," OPIS"),
        ("Hazard",0,8," OPIS"),
        ("Intuicja",0,5," OPIS"),
        ("Jeździectwo",0,6," OPIS"),
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
        ("Zastraszanie",0,3," OPIS");

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

   INSERT INTO "poziom_profesji" ("nazwa", "profesja_id","status_id")
   VALUES
        ("Uczennica Aptekarki", 1,3),
        ("Aptekarka", 1,6),
        ("Farmaceutka", 1, 8),
        ("Mistrzyni Aptekarstwa", 1, 1),

        ("Uczeń Czarodzieja", 2, 3),
        ("Czarodziej", 2, 8),
        ("Mistrz Magii", 2, 11),
        ("Arcymag", 2, 12),

        ("Student Inżynierii", 3, 4),
        ("Inżynier", 3, 7),
        ("Renomowany Inżynier", 3, 9),
        ("Mistrz Inżynierii", 3, 12),

        ("Kleryk", 4, 2),
        ("Kapłan", 4, 6),
        ("Arcykapłan", 4, 11),
        ("Lektor", 4, 12),

       ("Studentka Medycyny", 5, 4),
       ("Medyczka", 5, 8),
       ("Doktor",5 , 10),
       ("Nadworna Medyczka", 5, 11),

       ("Nowicjuszka", 6,1 ),
       ("Mniszka", 6, 4),
       ("Przeorysza", 6,7 ),
       ("Matka Przełożona", 6, 10),

       ("Studentka Prawa", 7, 4),
       ("Prawniczka", 7, 8),
       ("Obrońca", 7, 11),
       ("Sędzia", 7, 12),

       ("Student", 8, 3),
       ("Uczony", 8, 7),
       ("Wykładowca", 8, 10),
       ("Profesor", 8,11 ),

       ("Pamflecista", 9,1 ),
       ("Agitator", 9, 2),
       ("Podżegacz", 9, 3),
       ("Demagog", 9, 5),

       ("Handlarz", 10, 7),
       ("Kupiec", 10, 10),
       ("Mistrz Kupiectwa", 10, 11),
       ("Patrycjusz", 10,13 ),

       ("Przekupka", 11,6 ),
       ("Mieszczka", 11,7 ),
       ("Rajczyni Miejska", 11,10 ),
       ("Burmistrz", 11, 11),

       ("Czeladniczka", 12, 2),
       ("Rzemieślniczka", 12,6 ),
       ("Mistrzyni Rzemiosła", 12,8 ),
       ("Mistrzyni Cechu", 12,11 ),

       ("Rekrut Straży", 13, 3),
       ("Strażnik", 13, 6),
       ("Sierżant Straży", 13, 8),
       ("Kapitan Straży", 13, 11),

       ("Uczeń Szczurołapa", 14,3 ),
       ("Szczurołap", 14, 6),
       ("Strażnik Kanałów", 14,7 ),
       ("Tępiciel", 14, 8),

       ("Szpicel", 15, 6),
       ("Śledczy", 15, 7),
       ("Starszy Śledczy", 15, 8),
       ("Detektyw", 15,10 ),

       ("Biedak", 16, 17),
       ("Żebrak", 16, 2),
       ("Mistrz Żebraków", 16,4 ),
       ("Król Żebraków", 16, 7),

       ("Uczennica Artysty", 17, 6),
       ("Artystka", 17, 8),
       ("Mistrzyni Sztuki", 17,10 ),
       ("Maestro", 17,12 ),

       ("Asystent", 18,7 ),
       ("Doradca", 18,9 ),
       ("Radca", 18,11 ),
       ("Kanclerz", 18, 13),

       ("Nadzorca", 19, 6),
       ("Namiestnik", 19, 8),
       ("Seneszal", 19, 11),
       ("Gubernator", 19, 13),

       ("Herold", 20, 7),
       ("Poseł", 20,9 ),
       ("Dyplomata", 20, 12),
       ("Ambasador", 20, 15),

       ("Posługaczka", 21,6 ),
       ("Służąca", 21, 8),
       ("Pokojowa", 21,10 ),
       ("Ochmistrzyni", 21,11 ),

       ("Dziedzic", 22, 11),
       ("Szlachcic", 22, 13),
       ("Magnat", 22, 15),
       ("Lord", 22, 16),

       ("Informator", 23, 3),
       ("Szpieg", 23, 8),
       ("Agent", 23, 11),
       ("Mistrz Szpiegów", 23, 14),

       ("Szermierz", 24,8 ),
       ("Zwadźca", 24,10 ),
       ("Mistrz Pojedynków", 24,11 ),
       ("Szampierz Sądowy", 24,13 ),

       ("Wyrobnica", 25, 2),
       ("Chłopka", 25, 3),
       ("Gospodyni", 25, 4),
       ("Starsza Wioski", 25, 7),

       ("Poszukiwacz", 26, 2),
       ("Górnik", 26, 4),
       ("Sztygar", 26, 5),
       ("Mistrz Górnictwa", 26, 9),

       ("Uczeń Guślarza", 27, 1),
       ("Guślarz", 27, 2),
       ("Starszy Guślarzy", 27,3 ),
       ("Wiedzący", 27, 5),

       ("Traperka", 28, 2),
       ("Łowczyni", 28,4 ),
       ("Tropicielka", 28, 6),
       ("Nadworna Łowczyni", 28, 8),

       ("Wróżbiarka", 29, 1),
       ("Mistyczka", 29, 2),
       ("Widząca", 29, 3),
       ("Prorokini", 29, 4),

       ("Poborca Podatków", 30,6 ),
       ("Zarządca", 30,10 ),
       ("Włodarz", 30,11 ),
       ("Komornik", 30, 13),

       ("Zbieraczka Ziół", 31, 2),
       ("Zielarka", 31, 4),
       ("Mistrzyni Ziołolecznictwa", 31, 6),
       ("Arcyzielarka", 31,8),

       ("Przewodnik", 32, 3),
       ("Zwiadowca", 32,5 ),
       ("Przepatrywacz", 32, 6),
       ("Odkrywca", 32,10 ),

       ("Gorliwiec", 33, 17),
       ("Biczownik", 33, 17),
       ("Pokutnik", 33,17 ),
       ("Piewca Zagłady", 33,17 ),

       ("Powsinoga", 34, 1),
       ("Domokrążca", 34, 4),
       ("Doświadczony Domokrążca", 34,6 ),
       ("Wędrowny Handlarz", 34, 8),

       ("Muzykantka", 35, 3),
       ("Kuglarka", 35, 5),
       ("Trubadurka", 35, 8),
       ("Przywódczyni Trupy", 35, 11),

       ("Oprawca", 36,6 ),
       ("Łowca Czarownic", 36,8 ),
       ("Inkwizytor", 36, 10),
       ("Generał Łowców Czarownic", 36,11 ),

       ("Pogromczyni Złodziei", 37, 6),
       ("Łowczyni Nagród", 37,8 ),
       ("Doświadczona Łowczyni Nagród", 37,10 ),
       ("Legendarna Łowczyni Nagród", 37,11 ),

       ("Goniec", 38, 3),
       ("Posłaniec", 38, 6),
       ("Kurier", 38, 8),
       ("Kapitan Kurierów", 38,10 ),

       ("Mytniczka", 39,5 ),
       ("Strażniczka Dróg", 39, 7),
       ("Sierżant Strażników Dróg", 39, 9),
       ("Kapitan Strażników Dróg", 39, 11),

       ("Foryś", 40, 6),
       ("Woźnica", 40,7 ),
       ("Mistrz Woźniców", 40,8 ),
       ("Mistrz Szlaków", 40,10 ),

       ("Pomocnik Dokera", 41,3 ),
       ("Doker", 41, 6),
       ("Brygadzista", 41, 8),
       ("Mistrz Dokerów", 41,10 ),

       ("Rybak", 42, 2),
       ("Flisak", 42, 3),
       ("Znawca Rzeki", 42, 5),
       ("Starszy Rzeczny", 42, 7),

       ("Przewodniczka Rzeczna", 43,4 ),
       ("Pilotka Rzeczna", 43, 6),
       ("Starsza Pilotka", 43,8 ),
       ("Legendarna Pilotka", 43,10 ),

       ("Szabrownik", 44, 2),
       ("Pirat Rzeczny", 44, 3),
       ("Łupieżca", 44, 5),
       ("Kapitan Łupieżców", 44, 7),

       ("Szmuglerka Rzeczna", 45, 2),
       ("Przemytniczka", 45,3 ),
       ("Doświadczona Przemytniczka", 45, 5),
       ("Królowa Przemytników", 45,7 ),

       ("Chłopiec Pokładowy", 46, 6),
       ("Przewoźnik", 46, 7),
       ("Sternik", 46, 8),
       ("Kapitan Barki", 46, 10),

       ("Rekrut Rzeczny", 47, 6),
       ("Strażnik Rzeczny", 47, 7),
       ("Żołnierz Okrętowy", 47,9 ),
       ("Mistrz Żołnierzy Okrętowych", 47, 11),

       ("Szczur Lądowy", 48, 6),
       ("Żeglarz", 48, 8),
       ("Bosman", 48, 10),
       ("Kapitan Statku", 48,12 ),

       ("Zbój", 49, 1),
       ("Banita", 49, 2),
       ("Herszt Banitów", 49,4 ),
       ("Król Banitów", 49,7 ),

       ("Szeptucha", 50,1 ),
       ("Czarownica", 50, 2),
       ("Wiedźma", 50, 3),
       ("Arcyczarownica", 50, 5),

       ("Porywaczka Zwłok", 51,2 ),
       ("Hiena Cmentarna", 51, 3),
       ("Rabuś Grobowców", 51,6 ),
       ("Łowczyni Skarbów", 51, 10),

       ("Pośrednik", 52,6 ),
       ("Paser", 52, 7),
       ("Mistrz Paserów", 52, 8),
       ("Broker Informacji", 52, 9),

       ("Naganiacz", 53,1 ),
       ("Rajfur", 53, 3),
       ("Stręczyciel", 53,6 ),
       ("Herszt Rajfurów", 53, 8),

       ("Zakapiorka", 54,3 ),
       ("Rekietierka", 54,5 ),
       ("Głowa Gangu", 54,8 ),
       ("Królowa Podziemia", 54,10 ),

       ("Kanciarz", 55,3 ),
       ("Szarlatan", 55, 5),
       ("Oszust", 55, 7),
       ("Łajdak", 55, 9),

       ("Bandzior", 56, 1),
       ("Złodziej", 56, 3),
       ("Mistrz Złodziejski", 56,5),
       ("Włamywacz", 56, 8),

       ("Pięściarz", 57, 4),
       ("Gladiator", 57,7 ),
       ("Mistrz Areny", 57, 10),
       ("Legenda Areny", 57,12 ),

       ("Nowicjusz Kapłanów Bitewnych", 58,2 ),
       ("Kapłan Bitewny", 58, 7),
       ("Kapłan-sierżant", 58, 8),
       ("Kapłan-kapitan", 58, 9),

       ("Jeździec", 59, 7),
       ("Kawalerzysta", 59,9 ),
       ("Sierżant Kawalerii", 59,11 ),
       ("Oficer Kawalerii", 59, 12),

       ("Stróż", 60, 6),
       ("Ochroniarz", 60,7 ),
       ("Gwardzista", 60,8 ),
       ("Oficer Gwardii", 60, 10),

       ("Tani Drań", 61, 2),
       ("Oprych", 61,6 ),
       ("Płatny Morderca", 61,9 ),
       ("Skrytobójca", 61,11 ),

       ("Giermek", 62,8 ),
       ("Rycerz", 62, 10),
       ("Pierwszy Rycerz", 62,12 ),
       ("Rycerz Wewnętrznego Kręgu", 62,14 ),

       ("Zabójca Trolli", 63,2 ),
       ("Zabójca Olbrzymów", 63,2 ),
       ("Zabójca Smoków", 63, 2),
       ("Zabójca Demonów", 63, 2),

       ("Rekrut", 64,6 ),
       ("Żołnierz", 64, 8),
       ("Sierżant", 64,10 ),
       ("Oficer", 64,11);





