package pl.edu.us.warhammer_card.ui.karta.KartaCechy;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.databinding.FragmentKartaCechyBinding;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.PoziomProfesja;

public class KartaCechyFragment extends Fragment{
    FragmentKartaCechyBinding binding;
    LinearLayout dynamicCechyLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaCechyBinding.inflate(inflater, container, false);
        dynamicCechyLayout = binding.dynamicCechyLayout;


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

       List<Cechy> list = getCechy(db,kartaId);


        int[] schemat = getProfesjaSchemat(db,kartaId);
        for (Cechy cecha : list) {
            addDynamicRow(cecha, db, kartaId, schemat);

        }

        return binding.getRoot();
    }

    private void addDynamicRow(Cechy cecha, SQLiteDatabase db, int kartaId, int[] schemat) {
        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setWeightSum(4);
        row.setPadding(8, 60, 8, 8);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView nazwaTextView = new TextView(getContext());
        nazwaTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        nazwaTextView.setGravity(Gravity.CENTER);
        nazwaTextView.setTextSize(22);
        nazwaTextView.setText(cecha.getNazwaKrotka());
        nazwaTextView.setTextColor(Color.WHITE);


        if (schemat != null){
            int i=0;
            do {
                if (schemat[i]==cecha.getId()){
                    nazwaTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    nazwaTextView.setTypeface(null, Typeface.BOLD);
                }
                i++;
            }while (i!= schemat.length);

        }

        row.addView(nazwaTextView);

        EditText poczatekEditText = new EditText(getContext());
        poczatekEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        poczatekEditText.setGravity(Gravity.CENTER);
        poczatekEditText.setTextSize(22);
        poczatekEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        poczatekEditText.setTextColor(Color.WHITE);
        poczatekEditText.setText(String.valueOf(cecha.getWartPo()));
        row.addView(poczatekEditText);

        LinearLayout rozwLayout = new LinearLayout(getContext());
        rozwLayout.setOrientation(LinearLayout.HORIZONTAL);
        rozwLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        Button minusButton = new Button(getContext());
        minusButton.setText("-");
        minusButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        minusButton.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
        minusButton.setTextSize(22);
        minusButton.setPadding(16, 8, 16, 8);
        rozwLayout.addView(minusButton);

        TextView rozwTextView = new TextView(getContext());
        rozwTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        rozwTextView.setGravity(Gravity.CENTER);
        rozwTextView.setTextSize(22);
        rozwTextView.setTextColor(Color.WHITE);
        rozwTextView.setText(String.valueOf(cecha.getRozw()));
        rozwLayout.addView(rozwTextView);

        Button plusButton = new Button(getContext());
        plusButton.setText("+");
        plusButton.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        plusButton.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_light));
        plusButton.setTextSize(22);
        plusButton.setPadding(16, 8, 16, 8);
        rozwLayout.addView(plusButton);

        row.addView(rozwLayout);

        TextView aktualnaTextView = new TextView(getContext());
        aktualnaTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        aktualnaTextView.setGravity(Gravity.CENTER);
        aktualnaTextView.setTextSize(22);
        aktualnaTextView.setTextColor(Color.WHITE);
        aktualnaTextView.setText(String.valueOf(cecha.getWartPo() + cecha.getRozw()));
        row.addView(aktualnaTextView);

        minusButton.setOnClickListener(v -> {
            int currentRozw = cecha.getRozw();
            if (currentRozw > 0) {
                cecha.setRozw(currentRozw - 1);
                rozwTextView.setText(String.valueOf(cecha.getRozw()));
                aktualnaTextView.setText(String.valueOf(cecha.getWartPo() + cecha.getRozw()));
                updateCecha(db, cecha, kartaId);
            }
        });

        plusButton.setOnClickListener(v -> {
            int currentRozw = cecha.getRozw();
            cecha.setRozw(currentRozw + 1);
            rozwTextView.setText(String.valueOf(cecha.getRozw()));
            aktualnaTextView.setText(String.valueOf(cecha.getWartPo() + cecha.getRozw()));
            updateCecha(db, cecha, kartaId);
        });

        poczatekEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int poczatekValue = Integer.parseInt(poczatekEditText.getText().toString());
                    cecha.setWartPo(poczatekValue);
                    aktualnaTextView.setText(String.valueOf(cecha.getWartPo() + cecha.getRozw()));
                    updateCecha(db, cecha, kartaId);
                } catch (NumberFormatException e) {
                }
            }
        });

        dynamicCechyLayout.addView(row);
    }

    int getCechaFromKrta(SQLiteDatabase db){
        String query = "SELECT COUNT(*) FROM karta_cecha";

        SQLiteStatement statement = db.compileStatement(query);
      return  (int)statement.simpleQueryForLong();

    }
    List<Cechy> getCechy(SQLiteDatabase db, int id){

        String[] projection = { "nazwa" };
        String[] colums={"*"};
        String sortOrder = "cechy_id ASC";
        String[] selectionArgs={String.valueOf(id)};
        Cursor cursor = db.query("karta_cecha", colums, "karta_id = ?", selectionArgs, null, null,  sortOrder);

        List<Cechy> list = new ArrayList<>();

        while (cursor.moveToNext()) {
           Cechy cecha = new Cechy();

            cecha.setId(cursor.getInt(cursor.getColumnIndexOrThrow("cechy_id")));
            cecha.setWartPo(cursor.getInt(cursor.getColumnIndexOrThrow("wartość_pociątkowa")));
            cecha.setRozw(cursor.getInt(cursor.getColumnIndexOrThrow("rozwój")));
            cecha.setLvlUp(cursor.getInt(cursor.getColumnIndexOrThrow("lvl_up")));


            String[] selectionArgs2={String.valueOf(cecha.getId())};
            Cursor cursor2 = db.query("cechy", colums, "id = ?", selectionArgs2, null, null,  null);

            if (cursor2.moveToFirst()){
                cecha.setNazwaKrotka(cursor2.getString(cursor2.getColumnIndexOrThrow("nazwa_krótka")));

            }
            list.add(cecha);

        }
        cursor.close();

        return list;
    }

    void updateCecha(SQLiteDatabase db, Cechy cecha, int idKarta){
        ContentValues values = new ContentValues();
        values.put("wartość_pociątkowa", cecha.getWartPo());
        values.put("rozwój", cecha.getRozw());

        String selection = "karta_id = ? AND cechy_id = ?" ;
        String[] selectionArgs = {String.valueOf(idKarta), String.valueOf(cecha.getId())};

        db.update("karta_cecha",values,selection,selectionArgs);

    }

   int[] getProfesjaSchemat(SQLiteDatabase db, int id){
        String[] colums1={"*"};
        String[] selectionArgs1={String.valueOf(id)};

        Cursor cursor1 = db.query("karta", colums1, "id = ?",selectionArgs1, null, null, null);

        Karta karta = null;

        if (cursor1.moveToFirst()) {
            karta = new Karta();
            karta.setProfesjaId(cursor1.getInt(cursor1.getColumnIndexOrThrow("poziom_id")));
        }

        cursor1.close();
        PoziomProfesja poziomProfesjaprofesja = new PoziomProfesja();

        String[] colums={"*"};
        assert karta != null;
        String[] selectionArgs={String.valueOf(karta.getProfesjaId())};


        Cursor cursor = db.query("poziom", colums, "id = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            poziomProfesjaprofesja.setSchematCech(cursor.getString(cursor.getColumnIndexOrThrow("schemat_cech")));
        }
        cursor.close();

        return poziomProfesjaprofesja.getSchematCechTabel();

    }
}
