PRAGMA foreign_keys = ON;

CREATE TABLE "wyposarzenie" (
    "id"              INTEGER PRIMARY KEY,
    "nazwa"           TEXT NOT NULL,
    "obciążenie"      INTEGER
);

CREATE TABLE "dostępność" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL,
    "wioska"                         TEXT,
    "miasteczko"                         TEXT,
    "miasto"                         TEXT
);

CREATE TABLE "typ_pancerza" (
    "id"              INTEGER PRIMARY KEY,
    "nazwa"           TEXT NOT NULL
);

CREATE TABLE "lokalizacja_pancerza" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL
);

CREATE TABLE "cechy_pancerz" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL,
    "opis"                         TEXT NOT NULL
);

CREATE TABLE "pancerz" (
    "id"                           INTEGER PRIMARY KEY,
    "nazwa"                        TEXT NOT NULL,
    "cena"                         TEXT,

    "kara"                         TEXT,

    "punkty_pancerza"              INTEGER NOT NULL,

    "dostępność_id"                INTEGER NOT NULL,
    "typ_pancerza_id"              INTEGER NOT NULL,

     "opciążenie"                   INTEGER,


    FOREIGN KEY ("dostępność_id") REFERENCES "dostępność"("id") ON UPDATE CASCADE ON DELETE CASCADE
    FOREIGN KEY ("typ_pancerza_id") REFERENCES "typ_pancerza"("id") ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE "lokalizacja_pancerza_pancerz" (
    "pancerz_id"          INTEGER NOT NULL,
    "lokalizacja_pancerza_id"          INTEGER NOT NULL,


     FOREIGN KEY ("pancerz_id") REFERENCES "pancerz"("id") ON UPDATE CASCADE ON DELETE CASCADE,
     FOREIGN KEY ("lokalizacja_pancerza_id") REFERENCES "lokalizacja_pancerza"("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "cechy_pancerz_pancerz" (
    "pancerz_id"          INTEGER NOT NULL,
    "cecha_id"          INTEGER NOT NULL,


     FOREIGN KEY ("pancerz_id") REFERENCES "pancerz"("id") ON UPDATE CASCADE ON DELETE CASCADE,
     FOREIGN KEY ("cecha_id") REFERENCES "cechy_pancerz"("id") ON UPDATE CASCADE ON DELETE CASCADE
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
    "czy_zacięgowa" INTEGER NOT NULL,

    "opis_specjalny" TEXT
);

CREATE TABLE "cechy_broni" (
    "id"                   INTEGER PRIMARY KEY,
    "nazwa"          TEXT NOT NULL,
    "opis"          TEXT NOT NULL
);

CREATE TABLE "broń" (
    "id"                     INTEGER PRIMARY KEY,
    "nazwa"                  TEXT NOT NULL,

    "cena"                   TEXT,

    "zasięg"                 TEXT NOT NULL,

    "obrażenia"              TEXT NOT NULL,

    "obciążenie"             INTEGER,

    "czy_własna"             INTEGER NOT NULL,

    "typ_broni_id"           INTEGER NOT NULL,
    "dostępność_id"                   INTEGER NOT NULL,

    FOREIGN KEY ("typ_broni_id") REFERENCES "typ_broni"("id") ON UPDATE CASCADE ON DELETE CASCADE,
     FOREIGN KEY ("dostępność_id") REFERENCES "dostępność"("id") ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE "cecha_broni_broń" (
    "broń_id"          INTEGER NOT NULL,
    "cecha_id"          INTEGER NOT NULL,


     FOREIGN KEY ("broń_id") REFERENCES "broń"("id") ON UPDATE CASCADE ON DELETE CASCADE,
     FOREIGN KEY ("cecha_id") REFERENCES "cechy_broni"("id") ON UPDATE CASCADE ON DELETE CASCADE
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

    "punkty_bohatera"                    INTEGER,
    "punkty_determinacji"                INTEGER,

    "punkty_dodatkowe"                   INTEGER,

     "szybkosc"                          INTEGER,
     "xp_aktualny"                       INTEGER,
     "xp_wydany"                         INTEGER,

    "pensy"                              INTEGER,
    "sreblo"                             INTEGER,
    "złota_korona"                       INTEGER,

    "żywotność_aktualna"                 INTEGER,

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

CREATE TABLE "karta_wyposarzenie" (

    "karta_id"                       INTEGER NOT NULL,
    "wyposarzenie_id"                INTEGER NOT NULL,
    "sztuk"                          INTEGER,

     FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY ("wyposarzenie_id") REFERENCES "wyposarzenie"("id") ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE "tradycja" (
    "id"          INTEGER PRIMARY KEY,
    "nazwa"       TEXT NOT NULL,
    "opis"        TEXT NOT NULL
);


INSERT INTO "tradycja"("nazwa","opis")
    VALUES
        ('Tradycja Cieni','OPIS'),
        ('Tradycja Metalu','OPIS'),
        ('Tradycja Śmierci','OPIS'),
        ('Tradycja Ognia','OPIS'),
        ('Tradycja Niebios','OPIS'),
        ('Tradycja Zwierząt','OPIS'),
        ('Tradycja Światla','OPIS');




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

INSERT INTO "zaklęcia"("nazwa","poziom_zaklęcia","zacięg","cel","czas","opis","tradycja_id")
    VALUES

        ('Całun niewidzialności', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','Otulasz cel całunem Ulgu. Staje się on niewidzialny i nie może zostać dostrzeżony przez zwykłych śmiertelników. Automatycznie zmylisz każdego, kto nie posiada Talentu Percepcja Magiczna. Ci, którzy posiadają ten Talent, muszą zdać Wymagający (+0) Test Percepcji, by zauważyć, że ktoś jest w pobliżu, ale nie będą w stanie podać jego dokładnego położenia. Aby to zrobić, muszą rozproszyć zaklęcie. Oczywiście można wyczuć cel innymi zmysłami, a zaklęcie zostanie przerwane, jeśli cel będzie wydawał głośne dźwięki lub kogoś zaatakuje.',1),
        ('Dusiciel', 6 ,'Liczba metrów równa Bonusowi z Siły Woli','1','Liczba Rund równa Bonusowi z Siły Woli','Oplatasz szyje swoich wrogów cienistymi mackami Ulgu. Zakładając, że muszą oni oddychać, otrzymują 1 poziom Zmęczenia, nie mogą mówić, a ponadto stosuje się w ich przypadku zasady Duszenia',1),
        ('Sobowtór', 10 ,'Rzucający','Rzucający','Liczba minut równa Bonusowi z Inteligencji','Tkasz z Ulgu maskę i płaszcz, które nakładasz na siebie, przybierając postać podobną do innego humanoida, którego dobrze znasz (co ustalane jest przez MG). Podobieństwo jest na tyle dobre, że jeśli ktoś nie posiada Talentu Percepcja Magiczna, nie pozna się na sztuczce, choć niektórzy mogą zauważyć, że część manieryzmów nie jest identyczna. Nawet ci, którzy posiadają ten Talent, muszą zdać Trudny (-10) Test Percepcji, a w przypadku niepowodzenia nie przejrzą twojego magicznego przebrania. Jednakże, nawet jeśli uda im się wyczuć fortel, nie będą w stanie spojrzeć „poza” zaklęcie. Aby zobaczyć twoją prawdziwą formę, muszą rozproszyć zaklęcie.',1),
        ('Iluzja', 8 ,'Liczba metrów równa Sile Woli','Obszarowy (liczba metrów równa Bonusowi z Inicjatywy)','Liczba Rund równa Bonusowi z Siły Woli','Tworzysz zawiłą sieć z kosmyków Ulgu, zaciemniając Zasięg dzięki iluzorycznemu obrazowi, który sobie wymyśliłeś. Automatycznie zmylisz każdego, kto nie posiada Talentu Percepcja Magiczna. Nawet ci, którzy posiadają ten Talent, muszą zdać Trudny (-10) Test Percepcji, a w przypadku niepowodzenia nie przejrzą twojej iluzji. Jednakże, nawet jeśli uda im się wyczuć fortel, nie będą w stanie spojrzeć „poza” zaklęcie. W tym celu muszą rozproszyć zaklęcie. Iluzja z definicji jest statyczna. W ramach swojej Akcji, jeśli powiedzie ci się Trudny (-20) Test Splatania Magii, możesz sprawić, by iluzja się w tej Rundzie poruszała.',1),
        ('Krok przez cienie', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','Tworzysz cienisty portal z Wiatru Ulgu, który może nieść cię poprzez Eter. Znikasz w miejscu, w którym stoisz, i natychmiast pojawiasz się w innym, które znajduje się w Zasięgu równym twojej Sile Woli liczonym w metrach. Wszyscy wrogowie, którzy byli Związani walką z tobą lub stają się Związani walką, zostają Zaskoczeni.',1),
        ('Luka w pamięci', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','Czas: Liczba minut równa Sile Woli Wyczarowujesz delikatne nici Ulgu w umyśle swojego celu, sprawiając, że jego pamięć zupełnie znika na czas trwania zaklęcia. Gdy zaklęcie się skończy, cel musi zdać Przeciętny (+20) Test Inteligencji. Jeśli to się nie uda, utrata pamięci będzie trwała (choć można ją usunąć, rozpraszając ten efekt).',1),
        ('Rumak z cieni', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','Czas: Do następnego wschodu słońca Przyzywasz rumaka z cieni. Nienaturalne ciało stworzenia jest czarne jak noc o północy i wydaje się jednocześnie materialne i niematerialne. Stosuje się do niego normalne zasady jeździectwa. Kiedy rumak z cieni nie przebywa na słońcu, zyskuje następujące Cechy Stworzenia: Eteryczny, Magiczny, Nie Czuje Bólu, Skryty, Strach (1), Ochrona (9+) oraz Widzenie w Ciemności. Mimo tego, że rumak nie jest materialny, można na nim normalnie jeździć. Jeźdźcy, którzy posiadają Talent Magia Tajemna (Cienie), zyskują w tym przypadku premię +20 do Testów Jeździectwa. Wszyscy inni otrzymują karę -20 do Testów Jeździectwa. Rumaki z cieni nie znają zmęczenia i nie muszą odpoczywać (choć ich jeźdźcy owszem!). Gdy pierwsze promienie słońca przebiją się nad horyzontem, rumak rozwiewa się w niematerialną mgłę. Jeździec, który w momencie zakończenia zaklęcia lub jego rozproszenia będzie dosiadał rumaka, otrzyma Obrażenia z powodu Upadku',1),
        ('Strefa zamętu', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','Wysyłasz przed siebie wijące się kłęby mgły cieni, które przedzierają się przez powietrze i mylą zmysły. Każdy w zasięgu mgły, kto nie posiada Talentu Magia Tajemna (Cienie), zostaje dotknięty przez zamęt, otrzymując 1 poziom Ogłuszenia, Oślepienia i Zmęczenia, które będą się utrzymywać, dopóki trwa zaklęcie. Każdy objęty czarem musi także zdać Wymagający (+0) Test Percepcji, w przeciwnym wypadku zostanie Powalony. Jeśli zaklęcie zostanie rozproszone podczas trwania, każdy nim objęty musi zdać Łatwy (+40) Test Inicjatywy – jeśli się nie uda, otrzyma 1 poziom Oszołomienia.',1),

        ('Kuźnia Chamonu', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',2),
        ('Lśniąca szata', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',2),
        ('Pancerz z ołowiu', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',2),
        ('Przekształcenie metalu', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',2),
        ('Transmutacja Chamonu', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',2),
        ('Tygiel Chamonu', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',2),
        ('Umagicznienie broni', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',2),
        ('Złoto głupców', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',2),

        ('Błyskawica T Essli', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',3),
        ('Kometa Kasandory', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',3),
        ('Łaska losu', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',3),
        ('Niebiańska tarcza', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',3),
        ('Pod nieszczęśliwą gwiazdą', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',3),
        ('Pierwsze proroctwo Amul', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',3),
        ('Drugie proroctwo Amul', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',3),
        ('Trzecie proroctwo Amul', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',3),

        ('Gorejący miecz Rhuin', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',4),
        ('Ognista korona', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',4),
        ('Oczyszczający płomień', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',4),
        ('Płomienna zasłona', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',4),
        ('Pożoga zagłady U zhula', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',4),
        ('Przypalenie', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',4),
        ('Tarcza Aqshy', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',4),
        ('Żar serc', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',4),

        ('Dotyk śmierci', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',5),
        ('Kosa żniwiarza', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',5),
        ('Krąg śmierci', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',5),
        ('Ostatnie słowa', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',5),
        ('Pieszczota Laniph', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',5),
        ('Posłaniec śmierci', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',5),
        ('Purpurowy całun', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',5),
        ('Wyssanie życia', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',5),

        ('Jasność umysłu', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',6),
        ('Leczące światło', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',6),
        ('Natchnienie', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',6),
        ('Ochrona Phâ', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',6),
        ('Rozbłysk', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',6),
        ('Sieć Amyntoka', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',6),
        ('Wypędzenie', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',6),
        ('Zguba demonów', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',6),

        ('Bursztynowa włócznia', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',7),
        ('Dzika postać Wissana', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',7),
        ('Głos pana', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',7),
        ('Mowa zwierząt', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',7),
        ('Skóra łowcy', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',7),
        ('Szpony furii', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',7),
        ('Uczta kruków', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',7),
        ('Zwierzęca postać', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',7),

        ('Krew ziemi', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',8),
        ('Pole cierniowe', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',8),
        ('Rozkwitanie', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',8),
        ('Regeneracja', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',8),
        ('Skóra z kory', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',8),
        ('Wrota ziemi', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',8),
        ('Ziemia karmicielka', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',8),
        ('Ziemia przewodniczka', 8 ,'Dotyk','1','Liczba Rund równa Bonusowi z Siły Woli','',8);





CREATE TABLE "karta_zaklęcia"(
"karta_id"      INTEGER NOT NULL,
"zaklęcia_id"       INTEGER NOT NULL,

FOREIGN KEY ("karta_id") REFERENCES "karta"("id") ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY ("zaklęcia_id") REFERENCES "zaklęcia"("id") ON UPDATE CASCADE ON DELETE CASCADE
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

   INSERT INTO "poziom" (      "nazwa",       "profesja_id",  "status_id",   "schemat_cech",                           "schemat_umiejetnosci",                                                                                                   "schemat_talentow" )
   VALUES
                        ('Uczennica Aptekarki',   1,              3,            '4,7,8',                               '8,48,61,71,109,114,119',                                                                                                 '2,37,47,131,192'),
                        ('Aptekarka',             1,              6,           '4,7,8,10',                      '3,13,14,20,49,117,8,48,61,71,109,114,119',                                                                                            '1,45,130,205'),
                        ('Farmaceutka',           1,              8,          '4,5,7,8,10',                  '4,6,26,78,3,13,14,20,49,117,8,48,61,71,109,114,119',                                                                                     '24,91,95,115'),
                        ('Mistrzyni Aptekarstwa', 1,              1,          '4,5,7,8,9,10',               '7,25,4,6,26,78,3,13,14,20,49,117,8,48,61,71,109,114,119',                                                                                '189,197,201'),

                        ('Uczeń Czarodzieja',    2,               3,            '1,8,9',                        '2,6,13,21,28,51,87,88,89,90,91,92,93,94,115',                                                                                             '37,75,119,200'),
                        ('Czarodziej',           2,               8,            '1,6,8,9',                  '3,11,14,25,46,2,6,13,21,28,51,87,88,89,90,91,92,93,94,115',                                                                        '76,77,78,79,80,81,82,83,84,85,86,87,134,136,152'),
                        ('Mistrz Magii',         2,               11,           '1,5,6,8,9',            '7,68,121,3,11,14,25,46,2,6,13,21,28,51,87,88,89,90,91,92,93,94,115',                                                                           '44,54,127,190'),
                        ('Arcymag',              2,               12,               '1,5,6,8,9,10', '46,47,48,49,50,51,52,53,109,110,111,112,113,114,116,117,118,119,120,7,68,121,3,11,14,25,46,2,6,13,21,28,51,87,88,89,90,91,92,93,94,115',           '71,145,199,204'),

        ('Student Inżynierii', 3, 4, '2,7,8', '8,10,11,13,37,48,113', '3,37,163,198'),
        ('Inżynier', 3, 7, '2,5,7,8', '9,15,21,26,34,49,8,10,11,13,37,48,113', '45,148,188,192'),
        ('Renomowany Inżynier', 3, 9, '2,4,5,7,8', '4,7,50,78,9,15,21,26,34,49,8,10,11,13,37,48,113', '47,52,91,143'),
        ('Mistrz Inżynierii', 3, 12, '2,4,5,7,8,9', '109,110,111,112,113,114,115,116,117,118,119,120,4,7,50,78,9,15,21,26,34,49,8,10,11,13,37,48,113', '2,107,157,201'),

        ("Kleryk", 4, 2,"4,6,9","48,61,8,71,109,114,119",'1,2,3'),
        ("Kapłan", 4, 6,"4,6,9,10","48,61,8,71,109,114,119",'4,5,6'),
        ("Arcykapłan", 4, 11,"4,6,8,9,10","48,61,8,71,109,114,119",'7,8,9'),
        ("Lektor", 4, 12,"4,5,6,8,9,10","48,61,8,71,109,114,119",'10,11,12'),

       ("Studentka Medycyny", 5, 4,"7,8,9","48,61,8,71,109,114,119",'13,14,15'),
       ("Medyczka", 5, 8,"7,8,9,10","48,61,8,71,109,114,119",'16,17,18'),
       ("Doktor",5 , 10,"5,7,8,9,10","48,61,8,71,109,114,119",'19,20,21'),
       ("Nadworna Medyczka", 5, 11,"5,6,7,8,9,10","48,61,8,71,109,114,119",'22,23,24'),

       ("Nowicjuszka", 6,1 ,"7,8,10","48,61,8,71,109,114,119",'25,26,27'),
       ("Mniszka", 6, 4,"7,8,9,10","48,61,8,71,109,114,119",'28,29,30'),
       ("Przeorysza", 6,7 ,"5,7,8,9,10","48,61,8,71,109,114,119",'31,32,33'),
       ("Matka Przełożona", 6, 10,"4,5,7,8,9,10","48,61,8,71,109,114,119",'31,32,33'),

       ("Studentka Prawa", 7, 4,"5,7,8","48,61,8,71,109,114,119",'31,32,33'),
       ("Prawniczka", 7, 8,"5,7,8,10","48,61,8,71,109,114,119",'31,32,33'),
       ("Obrońca", 7, 11,"5,7,8,9,10","48,61,8,71,109,114,119",'31,32,33'),
       ("Sędzia", 7, 12,"4,5,7,8,9,10","48,61,8,71,109,114,119",'31,32,33'),

       ("Student", 8, 3,"4,8,9","48,61,8,71,109,114,119",'31,32,33'),
       ("Uczony", 8, 7,"4,5,8,9","48,61,8,71,109,114,119",'31,32,33'),
       ("Wykładowca", 8, 10,"4,5,8,9,10","48,61,8,71,109,114,119",'31,32,33'),
       ("Profesor", 8,11 ,"4,5,7,8,9,10","48,61,8,71,109,114,119",'31,32,33'),

       ("Pamflecista", 9,1 ,"2,8,10","48,61,8,71,109,114,119",'31,32,33'),
       ("Agitator", 9, 2,"2,6,8,10","48,61,8,71,109,114,119",'31,32,33'),
       ("Podżegacz", 9, 3,"1,2,6,8,10","48,61,8,71,109,114,119",'31,32,33'),
       ("Demagog", 9, 5,"1,2,5,6,8,10","48,61,8,71,109,114,119",'31,32,33'),

       ("Handlarz", 10, 7,"6,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Kupiec", 10, 10,"6,8,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Mistrz Kupiectwa", 10, 11,"5,6,8,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Patrycjusz", 10,13,"1,5,6,8,9,10" ,"48,61,8,71,109,114,119",'54,53,65'),

       ("Przekupka", 11,6 ,"6,8,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Mieszczka", 11,7 ,"5,6,8,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Rajczyni Miejska", 11,10 ,"5,6,7,8,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Burmistrz", 11, 11,"5,6,7,8,9,10","48,61,8,71,109,114,119",'54,53,65'),

       ("Czeladniczka", 12, 2,"3,4,7","48,61,8,71,109,114,119",'54,53,65'),
       ("Rzemieślniczka", 12,6 ,"3,4,7,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Mistrzyni Rzemiosła", 12,8 ,"3,4,7,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Mistrzyni Cechu", 12,11 ,"3,4,7,8,9,10","48,61,8,71,109,114,119",'54,53,65'),

       ("Rekrut Straży", 13, 3 ,"1,3,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Strażnik", 13, 6 ,"1,3,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Sierżant Straży", 13, 8 ,"1,3,5,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Kapitan Straży", 13, 11 ,"1,3,5,8,9,10","48,61,8,71,109,114,119",'54,53,65'),

       ("Uczeń Szczurołapa", 14,3 ,"1,2,9","48,61,8,71,109,114,119",'54,53,65'),
       ("Szczurołap", 14, 6 ,"1,2,4,9","48,61,8,71,109,114,119",'54,53,65'),
       ("Strażnik Kanałów", 14,7 ,"1,2,4,5,9","48,61,8,71,109,114,119",'54,53,65'),
       ("Tępiciel", 14, 8, "1,2,3,4,5,9","48,61,8,71,109,114,119",'54,53,65'),

       ("Szpicel", 15, 6 ,"5,6,8","48,61,8,71,109,114,119",'54,53,65'),
       ("Śledczy", 15, 7 ,"5,6,8,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Starszy Śledczy", 15, 8 ,"5,6,7,8,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Detektyw", 15,10 ,"5,6,7,8,9,10","48,61,8,71,109,114,119",'54,53,65'),

       ("Biedak", 16, 17 ,"4,6,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Żebrak", 16, 2 ,"4,6,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Mistrz Żebraków", 16,4 ,"1,4,6,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Król Żebraków", 16, 7,"1,4,5,6,9,10","48,61,8,71,109,114,119",'54,53,65') ,

       ("Uczennica Artysty", 17, 6,"3,5,7","48,61,8,71,109,114,119",'54,53,65'),
       ("Artystka", 17, 8,"3,5,7,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Mistrzyni Sztuki", 17,10,"3,5,7,9,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Maestro", 17,12 ,"3,5,7,8,9,10","48,61,8,71,109,114,119",'54,53,65'),

       ("Asystent", 18,7 ,"4,5,6","48,61,8,71,109,114,119",'54,53,65'),
       ("Doradca", 18,9 ,"4,5,6,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Radca", 18,11 ,"4,5,6,8,10","48,61,8,71,109,114,119",'54,53,65'),
       ("Kanclerz", 18, 13 ,"4,5,6,8,9,10","48,61,8,71,109,114,119",'54,53,65'),

       ("Nadzorca", 19, 6,"3,4,9","48,61,8,71,109,114,119",'45,98,46'),
       ("Namiestnik", 19, 8,"1,3,4,9","48,61,8,71,109,114,119",'45,98,46'),
       ("Seneszal", 19, 11 ,"1,3,4,9,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Gubernator", 19, 13 ,"1,3,4,8,9,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Herold", 20, 7 ,"4,6,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Poseł", 20,9 ,"4,6,8,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Dyplomata", 20, 12,"4,5,6,8,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Ambasador", 20, 15,"4,5,6,8,9,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Posługaczka", 21,6 ,"3,4,6","48,61,8,71,109,114,119",'45,98,46'),
       ("Służąca", 21, 8 ,"3,4,5,6","48,61,8,71,109,114,119",'45,98,46'),
       ("Pokojowa", 21,10 ,"3,4,5,6,8","48,61,8,71,109,114,119",'45,98,46'),
       ("Ochmistrzyni", 21,11 ,"3,4,5,6,8,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Dziedzic", 22, 11 ,"1,5,7","48,61,8,71,109,114,119",'45,98,46'),
       ("Szlachcic", 22, 13 ,"1,5,7,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Magnat", 22, 15 ,"1,5,7,8,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Lord", 22, 16 ,"1,5,7,8,9,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Informator", 23, 3 ,"6,9,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Szpieg", 23, 8 ,"1,6,9,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Agent", 23, 11 ,"1,5,6,9,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Mistrz Szpiegów", 23, 14,"1,5,6,8,9,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Szermierz", 24,8 ,"1,5,6","48,61,8,71,109,114,119",'45,98,46'),
       ("Zwadźca", 24,10 ,"1,2,5,6","48,61,8,71,109,114,119",'45,98,46'),
       ("Mistrz Pojedynków", 24,11,"1,2,3,5,6","48,61,8,71,109,114,119",'45,98,46'),
       ("Szampierz Sądowy", 24,13 ,"1,2,3,5,6,9","48,61,8,71,109,114,119",'45,98,46'),

       ("Wyrobnica", 25, 2,"3,4,6","48,61,8,71,109,114,119",'45,98,46'),
       ("Chłopka", 25, 3,"1,3,4,6","48,61,8,71,109,114,119",'45,98,46'),
       ("Gospodyni", 25, 4,"1,3,4,6,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Starsza Wioski", 25, 7,"1,3,4,6,8,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Poszukiwacz", 26, 2,"3,4,9","48,61,8,71,109,114,119",'45,98,46'),
       ("Górnik", 26, 4,"1,3,4,9","48,61,8,71,109,114,119",'45,98,46'),
       ("Sztygar", 26, 5,"1,3,4,5,9","48,61,8,71,109,114,119",'45,98,46'),
       ("Mistrz Górnictwa", 26, 9,"1,3,4,5,9,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Uczeń Guślarza", 27, 1,"4,5,7","48,61,8,71,109,114,119",'45,98,46'),
       ("Guślarz", 27, 2,"4,5,7,8","48,61,8,71,109,114,119",'45,98,46'),
       ("Starszy Guślarzy", 27,3 ,"4,5,7,8,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Wiedzący", 27, 5,"4,5,7,8,9,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Traperka", 28, 2,"3,4,7","48,61,8,71,109,114,119",'45,98,46'),
       ("Łowczyni", 28,4 ,"2,3,4,7","48,61,8,71,109,114,119",'45,98,46'),
       ("Tropicielka", 28, 6,"2,3,4,5,7","48,61,8,71,109,114,119",'45,98,46'),
       ("Nadworna Łowczyni", 28, 8,"2,3,4,5,7,8","48,61,8,71,109,114,119",'45,98,46'),

       ("Wróżbiarka", 29, 1,"5,7,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Mistyczka", 29, 2,"5,7,9,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Widząca", 29, 3,"5,6,7,9,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Prorokini", 29, 4,"5,6,7,8,9,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Poborca Podatków", 30,6 ,"1,5,9","48,61,8,71,109,114,119",'45,98,46'),
       ("Zarządca", 30,10 ,"1,5,9,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Włodarz", 30,11,"1,5,6,9,10","48,61,8,71,109,114,119",'45,98,46'),
       ("Komornik", 30, 13,"1,5,6,8,9,10","48,61,8,71,109,114,119",'45,98,46'),

       ("Zbieraczka Ziół", 31, 2,"4,5,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Zielarka", 31, 4,"4,5,6,7","48,61,8,71,109,114,119",'34,47,78'),
       ("Mistrzyni Ziołolecznictwa", 31, 6,"4,5,6,7,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Arcyzielarka", 31,8,"4,5,6,7,8,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Przewodnik", 32, 3,"4,5,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Zwiadowca", 32,5 ,"2,4,5,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Przepatrywacz", 32, 6,"2,4,5,6,8","48,61,8,71,109,114,119",'34,47,78'),
       ("Odkrywca", 32,10 ,"2,4,5,6,7,8","48,61,8,71,109,114,119",'34,47,78'),

       ("Gorliwiec", 33, 17,"1,3,4","48,61,8,71,109,114,119",'34,47,78'),
       ("Biczownik", 33, 17,"1,3,4,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Pokutnik", 33,17 ,"1,3,4,5,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Piewca Zagłady", 33,17 ,"1,3,4,5,9,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Powsinoga", 34, 1,"4,7,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Domokrążca", 34, 4,"4,7,9,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Doświadczony Domokrążca", 34,6 ,"4,5,7,9,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Wędrowny Handlarz", 34, 8,"4,5,7,8,9,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Muzykantka", 35, 3,"5,6,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Kuglarka", 35, 5,"1,5,6,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Trubadurka", 35, 8,"1,2,5,6,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Przywódczyni Trupy", 35, 11,"1,2,4,5,6,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Oprawca", 36,6,"1,4,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Łowca Czarownic", 36,8 ,"1,2,4,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Inkwizytor", 36, 10,"1,2,4,9,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Generał Łowców Czarownic", 36,11 ,"1,2,4,8,9,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Pogromczyni Złodziei", 37, 6,"1,4,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Łowczyni Nagród", 37,8 ,"1,2,4,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Doświadczona Łowczyni Nagród", 37,10,"1,2,3,4,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Legendarna Łowczyni Nagród", 37,11,"1,2,3,4,6,8","48,61,8,71,109,114,119",'34,47,78'),

       ("Goniec", 38, 3,"4,5,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Posłaniec", 38, 6,"1,4,5,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Kurier", 38, 8,"1,4,5,6,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Kapitan Kurierów", 38,10,"1,4,5,6,9,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Mytniczka", 39,5 ,"2,4,5","48,61,8,71,109,114,119",'34,47,78'),
       ("Strażniczka Dróg", 39, 7,"1,2,4,5","48,61,8,71,109,114,119",'34,47,78'),
       ("Sierżant Strażników Dróg", 39, 9,"1,2,4,5,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Kapitan Strażników Dróg", 39, 11,"1,2,4,5,8,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Foryś", 40, 6,"2,4,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Woźnica", 40,7 ,"2,4,6,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Mistrz Woźniców", 40,8,"1,2,4,6,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Mistrz Szlaków", 40,10 ,"1,2,4,5,6,9","48,61,8,71,109,114,119",'34,47,78'),

       ("Pomocnik Dokera", 41,3,"1,4,5","48,61,8,71,109,114,119",'34,47,78'),
       ("Doker", 41, 6,"1,3,4,5","48,61,8,71,109,114,119",'34,47,78'),
       ("Brygadzista", 41, 8,"1,3,4,5,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Mistrz Dokerów", 41,10 ,"1,3,4,5,8,9","48,61,8,71,109,114,119",'34,47,78'),

       ("Rybak", 42, 2,"4,6,7","48,61,8,71,109,114,119",'34,47,78'),
       ("Flisak", 42, 3,"1,4,6,7","48,61,8,71,109,114,119",'34,47,78'),
       ("Znawca Rzeki", 42, 5,"1,4,5,6,7","48,61,8,71,109,114,119",'34,47,78'),
       ("Starszy Rzeczny", 42, 7,"1,4,5,6,7,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Przewodniczka Rzeczna", 43,4,"1,4,5","48,61,8,71,109,114,119",'34,47,78'),
       ("Pilotka Rzeczna", 43, 6,"1,4,5,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Starsza Pilotka", 43,8 ,"1,4,5,8,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Legendarna Pilotka", 43,10,"1,4,5,8,9,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Szabrownik", 44, 2,"1,3,5","48,61,8,71,109,114,119",'34,47,78'),
       ("Pirat Rzeczny", 44, 3,"1,3,5,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Łupieżca", 44, 5,"1,2,3,5,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Kapitan Łupieżców", 44, 7,"1,2,3,5,9,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Szmuglerka Rzeczna", 45, 2,"6,7,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Przemytniczka", 45,3 ,"5,6,7,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Doświadczona Przemytniczka", 45, 5,"5,6,7,8,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Królowa Przemytników", 45,7,"5,6,7,8,9,10","48,61,8,71,109,114,119" ,'34,47,78'),

       ("Chłopiec Pokładowy", 46, 6,"3,4,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Przewoźnik", 46, 7,"3,4,5,6","48,61,8,71,109,114,119",'34,47,78'),
       ("Sternik", 46, 8,"3,4,5,6,7","48,61,8,71,109,114,119",'34,47,78'),
       ("Kapitan Barki", 46, 10,"3,4,5,6,7,8","48,61,8,71,109,114,119",'34,47,78'),

       ("Rekrut Rzeczny", 47, 6,"2,3,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Strażnik Rzeczny", 47, 7,"1,2,3,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Żołnierz Okrętowy", 47,9 ,"1,2,3,5,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Mistrz Żołnierzy Okrętowych", 47, 11,"1,2,3,5,8,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Szczur Lądowy", 48, 6,"6,7,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Żeglarz", 48, 8,"1,6,7,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Bosman", 48, 10,"1,5,6,7,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Kapitan Statku", 48,12 ,"1,5,6,7,8,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Zbój", 49, 1,"1,3,4","48,61,8,71,109,114,119",'34,47,78'),
       ("Banita", 49, 2,"1,2,3,4","48,61,8,71,109,114,119",'34,47,78'),
       ("Herszt Banitów", 49,4,"1,2,3,4,5","48,61,8,71,109,114,119",'34,47,78'),
       ("Król Banitów", 49,7,"1,2,3,4,5,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Szeptucha", 50,1 ,"1,4,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Czarownica", 50, 2,"1,4,5,9","48,61,8,71,109,114,119",'34,47,78'),
       ("Wiedźma", 50, 3,"1,4,5,9,10","48,61,8,71,109,114,119",'34,47,78'),
       ("Arcyczarownica", 50, 5,"1,4,5,8,9,10","48,61,8,71,109,114,119",'34,47,78'),

       ("Porywaczka Zwłok", 51,2,"3,5,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Hiena Cmentarna", 51, 3,"1,3,5,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Rabuś Grobowców", 51,6 ,"1,3,5,7,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Łowczyni Skarbów", 51, 10,"1,3,5,7,8,9","48,61,8,71,109,114,119",'76,53,21'),

       ("Pośrednik", 52,6 ,"5,6,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Paser", 52, 7,"5,6,7,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Mistrz Paserów", 52, 8,"5,6,7,8,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Broker Informacji", 52, 9,"5,6,7,8,9,10","48,61,8,71,109,114,119",'76,53,21'),

       ("Naganiacz", 53,1 ,"6,7,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Rajfur", 53, 3,"5,6,7,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Stręczyciel", 53,6 ,"5,6,7,9,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Herszt Rajfurów", 53, 8,"5,6,7,8,9,10","48,61,8,71,109,114,119",'76,53,21'),

       ("Zakapiorka", 54,3,"1,3,4" ,"48,61,8,71,109,114,119",'76,53,21'),
       ("Rekietierka", 54,5,"1,3,4,10" ,"48,61,8,71,109,114,119",'76,53,21'),
       ("Głowa Gangu", 54,8 ,"1,3,4,9,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Królowa Podziemia", 54,10,"1,3,4,8,9,10","48,61,8,71,109,114,119",'76,53,21' ),

       ("Kanciarz", 55,3 ,"5,7,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Szarlatan", 55, 5,"5,7,9,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Oszust", 55, 7,"5,6,7,9,10","48,61,8,71,109,114,119",'76,53,21'),
       ("Łajdak", 55, 9,"5,6,7,8,9,10","48,61,8,71,109,114,119",'76,53,21'),

       ("Bandzior", 56, 1,"5,6,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Złodziej", 56, 3,"5,6,7,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Mistrz Złodziejski", 56,5,"3,5,6,7,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Włamywacz", 56, 8,"3,5,6,7,9,10","48,61,8,71,109,114,119",'76,53,21'),

       ("Pięściarz", 57, 4,"1,3,4","48,61,8,71,109,114,119",'76,53,21'),
       ("Gladiator", 57,7,"1,3,4,5" ,"48,61,8,71,109,114,119",'76,53,21'),
       ("Mistrz Areny", 57, 10,"1,3,4,5,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Legenda Areny", 57,12 ,"1,3,4,5,6,10","48,61,8,71,109,114,119",'76,53,21'),

       ("Nowicjusz Kapłanów Bitewnych", 58,2 ,"1,4,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Kapłan Bitewny", 58, 7,"1,3,4,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Kapłan-sierżant", 58, 8,"1,3,4,5,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Kapłan-kapitan", 58, 9,"1,3,4,5,9,10","48,61,8,71,109,114,119",'76,53,21'),

       ("Jeździec", 59, 7,"1,3,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Kawalerzysta", 59,9 ,"1,2,3,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Sierżant Kawalerii", 59,11,"1,2,3,5,6" ,"48,61,8,71,109,114,119",'76,53,21'),
       ("Oficer Kawalerii", 59, 12,"1,2,3,5,6,10","48,61,8,71,109,114,119",'76,53,21'),

       ("Stróż", 60, 6,"1,4,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Ochroniarz", 60,7 ,"1,4,5,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Gwardzista", 60,8 ,"1,3,4,5,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Oficer Gwardii", 60, 10,"1,3,4,5,6,8","48,61,8,71,109,114,119",'76,53,21'),

       ("Tani Drań", 61, 2,"1,4,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Oprych", 61,6 ,"1,4,5,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Płatny Morderca", 61,9,"1,2,4,5,6" ,"48,61,8,71,109,114,119",'76,53,21'),
       ("Skrytobójca", 61,11,"1,2,4,5,6,10" ,"48,61,8,71,109,114,119",'76,53,21'),

       ("Giermek", 62,8,"3,5,6" ,"48,61,8,71,109,114,119",'76,53,21'),
       ("Rycerz", 62, 10,"1,3,5,6","48,61,8,71,109,114,119",'76,53,21'),
       ("Pierwszy Rycerz", 62,12 ,"1,3,5,6,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Rycerz Wewnętrznego Kręgu", 62,14,"1,3,5,6,9,10" ,"48,61,8,71,109,114,119",'76,53,21'),

       ("Zabójca Trolli", 63,2 ,"1,3,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Zabójca Olbrzymów", 63,2 ,"1,3,4,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Zabójca Smoków", 63, 2 ,"1,3,4,6,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Zabójca Demonów", 63, 2 ,"1,3,4,5,6,9","48,61,8,71,109,114,119",'76,53,21'),

       ("Rekrut", 64,6 ,"1,4,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Żołnierz", 64, 8,"1,2,4,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Sierżant", 64,10 ,"1,2,4,5,9","48,61,8,71,109,114,119",'76,53,21'),
       ("Oficer", 64,11 ,"1,2,4,5,9,10","48,61,8,71,109,114,119",'76,53,21');


  INSERT INTO "dostępność"("nazwa","wioska","miasteczko","miasto")
    VALUES
        ('Powszechna',"W sprzedaży!","W sprzedaży!","W sprzedaży!"),
        ('Ograniczona',"30%","60%","90%"),
        ('Rzadka',"15%","30%","45%"),
        ('Egzotyczna',"Nie ma","Nie ma","Nie ma");


    INSERT INTO "cechy_pancerz"("nazwa","opis")
       VALUES
        ('Giętki',"Giętki pancerz możesz nosić pod warstwą innego pancerza (niepo- siadającego tej Zalety). W takim przypadku uzyskujesz korzyści obu pancerzy"),
        ('Nieprzebijalny',"Taki pancerz jest wyjątkowo odporny, co sprawia, że większość ata- ków nie jest w stanie go przebić. Wszystkie Rany Krytyczne wynikłe na skutek nieparzystego wyniku rzutu na trafienie (na przykład 11 lub 33) są ignorowane."),
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
        ('wszystko'),
        ('ramiona'),
        ('korpus'),
        ('nogi'),
        ('głowa'),
        ('Prawa Ręka'),
        ('Lewa Ręka'),
        ('Prawa Noga'),
        ('Lewa Noga');


  INSERT INTO "pancerz"("nazwa","cena","kara","punkty_pancerza","dostępność_id","typ_pancerza_id")
    VALUES
        ('Skórzana kurta','12s',NULL,1,1,1),
        ('Czepiec kolczy','1 ZK','-10 do Percepcji',2,2,1),
        ('Nagolenniki płytowe','10 ZK','-10 do Skradania',2,3,4);


  INSERT INTO "lokalizacja_pancerza_pancerz"("pancerz_id","lokalizacja_pancerza_id")
    VALUES
        (1,2),
        (1,3),
        (2,1),
        (3,5);

  INSERT INTO "cechy_pancerz_pancerz"("pancerz_id","cecha_id")
    VALUES
        (2,1),
        (2,3),
        (3,2),
        (3,4);


   INSERT INTO "talent" ("nazwa", "maksimum","testy","opis","cechy_id" )
       VALUES
       ('Aptekarz','Bonus z Inteligencj','Rzemiosło (Aptekarstwo)','Jesteś świetnym aptekarzem i lepiej od innych wyrabiasz pigułki, maści, smarowidła, olejki, kremy i im podobne. Możesz odwrócić ko- lejność kości nieudanego Testu Rzemiosła (Aptekarstwa), jeśli nowy wynik pozwoli ci odnieść sukces.',8),
       ('Arcydzieło','brak','','Jesteś niekwestionowanym mistrzem w swojej dziedzinie, two- rzącym dzieła tak złożone, że inni mogą je tylko podziwiać, zachwycając się twoim geniuszem. Za każdym razem, gdy wy- kupujesz ten Talent, tworzysz niezwykłe dzieło, wykorzystując Umiejętność Sztuka lub Rzemiosło. Nie ma ono sobie równych, będzie po wieki inspirowało, zadziwiało i budziło zachwyt swoją wyjątkowością. MG określa premie, które ci przysługują z tej ra- cji. Zwykle wpływają one na Testy Ogłady w kontaktach z tymi, którzy podziwiają twoją sztukę. Sprzedaż dzieła powinna dać ci przynajmniej dziesięciokrotną wartość zwykłej ceny, a czasami nawet więcej.',0),
       ('Artylerzysta','Bonus ze Zręcznośc','','Z łatwością przeładowujesz broń prochową. Dodaj PS równe liczbie wykupień tego Talentu do każdego Wydłużonego Testu związanego z przeładowaniem broni prochowej.',0),
       ('Atak Wyprzedzający','Bonus z Inicjatywy','','Twoje błyskawiczne ciosy pozwalają na powalenie przeciwników, zanim oni sami zdążą zaatakować. Kiedy wróg na ciebie Szarżuje, wykonaj udany Wymagający (+0) Test Inicjatywy, by natychmiastwykonać Darmowy Atak poza normalną kolejnością Rundy. Używasz broni, którą trzymasz w wiodącej ręce. Możesz wykonać tyle. Ataków Wyprzedzających w Rundzie, ile razy wykupiłeś ten Talent, ale tylko raz na każdego szarżującego.',0),
       ('Atrakcyjny','Bonus z Ogłady','Charyzma, by wpłynąć na tych, którym się podobasz','OPIS',0),

       ('Bardzo Silny','1','','OPIS',0),
       ('Bardzo Szybki','1','testykiedydziala','OPIS',0),
       ('Berserkerska Szarża','Maksimum','Testy','OPIS',0),
       ('Biczownik','Maksimum','Testy','OPIS',0),
       ('Bitewna Furia','Maksimum','Testy','OPIS',0),
       ('Bitewny Refleks','Maksimum','Testy','OPIS',0),
       ('Błękitna Krew','Maksimum','Testy','OPIS',0),

       ('Błogosławieństwo (Manann)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Morr)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Myrmidia)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Ranald)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Rhya)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Shallya)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Sigmar)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Taal)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Ulryk)','Maksimum','Testy','OPIS',0),
       ('Błogosławieństwo (Verena)','Maksimum','Testy','OPIS',0),

       ('Błyskawiczny Strzał','Maksimum','Testy','OPIS',0),
       ('Błyskotliwość','Maksimum','Testy','OPIS',0),

       ('Celny Strzał','Maksimum','Testy','OPIS',0),
       ('Charyzmatyczny','Maksimum','Testy','OPIS',0),
       ('Chirurgia','Maksimum','Testy','OPIS',0),
       ('Chodu!','Maksimum','Testy','OPIS',0),
       ('Cień','Maksimum','Testy','OPIS',0),
       ('Cios Mierzony','Maksimum','Testy','OPIS',0),
       ('Cios Poniżej Pasa','Maksimum','Testy','OPIS',0),
       ('Czarownica!','Maksimum','Testy','OPIS',0),
       ('Człowiek Guma','Maksimum','Testy','OPIS',0),
       ('Czujny','Maksimum','Testy','OPIS',0),
       ('Czysta Dusza','Maksimum','Testy','OPIS',0),
       ('Czytanie z Ruchu Warg','Maksimum','Testy','OPIS',0),
       ('Czytanie/Pisanie','Maksimum','Testy','OPIS',0),

       ('Defraudant','Maksimum','Testy','OPIS',0),
       ('Dobrze Przygotowany','Maksimum','Testy','OPIS',0),

       ('Doświadczony Wędrowiec (Lasy)','Maksimum','Testy','OPIS',0),
       ('Doświadczony Wędrowiec (Pustyni)','Maksimum','Testy','OPIS',0),
       ('Doświadczony Wędrowiec (Wybrzeża)','Maksimum','Testy','OPIS',0),
       ('Doświadczony Wędrowiec (Inne)','Maksimum','Testy','OPIS',0),

       ('Dwie Bronie','Maksimum','Testy','OPIS',0),

       ('Etykieta (Członkowie Gildii)','Maksimum','Testy','OPIS',0),
       ('Etykieta (Kultyści)','Maksimum','Testy','OPIS',0),
       ('Etykieta (Uczeni)','Maksimum','Testy','OPIS',0),
       ('Etykieta (Szlachta)','Maksimum','Testy','OPIS',0),
       ('Etykieta (Inne)','Maksimum','Testy','OPIS',0),


       ('Finta','Maksimum','Testy','OPIS',0),

       ('Gadanina','Maksimum','Testy','OPIS',0),
       ('Geniusz Arytmetyczny','Maksimum','Testy','OPIS',0),
       ('Gładkie Słówka','Maksimum','Testy','OPIS',0),
       ('Groźny','Maksimum','Testy','OPIS',0),

       ('Hulaka','Maksimum','Testy','OPIS',0),

       ('Inspirujący','Maksimum','Testy','OPIS',0),
       ('Intrygant','Maksimum','Testy','OPIS',0),

       ('Inwokacja (Manann)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Morr)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Myrmidia)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Ranald)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Rhya)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Shallya)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Sigmar)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Taal)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Ulryk)','Maksimum','Testy','OPIS',0),
       ('Inwokacja (Verena)','Maksimum','Testy','OPIS',0),

       ('Krasomówstwo','Maksimum','Testy','OPIS',0),
       ('Krzepki','Maksimum','Testy','OPIS',0),

       ('Łapówkarz','Maksimum','Testy','OPIS',0),

       ('Mag Bitewny','Maksimum','Testy','OPIS',0),

       ('Magia Chaosu (Nurgla)','Maksimum','Testy','OPIS',0),
       ('Magia Chaosu (Slaanesha)','Maksimum','Testy','OPIS',0),
       ('Magia Chaosu (Tzeentcha)','Maksimum','Testy','OPIS',0),

       ('Magia Prosta','Maksimum','Testy','OPIS',0),

       ('Magia Tajemna (Cieni)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Metalu)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Niebios)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Ognia)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Śmierci)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Światłą)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Zwierząt)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Życia)','Maksimum','Testy','OPIS',0),

       ('Magia Tajemna (Guślarstwa)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Czarownictwa)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Demonologii)','Maksimum','Testy','OPIS',0),
       ('Magia Tajemna (Nekromancji)','Maksimum','Testy','OPIS',0),

       ('Majętny','Maksimum','Testy','OPIS',0),
       ('Mały','Maksimum','Testy','OPIS',0),
       ('Mistrz Charakteryzacji','Maksimum','Testy','OPIS',0),

       ('Mistrz Rzemiosła (Rzemiosło)','Maksimum','Testy','OPIS',0),

       ('Mistrz Walki','Maksimum','Testy','OPIS',0),
       ('Mocne Plecy','Maksimum','Testy','OPIS',0),
       ('Morderczy Atak','Maksimum','Testy','OPIS',0),
       ('Mól Książkowy','Maksimum','Testy','OPIS',0),
       ('Mówca','Maksimum','Testy','OPIS',0),
       ('Musztra','Maksimum','Testy','OPIS',0),

       ('Na Cztery Łapy','Maksimum','Testy','OPIS',0),
       ('Naciągacz','Maksimum','Testy','OPIS',0),
       ('Naśladowca','Maksimum','Testy','OPIS',0),
       ('Niegodny Uwagi','Maksimum','Testy','OPIS',0),

       ('Nienawiść (Grupa)','Maksimum','Testy','OPIS',0),

       ('Nieubłagany','Maksimum','Testy','OPIS',0),
       ('Nieugięty','Maksimum','Testy','OPIS',0),
       ('Nieustępliwy','Maksimum','Testy','OPIS',0),

       ('Nieustraszony (typ wroga)','Maksimum','Testy','OPIS',0),

       ('Niewzruszony','Maksimum','Testy','OPIS',0),
       ('Niezwykle Odporny','Maksimum','Testy','OPIS',0),
       ('Nos do Kłopotów','Maksimum','Testy','OPIS',0),
       ('Numizmatyka','Maksimum','Testy','OPIS',0),

       ('Obieżyświat','Maksimum','Testy','OPIS',0),
       ('Oburęczność','Maksimum','Testy','OPIS',0),
       ('Odporność na Magię','Maksimum','Testy','OPIS',0),
       ('Odporność Psychiczna','Maksimum','Testy','OPIS',0),

       ('Odporny na (Wybrane Zagrożenie)','Maksimum','Testy','OPIS',0),

       ('Odwrócenie Szans','Maksimum','Testy','OPIS',0),
       ('Ogłuszenie','Maksimum','Testy','OPIS',0),
       ('Oko Łowcy','Maksimum','Testy','OPIS',0),

       ('Percepcja Magiczna','Maksimum','Testy','OPIS',0),
       ('Pierwsza Pomoc','Maksimum','Testy','OPIS',0),
       ('Pilot','Maksimum','Testy','OPIS',0),
       ('Pilot Rzeczny','Maksimum','Testy','OPIS',0),
       ('Poliglota','Maksimum','Testy','OPIS',0),
       ('Pomocny','Maksimum','Testy','OPIS',0),
       ('Porywająca Gorliwość','Maksimum','Testy','OPIS',0),
       ('Posłuch u Zwierząt','Maksimum','Testy','OPIS',0),
       ('Precyzyjne Inkantowanie','Maksimum','Testy','OPIS',0),
       ('Prosto Między Oczy','Maksimum','Testy','OPIS',0),
       ('Przekonujący','Maksimum','Testy','OPIS',0),
       ('Przestępca','Maksimum','Testy','OPIS',0),
       ('Przyrządzanie Mikstur','Maksimum','Testy','OPIS',0),

       ('Riposta','Maksimum','Testy','OPIS',0),
       ('Rozbrojenie','Maksimum','Testy','OPIS',0),
       ('Rozpoznanie Artefaktu','Maksimum','Testy','OPIS',0),
       ('Rozproszenie Uwagi','Maksimum','Testy','OPIS',0),
       ('Ruchliwe dłonie','Maksimum','Testy','OPIS',0),
       ('Rybak','Maksimum','Testy','OPIS',0),
       ('Sekretna Tożsamość','Maksimum','Testy','OPIS',0),

       ('Silne Nogi','Maksimum','Testy','OPIS',0),
       ('Silny Cios','Maksimum','Testy','OPIS',0),
       ('Skrócenie Dystansu','Maksimum','Testy','OPIS',0),
       ('Słuch Absolutny','Maksimum','Testy','OPIS',0),
       ('Snajper','Maksimum','Testy','OPIS',0),
       ('Sprężynka','Maksimum','Testy','OPIS',0),
       ('Straszny','Maksimum','Testy','OPIS',0),
       ('Strzał Przebijający','Maksimum','Testy','OPIS',0),
       ('Strzał w Dziesiątkę','Maksimum','Testy','OPIS',0),
       ('Strzelec Wyborowy','Maksimum','Testy','OPIS',0),
       ('Szał Bojowy','Maksimum','Testy','OPIS',0),
       ('Szczęście','Maksimum','Testy','OPIS',0),
       ('Szczur Tunelowy','Maksimum','Testy','OPIS',0),
       ('Szósty Zmysł','Maksimum','Testy','OPIS',0),
       ('Szuler','Maksimum','Testy','OPIS',0),
       ('Szuler Kościany','Maksimum','Testy','OPIS',0),
       ('Szybki Refleks','Maksimum','Testy','OPIS',0),
       ('Szybkie Czytanie','Maksimum','Testy','OPIS',0),
       ('Szybkie Przeładowanie','Maksimum','Testy','OPIS',0),
       ('Szybkobiegacz','Maksimum','Testy','OPIS',0),
       ('Szycha','Maksimum','Testy','OPIS',0),

       ('Świetny Pływak','Maksimum','Testy','OPIS',0),
       ('Święta Nienawiść','Maksimum','Testy','OPIS',0),
       ('Święte Wizje','Maksimum','Testy','OPIS',0),

       ('Talent Artystyczny','Maksimum','Testy','OPIS',0),
       ('Tarczownik','Maksimum','Testy','OPIS',0),
       ('Towarzyski','Maksimum','Testy','OPIS',0),
       ('Tragarz','Maksimum','Testy','OPIS',0),
       ('Traper','Maksimum','Testy','OPIS',0),
       ('Twardziel','Maksimum','Testy','OPIS',0),

       ('Ulicznik','Maksimum','Testy','OPIS',0),
       ('Urodzony w Siodle','Maksimum','Testy','OPIS',0),
       ('Urodzony Wojownik','Maksimum','Testy','OPIS',0),
       ('Urodzony Żeglarz','Maksimum','Testy','OPIS',0),

       ('Waleczne Serce','Maksimum','Testy','OPIS',0),
       ('Walka w Ciasnocie','Maksimum','Testy','OPIS',0),
       ('Widzenie w Ciemności','Maksimum','Testy','OPIS',0),
       ('Wieża Pamięci','Maksimum','Testy','OPIS',0),
       ('Wilk Morski','Maksimum','Testy','OPIS',0),
       ('Władcza Postura','Maksimum','Testy','OPIS',0),
       ('Włóczykij','Maksimum','Testy','OPIS',0),
       ('Wodniak','Maksimum','Testy','OPIS',0),
       ('Woltyżerka','Maksimum','Testy','OPIS',0),
       ('Wódz','Maksimum','Testy','OPIS',0),
       ('Wróżba Losu','Maksimum','Testy','OPIS',0),
       ('Wstrzemięźliwy','Maksimum','Testy','OPIS',0),
       ('Wściekły Atak','Maksimum','Testy','OPIS',0),
       ('Wtargnięcie z Włamaniem','Maksimum','Testy','OPIS',0),
       ('Wyborny Wspinacz','Maksimum','Testy','OPIS',0),
       ('Wyczucie Kierunku','Maksimum','Testy','OPIS',0),
       ('Wyczulony Zmysł','Maksimum','Testy','OPIS',0),
       ('Wykrywanie Magii','Maksimum','Testy','OPIS',0),
       ('Wytrwały','Maksimum','Testy','OPIS',0),
       ('Wytwórca','Maksimum','Testy','OPIS',0),

       ('Z Bata','Maksimum','Testy','OPIS',0),
       ('Zabójca','Maksimum','Testy','OPIS',0),
       ('Zbicie Broni','Maksimum','Testy','OPIS',0),
       ('Zejście z Linii','Maksimum','Testy','OPIS',0),
       ('Zimna krew','Maksimum','Testy','OPIS',0),
       ('Złota Rączka','Maksimum','Testy','OPIS',0),
       ('Zmysł Bitewny','Maksimum','Testy','OPIS',0),
       ('Zmysł Magii','Maksimum','Testy','OPIS',0),
       ('Znawca (Wiedza)','Maksimum','Testy','OPIS',0),
       ('Zręczny','Maksimum','Testy','OPIS',0),

       ('Żelazna Szczęka','Maksimum','Testy','OPIS',0),
       ('Żelazna Wola','Maksimum','Testy','OPIS',0),
       ('Żyłka Handlowa','Bonus z Ogłady','Targowanie','Jesteś sprawnym przekupniem i znasz sposoby na dobicie targu. Kiedy używasz Umiejętności Targowanie, obniżasz lub podwyższasz cenę o dodatkowe 10%.',0);


  INSERT INTO "typ_broni"("nazwa","czy_zacięgowa","opis_specjalny")
    VALUES
        ("PODSTAWOWA",0,NULL),
        ("KAWALERYJSKA",0,"Broń kawaleryjska przeznaczona jest do używania z grzbietu wierz-  broni kawaleryjskiej nie jest używana z wierzchowca, liczy się po pro-stu jako Broń Dwuręczna. Jednoręczna broń kawaleryjska normalnie nie jest przeznaczona do używania, gdy nie dosiada się wierzchowca."),
        ("SZERMIERCZA",0,NULL),
        ("BIJATYKA",0,NULL),
        ("KORBACZE",0,"Nieposiadający odpowiedniej Umiejętności bohaterowie dodają do opisu używanego korbacza efekt Wady broni Niebezpieczna. Jak zwykle Zalety broni nie mogą zostać wykorzystane."),
        ("PARUJĄCA",0,"Dowolna broń jednoręczna z Zaletą Parująca może być używana z wykorzystaniem Umiejętności Broń Biała (Parująca). Używając Umiejętności Broń Biała (Parująca), można wykorzystać taki oręż do sparowania ataku przeciwnika bez normalnej kary -20 za użycie broni w drugiej ręce."),
        ("DRZEWCOWA",0,NULL),
        ("DWURĘCZNA",0,NULL),

        ("PROCHOWA",1,NULL),
        ("ŁUKI",1,NULL),
        ("KUSZE",1,NULL),
        ("EKSPERYMENTALNA",1,NULL),
        ("MATERIAŁY WYBUCHOWE",1,NULL),
        ("PROCE",1,NULL),
        ("MIOTANA",1,NULL),
        ("PROCE",1,NULL);

  INSERT INTO "cechy_broni"("nazwa","opis")
    VALUES
        ('Celna','Strzelając z tej broni, łatwo trafić w cel. W takiej sytuacji zyskujesz premię +10 do Testu trafienia.'),
        ('Dekoncentrująca','Ze względu na swoją niebezpieczną naturę broń Dekoncentrująca może zmuszać wroga do cofania się. Zwykle działa podobnie jak bicz. Zamiast zadawać Obrażenia, udany atak bronią Dekoncentrującą może zmusić przeciwnika do cofnięcia się o 1 metr na każdy PS,o który wygrywasz Test Przeciwstawny.'),
        ('Druzgocząca','OPIS'),
        ('Łamacz mieczy','OPIS'),
        ('Nadziewająca','OPIS'),
        ('Niełamliwa','OPIS'),
        ('Ogłuszająca','OPIS'),
        ('Parująca','OPIS'),
        ('Pistolety','OPIS'),
        ('Plącząca','OPIS'),
        ('Precyzyjna','Tą bronią łatwo trafić w cel. Zyskujesz premię +1 PS do każdego udanego Testu podczas ataku tą bronią.'),
        ('Prochowa','OPIS'),
        ('Przebijająca','OPIS'),
        ('Przekłuwająca','OPIS'),
        ('Rąbiąca','OPIS'),
        ('Szybka','OPIS'),
        ('Tarcza (wartość)','OPIS'),
        ('Unieruchamiająca','OPIS'),
        ('Wielostrzał (wartość)','OPIS'),
        ('Odłamkowa (wartość)','OPIS'),

        ('Ciężka','Używanie tej broni jest męczące lub trudno ją opanować. Możesz korzystać z Zalet broni Druzgocząca i Przebijająca tylko w Turze, w której wykonujesz Szarżę.'),
        ('Niebezpieczna','OPIS'),
        ('Nieprecyzyjna','OPIS'),
        ('Powolna','OPIS'),
        ('Przeładowanie (wartość)','OPIS'),
        ('Tępa','OPIS');

        INSERT INTO "broń"("nazwa","cena","zasięg","obrażenia","czy_własna","typ_broni_id","dostępność_id")
         VALUES

                ('Sztylet','16 s','Bardzo krótka','+BS+2',0,1,1),
                ('Floret','18 s 2 p','Kontaktowa','+BS+3',0,1,2),
                ('Korbacz','1 ZK 2 s 6 p','Bardzo długa','+BS+1',1,3,2),
                ('Nadziak','darmo','Długa','+BS+2',1,5,3),
                ('Włócznia','3 ZK','Średnia','+BS+2',1,6,3),
                ('Lewak','33 ZK','Bardzo krótka','+BS+5',1,7,4);


         INSERT INTO "cecha_broni_broń"("broń_id","cecha_id")
            VALUES
            (1,1),
            (1,2),
            (1,5),
            (2,1),
            (2,2),
            (2,6),
            (3,3),
            (3,4),
            (3,7),
            (4,1),
            (4,6),
            (4,2),
            (5,2),
            (5,5),
            (5,9),
            (6,1),
            (6,3),
            (6,7);



  INSERT INTO "wyposarzenie"("nazwa")
    VALUES
        ('Lina'),
        ('Plecak'),
        ('Namiot'),
        ('Kartka papieru'),
        ('Czapka');