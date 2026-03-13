package pl.edu.us.warhammer_card.ui.karta.KartaUmiejetnosci;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.table.Umiejetnosci;

public class UmiejetnosciViewModel2 extends ViewModel {
    Context context;
    private final MutableLiveData<List<Umiejetnosci>> umiejetnoscMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Umiejetnosci>> getUmiejetnoscLiveData(){
        return umiejetnoscMutableLiveData;
    }

    public List<Umiejetnosci> getAllZaawansowaneUmiejetnosciForKarta(SQLiteDatabase db, int kartaId){
        AppSQLiteHelper appSQLiteHelper = new AppSQLiteHelper(context);
        List<Umiejetnosci> list =appSQLiteHelper.getAllZaawansowaneUmiejetnosciForKarta( db, kartaId);
        umiejetnoscMutableLiveData.postValue(list);

        return list;
    };

    List<Umiejetnosci> getZaawansowaneForProfesja(SQLiteDatabase db, int[] schemat) {
        List<Umiejetnosci> list = new ArrayList<>();

        String query = "SELECT  id, nazwa " +
                "FROM umiejętności " +
                "WHERE id = ?  AND czy_zaawansowana = 1 ";

        for (int i = 0; i < schemat.length; i++) {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(schemat[i])});

            if (cursor.moveToNext()) {
                Umiejetnosci umiejetnosci = new Umiejetnosci();

                umiejetnosci.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                umiejetnosci.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));

                list.add(umiejetnosci);
            }
            cursor.close();
        }

        return list;

    }
    public void  updateUmiejetnosc(SQLiteDatabase db, Umiejetnosci umiejetnosci, int idKarta){
        ContentValues values = new ContentValues();
        values.put("rozwój", umiejetnosci.getRozwoj());

        String selection = "karta_id = ? AND umiejętności_id = ?" ;
        String[] selectionArgs = {String.valueOf(idKarta), String.valueOf(umiejetnosci.getId())};

        db.update("karta_umiętność",values,selection,selectionArgs);
        umiejetnoscMutableLiveData.postValue(getAllZaawansowaneUmiejetnosciForKarta(db, idKarta));

    };
}
