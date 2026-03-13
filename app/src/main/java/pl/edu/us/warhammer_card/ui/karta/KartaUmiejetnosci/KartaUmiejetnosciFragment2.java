package pl.edu.us.warhammer_card.ui.karta.KartaUmiejetnosci;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.adapter.UmiejetnoscAdapter2;
import pl.edu.us.warhammer_card.databinding.AddWlasneWyposazeniaDialogBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaUmiejetnosci2Binding;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.PoziomProfesja;
import pl.edu.us.warhammer_card.table.Umiejetnosci;
import pl.edu.us.warhammer_card.table.Wyposarzenia;

public class KartaUmiejetnosciFragment2 extends Fragment {

    FragmentKartaUmiejetnosci2Binding binding;

    UmiejetnoscAdapter2 adapter;
    Karta karta;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaUmiejetnosci2Binding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

        karta = dbHelper.getKartaById(db, kartaId);

        adapter = new UmiejetnoscAdapter2(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        UmiejetnosciViewModel2 umiejetnosciViewModel2 =
                new ViewModelProvider(this).get(UmiejetnosciViewModel2.class);
        umiejetnosciViewModel2.getAllZaawansowaneUmiejetnosciForKarta(db, kartaId);

        umiejetnosciViewModel2.getUmiejetnoscLiveData().observe(getViewLifecycleOwner(), adapter::updateList);

        adapter.setOnMinusClickListener(umiejetnosci -> {
            int currentRozw = umiejetnosci.getRozwoj();
            if (currentRozw > 0) {
                umiejetnosci.setRozwoj(currentRozw - 1);
                umiejetnosciViewModel2.updateUmiejetnosc(db, umiejetnosci, kartaId);
            }
        });

        adapter.setOnPlusClickListener(umiejetnosci -> {
            int currentRozw = umiejetnosci.getRozwoj();
            umiejetnosci.setRozwoj(currentRozw + 1);
            umiejetnosciViewModel2.updateUmiejetnosc(db, umiejetnosci, kartaId);
        });

        List<Cechy> listCecha = getCechy(db, kartaId);
        List<Umiejetnosci> listUmiejetnosci = getUmiejetnosciToKarta(db, kartaId);

        for (Umiejetnosci umiejetnosc : listUmiejetnosci) {
            String cechaNazwa = umiejetnosc.getCechaNazwa();
            for (Cechy cecha : listCecha) {
                if (cechaNazwa.equals(cecha.getNazwaKrotka())) {
                    umiejetnosc.setWortascCecha(cecha.getWartPo() + cecha.getRozw());
                    break;
                }
            }
        }



        adapter.setOnItemLongClickListener(umiejetnosci -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeleteUmiejtnosc(db, umiejetnosci, kartaId));

            String[] optionsArray = options.toArray(new String[0]);

            new AlertDialog.Builder(requireContext())
                    .setTitle("Wybierz akcję")
                    .setItems(optionsArray, (dialog, which) -> {
                        String selectedOption = optionsArray[which];
                        if (actions.containsKey(selectedOption)) {
                            actions.get(selectedOption).run();
                        }
                    })
                    .show();
        });

        binding.wszystkieZaawansowane.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("Dodaj Umiejęntność");
            List<Umiejetnosci> listUmiejetnosciFromDialog = dbHelper.getAllZaawansowaneUmijetnosci(db);
            String[] listaElementow = new String[listUmiejetnosciFromDialog.size()];
            int i = 0;
            for (Umiejetnosci umiejetnosc : listUmiejetnosciFromDialog) {
                listaElementow[i] = umiejetnosc.getNazwa();
                i++;
            }
            builder.setItems(listaElementow, (dialog, which) -> {
                String wybranyElement = listaElementow[which];
                int idUmiejetnosci = getUmiejetnosciIdByName(db, wybranyElement);

                addUmiejetnosciToKart(db, idUmiejetnosci, kartaId);

                Umiejetnosci umiejetnoscs = getUmiejetnosc(db, kartaId, idUmiejetnosci);
                String cechaNazwa = umiejetnoscs.getCechaNazwa();

                for (Cechy cecha : listCecha) {
                    if (cechaNazwa.equals(cecha.getNazwaKrotka())) {
                        umiejetnoscs.setWortascCecha(cecha.getWartPo() + cecha.getRozw());
                        break;
                    }
                }

                List<Umiejetnosci> newList = umiejetnosciViewModel2.getAllZaawansowaneUmiejetnosciForKarta(db,kartaId);
                adapter.updateList(newList);


            });
            builder.show();

        });


        binding.zaawansowaneProfesji.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("Dodaj Umiejęntność");

            int[] schemat = getProfesjaSchemat(db, kartaId);
            if (schemat == null){
                schemat = new int[0];
            }

            List<Umiejetnosci> listUmiejetnosciFromDialog = getZaawansowaneForProfesja(db, schemat);
            String[] listaElementow = new String[listUmiejetnosciFromDialog.size()];
            int i = 0;
            for (Umiejetnosci umiejetnosc : listUmiejetnosciFromDialog) {
                listaElementow[i] = umiejetnosc.getNazwa();
                i++;
            }
            builder.setItems(listaElementow, (dialog, which) -> {
                String wybranyElement = listaElementow[which];
                int idUmiejetnosci = getUmiejetnosciIdByName(db, wybranyElement);

                addUmiejetnosciToKart(db, idUmiejetnosci, kartaId);

                Umiejetnosci umiejetnoscs = getUmiejetnosc(db, kartaId, idUmiejetnosci);
                String cechaNazwa = umiejetnoscs.getCechaNazwa();

                for (Cechy cecha : listCecha) {
                    if (cechaNazwa.equals(cecha.getNazwaKrotka())) {
                        umiejetnoscs.setWortascCecha(cecha.getWartPo() + cecha.getRozw());
                        break;
                    }
                }
                List<Umiejetnosci> newList = umiejetnosciViewModel2.getAllZaawansowaneUmiejetnosciForKarta(db,kartaId);
                adapter.updateList(newList);

            });
            builder.show();

        });

        return binding.getRoot();
    }

    private void confirmDeleteUmiejtnosc(SQLiteDatabase db, Umiejetnosci umiejetnosci, int kartaId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć?")
                .setPositiveButton("Tak", (dialog, which) -> deleteUmiejtnosc(db, umiejetnosci, kartaId))
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void deleteUmiejtnosc(SQLiteDatabase db, Umiejetnosci umiejetnosci, int kartaId) {
        db.delete("karta_umiętność", "umiejętności_id=? AND karta_id=?", new String[]{String.valueOf(umiejetnosci.getId()), String.valueOf(kartaId)});
        List<Umiejetnosci> currentList = new ArrayList<>(adapter.getUmiejetnosciList());
        currentList.remove(umiejetnosci);

        adapter.updateList(currentList);
    }

    List<Umiejetnosci> getZaawansowaneForProfesja(SQLiteDatabase db, int[] schemat) {
        List<Umiejetnosci> list = new ArrayList<>();

        String query = "SELECT  id, nazwa " +
                "FROM umiejętności " +
                "WHERE id = ?  AND czy_zaawansowana = 1 ";

        for (int i = 0; i < schemat.length; i++) {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(schemat[i])});

            if (cursor.moveToNext()) {
                Umiejetnosci umiejetnosci = new Umiejetnosci();

                umiejetnosci.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                umiejetnosci.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));

                list.add(umiejetnosci);
            }
            cursor.close();
        }

        return list;

    }

    int getUmiejetnosciIdByName(SQLiteDatabase db, String nazwa) {
        int id = 0;
        String[] selectionArgs = {String.valueOf(nazwa)};
        String query = "SELECT  id, nazwa " +
                "FROM umiejętności " +
                "WHERE nazwa = ? ";

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }
        cursor.close();
        return id;
    }

    private void addUmiejetnosciToKart(SQLiteDatabase db, int idUmiejetnosci, int kartaId) {
        ContentValues values = new ContentValues();

        values.put("karta_id", kartaId);
        values.put("umiejętności_id", idUmiejetnosci);
        values.put("rozwój", 0);
        db.insert("karta_umiętność", null, values);
    }
    private int[] getProfesjaSchemat(SQLiteDatabase db, int id) {
        String[] colums1 = {"*"};
        String[] selectionArgs1 = {String.valueOf(id)};

        Cursor cursor1 = db.query("karta", colums1, "id = ?", selectionArgs1, null, null, null);

        Karta karta = null;

        if (cursor1.moveToFirst()) {
            karta = new Karta();
            karta.setProfesjaId(cursor1.getInt(cursor1.getColumnIndexOrThrow("poziom_id")));
        }

        cursor1.close();
        PoziomProfesja poziomProfesjaprofesja = new PoziomProfesja();

        String[] colums = {"*"};
        assert karta != null;
        String[] selectionArgs = {String.valueOf(karta.getProfesjaId())};


        Cursor cursor = db.query("poziom", colums, "id = ?", selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            poziomProfesjaprofesja.setSchematCech(cursor.getString(cursor.getColumnIndexOrThrow("schemat_umiejetnosci")));
        }
        cursor.close();

        return poziomProfesjaprofesja.getSchematCechTabel();

    }

    private Umiejetnosci getUmiejetnosc(SQLiteDatabase db, int idKarta, int idUm) {

        String[] selectionArgs = {String.valueOf(idKarta), String.valueOf(idUm)};

        String query = "SELECT karta_umiętność.rozwój, umiejętności.nazwa, cechy.nazwa_krótka, umiejętności.id " +
                "FROM karta_umiętność " +
                "JOIN umiejętności ON karta_umiętność.umiejętności_id = umiejętności.id " +
                "JOIN cechy ON umiejętności.cechy_id = cechy.id " +
                "WHERE karta_umiętność.karta_id = ? AND  karta_umiętność.umiejętności_id = ?";

        Cursor cursor = db.rawQuery(query, selectionArgs);
        Umiejetnosci umiejetnosc =null;

        if (cursor != null && cursor.moveToFirst()) {
            umiejetnosc = new Umiejetnosci();
            umiejetnosc.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            umiejetnosc.setRozwoj(cursor.getInt(cursor.getColumnIndexOrThrow("rozwój")));
            umiejetnosc.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            umiejetnosc.setCechaNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa_krótka")));
        }
        cursor.close();

        return umiejetnosc;
    }

    private List<Cechy> getCechy(SQLiteDatabase db, int id) {

        String[] projection = {"nazwa"};
        String[] colums = {"*"};
        String sortOrder = "cechy_id ASC";

        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query("karta_cecha", colums, "karta_id = ?", selectionArgs, null, null, sortOrder);

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

    private List<Umiejetnosci> getUmiejetnosciToKarta(SQLiteDatabase db, int id) {

        String[] selectionArgs = {String.valueOf(id)};

        String query = "SELECT karta_umiętność.rozwój, umiejętności.nazwa, cechy.nazwa_krótka, umiejętności.id " +
                "FROM karta_umiętność " +
                "JOIN umiejętności ON karta_umiętność.umiejętności_id = umiejętności.id " +
                "JOIN cechy ON umiejętności.cechy_id = cechy.id " +
                "WHERE karta_umiętność.karta_id = ? AND umiejętności.czy_zaawansowana = 1 ";
        Cursor cursor = db.rawQuery(query, selectionArgs);

        List<Umiejetnosci> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Umiejetnosci umiejetnosci = new Umiejetnosci();

            umiejetnosci.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            umiejetnosci.setRozwoj(cursor.getInt(cursor.getColumnIndexOrThrow("rozwój")));
            umiejetnosci.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            umiejetnosci.setCechaNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa_krótka")));

            list.add(umiejetnosci);

            Log.d("UM",String.valueOf(umiejetnosci.getRozwoj()+" "+umiejetnosci.getNazwa()+" "+umiejetnosci.getCechaNazwa()));

        }
        cursor.close();

        return list;
    }
}
