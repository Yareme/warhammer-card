package pl.edu.us.warhammer_card.ui.karta2.pancerz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Pancerz;

public class PancerzViewModel extends ViewModel {
    private final MutableLiveData<List<Pancerz>> pancerzMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Pancerz>> getPancerzLiveData(){return pancerzMutableLiveData;}

    public List<Pancerz> getPancerzFromKarta(SQLiteDatabase db , int kartaId){
        List<Pancerz> list = new ArrayList<>();

        String[] selectionArgs = {String.valueOf(kartaId)};

        String query = "SELECT karta_pancerz.karta_id, karta_pancerz.czy_zalożony, pancerz.id, pancerz.nazwa AS pancerz_nazwa, pancerz.cena, pancerz.kara, pancerz.lokalizacja_pancerza, " +
                "pancerz.punkty_pancerza, pancerz.zalety,pancerz.wady,pancerz.dostępność_id,pancerz.dostępność_id, typ_pancerza.nazwa AS typ_pancerza_nazwa, dostępność.nazwa AS dostępność_nazwa, pancerz.typ_pancerza_id " +
                "FROM karta_pancerz " +
                "JOIN pancerz ON karta_pancerz.pancerz_id = pancerz.id " +
                "JOIN typ_pancerza ON pancerz.typ_pancerza_id = typ_pancerza.id " +
                "JOIN dostępność ON pancerz.dostępność_id = dostępność.id " +
                "WHERE karta_pancerz.karta_id = ? ";

        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            Pancerz pancerz = new Pancerz();

            pancerz.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

            pancerz.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("pancerz_nazwa")));
            pancerz.setCena(cursor.getString(cursor.getColumnIndexOrThrow("cena")));
            pancerz.setKara(cursor.getString(cursor.getColumnIndexOrThrow("kara")));
            pancerz.setLokalizacja_pancerza(cursor.getString(cursor.getColumnIndexOrThrow("lokalizacja_pancerza")));
            pancerz.setPunktyPancerza(cursor.getInt(cursor.getColumnIndexOrThrow("punkty_pancerza")));

            pancerz.setZalety(cursor.getString(cursor.getColumnIndexOrThrow("zalety")));
            pancerz.setWady(cursor.getString(cursor.getColumnIndexOrThrow("wady")));

            pancerz.setDostepnoscId(cursor.getInt(cursor.getColumnIndexOrThrow("dostępność_id")));
            pancerz.setTypPancerzaId(cursor.getInt(cursor.getColumnIndexOrThrow("typ_pancerza_id")));

            pancerz.setTypPancerza(cursor.getString(cursor.getColumnIndexOrThrow("typ_pancerza_nazwa")));
            pancerz.setDastepnosc(cursor.getString(cursor.getColumnIndexOrThrow("dostępność_nazwa")));

            pancerz.setCzyZalozone(cursor.getInt(cursor.getColumnIndexOrThrow("czy_zalożony")));


            list.add(pancerz);

        }
        cursor.close();

        pancerzMutableLiveData.postValue(list);

        return list;
    }
}