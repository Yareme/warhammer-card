package pl.edu.us.warhammer_card.ui.karta.KartaTalenty;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.adapter.TalentAdapter;
import pl.edu.us.warhammer_card.databinding.FragmentKartaTalentBinding;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.PoziomProfesja;
import pl.edu.us.warhammer_card.table.Talent;


public class KartaTalentFragment extends Fragment {

    FragmentKartaTalentBinding binding;
    TalentAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentKartaTalentBinding.inflate(inflater, container, false);


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TalentAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);


        KartaTalentViewModel kartaTalentViewModel =
                new ViewModelProvider(this).get(KartaTalentViewModel.class);
        kartaTalentViewModel.getTalentFromKarta(db, kartaId);

        kartaTalentViewModel.getTalentLiveData().observe(getViewLifecycleOwner(), adapter::updateList);

        binding.wszystkeiTalenty.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Dodaj Talen");

            List<Talent> listTalentFromDialog = getTalentFromDialog(db);

            String[] listaElementow = new String[listTalentFromDialog.size()];
            int i = 0;
            for (Talent talent : listTalentFromDialog) {
                listaElementow[i] = talent.getNazwa();
                i++;
            }

            builder.setItems(listaElementow, (dialog, which) -> {
                Talent wybranyElement = listTalentFromDialog.get(which);

                addTalentToKarta(db, kartaId, wybranyElement.getId());


                List<Talent> newList = kartaTalentViewModel.getTalentFromKarta(db, kartaId);
                adapter.updateList(newList);

            });
            builder.show();
        });

        binding.talentyProfesji.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Dodaj Talent");


                int[] tab = getProfesjaSchemat(db,kartaId);
                if (tab==null){
                    tab = new int[0];
                }


            List<Talent> listTalentFromDialog = getTalentyForProfesja(db, tab);


            String[] listaElementow = new String[listTalentFromDialog.size()];
            int i = 0;
            for (Talent talent : listTalentFromDialog) {
                listaElementow[i] = talent.getNazwa();
                i++;
            }

            builder.setItems(listaElementow, (dialog, which) -> {
                Talent wybranyElement = listTalentFromDialog.get(which);

                addTalentToKarta(db, kartaId, wybranyElement.getId());


                List<Talent> newList = kartaTalentViewModel.getTalentFromKarta(db, kartaId);
                adapter.updateList(newList);

            });
            builder.show();
        });


        adapter.setOnItemLongClickListener(talent -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeleteTalent(db, talent, kartaId));

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

        adapter.setOnHelpClickListener(v -> {

            new AlertDialog.Builder(getContext())
                    .setTitle("Pomoc")
                    .setMessage("Wyjaśnienie ....")
                    .setPositiveButton("OK", null)
                    .show();
        });


        adapter.setOnMinusClickListener(talent -> {
            int currentRozw = talent.getPoziom();
            if (currentRozw > 0) {
                talent.setPoziom(currentRozw - 1);
                kartaTalentViewModel.updateTalent(db, talent, kartaId);
            }
        });

        adapter.setOnPlusClickListener(talent -> {
            int currentRozw = talent.getPoziom();
            talent.setPoziom(currentRozw + 1);
            kartaTalentViewModel.updateTalent(db, talent, kartaId);
        });

        return binding.getRoot();

    }


    private void confirmDeleteTalent(SQLiteDatabase db, Talent talent, int kartaId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć talent?")
                .setPositiveButton("Tak", (dialog, which) -> deleteTalent(db, talent, kartaId))
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void deleteTalent(SQLiteDatabase db, Talent talent, int kartaId) {
        db.delete("karta_talent", "talent_id=? AND karta_id=?", new String[]{String.valueOf(talent.getId()), String.valueOf(kartaId)});
        List<Talent> currentList = new ArrayList<>(adapter.getTalentList());
        currentList.remove(talent);

        adapter.updateList(currentList);
    }

    private List<Talent> getTalentFromDialog(SQLiteDatabase db) {
        List<Talent> list = new ArrayList<>();

        String query = "SELECT  * " +
                "FROM talent ";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Talent talent = new Talent();

            talent.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

            talent.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            talent.setMaksimum(cursor.getString(cursor.getColumnIndexOrThrow("maksimum")));
            talent.setTesty(cursor.getString(cursor.getColumnIndexOrThrow("testy")));
            talent.setOpis(cursor.getString(cursor.getColumnIndexOrThrow("opis")));

            talent.setIdCecha(cursor.getInt(cursor.getColumnIndexOrThrow("cechy_id")));

            list.add(talent);

        }
        cursor.close();

        return list;
    }

    private void addTalentToKarta(SQLiteDatabase db, int kartaId, int talentId) {

        ContentValues values = new ContentValues();
        values.put("karta_id", kartaId);
        values.put("talent_id", talentId);
        values.put("poziom", 1);
        values.put("lvl_up", 0);
        db.insert("karta_talent", null, values);
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
            poziomProfesjaprofesja.setSchematCech(cursor.getString(cursor.getColumnIndexOrThrow("schemat_talentow")));
        }
        cursor.close();

        return poziomProfesjaprofesja.getSchematCechTabel();
    }

    List<Talent> getTalentyForProfesja(SQLiteDatabase db, int[] schemat) {
        List<Talent> list = new ArrayList<>();

        String query = "SELECT  id, nazwa " +
                "FROM talent " +
                "WHERE id = ? ";

        for (int i = 0; i < schemat.length; i++) {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(schemat[i])});

            if (cursor.moveToNext()) {
                Talent talent = new Talent();

                talent.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                talent.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));

                list.add(talent);
            }
            cursor.close();
        }return list;

    }
}