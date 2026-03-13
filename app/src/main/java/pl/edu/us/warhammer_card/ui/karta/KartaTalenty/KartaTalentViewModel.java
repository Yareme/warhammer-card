package pl.edu.us.warhammer_card.ui.karta.KartaTalenty;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.table.Talent;

public class KartaTalentViewModel extends ViewModel {

    private final MutableLiveData<List<Talent>> talentMutableLiveData = new MutableLiveData<>();



    public LiveData<List<Talent>> getTalentLiveData() {
        return talentMutableLiveData;
    }

    public List<Talent> getTalentFromKarta(SQLiteDatabase db ,int kartaId){
        List<Talent> list = new ArrayList<>();

        String[] selectionArgs = {String.valueOf(kartaId)};

        String query = "SELECT karta_talent.karta_id,talent.id, talent.nazwa, talent.maksimum, talent.testy, talent.opis, karta_talent.poziom, talent.cechy_id " +
                "FROM karta_talent " +
                "JOIN talent ON karta_talent.talent_id = talent.id " +
                "WHERE karta_talent.karta_id = ? ";

        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            Talent talent = new Talent();

            talent.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

            talent.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            talent.setMaksimum(cursor.getString(cursor.getColumnIndexOrThrow("maksimum")));
            talent.setTesty(cursor.getString(cursor.getColumnIndexOrThrow("testy")));
            talent.setOpis(cursor.getString(cursor.getColumnIndexOrThrow("opis")));

            talent.setPoziom(cursor.getInt(cursor.getColumnIndexOrThrow("poziom")));

            talent.setIdCecha(cursor.getInt(cursor.getColumnIndexOrThrow("cechy_id")));

            list.add(talent);

        }
        cursor.close();
        talentMutableLiveData.postValue(list);

        return list;
    }

    public void updateTalent(SQLiteDatabase db, Talent talent, int kartaId){

        ContentValues values = new ContentValues();
        values.put("poziom", talent.getPoziom());

        String selection = "karta_id = ? AND talent_id = ?" ;
        String[] selectionArgs = {String.valueOf(kartaId), String.valueOf(talent.getId())};
        db.update("karta_talent",values,selection,selectionArgs);

        talentMutableLiveData.postValue(getTalentFromKarta(db, kartaId));
    }

}
