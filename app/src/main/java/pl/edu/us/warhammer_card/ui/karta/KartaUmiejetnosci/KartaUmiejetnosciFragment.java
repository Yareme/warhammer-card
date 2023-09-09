package pl.edu.us.warhammer_card.ui.karta.KartaUmiejetnosci;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pl.edu.us.warhammer_card.databinding.FragmentKartaBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaFrontBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaUmiejetnosci2Binding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaUmiejetnosciBinding;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Profesja;
import pl.edu.us.warhammer_card.table.Umiejetnosci;

public class KartaUmiejetnosciFragment extends Fragment {

    FragmentKartaUmiejetnosciBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // setHasOptionsMenu(true);
        //  View view = inflater.inflate(R.layout.fragment_karta_front, container, false);


        binding = FragmentKartaUmiejetnosciBinding.inflate(inflater, container, false);

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
                binding.nazwa11,
                binding.nazwa12,
                binding.nazwa13
        };

        TextView[] cechaArray = {
                binding.cecha1,
                binding.cecha2,
                binding.cecha3,
                binding.cecha4,
                binding.cecha5,
                binding.cecha6,
                binding.cecha7,
                binding.cecha8,
                binding.cecha9,
                binding.cecha10,
                binding.cecha11,
                binding.cecha12,
                binding.cecha13
        };

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

        List<Cechy> listCecha = getCechy(db,kartaId);
        List<Umiejetnosci> list = getUmiejetnosci(db,kartaId);

        // int[] sums = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            String cechaNazwa = list.get(i).getCecha_nazwa();
            for (int j = 0; j < listCecha.size(); j++) {
                String nazwaKrotka = listCecha.get(j).getNazwaKrotka();
                if (cechaNazwa.equals(listCecha.get(j).getNazwaKrotka())) {
                    list.get(i).setWortascCecha(listCecha.get(j).getWartPo() + listCecha.get(j).getRozw());
                    Log.d("UMD", " Nazwa czechi z listy cech: " + listCecha.get(j).getNazwaKrotka());
                    break;
                }
            }
        }




        for (int i = 0; i < cechaArray.length; i++) {
            nazwaArrayBinding[i].setText(list.get(i).getNazwa() +"("+ list.get(i).getCecha_nazwa() +")");
            cechaArray[i].setText(String.valueOf(list.get(i).getWortascCecha()));
        }


        TextView[] rozArrayBinding = {
                binding.roz1,
                binding.roz2,
                binding.roz3,
                binding.roz4,
                binding.roz5,
                binding.roz6,
                binding.roz7,
                binding.roz8,
                binding.roz9,
                binding.roz10,
                binding.roz11,
                binding.roz12,
                binding.roz13
        };



        // Log.d("UM",String.valueOf(list.get(1).getRozwoj()+" "+list.get(1).getNazwa()+" "+list.get(1).getCecha_nazwa()));
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



                String[] rozArray = {
                        binding.roz1.getText().toString(),
                        binding.roz2.getText().toString(),
                        binding.roz3.getText().toString(),
                        binding.roz4.getText().toString(),
                        binding.roz5.getText().toString(),
                        binding.roz6.getText().toString(),
                        binding.roz7.getText().toString(),
                        binding.roz8.getText().toString(),
                        binding.roz9.getText().toString(),
                        binding.roz10.getText().toString(),
                        binding.roz11.getText().toString(),
                        binding.roz12.getText().toString(),
                        binding.roz13.getText().toString()
                };

                TextView[] sumaArray = {
                        binding.suma1,
                        binding.suma2,
                        binding.suma3,
                        binding.suma4,
                        binding.suma5,
                        binding.suma6,
                        binding.suma7,
                        binding.suma8,
                        binding.suma9,
                        binding.suma10,
                        binding.suma11,
                        binding.suma12,
                        binding.suma13
                };

                for (int i = 0; i < rozArray.length; i++) {
                    if (rozArrayBinding[i].getText() == editable) {
                        try {
                            list.get(i).setRozwoj(Integer.parseInt(rozArray[i]));
                            updateUmiejetnosci(db, list.get(i), kartaId);
                        } catch (Exception e) {
                            list.get(i).setRozwoj(0);
                            updateUmiejetnosci(db, list.get(i), kartaId);
                        }
                    }
                    sumaArray[i].setText(String.valueOf(list.get(i).getWortascCecha() + list.get(i).getRozwoj()));
                }

            }
        };




        for (int i = 0; i<rozArrayBinding.length;i++){
            rozArrayBinding[i].addTextChangedListener(textWatcher);
        }

        for (int i = 0; i<rozArrayBinding.length;i++){
            rozArrayBinding[i].setText(String.valueOf(list.get(i).getRozwoj()));
        }



        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("KartaId", kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_umiejetnosci_to_fragment_karta_front,args);

            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("KartaId",kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_umiejetnosci_to_fragment_karta_cechy,args);
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putInt("KartaId", kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();

                navController.navigate(R.id.action_fragment_karta_umiejetnosci_to_fragment_karta_umiejetnosci2,args);

            }
        });


        View root = binding.getRoot();
        return root;
    }


    List<Umiejetnosci> getUmiejetnosci(SQLiteDatabase db, int id){

        String[] selectionArgs = {String.valueOf(id)};

        String query = "SELECT karta_umiętność.rozwój, umiejętności.nazwa, cechy.nazwa_krótka, umiejętności.id " +
                "FROM karta_umiętność " +
                "JOIN umiejętności ON karta_umiętność.umiejętności_id = umiejętności.id " +
                "JOIN cechy ON umiejętności.cechy_id = cechy.id " +
                "WHERE karta_umiętność.karta_id = ? LIMIT 13";
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
