package pl.edu.us.warhammer_card.ui.karta.KartaUmiejetnosci;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.databinding.FragmentKartaUmiejetnosci2Binding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaUmiejetnosciBinding;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Umiejetnosci;

public class KartaUmiejetnosciFragment2 extends Fragment {

    FragmentKartaUmiejetnosci2Binding binding;

    LinearLayout dynamicUmiejetnosciLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaUmiejetnosci2Binding.inflate(inflater, container, false);
        dynamicUmiejetnosciLayout = binding.dynamicUmiejetnosciLayout;

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

        // Pobieranie cech i umiejętności z bazy danych
        List<Cechy> listCecha = getCechy(db, kartaId);
        List<Umiejetnosci> listUmiejetnosci = getUmiejetnosci(db, kartaId);

        // Ustawianie wartości cech w umiejętnościach
        for (Umiejetnosci umiejetnosc : listUmiejetnosci) {
            String cechaNazwa = umiejetnosc.getCecha_nazwa();
            for (Cechy cecha : listCecha) {
                if (cechaNazwa.equals(cecha.getNazwaKrotka())) {
                    umiejetnosc.setWortascCecha(cecha.getWartPo() + cecha.getRozw());
                    break;
                }
            }
        }

        // Generowanie wszystkich dynamicznych wierszy dla umiejętności
        for (Umiejetnosci umiejetnosc : listUmiejetnosci) {
            addDynamicRow(umiejetnosc, db, kartaId);
        }

        return binding.getRoot();
    }

    // Funkcja dodająca dynamiczny wiersz umiejętności do layoutu
    private void addDynamicRow(Umiejetnosci umiejetnosc, SQLiteDatabase db, int kartaId) {
        // Tworzenie nowego LinearLayout na wiersz
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setWeightSum(4);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // Nazwa umiejętności (TextView)
        TextView nazwaTextView = new TextView(getContext());
        nazwaTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        nazwaTextView.setGravity(Gravity.CENTER);
        nazwaTextView.setText(umiejetnosc.getNazwa() + " (" + umiejetnosc.getCecha_nazwa() + ")");
        row.addView(nazwaTextView);

        // Cecha (EditText - nieedytowalny)
        EditText cechaEditText = new EditText(getContext());
        cechaEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        cechaEditText.setGravity(Gravity.CENTER);
        cechaEditText.setText(String.valueOf(umiejetnosc.getWortascCecha()));
        cechaEditText.setEnabled(false); // Pole nieedytowalne
        row.addView(cechaEditText);

        // Rozwój (EditText - edytowalny)
        EditText rozwojEditText = new EditText(getContext());
        rozwojEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        rozwojEditText.setGravity(Gravity.CENTER);
        rozwojEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        rozwojEditText.setText(String.valueOf(umiejetnosc.getRozwoj()));
        row.addView(rozwojEditText);

        // Suma (EditText - nieedytowalny)
        EditText sumaEditText = new EditText(getContext());
        sumaEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        sumaEditText.setGravity(Gravity.CENTER);
        sumaEditText.setText(String.valueOf(umiejetnosc.getWortascCecha() + umiejetnosc.getRozwoj()));
        sumaEditText.setEnabled(false); // Pole nieedytowalne
        row.addView(sumaEditText);

        // Dodanie TextWatcher do śledzenia zmian w polu rozwój
        rozwojEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int rozwojValue = Integer.parseInt(editable.toString());
                    umiejetnosc.setRozwoj(rozwojValue);
                    updateUmiejetnosci(db, umiejetnosc, kartaId);
                    sumaEditText.setText(String.valueOf(umiejetnosc.getWortascCecha() + rozwojValue));
                } catch (NumberFormatException e) {
                    // Jeśli nieprawidłowy input, ustaw rozwój na 0
                    umiejetnosc.setRozwoj(0);
                    updateUmiejetnosci(db, umiejetnosc, kartaId);
                    sumaEditText.setText(String.valueOf(umiejetnosc.getWortascCecha()));
                }
            }
        });

        // Dodanie wiersza do dynamicznego layoutu
        dynamicUmiejetnosciLayout.addView(row);
    }

    // Przykładowe funkcje do pobierania danych z bazy


    List<Umiejetnosci> getUmiejetnosci(SQLiteDatabase db, int id){

        String[] selectionArgs = {String.valueOf(id)};

        String query = "SELECT karta_umiętność.rozwój, umiejętności.nazwa, cechy.nazwa_krótka, umiejętności.id " +
                "FROM karta_umiętność " +
                "JOIN umiejętności ON karta_umiętność.umiejętności_id = umiejętności.id " +
                "JOIN cechy ON umiejętności.cechy_id = cechy.id " +
                "WHERE karta_umiętność.karta_id = ?";
        Cursor cursor = db.rawQuery(query, selectionArgs);

        List<Umiejetnosci> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Umiejetnosci umiejetnosci = new Umiejetnosci();

            umiejetnosci.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            umiejetnosci.setRozwoj(cursor.getInt(cursor.getColumnIndexOrThrow("rozwój")));
            umiejetnosci.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            umiejetnosci.setCecha_nazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa_krótka")));

            list.add(umiejetnosci);

            Log.d("UM",String.valueOf(umiejetnosci.getRozwoj()+" "+umiejetnosci.getNazwa()+" "+umiejetnosci.getCecha_nazwa()));

        }
        cursor.close();

        return list;
    }


    void updateUmiejetnosci(SQLiteDatabase db, Umiejetnosci umiejetnosci, int idKarta){
        ContentValues values = new ContentValues();
        values.put("rozwój",  umiejetnosci.getRozwoj());

        String selection = "karta_id = ? AND umiejętności_id = ?" ;
        String[] selectionArgs = {String.valueOf(idKarta), String.valueOf(umiejetnosci.getId())};

        int ii= db.update("karta_umiętność",values,selection,selectionArgs);
        Log.d("cechy",String.valueOf(ii));


    }

    List<Cechy> getCechy(SQLiteDatabase db, int id){

        String[] projection = { "nazwa" };
        String[] colums={"*"};
        String sortOrder = "cechy_id ASC";   /*ASC*/ /* DESC*/
        String[] selectionArgs={String.valueOf(id)};
        Cursor cursor = db.query("karta_cecha", colums, "karta_id = ?", selectionArgs, null, null,  sortOrder);

        List<Cechy> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Cechy cecha = new Cechy();

            cecha.setId(cursor.getInt(cursor.getColumnIndexOrThrow("cechy_id")));
            cecha.setWartPo(cursor.getInt(cursor.getColumnIndexOrThrow("wartość_pociątkowa")));
            cecha.setRozw(cursor.getInt(cursor.getColumnIndexOrThrow("rozwój")));


            String[] selectionArgs2={String.valueOf(cecha.getId())};
            Cursor cursor2 = db.query("cechy", colums, "id = ?", selectionArgs2, null, null,  null);
            String name = "";

            if (cursor2.moveToFirst()){
                cecha.setNazwaKrotka(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa_krótka")));

            }

            list.add(cecha);

            Log.d("cechy",String.valueOf(cecha.getId() +" "+ cecha.getWartPo()+" "+cecha.getRozw()+ name));
        }
        cursor.close();

        return list;
    }
}
