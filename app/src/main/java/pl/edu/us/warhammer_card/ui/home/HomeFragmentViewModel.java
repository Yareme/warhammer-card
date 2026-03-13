package pl.edu.us.warhammer_card.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Kampania;

public class HomeFragmentViewModel extends ViewModel {

    private final MutableLiveData<List<Kampania>> kampaniaLiveData= new MutableLiveData<>();
    public LiveData<List<Kampania>> getKampaniaList(){
        return kampaniaLiveData;
    }

    public void getKampaniaList(SQLiteDatabase db) {
        String[] projection = {"nazwa"};
        String[] colums = {"*"};
        String sortOrder = "nazwa ASC";

        Cursor cursor = db.query("kampania", colums, null, null, null, null, sortOrder);
        List<Kampania> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Kampania kampania = new Kampania();
            kampania.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            kampania.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            kampania.setDate(cursor.getLong(cursor.getColumnIndexOrThrow("data_utworzenia")));
            list.add(kampania);
            Log.d("Kampania", String.valueOf(kampania.getId()) + " " + kampania.getNazwa() + " " + kampania.getDate());

        }
        cursor.close();
        kampaniaLiveData.postValue(list);
    }
}