package pl.edu.us.warhammer_card.ui.karta.KartaFront;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

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
import pl.edu.us.warhammer_card.databinding.FragmentKartaFrontBinding;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.PoziomProfesja;
import pl.edu.us.warhammer_card.table.Profesja;
import pl.edu.us.warhammer_card.table.Rasa;

public class KartaFrontFragment extends Fragment {


    private FragmentKartaFrontBinding binding;
    Karta karta = new Karta();
    String rasa;
    int kartaId;


    @SuppressLint({"ClickableViewAccessibility", "SuspiciousIndentation", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


       // setHasOptionsMenu(true);
        //  View view = inflater.inflate(R.layout.fragment_karta_front, container, false);


        binding = FragmentKartaFrontBinding.inflate(inflater, container, false);

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId = args.getInt("KartaId");

        karta=getKarta(db,kartaId);

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putInt("KartaId", karta.getId());
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_front_to_fragment_karta_cechy,args);

            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putInt("KartaId", karta.getId());
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_front_to_fragment_karta_umiejetnosci,args);

            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("KartaId", karta.getId());
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_front_to_fragment_karta_umiejetnosci2,args);
            }
        });


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
                String name = binding.imie.getText().toString();
                String wiek = binding.wiek.getText().toString();


                String wzrost = binding.wzrost.getText().toString();
                String wlosy = binding.wlosy.getText().toString();
                String oczy = binding.oczy.getText().toString();

              //  karta.setImie(name);
//                karta.setWiek(wiek);

                if (binding.imie.getText() == editable) {

                    karta.setImie(name);
                    updateKarta(db,karta);

                } else if (binding.wiek.getText() == editable) {

                    karta.setWiek(wiek);
                    updateKarta(db,karta);

                } else if (binding.wzrost.getText() == editable) {

                    karta.setWzrost(wzrost);
                    updateKarta(db,karta);

                } else if (binding.wlosy.getText() == editable) {

                    karta.setWlosy(wlosy);
                    updateKarta(db,karta);

                } else if (binding.oczy.getText() == editable) {

                    karta.setOczy(oczy);
                    updateKarta(db,karta);

                }
            }
        };

        {

            binding.imie.addTextChangedListener(textWatcher);
            binding.wiek.addTextChangedListener(textWatcher);
            binding.wzrost.addTextChangedListener(textWatcher);
            binding.wlosy.addTextChangedListener(textWatcher);
            binding.oczy.addTextChangedListener(textWatcher);


            binding.imie.setText(karta.getImie());
            binding.wiek.setText(karta.getWiek());
            binding.wzrost.setText(karta.getWzrost());
            binding.wlosy.setText(karta.getWlosy());
            binding.oczy.setText(karta.getOczy());
        }


        List<Rasa> listRasa;
        listRasa= getListRasa(db);

        binding.rasa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Wybierz Rase");

            String[] listaElementow = new String[listRasa.size()];
            int i =0;
            for (Rasa rasa: listRasa) {
                listaElementow[i]=rasa.getName();
                i++;
            }
            // Przygotowanie danych dla dialogu wyboru

            // Ustawienie listy elementów w dialogu
            builder.setItems(listaElementow, (dialog, which) -> {
                String wybranyElement = listaElementow[which];
                binding.rasa.setText(wybranyElement);

                this.karta.setRasaId(getIdRasaForName(db,wybranyElement));
                binding.profesja.setText("");
                binding.klasa.setText("");
                binding.poziomProfesji.setText("");
                binding.sciezkaProfesji.setText("");
                binding.status.setText("");
                Log.d("id",String.valueOf(this.karta.getRasaId()));
                updateKarta(db,this.karta);
            });
            builder.show();
        });

        binding.profesja.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("Wybierz Profecje");

            List<Profesja> listProfesja;
            listProfesja= getListProfesja(db,karta);

            String[] listaElementow = new String[listProfesja.size()];
            int i =0;
            for (Profesja profesja: listProfesja) {
                listaElementow[i]=profesja.getNazawa();
                i++;
            }
            // Przygotowanie danych dla dialogu wyboru

            // Ustawienie listy elementów w dialogu
            builder.setItems(listaElementow, (dialog, which) -> {
                String wybranyElement = listaElementow[which];
                binding.profesja.setText(wybranyElement);

                binding.poziomProfesji.setText("");
                binding.status.setText("");

                karta.setProfesjaId(getIdProfesjaForName(db,wybranyElement));
                binding.klasa.setText(getKlasaName(db, getProfesja(db,karta.getProfesjaId()).getKlasaId()));
                binding.sciezkaProfesji.setText(getSciezkaProfesji(db,karta.getProfesjaId()));
                updateKarta(db,karta);
            });

            // Wyświetlenie dialogu
            builder.show();

        });

        binding.poziomProfesji.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("Wybierz Poziom");

            List<PoziomProfesja> listPoziomProfesja;
            listPoziomProfesja= getListPoziomProfesja(db,karta.getProfesjaId());

            String[] listaElementow = new String[listPoziomProfesja.size()];
            int i =0;
            for (PoziomProfesja poziom: listPoziomProfesja) {
                listaElementow[i]=poziom.getNazwa();
                i++;
            }
            // Przygotowanie danych dla dialogu wyboru

            // Ustawienie listy elementów w dialogu
            builder.setItems(listaElementow, (dialog, which) -> {
                String wybranyElement = listaElementow[which];

                binding.poziomProfesji.setText(wybranyElement);

                karta.setPoziomProfesjiId(getIdPoziomProfesjaForName(db,wybranyElement));

                binding.status.setText( getStatus(db,karta.getPoziomProfesjiId()));
                updateKarta(db,karta);
            });

            // Wyświetlenie dialogu
            builder.show();

        });


        binding.rasa.setText(getRasaName(db,karta.getRasaId()));
        binding.profesja.setText(getProfesja(db,karta.getProfesjaId()).getNazawa());
        binding.poziomProfesji.setText(getPoziomProfesja(db,karta.getPoziomProfesjiId()).getNazwa());


        binding.klasa.setText(getKlasaName(db, getProfesja(db,karta.getProfesjaId()).getKlasaId()));
        binding.status.setText(getStatus(db,karta.getPoziomProfesjiId()));
        binding.sciezkaProfesji.setText(getSciezkaProfesji(db,karta.getProfesjaId()));



        View root = binding.getRoot();
        return root;
    }




    String getSciezkaProfesji(SQLiteDatabase db,int profesjaId){
        String sciezka="";

        List<PoziomProfesja> list = getListPoziomProfesja(db,profesjaId);
        int i =1;
        for (PoziomProfesja poziom: list) {
                if (i!=list.size()){
                    sciezka=sciezka + poziom.getNazwa()+"->";
                }else {
                    sciezka=sciezka + poziom.getNazwa();
                }
                i++;

        }
        return sciezka;
    }

    int getIdPoziomProfesjaForName(SQLiteDatabase db,String name){
        int id=0;


        String[] colums={"*"};
        String[] selectionArgs={name};

        Cursor cursor = db.query("poziom_profesji", colums, "nazwa = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            id=cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }

        cursor.close();

        return id;
    }

    String getStatus(SQLiteDatabase db,int profesjaId){
        String status=null;

        String[] colums={"*"};
        String[] selectionArgs={String.valueOf(profesjaId)};
        String query = "SELECT poziom_profesji.id,poziom_profesji.status_id, status.nazwa,status.id " +
                "FROM poziom_profesji " +
                "JOIN status ON poziom_profesji.status_id = status.id "  +
                "WHERE poziom_profesji.id = ? ";
        Cursor cursor = db.rawQuery(query, selectionArgs);


      //  Cursor cursor = db.query("status", colums, "id = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
           status=cursor.getString(cursor.getColumnIndexOrThrow("nazwa"));
        }
        cursor.close();
        return status;
    }
    int getIdProfesjaForName(SQLiteDatabase db,String name){
        int id=0;


        String[] colums={"*"};
        String[] selectionArgs={name};

        Cursor cursor = db.query("profesja", colums, "nazwa = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            id=cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }

        cursor.close();

        return id;
    }
    int getIdRasaForName(SQLiteDatabase db,String name){
        int id=0;


        String[] colums={"*"};
        String[] selectionArgs={name};
        // String sortOrder = "nazwa ASC";
        // String[] id ={"3"};

        Cursor cursor = db.query("rasa", colums, "name = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
           id=cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }

        cursor.close();

        return id;
    }

    List<Profesja> getListProfesja(SQLiteDatabase db,Karta karta){
        String[] colums={"*"};
        String[] selectionArgs = {String.valueOf(karta.getRasaId())};

        String query = "SELECT profesja.id, profesja.klasa_id, profesja.nazwa, rasa_profesja.rasa_id,  rasa_profesja.profesja_id " +
                "FROM  rasa_profesja  " +
                "JOIN profesja ON profesja.id = rasa_profesja.profesja_id " +
                "WHERE rasa_profesja.rasa_id = ?";

        Cursor cursor = db.rawQuery(query, selectionArgs);

       // Cursor cursor = db.query("rasa", colums, null,null, null, null, null);

        List<Profesja> list = new ArrayList<>();

        while (cursor.moveToNext()) {
           Profesja  profesja= new Profesja();
            profesja.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            profesja.setNazawa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            profesja.setKlasaId(cursor.getInt(cursor.getColumnIndexOrThrow("klasa_id")));

            list.add(profesja);
        }

        cursor.close();

        return list;

    }
    List<Rasa> getListRasa(SQLiteDatabase db){

        String[] colums={"*"};

        Cursor cursor = db.query("rasa", colums, null,null, null, null, null);

        List<Rasa> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Rasa  rasa= new Rasa();
            rasa.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            rasa.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            rasa.setWartoscMin(cursor.getInt(cursor.getColumnIndexOrThrow("wartość_min")));
            rasa.setWartoscMax(cursor.getInt(cursor.getColumnIndexOrThrow("wartość_max")));

            list.add(rasa);
        }

        cursor.close();

        return list;
    }
    void updateKarta(SQLiteDatabase db,Karta karta){

        ContentValues values = new ContentValues();
        values.put("imie", karta.getImie());
        values.put("wiek", karta.getWiek());
        values.put("wzrost", karta.getWzrost());
        values.put("wlosy", karta.getWlosy());
        values.put("oczy", karta.getOczy());
        values.put("rasa_id", karta.getRasaId());
        values.put("profesja_id", karta.getProfesjaId());
        values.put("poziom_profesji_id", karta.getPoziomProfesjiId());

        Log.d("up" ,karta.getImie()+" "+karta.getWiek()+" "+karta.getWzrost()+" "+karta.getWlosy()+" "+karta.getOczy());

        String selection = "id = ?";
        String[] selectionArgs={String.valueOf(karta.getId())};

        db.update("karta",values,selection,selectionArgs);

    }
    Karta getKarta(SQLiteDatabase db,int id){

        String[] projection = { "kampania_id" };
        String[] colums={"*"};
        String[] selectionArgs={String.valueOf(id)};
        // String sortOrder = "nazwa ASC";
        // String[] id ={"3"};

        Cursor cursor = db.query("karta", colums, "id = ?",selectionArgs, null, null, null);

       Log.d("KartaID", Integer.toString(id));

        Karta karta = null;

        if (cursor.moveToFirst()) {
            karta = new Karta();
            karta.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            karta.setProfesjaId(cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id")));
            karta.setRasaId(cursor.getInt(cursor.getColumnIndexOrThrow("rasa_id")));
            karta.setPoziomProfesjiId(cursor.getInt(cursor.getColumnIndexOrThrow("poziom_profesji_id")));
            karta.setImie(cursor.getString(cursor.getColumnIndexOrThrow("imie")));

            karta.setWiek(cursor.getString(cursor.getColumnIndexOrThrow("wiek")));
            karta.setWzrost(cursor.getString(cursor.getColumnIndexOrThrow("wzrost")));
            karta.setWlosy(cursor.getString(cursor.getColumnIndexOrThrow("wlosy")));
            karta.setOczy(cursor.getString(cursor.getColumnIndexOrThrow("oczy")));

            Log.d("Karta", String.valueOf(karta.getId()));
        }

        cursor.close();

        return karta;
    }
    String getRasaName(SQLiteDatabase db,int id){
        String name=null;

        String[] colums={"*"};
        String[] selectionArgs={String.valueOf(id)};
        // String sortOrder = "nazwa ASC";
        // String[] id ={"3"};

        Cursor cursor = db.query("rasa", colums, "id = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        }

        if (name==null){
            name="";
        }
        cursor.close();
        return name;
    }

    Profesja getProfesja(SQLiteDatabase db, int id){

     /*   String[] columns = {"id"};
        Cursor cursor = db.query("profesja", columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id2 = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                // Wykonaj odpowiednie działania na identyfikatorze
                // np. wyświetl go w logach lub zapisz do listy
                Log.d("Identifiers", "Profesja ID: " + id2);
            } while (cursor.moveToNext());
        }

        cursor.close();*/
        Profesja profesja = new Profesja();

        String[] colums={"*"};
        String[] selectionArgs={String.valueOf(id)};
        // String sortOrder = "nazwa ASC";


        Cursor cursor = db.query("profesja", colums, "id = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            profesja.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            profesja.setNazawa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            profesja.setKlasaId(cursor.getInt(cursor.getColumnIndexOrThrow("klasa_id")));

        }

        if (profesja.getNazawa()==null){
            profesja.setNazawa("");
        }
        cursor.close();
        return profesja;
    }

    String getKlasaName(SQLiteDatabase db,int id){
        String name = null;


        String[] colums={"*"};
        String[] selectionArgs={String.valueOf(id)};
        // String sortOrder = "nazwa ASC";
        // String[] id ={"3"};

        Cursor cursor = db.query("klasa", colums, "id = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow("nazwa"));
        }

        if (name==null){
            name="";
        }
        cursor.close();
        return name;
    }

    List<PoziomProfesja>getListPoziomProfesja(SQLiteDatabase db,int profesjaID){

        String[] colums={"id","nazwa","profesja_id","status_id"};

        String[] selectionArgs={String.valueOf(profesjaID)};

        Cursor cursor = db.query("poziom_profesji", colums, "profesja_id = ?",selectionArgs, null, null, null);


        List<PoziomProfesja> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            PoziomProfesja  poziom= new PoziomProfesja();
            poziom.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            poziom.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            poziom.setProfesjaId(cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id")));
            poziom.setStatusId(cursor.getInt(cursor.getColumnIndexOrThrow("status_id")));

            list.add(poziom);
        }

        cursor.close();

        return list;
    }

    PoziomProfesja getPoziomProfesja(SQLiteDatabase db,int poziomProfesjaId){
        PoziomProfesja poziomProfesja = new  PoziomProfesja();

        String[] colums={"*"};
        String[] selectionArgs={String.valueOf(poziomProfesjaId)};
        // String sortOrder = "nazwa ASC";


        Cursor cursor = db.query("poziom_profesji", colums, "id = ?",selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            poziomProfesja.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            poziomProfesja.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            poziomProfesja.setStatusId(cursor.getInt(cursor.getColumnIndexOrThrow("status_id")));

        }
        cursor.close();
        return  poziomProfesja;
    }
}
