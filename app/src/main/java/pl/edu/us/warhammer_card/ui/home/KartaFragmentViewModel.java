package pl.edu.us.warhammer_card.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Karta;


public class KartaFragmentViewModel extends ViewModel {

    private final MutableLiveData<List<Karta>> kartaLiveData= new MutableLiveData<>();

    public LiveData<List<Karta>> getKartaList(){
        return kartaLiveData;
    }


    public List<Karta> getKartaList(SQLiteDatabase db, int kampanieID) {


        String[] projection = {"kampania_id"};
        String[] colums = {"*"};
        String[] selectionArgs = {Integer.toString(kampanieID)};


        Cursor cursor = db.query("karta", colums, "kampania_id = ?", selectionArgs, null, null, null);
        List<Karta> listaKarta = new ArrayList<>();
        Log.d("Karta", Integer.toString(kampanieID));


        while (cursor.moveToNext()) {
            Karta karta = new Karta();
            karta.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            karta.setProfesjaId(cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id")));
            karta.setRasaId(cursor.getInt(cursor.getColumnIndexOrThrow("rasa_id")));
            karta.setImie(cursor.getString(cursor.getColumnIndexOrThrow("imie")));

            listaKarta.add(karta);
            Log.d("Karta", String.valueOf("KartaID=" + karta.getId()));
        }
        cursor.close();
       kartaLiveData.postValue(listaKarta);
        return listaKarta;
    }
}
