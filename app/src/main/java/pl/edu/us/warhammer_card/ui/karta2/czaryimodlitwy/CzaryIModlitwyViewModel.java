package pl.edu.us.warhammer_card.ui.karta2.czaryimodlitwy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.Zaklecia;

public class CzaryIModlitwyViewModel extends ViewModel {


    private final MutableLiveData<List<Zaklecia>>zaklecieMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Zaklecia>> getZakleciaLiveData() {
        return zaklecieMutableLiveData;
    }


    public List<Zaklecia> getZakleciaForKarta(SQLiteDatabase db, int kartaId) {
        List<Zaklecia> list = new ArrayList<>();

        String query = "SELECT  z.* " +
                "FROM zaklęcia z " +
                "JOIN karta_zaklęcia kz ON z.id = kz.zaklęcia_id "+
                "WHERE kz.karta_id = ?";

        Cursor cursor = db.rawQuery(query,  new String[]{String.valueOf(kartaId)});

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

        zaklecieMutableLiveData.postValue(list);
        return list;
    }
}
