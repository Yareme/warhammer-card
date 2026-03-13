package pl.edu.us.warhammer_card.ui.karta2.wyposazenie;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import pl.edu.us.warhammer_card.adapter.WyposazeniaAdapter;
import pl.edu.us.warhammer_card.databinding.AddWlasneWyposazeniaDialogBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBronBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaWyposazenieBinding;
import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Pancerz;
import pl.edu.us.warhammer_card.table.Wyposarzenia;

public class WyposazeniaFragment extends Fragment {
    FragmentKartaWyposazenieBinding binding;
    WyposazeniaAdapter adapter;
    Karta karta = new Karta();
    int kartaId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaWyposazenieBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId = args.getInt("KartaId");

        karta = dbHelper.getKartaById(db, kartaId);

        adapter = new WyposazeniaAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        WyposazeniaViewModel wyposazeniaViewModel =
                new ViewModelProvider(this).get(WyposazeniaViewModel.class);
        wyposazeniaViewModel.getWyposazeniaListFromKarta(db, kartaId);

        wyposazeniaViewModel.getWyposazeniaLiveData().observe(getViewLifecycleOwner(), adapter::updateList);

        adapter.setOnItemLongClickListener(wyposazenia -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeleteWyposazenia(db, wyposazenia, kartaId));

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


        binding.wybierzZListy.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Dodaj Wypasażenie");

            List<Wyposarzenia> listWyposazenia = dbHelper.getAllWyposazenia(db);

            String[] listaElementow = new String[listWyposazenia.size()];
            int i = 0;
            for (Wyposarzenia wyposarzenia : listWyposazenia) {
                listaElementow[i] = wyposarzenia.getNazwa();
                i++;
            }

            builder.setItems(listaElementow, (dialog, which) -> {
                Wyposarzenia wybranyElement = listWyposazenia.get(which);

                addWyposazeniaToKarta(db, kartaId, wybranyElement);

                List<Wyposarzenia> newList = wyposazeniaViewModel.getWyposazeniaListFromKarta(db, kartaId);
                adapter.updateList(newList);

            });
            builder.show();
        });

        binding.dodajWlasne.setOnClickListener(wyposazzenia -> {
            AddWlasneWyposazeniaDialogBinding dialogBinding = AddWlasneWyposazeniaDialogBinding.inflate(LayoutInflater.from(requireContext()));
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(dialogBinding.getRoot())
                    .create();
            Wyposarzenia wyposarzeniaW = new Wyposarzenia();
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (dialogBinding.nazwa.hasFocus()) {
                        wyposarzeniaW.setNazwa(dialogBinding.nazwa.getText().toString());
                    }

                }
            };

            dialogBinding.nazwa.addTextChangedListener(textWatcher);

            dialogBinding.buttonAnuluj.setOnClickListener(v2 -> dialog.dismiss());

            dialogBinding.buttonOk.setOnClickListener(v2 -> {
                addWlasneWypasazenie(db, wyposarzeniaW);
                List<Wyposarzenia> newList = wyposazeniaViewModel.getWyposazeniaListFromKarta(db, kartaId);
                adapter.updateList(newList);
                dialog.dismiss();
            });
            dialog.show();
        });

        adapter.setOnMinusClickListener(wyposarzenia -> {
            int currentRozw = wyposarzenia.getSztuk();
            if (currentRozw > 0) {
                wyposarzenia.setSztuk(currentRozw - 1);
                wyposazeniaViewModel.updateWyposarzenia(db, wyposarzenia, kartaId);
            }
        });

        adapter.setOnPlusClickListener(wyposarzenia -> {
            int currentRozw = wyposarzenia.getSztuk();
            wyposarzenia.setSztuk(currentRozw + 1);
            wyposazeniaViewModel.updateWyposarzenia(db, wyposarzenia, kartaId);
        });



        return binding.getRoot();
    }


    private void confirmDeleteWyposazenia(SQLiteDatabase db, Wyposarzenia wyposarzenia, int kartaId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć?")
                .setPositiveButton("Tak", (dialog, which) -> deleteWyposazenia(db, wyposarzenia, kartaId))
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void deleteWyposazenia(SQLiteDatabase db, Wyposarzenia wyposarzenia, int kartaId) {
        db.delete("karta_wyposarzenie", "wyposarzenie_id=? AND karta_id=?", new String[]{String.valueOf(wyposarzenia.getId()), String.valueOf(kartaId)});
        List<Wyposarzenia> currentList = new ArrayList<>(adapter.getWyposarzeniaList());
        currentList.remove(wyposarzenia);

        adapter.updateList(currentList);
    }

    private void addWyposazeniaToKarta(SQLiteDatabase db, int kartaId, Wyposarzenia wyposarzenia) {

        ContentValues values = new ContentValues();
        values.put("karta_id", kartaId);
        values.put("wyposarzenie_id", wyposarzenia.getId());
        values.put("sztuk", 1);
        db.insert("karta_wyposarzenie", null, values);
    }

    private void addWlasneWypasazenie(SQLiteDatabase db, Wyposarzenia wyposarzenia) {
        ContentValues values = new ContentValues();
        values.put("nazwa", wyposarzenia.getNazwa());
        int id = (int) db.insert("wyposarzenie", null, values);

        values.clear();

        values.put("karta_id", kartaId);
        values.put("wyposarzenie_id", id);
        values.put("sztuk", 1);
        db.insert("karta_wyposarzenie", null, values);

    }

}
