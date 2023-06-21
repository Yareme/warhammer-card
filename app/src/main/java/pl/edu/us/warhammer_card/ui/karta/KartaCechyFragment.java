package pl.edu.us.warhammer_card.ui.karta;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.databinding.FragmentKartaCechyBinding;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Kampania;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Umiejetnosci;

public class KartaCechyFragment extends Fragment{
    FragmentKartaCechyBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentKartaCechyBinding.inflate(inflater, container, false);


        TextView[] nazwaArrayBinding = {
                binding.nazwa1,
                binding.nazwa2,
                binding.nazwa3,
                binding.nazwa4,
                binding.nazwa5,
                binding.nazwa6,
                binding.nazwa7,
                binding.nazwa8,
                binding.nazwa9,
                binding.nazwa10,

        };
        TextView[] poczatekArrayBinding = {
                binding.poczatek1,
                binding.poczatek2,
                binding.poczatek3,
                binding.poczatek4,
                binding.poczatek5,
                binding.poczatek6,
                binding.poczatek7,
                binding.poczatek8,
                binding.poczatek9,
                binding.poczatek10

        };

        TextView[] rozwojArrayBinding = {
                binding.rozwoj1,
                binding.rozwoj2,
                binding.rozwoj3,
                binding.rozwoj4,
                binding.rozwoj5,
                binding.rozwoj6,
                binding.rozwoj7,
                binding.rozwoj8,
                binding.rozwoj9,
                binding.rozwoj10

        };

        TextView[] aktualnaArrayBinding = {
                binding.aktualna1,
                binding.aktualna2,
                binding.aktualna3,
                binding.aktualna4,
                binding.aktualna5,
                binding.aktualna6,
                binding.aktualna7,
                binding.aktualna8,
                binding.aktualna9,
                binding.aktualna10

        };

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

       List<Cechy> list = getCechy(db,kartaId);

        for (int i = 0; i < list.size(); i++) {
           nazwaArrayBinding[i].setText(list.get(i).getNazwaKrotka());

        }


       TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Metoda wywoływana przed zmianą tekstu
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Metoda wywoływana w trakcie zmiany tekstu
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String[] rozwojArray = {
                        binding.rozwoj1.getText().toString(),
                        binding.rozwoj2.getText().toString(),
                        binding.rozwoj3.getText().toString(),
                        binding.rozwoj4.getText().toString(),
                        binding.rozwoj5.getText().toString(),
                        binding.rozwoj6.getText().toString(),
                        binding.rozwoj7.getText().toString(),
                        binding.rozwoj8.getText().toString(),
                        binding.rozwoj9.getText().toString(),
                        binding.rozwoj10.getText().toString()

                };


                String[] poczatekArray = {
                        binding.poczatek1.getText().toString(),
                        binding.poczatek2.getText().toString(),
                        binding.poczatek3.getText().toString(),
                        binding.poczatek4.getText().toString(),
                        binding.poczatek5.getText().toString(),
                        binding.poczatek6.getText().toString(),
                        binding.poczatek7.getText().toString(),
                        binding.poczatek8.getText().toString(),
                        binding.poczatek9.getText().toString(),
                        binding.poczatek10.getText().toString()

                };



                for (int i = 0; i < poczatekArray.length; i++) {
                    if (poczatekArrayBinding[i].getText() == editable) {
                        try {
                            list.get(i).setWartPo(Integer.parseInt(poczatekArray[i]));
                            updateCecha(db, list.get(i), kartaId);
                        } catch (Exception e) {
                            list.get(i).setWartPo(0);
                            updateCecha(db, list.get(i), kartaId);
                        }
                    }else if(rozwojArrayBinding[i].getText() == editable){

                        try {
                            list.get(i).setRozw(Integer.parseInt(rozwojArray[i]));
                            updateCecha(db, list.get(i), kartaId);
                        }catch (Exception e){

                            list.get(i).setRozw(0);
                            updateCecha(db, list.get(i), kartaId);
                        }
                    }

                    aktualnaArrayBinding[i].setText(String.valueOf(list.get(i).getWartPo()+ list.get(i).getRozw()));
                }


            }
        };



        for (int i = 0; i<poczatekArrayBinding.length;i++){
            poczatekArrayBinding[i].addTextChangedListener(textWatcher);
        }

        for (int i = 0; i<rozwojArrayBinding.length;i++){
            rozwojArrayBinding[i].addTextChangedListener(textWatcher);
        }

        for (int i = 0; i<poczatekArrayBinding.length;i++){
                poczatekArrayBinding[i].setText((String.valueOf(list.get(i).getWartPo())));
            }

        for (int i = 0; i<rozwojArrayBinding.length;i++){
                rozwojArrayBinding[i].setText(String.valueOf(list.get(i).getRozw()));
            }


        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putInt("KartaId",kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();

                navController.navigate(R.id.action_fragment_karta_cechy_to_fragment_karta_front,args);
               /* String[] a ={"1"};
                db.delete("karta",null,null);*/
            }
        });


        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("KartaId", kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_cechy_to_fragment_karta_front,args);

            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putInt("KartaId", kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();

                navController.navigate(R.id.action_fragment_karta_cechy_to_fragment_karta_umiejetnosci,args);

            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("KartaId", kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_cechy_to_fragment_karta_umiejetnosci2,args);
            }
        });



        View root = binding.getRoot();
        return root;
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

}
