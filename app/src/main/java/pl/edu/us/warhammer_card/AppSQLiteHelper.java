package pl.edu.us.warhammer_card;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Kampania;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Pancerz;
import pl.edu.us.warhammer_card.table.Talent;

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

        }
        return karta;
    }
    /*TODO aktualizacja calej karty a nie tylko punktów*/
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

        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(karta.getId()) };

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
            pancerz.setLokalizacja_pancerza(cursor.getString(cursor.getColumnIndexOrThrow("lokalizacja_pancerza")));
            pancerz.setPunktyPancerza(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_pancerza")));

            pancerz.setZalety(cursor.getString(cursor.getColumnIndexOrThrow("zalety")));
            pancerz.setWady(cursor.getString(cursor.getColumnIndexOrThrow("wady")));

            pancerz.setDostepnoscId(cursor.getInt(cursor.getColumnIndexOrThrow("dostępność_id")));
            pancerz.setTypPancerzaId(cursor.getInt(cursor.getColumnIndexOrThrow("typ_pancerza_id")));

            list.add(pancerz);

        }
        cursor.close();

        return list;
    }

}
