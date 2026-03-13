package pl.edu.us.warhammer_card.ui.karta2.bron;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.CechaBroni;
import pl.edu.us.warhammer_card.table.TypBroni;

public class BronViewModel extends ViewModel {

    private final MutableLiveData<List<Bron>> bronMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Bron>> getBronLiveData() {
        return bronMutableLiveData;
    }

    public List<Bron> getBronListForKarta(SQLiteDatabase db, int kartaId) {
        List<Bron> list = new ArrayList<>();

        String query = "SELECT b.* "+
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

        bronMutableLiveData.postValue(list);

        return list;
    }
}
