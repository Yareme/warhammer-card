package pl.edu.us.warhammer_card.ui.karta.KartaTalenty;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
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
import pl.edu.us.warhammer_card.table.Kampania;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Talent;
import pl.edu.us.warhammer_card.table.Umiejetnosci;

public class KartaTalentFragment extends Fragment {

    FragmentKartaTalentBinding binding;
    TalentAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentKartaTalentBinding.inflate(inflater,container,false);


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

       // Karta karta = dbHelper.getKartaById(db, kartaId);

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TalentAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);


        KartaTalentViewModel kartaTalentViewModel =
                new ViewModelProvider(this).get(KartaTalentViewModel.class);
        kartaTalentViewModel.getTalentFromKarta(db, kartaId);

        kartaTalentViewModel.getTalentLiveData().observe(getViewLifecycleOwner(), adapter::updateList);

        binding.fab.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Dodaj Talen");

            List<Talent> listTalentFromDialog = getTalentFromDialog(db);

            String[] listaElementow = new  String[listTalentFromDialog.size()];
            int i = 0;
            for (Talent talent: listTalentFromDialog){
                listaElementow[i] = talent.getNazwa();
                i++;
            }

            builder.setItems(listaElementow,(dialog, which) ->{
                Talent wybranyElement = listTalentFromDialog.get(which);

                addTalentToKarta(db, kartaId,wybranyElement.getId());


                List<Talent> newList = kartaTalentViewModel.getTalentFromKarta(db, kartaId);
                adapter.updateList(newList);

            });
            builder.show();
        });

        adapter.setOnItemLongClickListener(talent ->{

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeleteKampania(db, talent,kartaId));

            String[] optionsArray = options.toArray(new String[0]);

            // Tworzenie dialogu
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

        return binding.getRoot();

    }

    private void confirmDeleteKampania(SQLiteDatabase db, Talent talent, int kartaId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć talent?")
                .setPositiveButton("Tak", (dialog, which) -> deleteTalent(db, talent,kartaId)) // Teraz pobiera nową listę
                .setNegativeButton("Anuluj", null)
                .show();
    }
    private void deleteTalent(SQLiteDatabase db, Talent talent, int kartaId) {
        db.delete("karta_talent", "talent_id=? AND karta_id=?", new String[]{String.valueOf(talent.getId()),String.valueOf(kartaId)});
        List<Talent> currentList = new ArrayList<>(adapter.getTalentList());
        currentList.remove(talent);

        // Aktualizacja adaptera
        adapter.updateList(currentList);
    }

   private List<Talent> getTalentFromDialog(SQLiteDatabase db){
        List<Talent> list = new ArrayList<>();

       String query = "SELECT  * " +
               "FROM talent " ;

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

   private void addTalentToKarta(SQLiteDatabase db, int kartaId, int talentId){

       ContentValues values = new ContentValues();
       values.put("karta_id", kartaId);
       values.put("talent_id", talentId);
       values.put("poziom",1);
       values.put("lvl_up",0);
       db.insert("karta_talent",null, values);
   }
}
