package pl.edu.us.warhammer_card.ui.karta.KartaAmbicjeIDruzyna;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.table.Karta;

public class KartaAmbicjeIDruzynaViewModel extends AndroidViewModel {

    private MutableLiveData<Karta> kartaLiveData;
    private AppSQLiteHelper dbHelper;

    public KartaAmbicjeIDruzynaViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new AppSQLiteHelper(application.getApplicationContext());
    }

    public LiveData<Karta> getKarta(int kartaId) {
        if (kartaLiveData == null) {
            kartaLiveData = new MutableLiveData<>();
            loadKarta(kartaId);
        }
        return kartaLiveData;
    }

    private void loadKarta(int kartaId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Karta karta = dbHelper.getKartaById(db, kartaId);

                if (karta != null) {
                    kartaLiveData.postValue(karta);
                }
            }
        }).start();
    }

    public void updateKarta(Karta karta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.updateKarta(db, karta);
    }
}
