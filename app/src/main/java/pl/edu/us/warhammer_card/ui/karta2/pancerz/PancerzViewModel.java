package pl.edu.us.warhammer_card.ui.karta2.pancerz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.CechaPancerza;
import pl.edu.us.warhammer_card.table.LokalizacjaPancerza;
import pl.edu.us.warhammer_card.table.Pancerz;

public class PancerzViewModel extends ViewModel {
    private final MutableLiveData<List<Pancerz>> pancerzMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Pancerz>> getPancerzLiveData(){return pancerzMutableLiveData;}

    public List<Pancerz> getPancerzFromKarta(SQLiteDatabase db , int kartaId){
        List<Pancerz> list = new ArrayList<>();

        String query = "SELECT  p.* , kp.* " +
                "FROM pancerz p " +
                "JOIN karta_pancerz kp ON p.id = kp.pancerz_id "+
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

            List<CechaPancerza> cechaPancerzaList  = new ArrayList<>();

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

        pancerzMutableLiveData.postValue(list);

        return list;
    }
}