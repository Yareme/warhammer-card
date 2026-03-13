package pl.edu.us.warhammer_card.ui.karta2.wyposazenie;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import pl.edu.us.warhammer_card.table.Wyposarzenia;

public class WyposazeniaViewModel extends ViewModel {
    private final MutableLiveData<List<Wyposarzenia>> wyposarzeniaMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Wyposarzenia>> getWyposazeniaLiveData() {
        return wyposarzeniaMutableLiveData;
    }

    public List<Wyposarzenia> getWyposazeniaListFromKarta(SQLiteDatabase db, int kartaId) {
        List<Wyposarzenia> list = new ArrayList<>();

        String query = "SELECT id ,nazwa, obciążenie, sztuk "+
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

        wyposarzeniaMutableLiveData.postValue(list);

        return list;
    }

    public void updateWyposarzenia(SQLiteDatabase db, Wyposarzenia wyposarzenia, int idKarta){
        ContentValues values = new ContentValues();
        values.put("sztuk", wyposarzenia.getSztuk());

        String selection = "karta_id = ? AND wyposarzenie_id = ?" ;
        String[] selectionArgs = {String.valueOf(idKarta), String.valueOf(wyposarzenia.getId())};

        db.update("karta_wyposarzenie",values,selection,selectionArgs);
        wyposarzeniaMutableLiveData.postValue(getWyposazeniaListFromKarta(db, idKarta));

    };

}