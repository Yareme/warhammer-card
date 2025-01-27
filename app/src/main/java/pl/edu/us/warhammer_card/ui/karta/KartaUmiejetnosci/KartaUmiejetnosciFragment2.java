package pl.edu.us.warhammer_card.ui.karta.KartaUmiejetnosci;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        row.setPadding(16, 8, 16, 8); // Padding dla lepszego wyglądu
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // Nazwa umiejętności (TextView)
        TextView nazwaTextView = new TextView(getContext());
        nazwaTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        nazwaTextView.setGravity(Gravity.CENTER);
        nazwaTextView.setTextSize(16); // Większy rozmiar czcionki
        nazwaTextView.setTextColor(Color.WHITE);
        nazwaTextView.setText(umiejetnosc.getNazwa() + " (" + umiejetnosc.getCecha_nazwa() + ")");
        row.addView(nazwaTextView);

        // Cecha (TextView - nieedytowalny)
        TextView cechaTextView = new TextView(getContext());
        cechaTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        cechaTextView.setGravity(Gravity.CENTER);
        cechaTextView.setTextSize(16); // Większy rozmiar czcionki
        cechaTextView.setTextColor(Color.WHITE);
        cechaTextView.setText(String.valueOf(umiejetnosc.getWortascCecha()));
        row.addView(cechaTextView);

        // Layout dla przycisków i wartości Rozwój
        LinearLayout rozwójLayout = new LinearLayout(getContext());
        rozwójLayout.setOrientation(LinearLayout.HORIZONTAL);
        rozwójLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        // Przycisk "-"
        Button minusButton = new Button(getContext());
        minusButton.setText("-");
        minusButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        minusButton.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light)); // Przyjazny kolor
        minusButton.setTextSize(18); // Większy tekst
        minusButton.setPadding(16, 8, 16, 8); // Padding dla łatwiejszego naciskania
        rozwójLayout.addView(minusButton);

        // Wartość Rozwój (TextView)
        TextView rozwojTextView = new TextView(getContext());
        rozwojTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        rozwojTextView.setGravity(Gravity.CENTER);
        rozwojTextView.setTextSize(16); // Większy rozmiar czcionki
        rozwojTextView.setTextColor(Color.WHITE);
        rozwojTextView.setText(String.valueOf(umiejetnosc.getRozwoj()));
        rozwójLayout.addView(rozwojTextView);

        // Przycisk "+"
        Button plusButton = new Button(getContext());
        plusButton.setText("+");
        plusButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        plusButton.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_light)); // Przyjazny kolor
        plusButton.setTextSize(18); // Większy tekst
        plusButton.setPadding(16, 8, 16, 8); // Padding dla łatwiejszego naciskania
        rozwójLayout.addView(plusButton);

        row.addView(rozwójLayout);

        // Suma (TextView - nieedytowalny)
        TextView sumaTextView = new TextView(getContext());
        sumaTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        sumaTextView.setGravity(Gravity.CENTER);
        sumaTextView.setTextSize(16); // Większy rozmiar czcionki
        sumaTextView.setTextColor(Color.WHITE);
        sumaTextView.setText(String.valueOf(umiejetnosc.getWortascCecha() + umiejetnosc.getRozwoj()));
        row.addView(sumaTextView);

        // Obsługa przycisków
        minusButton.setOnClickListener(v -> {
            int currentValue = umiejetnosc.getRozwoj();
            if (currentValue > 0) {
                umiejetnosc.setRozwoj(currentValue - 1);
                updateUmiejetnosci(db, umiejetnosc, kartaId);
                rozwojTextView.setText(String.valueOf(umiejetnosc.getRozwoj()));
                sumaTextView.setText(String.valueOf(umiejetnosc.getWortascCecha() + umiejetnosc.getRozwoj()));
            }
        });

        plusButton.setOnClickListener(v -> {
            int currentValue = umiejetnosc.getRozwoj();
            umiejetnosc.setRozwoj(currentValue + 1);
            updateUmiejetnosci(db, umiejetnosc, kartaId);
            rozwojTextView.setText(String.valueOf(umiejetnosc.getRozwoj()));
            sumaTextView.setText(String.valueOf(umiejetnosc.getWortascCecha() + umiejetnosc.getRozwoj()));
        });

        // Dodanie wiersza do dynamicznego layoutu
        dynamicUmiejetnosciLayout.addView(row);
    }

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
