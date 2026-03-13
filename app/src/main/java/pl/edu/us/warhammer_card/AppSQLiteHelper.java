package pl.edu.us.warhammer_card;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.CechaBroni;
import pl.edu.us.warhammer_card.table.CechaPancerza;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Dostepnosc;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Klasa;
import pl.edu.us.warhammer_card.table.LokalizacjaPancerza;
import pl.edu.us.warhammer_card.table.Pancerz;
import pl.edu.us.warhammer_card.table.PoziomProfesja;
import pl.edu.us.warhammer_card.table.Profesja;
import pl.edu.us.warhammer_card.table.Rasa;
import pl.edu.us.warhammer_card.table.Status;
import pl.edu.us.warhammer_card.table.Talent;
import pl.edu.us.warhammer_card.table.TypBroni;
import pl.edu.us.warhammer_card.table.TypPancerza;
import pl.edu.us.warhammer_card.table.Umiejetnosci;
import pl.edu.us.warhammer_card.table.Wyposarzenia;
import pl.edu.us.warhammer_card.table.Zaklecia;

public class AppSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "data";
    private static final int DATABASE_VERSION = 1;
    Context context;

    public AppSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            InputStream inputStream = context.getAssets().open("db_init.sql");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
            String sqlStatements = stringBuilder.toString();
            String[] queries = sqlStatements.split(";");

            for (String query : queries) {
                if (query.trim().length() > 0) {
                    db.execSQL(query);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            InputStream inputStream = context.getAssets().open("update.sql");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
            String sqlStatements = stringBuilder.toString();
            String[] queries = sqlStatements.split(";");

            for (String query : queries) {
                if (query.trim().length() > 0) {
                    db.execSQL(query);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Karta getKartaById(SQLiteDatabase db, int kartaId) {

        String[] selectionArgs = {String.valueOf(kartaId)};

        String query = "SELECT * " +
                "FROM karta " +
                "WHERE id = ?";
        Cursor cursor = db.rawQuery(query, selectionArgs);
        Karta karta = new Karta();

        if (cursor.moveToFirst()){

            karta.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

            karta.setImie(cursor.getString(cursor.getColumnIndexOrThrow("imie")));
            karta.setWiek(cursor.getString(cursor.getColumnIndexOrThrow("wiek")));
            karta.setWzrost(cursor.getString(cursor.getColumnIndexOrThrow("wzrost")));
            karta.setWlosy(cursor.getString(cursor.getColumnIndexOrThrow("wlosy")));
            karta.setOczy(cursor.getString(cursor.getColumnIndexOrThrow("oczy")));

            karta.setPunktyPrzeznaczenia(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_przeznaczenia")));
            karta.setPunktySzczescia(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_szczescia")));
            karta.setPunktyBohatera(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_bohatera")));
            karta.setPunktyDeterminacji(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_determinacji")));
            karta.setPunktyDodatkowe(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_dodatkowe")));
            karta.setSzybkosc(cursor.getInt(cursor.getColumnIndexOrThrow("szybkosc")));
            karta.setXpAktualny(cursor.getInt(cursor.getColumnIndexOrThrow("xp_aktualny")));
            karta.setXpWydany(cursor.getInt(cursor.getColumnIndexOrThrow("xp_wydany")));

            karta.setPensy(cursor.getInt(cursor.getColumnIndexOrThrow("pensy")));
            karta.setSreblo(cursor.getInt(cursor.getColumnIndexOrThrow("sreblo")));
            karta.setZlotaKorona(cursor.getInt(cursor.getColumnIndexOrThrow("złota_korona")));

            karta.setPsyhologia(cursor.getString(cursor.getColumnIndexOrThrow("psyhologia")));
            karta.setMotywacja(cursor.getString(cursor.getColumnIndexOrThrow("motywacja")));
            karta.setZepsucieIMutacje(cursor.getString(cursor.getColumnIndexOrThrow("zepsucie_i_mutacje")));
            karta.setPunktyGrzechu(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_grzechu")));

            karta.setZywotnoscAktualna(cursor.getInt(cursor.getColumnIndexOrThrow("żywotność_aktualna")));

            karta.setAmbicjaKrotkoterminowa(cursor.getString(cursor.getColumnIndexOrThrow("ambicja_krótkoterminowa")));
            karta.setAmbicjaDlugoterminowa(cursor.getString(cursor.getColumnIndexOrThrow("ambicja_długoterminowa")));

            karta.setNazwaDruzyny(cursor.getString(cursor.getColumnIndexOrThrow("nazwa_drużyny")));
            karta.setAmbicjaDruzynowaKrotkoterm(cursor.getString(cursor.getColumnIndexOrThrow("ambicja_drużynowa_krótkoterm")));
            karta.setAmbicjaDruzynowaDlugoterm(cursor.getString(cursor.getColumnIndexOrThrow("ambicja_drużynowa_długoterm")));
            karta.setCzlonkowieDruzyny(cursor.getString(cursor.getColumnIndexOrThrow("członkowie_drużyny")));

            karta.setKampaniaId(cursor.getInt(cursor.getColumnIndexOrThrow("kampania_id")));
            karta.setRasaId(cursor.getInt(cursor.getColumnIndexOrThrow("rasa_id")));
            karta.setProfesjaId(cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id")));
            karta.setPoziomProfesjiId(cursor.getInt(cursor.getColumnIndexOrThrow("poziom_id")));


            List<Cechy> cechyList = new ArrayList<>();

            String[] projection = {"nazwa"};
            String[] colums = {"*"};
            String sortOrder = "cechy_id ASC";
            String[] selectionArgs2 = {String.valueOf(kartaId)};

            Cursor cursor2 = db.query("karta_cecha", colums, "karta_id = ?", selectionArgs2, null, null, sortOrder);
            while (cursor2.moveToNext()) {
                Cechy cecha = new Cechy();

                cecha.setId(cursor2.getInt(cursor2.getColumnIndexOrThrow("cechy_id")));
                cecha.setWartPo(cursor2.getInt(cursor2.getColumnIndexOrThrow("wartość_pociątkowa")));
                cecha.setRozw(cursor2.getInt(cursor2.getColumnIndexOrThrow("rozwój")));
                cecha.setLvlUp(cursor2.getInt(cursor2.getColumnIndexOrThrow("lvl_up")));


                String[] selectionArgs3 = {String.valueOf(cecha.getId())};
                Cursor cursor3 = db.query("cechy", colums, "id = ?", selectionArgs3, null, null, null);

                if (cursor3.moveToFirst()) {
                    cecha.setNazwaKrotka(cursor3.getString(cursor3.getColumnIndexOrThrow("nazwa_krótka")));

                }
                cechyList.add(cecha);
                cursor3.close();
            }
            karta.setCechyList(cechyList);

            cursor2.close();
            cursor.close();

            List<Talent> listTalent = new ArrayList<>();

            String[] selectionArgs4 = {String.valueOf(kartaId)};

            String query4 = "SELECT karta_talent.karta_id,talent.id, talent.nazwa, talent.maksimum, talent.testy, talent.opis, talent.cechy_id,karta_talent.poziom " +
                    "FROM karta_talent " +
                    "JOIN talent ON karta_talent.talent_id = talent.id " +
                    "WHERE karta_talent.karta_id = ? ";

            Cursor cursor4 = db.rawQuery(query4, selectionArgs4);

            while (cursor4.moveToNext()) {
                Talent talent = new Talent();

                talent.setId(cursor4.getInt(cursor4.getColumnIndexOrThrow("id")));

                talent.setNazwa(cursor4.getString(cursor4.getColumnIndexOrThrow("nazwa")));
                talent.setMaksimum(cursor4.getString(cursor4.getColumnIndexOrThrow("maksimum")));
                talent.setTesty(cursor4.getString(cursor4.getColumnIndexOrThrow("testy")));
                talent.setOpis(cursor4.getString(cursor4.getColumnIndexOrThrow("opis")));

                talent.setPoziom(cursor4.getInt(cursor4.getColumnIndexOrThrow("poziom")));

                talent.setIdCecha(cursor4.getInt(cursor4.getColumnIndexOrThrow("cechy_id")));

                listTalent.add(talent);

            }
            cursor4.close();
            karta.setTalentList(listTalent);


            karta.setRasa(getRasaById(db, kartaId));

            karta.setProfesja(getProfesja(db, karta.getProfesjaId()));

            karta.setPoziomProfesja(getPoziomProfesja(db, karta.getPoziomProfesjiId()));

            karta.getPoziomProfesja().setProfesja(karta.getProfesja());

            karta.setUmiejetnosciList(getAllPodstawoweUmiejetnosciForKarta(db, kartaId));
            karta.setUmiejetnosciZaawansowaneList(getAllZaawansowaneUmiejetnosciForKarta(db, kartaId));

            karta.setPancerzList(getPancerzFromKarta(db, kartaId));

            karta.setBronList(getBronListForKarta(db, kartaId));

            karta.setWyposarzeniaList(getWyposazeniaListFromKarta(db, kartaId));

            karta.setZakleciaList(getZakleciaForKarta(db, kartaId));


        }
        return karta;
    }

    public List<CechaPancerza> getChechyPancerza(SQLiteDatabase db) {
        List<CechaPancerza> list = new ArrayList<>();
        String query = "SELECT  * " +
                "FROM cechy_pancerz  ";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            CechaPancerza cechaPancerza = new CechaPancerza();
            cechaPancerza.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            cechaPancerza.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            cechaPancerza.setOpis(cursor.getString(cursor.getColumnIndexOrThrow("opis")));

            list.add(cechaPancerza);
        }

        return list;
    }
    public List<CechaBroni> getCechyBroni(SQLiteDatabase db) {
        List<CechaBroni> list = new ArrayList<>();
        String query = "SELECT  * " +
                "FROM cechy_broni  ";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            CechaBroni cechaBroni = new CechaBroni();
            cechaBroni.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            cechaBroni.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            cechaBroni.setOpis(cursor.getString(cursor.getColumnIndexOrThrow("opis")));

            list.add(cechaBroni);
        }

        return list;
    }

    public List<Zaklecia> getZakleciaForKarta(SQLiteDatabase db, int kartaId) {
        List<Zaklecia> list = new ArrayList<>();

        String query = "SELECT  z.* " +
                "FROM zaklęcia z " +
                "JOIN karta_zaklęcia kz ON z.id = kz.zaklęcia_id " +
                "WHERE kz.karta_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kartaId)});

        while (cursor.moveToNext()) {
            Zaklecia zaklecia = new Zaklecia();

            zaklecia.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            zaklecia.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            zaklecia.setPoziomZaklecie(cursor.getInt(cursor.getColumnIndexOrThrow("poziom_zaklęcia")));
            zaklecia.setZacieg(cursor.getString(cursor.getColumnIndexOrThrow("zacięg")));
            zaklecia.setCel(cursor.getString(cursor.getColumnIndexOrThrow("cel")));
            zaklecia.setCzas(cursor.getString(cursor.getColumnIndexOrThrow("czas")));
            zaklecia.setOpis(cursor.getString(cursor.getColumnIndexOrThrow("opis")));
            zaklecia.setTradycjaId(cursor.getInt(cursor.getColumnIndexOrThrow("tradycja_id")));

            list.add(zaklecia);
        }
        cursor.close();

        return list;
    }

    public List<Bron> getBronListForKarta(SQLiteDatabase db, int kartaId) {
        List<Bron> list = new ArrayList<>();

        String query = "SELECT b.* " +
                "FROM broń b " +
                "JOIN karta_broń kb ON b.id = kb.broń_id " +
                "WHERE kb.karta_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kartaId)});

        while (cursor.moveToNext()) {
            Bron bron = new Bron();

            bron.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            bron.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            bron.setCena(cursor.getString(cursor.getColumnIndexOrThrow("cena")));
            bron.setZasieg(cursor.getString(cursor.getColumnIndexOrThrow("zasięg")));
            bron.setObrazenia(cursor.getString(cursor.getColumnIndexOrThrow("obrażenia")));
            bron.setCzyWlasna(cursor.getInt(cursor.getColumnIndexOrThrow("czy_własna")));
            bron.setTypBroniId(cursor.getInt(cursor.getColumnIndexOrThrow("typ_broni_id")));
            bron.setDostepnoscBroniID(cursor.getInt(cursor.getColumnIndexOrThrow("dostępność_id")));

            // Pobieranie listy cech broni
            List<CechaBroni> cechyList = new ArrayList<>();
            String query2 = "SELECT cb.nazwa, cb.opis FROM cecha_broni_broń cbb " +
                    "JOIN cechy_broni cb ON cbb.cecha_id = cb.id " +
                    "WHERE cbb.broń_id = ?";
            Cursor cursor2 = db.rawQuery(query2, new String[]{String.valueOf(bron.getId())});

            while (cursor2.moveToNext()) {
                CechaBroni cecha = new CechaBroni();
                cecha.setNazwa(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa")));
                cecha.setOpis(cursor2.getString(cursor2.getColumnIndexOrThrow("opis")));
                cechyList.add(cecha);
            }
            cursor2.close();
            bron.setListaCech(cechyList);

            // Pobieranie danych o typie broni
            TypBroni typBroni = new TypBroni();
            String query3 = "SELECT * FROM typ_broni WHERE id = ?";
            Cursor cursor3 = db.rawQuery(query3, new String[]{String.valueOf(bron.getTypBroniId())});

            if (cursor3.moveToNext()) {
                typBroni.setId(cursor3.getInt(cursor3.getColumnIndexOrThrow("id")));
                typBroni.setNazwa(cursor3.getString(cursor3.getColumnIndexOrThrow("nazwa")));
                typBroni.setCzyZaciegowa(cursor3.getInt(cursor3.getColumnIndexOrThrow("czy_zacięgowa")));
                typBroni.setOpisSpecjalny(cursor3.getString(cursor3.getColumnIndexOrThrow("opis_specjalny")));
                bron.setTypBroni(typBroni);
            }
            cursor3.close();

            // Pobieranie dostępności
            String query4 = "SELECT nazwa FROM dostępność WHERE id = ?";
            Cursor cursor4 = db.rawQuery(query4, new String[]{String.valueOf(bron.getDostepnoscBroniID())});

            if (cursor4.moveToNext()) {
                bron.setDostemnoscText(cursor4.getString(cursor4.getColumnIndexOrThrow("nazwa")));
            }
            cursor4.close();

            list.add(bron);
        }
        cursor.close();

        return list;
    }

    public List<Wyposarzenia> getWyposazeniaListFromKarta(SQLiteDatabase db, int kartaId) {
        List<Wyposarzenia> list = new ArrayList<>();

        String query = "SELECT id ,nazwa, obciążenie, sztuk " +
                "FROM wyposarzenie w " +
                "JOIN karta_wyposarzenie kw ON w.id = kw.wyposarzenie_id " +
                "WHERE kw.karta_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kartaId)});

        while (cursor.moveToNext()) {
            Wyposarzenia wyposarzenia = new Wyposarzenia();
            wyposarzenia.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            wyposarzenia.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            wyposarzenia.setOciazenie(cursor.getInt(cursor.getColumnIndexOrThrow("obciążenie")));
            wyposarzenia.setSztuk(cursor.getInt(cursor.getColumnIndexOrThrow("sztuk")));
            list.add(wyposarzenia);
        }
        cursor.close();

        return list;
    }

    public List<Pancerz> getPancerzFromKarta(SQLiteDatabase db, int kartaId) {
        List<Pancerz> list = new ArrayList<>();

        String query = "SELECT  p.* , kp.* " +
                "FROM pancerz p " +
                "JOIN karta_pancerz kp ON p.id = kp.pancerz_id " +
                "WHERE kp.karta_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(kartaId)});

        while (cursor.moveToNext()) {
            Pancerz pancerz = new Pancerz();

            pancerz.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

            pancerz.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            pancerz.setCena(cursor.getString(cursor.getColumnIndexOrThrow("cena")));
            pancerz.setKara(cursor.getString(cursor.getColumnIndexOrThrow("kara")));
            pancerz.setPunktyPancerza(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_pancerza")));

            pancerz.setCzyZalozone(cursor.getInt(cursor.getColumnIndexOrThrow("czy_zalożony")));

            pancerz.setDostepnoscId(cursor.getInt(cursor.getColumnIndexOrThrow("dostępność_id")));
            pancerz.setTypPancerzaId(cursor.getInt(cursor.getColumnIndexOrThrow("typ_pancerza_id")));

            List<CechaPancerza> cechaPancerzaList = new ArrayList<>();

            String query2 = "SELECT cp.nazwa, cp.opis " +
                    "FROM cechy_pancerz_pancerz cpp " +
                    "JOIN cechy_pancerz cp ON cpp.cecha_id = cp.id " +
                    "WHERE cpp.pancerz_id = ?";
            Cursor cursor2 = db.rawQuery(query2, new String[]{String.valueOf(pancerz.getId())});

            while (cursor2.moveToNext()) {
                CechaPancerza cecha = new CechaPancerza();
                cecha.setNazwa(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa")));
                cecha.setOpis(cursor2.getString(cursor2.getColumnIndexOrThrow("opis")));

                cechaPancerzaList.add(cecha);
            }
            cursor2.close();

            pancerz.setCechaPancerzaList(cechaPancerzaList);

            List<LokalizacjaPancerza> lokalizacjaPancerzaList = new ArrayList<>();

            String query3 = "SELECT lp.nazwa, lp.id " +
                    "FROM lokalizacja_pancerza_pancerz lpp " +
                    "JOIN lokalizacja_pancerza lp ON lpp.lokalizacja_pancerza_id = lp.id " +
                    "WHERE lpp.pancerz_id = ?";

            Cursor cursor3 = db.rawQuery(query3, new String[]{String.valueOf(pancerz.getId())});

            while (cursor3.moveToNext()) {
                LokalizacjaPancerza lokalizacjaPancerza = new LokalizacjaPancerza();
                lokalizacjaPancerza.setId(cursor3.getInt(cursor3.getColumnIndexOrThrow("id")));
                lokalizacjaPancerza.setNazwa(cursor3.getString(cursor3.getColumnIndexOrThrow("nazwa")));

                lokalizacjaPancerzaList.add(lokalizacjaPancerza);
            }
            cursor3.close();

            pancerz.setLokalizacjaPancerzaList(lokalizacjaPancerzaList);

            list.add(pancerz);

        }
        cursor.close();


        return list;
    }

    Cechy getCechaByid(SQLiteDatabase db, int idCecha, int kartaId) {

        String[] projection = {"nazwa"};
        String[] colums = {"*"};
        String sortOrder = "cechy_id ASC";
        String[] selectionArgs = {String.valueOf(idCecha), String.valueOf(kartaId)};
        Cursor cursor = db.query("karta_cecha", colums, "cechy_id = ? AND karta_id = ?", selectionArgs, null, null, sortOrder);

        Cechy cecha = new Cechy();

        if (cursor.moveToNext()) {

            cecha.setId(cursor.getInt(cursor.getColumnIndexOrThrow("cechy_id")));
            cecha.setWartPo(cursor.getInt(cursor.getColumnIndexOrThrow("wartość_pociątkowa")));
            cecha.setRozw(cursor.getInt(cursor.getColumnIndexOrThrow("rozwój")));


            String[] selectionArgs2 = {String.valueOf(cecha.getId())};
            Cursor cursor2 = db.query("cechy", colums, "id = ?", selectionArgs2, null, null, null);
            String name = "";

            if (cursor2.moveToFirst()) {
                cecha.setNazwaKrotka(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa_krótka")));

            }


            Log.d("cechy", String.valueOf(cecha.getId() + " " + cecha.getWartPo() + " " + cecha.getRozw() + name));
        }
        cursor.close();

        return cecha;
    }

    PoziomProfesja getPoziomProfesja(SQLiteDatabase db, int poziomProfesjaId) {
        PoziomProfesja poziomProfesja = new PoziomProfesja();

        String[] colums = {"*"};
        String[] selectionArgs = {String.valueOf(poziomProfesjaId)};


        Cursor cursor = db.query("poziom", colums, "id = ?", selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            poziomProfesja.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            poziomProfesja.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));

            poziomProfesja.setSchematCech(cursor.getString(cursor.getColumnIndexOrThrow("schemat_cech")));
            poziomProfesja.setSchematCech(cursor.getString(cursor.getColumnIndexOrThrow("schemat_umiejetnosci")));

            //TODO Odkomentować jak będzie dodany schemat dla talentow
             poziomProfesja.setSchematCech(cursor.getString(cursor.getColumnIndexOrThrow("schemat_talentow")));

            poziomProfesja.setStatusId(cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id")));
            poziomProfesja.setStatusId(cursor.getInt(cursor.getColumnIndexOrThrow("status_id")));

        }
        cursor.close();

        Status status = new Status();

        String query = "SELECT  * " +
                "FROM status " +
                "WHERE id = ? ";

        Cursor cursor2 = db.rawQuery(query, new String[]{String.valueOf(poziomProfesja.getStatusId())});

        if (cursor2.moveToNext()) {
            status.setId(cursor2.getInt(cursor2.getColumnIndexOrThrow("id")));
            status.setNazwa(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa")));
        }

        poziomProfesja.setStatus(status);

        return poziomProfesja;
    }

    String getSciezkaProfesji(SQLiteDatabase db, int profesjaId) {
        String sciezka = "";

        List<PoziomProfesja> list = getListPoziomsForProfesja(db, profesjaId);
        int i = 1;
        for (PoziomProfesja poziom : list) {
            if (i != list.size()) {
                sciezka = sciezka + poziom.getNazwa() + "->";
            } else {
                sciezka = sciezka + poziom.getNazwa();
            }
            i++;

        }
        return sciezka;
    }

    List<PoziomProfesja> getListPoziomsForProfesja(SQLiteDatabase db, int profesjaID) {

        String[] colums = {"id", "nazwa", "profesja_id", "status_id"};

        String[] selectionArgs = {String.valueOf(profesjaID)};

        Cursor cursor = db.query("poziom", colums, "profesja_id = ?", selectionArgs, null, null, null);


        List<PoziomProfesja> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            PoziomProfesja poziom = new PoziomProfesja();
            poziom.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            poziom.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            poziom.setProfesjaId(cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id")));
            poziom.setStatusId(cursor.getInt(cursor.getColumnIndexOrThrow("status_id")));

            list.add(poziom);
        }

        cursor.close();

        return list;
    }

    public Profesja getProfesja(SQLiteDatabase db, int kartaId) {

        Profesja profesja = new Profesja();

        String[] colums = {"*"};
        String[] selectionArgs = {String.valueOf(kartaId)};

        Cursor cursor = db.query("profesja", colums, "id = ?", selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            profesja.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            profesja.setNazawa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            profesja.setKlasaId(cursor.getInt(cursor.getColumnIndexOrThrow("klasa_id")));
        }
        if (profesja.getNazawa() == null) {
            profesja.setNazawa("");
        }
        cursor.close();

        Klasa klasa = new Klasa();
        String query = "SELECT  * " +
                "FROM klasa " +
                "WHERE id = ?";

        Cursor cursor2 = db.rawQuery(query, new String[]{String.valueOf(profesja.getKlasaId())});

        if (cursor2.moveToNext()) {
            klasa.setId(cursor2.getInt(cursor2.getColumnIndexOrThrow("id")));
            klasa.setNazwa(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa")));
        }
        cursor2.close();

        profesja.setKlasa(klasa);


        profesja.setSciezkaProfesji(getSciezkaProfesji(db, profesja.getId()));

        return profesja;
    }

    public Rasa getRasaById(SQLiteDatabase db, int id) {
        Rasa rasa = new Rasa();
        String[] colums = {"*"};

        String[] selectionArgs = {String.valueOf(id)};

        String query = "SELECT id, name " +
                "FROM rasa " +
                "WHERE id=?";

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToNext()) {
            rasa.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            rasa.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        }
        cursor.close();

        return rasa;
    }

    public void updateKarta(SQLiteDatabase db, Karta karta) {
        ContentValues values = new ContentValues();
        values.put("ambicja_krótkoterminowa", karta.getAmbicjaKrotkoterminowa());
        values.put("ambicja_długoterminowa", karta.getAmbicjaDlugoterminowa());

        values.put("nazwa_drużyny", karta.getNazwaDruzyny());
        values.put("ambicja_drużynowa_krótkoterm", karta.getAmbicjaDruzynowaKrotkoterm());
        values.put("ambicja_drużynowa_długoterm", karta.getAmbicjaDruzynowaDlugoterm());
        values.put("członkowie_drużyny", karta.getCzlonkowieDruzyny());

        values.put("punkty_przeznaczenia", karta.getPunktyPrzeznaczenia());
        values.put("punkty_szczescia", karta.getPunktySzczescia());
        values.put("punkty_bohatera", karta.getPunktyBohatera());
        values.put("punkty_determinacji", karta.getPunktyDeterminacji());
        values.put("punkty_dodatkowe", karta.getPunktyDodatkowe());
        values.put("szybkosc", karta.getSzybkosc());
        values.put("xp_aktualny", karta.getXpAktualny());
        values.put("xp_wydany", karta.getXpWydany());
        values.put("motywacja", karta.getMotywacja());

        values.put("zepsucie_i_mutacje", karta.getZepsucieIMutacje());
        values.put("psyhologia", karta.getPsyhologia());

        values.put("złota_korona", karta.getZlotaKorona());
        values.put("sreblo", karta.getSreblo());
        values.put("pensy", karta.getPensy());

        values.put("żywotność_aktualna", karta.getZywotnoscAktualna());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(karta.getId())};

        db.update("karta", values, whereClause, whereArgs);
    }

    public List<Pancerz> getAllPancerzList(SQLiteDatabase db){
        List<Pancerz> list = new ArrayList<>();

        String query = "SELECT  * " +
                "FROM pancerz " ;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Pancerz pancerz = new Pancerz();

            pancerz.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

            pancerz.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            pancerz.setCena(cursor.getString(cursor.getColumnIndexOrThrow("cena")));
            pancerz.setKara(cursor.getString(cursor.getColumnIndexOrThrow("kara")));
            pancerz.setPunktyPancerza(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_pancerza")));

            pancerz.setDostepnoscId(cursor.getInt(cursor.getColumnIndexOrThrow("dostępność_id")));
            pancerz.setTypPancerzaId(cursor.getInt(cursor.getColumnIndexOrThrow("typ_pancerza_id")));

            List<CechaPancerza> cechaPancerzaList = new ArrayList<>();

            String query2 = "SELECT cp.nazwa, cp.opis " +
                    "FROM cechy_pancerz_pancerz cpp " +
                    "JOIN cechy_pancerz cp ON cpp.cecha_id = cp.id " +
                    "WHERE cpp.pancerz_id = ?";
            Cursor cursor2 = db.rawQuery(query2, new String[]{String.valueOf(pancerz.getId())});

            while (cursor2.moveToNext()) {
                CechaPancerza cecha = new CechaPancerza();
                cecha.setNazwa(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa")));
                cecha.setOpis(cursor2.getString(cursor2.getColumnIndexOrThrow("opis")));

                cechaPancerzaList.add(cecha);
            }
            cursor2.close();

            pancerz.setCechaPancerzaList(cechaPancerzaList);

            List<LokalizacjaPancerza> lokalizacjaPancerzaList = new ArrayList<>();

            String query3 = "SELECT lp.nazwa, lp.id " +
                    "FROM lokalizacja_pancerza_pancerz lpp " +
                    "JOIN lokalizacja_pancerza lp ON lpp.lokalizacja_pancerza_id = lp.id " +
                    "WHERE lpp.pancerz_id = ?";

            Cursor cursor3 = db.rawQuery(query3, new String[]{String.valueOf(pancerz.getId())});

            while (cursor3.moveToNext()) {
                LokalizacjaPancerza lokalizacjaPancerza = new LokalizacjaPancerza();
                lokalizacjaPancerza.setId(cursor3.getInt(cursor3.getColumnIndexOrThrow("id")));
                lokalizacjaPancerza.setNazwa(cursor3.getString(cursor3.getColumnIndexOrThrow("nazwa")));

                lokalizacjaPancerzaList.add(lokalizacjaPancerza);
            }
            cursor3.close();

            pancerz.setLokalizacjaPancerzaList(lokalizacjaPancerzaList);

            list.add(pancerz);

        }
        cursor.close();

        return list;
    }

    public List<Bron> getAllBronList(SQLiteDatabase db) {
        List<Bron> list = new ArrayList<>();

        String query = "SELECT  * " +
                "FROM broń ";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Bron bron = new Bron();

            bron.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

            bron.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            bron.setCena(cursor.getString(cursor.getColumnIndexOrThrow("cena")));
            bron.setZasieg(cursor.getString(cursor.getColumnIndexOrThrow("zasięg")));
            bron.setObrazenia(cursor.getString(cursor.getColumnIndexOrThrow("obrażenia")));
            bron.setCzyWlasna(cursor.getInt(cursor.getColumnIndexOrThrow("czy_własna")));

            bron.setTypBroniId(cursor.getInt(cursor.getColumnIndexOrThrow("typ_broni_id")));
            bron.setDostepnoscBroniID(cursor.getInt(cursor.getColumnIndexOrThrow("dostępność_id")));

            List<CechaBroni> cechyList = new ArrayList<>();

            String query2 = "SELECT cb.nazwa, cb.opis FROM cecha_broni_broń cbb " +
                    "JOIN cechy_broni cb ON cbb.cecha_id = cb.id " +
                    "WHERE cbb.broń_id = ?";

            Cursor cursor2 = db.rawQuery(query2, new String[]{String.valueOf(bron.getId())});

            while (cursor2.moveToNext()) {
                CechaBroni cecha = new CechaBroni();
                cecha.setNazwa(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa")));
                cecha.setOpis(cursor2.getString(cursor2.getColumnIndexOrThrow("opis")));
                cechyList.add(cecha);
            }
            cursor2.close();

            bron.setListaCech(cechyList);

            TypBroni typBroni = new TypBroni();

            String query3 = "SELECT * " +
                    "FROM typ_broni " +
                    "WHERE id = ?";

            Cursor cursor3 = db.rawQuery(query3, new String[]{String.valueOf(bron.getTypBroniId())});

            if (cursor3.moveToNext()) {
                typBroni.setNazwa(cursor3.getString(cursor3.getColumnIndexOrThrow("id")));
                typBroni.setNazwa(cursor3.getString(cursor3.getColumnIndexOrThrow("nazwa")));
                typBroni.setCzyZaciegowa(cursor3.getInt(cursor3.getColumnIndexOrThrow("czy_zacięgowa")));
                typBroni.setOpisSpecjalny(cursor3.getString(cursor3.getColumnIndexOrThrow("opis_specjalny")));
                bron.setTypBroni(typBroni);
            }


            String query4 = "SELECT * " +
                    "FROM dostępność " +
                    "WHERE id = ?";

            Cursor cursor4 = db.rawQuery(query4, new String[]{String.valueOf(bron.getDostepnoscBroniID())});

            if (cursor4.moveToNext()) {
                bron.setDostemnoscText(cursor4.getString(cursor4.getColumnIndexOrThrow("nazwa")));
            }

            list.add(bron);

        }
        cursor.close();

        return list;
    }

    public List<Wyposarzenia> getAllWyposazenia(SQLiteDatabase db) {
        List<Wyposarzenia> list = new ArrayList<>();

        String query = "SELECT  * " +
                "FROM wyposarzenie ";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Wyposarzenia wyposarzenia = new Wyposarzenia();

            wyposarzenia.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            wyposarzenia.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            wyposarzenia.setOciazenie(cursor.getInt(cursor.getColumnIndexOrThrow("obciążenie")));

            list.add(wyposarzenia);
        }
        cursor.close();

        return list;
    }

    public List<Zaklecia> getAllZaklecia(SQLiteDatabase db) {
        List<Zaklecia> list = new ArrayList<>();

        String query = "SELECT  z.* " +
                "FROM zaklęcia z " +
                "JOIN tradycja t ON z.tradycja_id = t.id";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Zaklecia zaklecia = new Zaklecia();

            zaklecia.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            zaklecia.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("z.nazwa")));
            zaklecia.setPoziomZaklecie(cursor.getInt(cursor.getColumnIndexOrThrow("poziom_zaklęcia")));
            zaklecia.setZacieg(cursor.getString(cursor.getColumnIndexOrThrow("zacięg")));
            zaklecia.setCel(cursor.getString(cursor.getColumnIndexOrThrow("cel")));
            zaklecia.setCzas(cursor.getString(cursor.getColumnIndexOrThrow("czas")));
            zaklecia.setOpis(cursor.getString(cursor.getColumnIndexOrThrow("opis")));
            zaklecia.setTradycjaId(cursor.getInt(cursor.getColumnIndexOrThrow("tradycja_id")));

            zaklecia.setTradycjaName(cursor.getString(cursor.getColumnIndexOrThrow("t.nazwa")));

            list.add(zaklecia);
        }
        cursor.close();
        return list;
    }

    public List<TypPancerza> getTypPancerzaList(SQLiteDatabase db) {
        String[] colums = {"*"};

        Cursor cursor = db.query("typ_pancerza", colums, null, null, null, null, null);

        List<TypPancerza> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            TypPancerza typPancerza = new TypPancerza();
            typPancerza.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            typPancerza.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));

            list.add(typPancerza);
        }
        cursor.close();
        return list;
    }
    public List<TypBroni> getTypBroniList(SQLiteDatabase db) {
        String[] colums = {"*"};

        Cursor cursor = db.query("typ_broni", colums, null, null, null, null, null);

        List<TypBroni> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            TypBroni typBroni = new TypBroni();
            typBroni.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            typBroni.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));

            list.add(typBroni);
        }
        cursor.close();
        return list;
    }

    public List<Dostepnosc> getDostepnoscList(SQLiteDatabase db) {
        String[] colums = {"*"};

        Cursor cursor = db.query("dostępność", colums, null, null, null, null, null);

        List<Dostepnosc> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Dostepnosc dostepnosc = new Dostepnosc();
            dostepnosc.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            dostepnosc.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));

            list.add(dostepnosc);
        }
        cursor.close();
        return list;

    }

    public List<Umiejetnosci> getAllZaawansowaneUmijetnosci(SQLiteDatabase db) {

        String query = "SELECT  id, nazwa, czy_zaawansowana " +
                "FROM umiejętności " +
                "WHERE umiejętności.czy_zaawansowana = 1 ";
        Cursor cursor = db.rawQuery(query, null);

        List<Umiejetnosci> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Umiejetnosci umiejetnosci = new Umiejetnosci();

            umiejetnosci.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            umiejetnosci.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));

            list.add(umiejetnosci);

            Log.d("UM", String.valueOf(umiejetnosci.getRozwoj() + " " + umiejetnosci.getNazwa() + " " + umiejetnosci.getCechaNazwa()));

        }
        cursor.close();

        return list;
    }

    public List<Umiejetnosci> getAllPodstawoweUmiejetnosciForKarta(SQLiteDatabase db, int kartaId) {

        String[] selectionArgs = {String.valueOf(kartaId)};

        String query = "SELECT karta_umiętność.rozwój, umiejętności.nazwa, cechy.nazwa_krótka, umiejętności.id, umiejętności.cechy_id " +
                "FROM karta_umiętność " +
                "JOIN umiejętności ON karta_umiętność.umiejętności_id = umiejętności.id " +
                "JOIN cechy ON umiejętności.cechy_id = cechy.id " +
                "WHERE karta_umiętność.karta_id = ? AND czy_zaawansowana = 0 ";
        Cursor cursor = db.rawQuery(query, selectionArgs);

        List<Umiejetnosci> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Umiejetnosci umiejetnosci = new Umiejetnosci();

            umiejetnosci.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            umiejetnosci.setRozwoj(cursor.getInt(cursor.getColumnIndexOrThrow("rozwój")));
            umiejetnosci.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            umiejetnosci.setCechaNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa_krótka")));

            Cechy cechy = getCechaByid(db, cursor.getInt(cursor.getColumnIndexOrThrow("cechy_id")), kartaId);


            umiejetnosci.setCecha(cechy);

            list.add(umiejetnosci);

        }
        cursor.close();

        return list;
    }

    public List<Umiejetnosci> getAllZaawansowaneUmiejetnosciForKarta(SQLiteDatabase db, int kartaId) {

        String[] selectionArgs = {String.valueOf(kartaId)};

        String query = "SELECT karta_umiętność.rozwój, umiejętności.nazwa, cechy.nazwa_krótka, umiejętności.id, umiejętności.cechy_id " +
                "FROM karta_umiętność " +
                "JOIN umiejętności ON karta_umiętność.umiejętności_id = umiejętności.id " +
                "JOIN cechy ON umiejętności.cechy_id = cechy.id " +
                "WHERE karta_umiętność.karta_id = ? AND czy_zaawansowana = 1 ";
        Cursor cursor = db.rawQuery(query, selectionArgs);

        List<Umiejetnosci> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Umiejetnosci umiejetnosci = new Umiejetnosci();

            umiejetnosci.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            umiejetnosci.setRozwoj(cursor.getInt(cursor.getColumnIndexOrThrow("rozwój")));
            umiejetnosci.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            umiejetnosci.setCechaNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa_krótka")));

            Cechy cechy = getCechaByid(db, cursor.getInt(cursor.getColumnIndexOrThrow("cechy_id")), kartaId);


            umiejetnosci.setCecha(cechy);

            list.add(umiejetnosci);

        }
        cursor.close();

        return list;
    }

}
