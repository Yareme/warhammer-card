package pl.edu.us.warhammer_card.ui.karta.KartaUmiejetnosci;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Umiejetnosci;
import pl.edu.us.warhammer_card.table.Wyposarzenia;


public class UmiejetnosciViewModel extends ViewModel {
    Context context;
    private final MutableLiveData<List<Umiejetnosci>> umiejetnoscMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Umiejetnosci>> getUmiejetnoscLiveData(){
        return umiejetnoscMutableLiveData;
    }

    public List<Umiejetnosci> getUmiejetnosciPodstawoweList(SQLiteDatabase db, int kartaId){
        AppSQLiteHelper appSQLiteHelper = new AppSQLiteHelper(context);
        List<Umiejetnosci> list =appSQLiteHelper.getAllPodstawoweUmiejetnosciForKarta( db, kartaId);
        umiejetnoscMutableLiveData.postValue(list);

        return list;
    };
    public void  updateUmiejetnosc(SQLiteDatabase db, Umiejetnosci umiejetnosci, int idKarta){
        ContentValues values = new ContentValues();
        values.put("rozwój", umiejetnosci.getRozwoj());

        String selection = "karta_id = ? AND umiejętności_id = ?" ;
        String[] selectionArgs = {String.valueOf(idKarta), String.valueOf(umiejetnosci.getId())};

        db.update("karta_umiętność",values,selection,selectionArgs);
        umiejetnoscMutableLiveData.postValue(getUmiejetnosciPodstawoweList(db, idKarta));

    }
}
