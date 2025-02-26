package pl.edu.us.warhammer_card.ui.karta2.pancerz;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.adapter.KartaAdapter;
import pl.edu.us.warhammer_card.adapter.PancerzAdapter;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBronBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaPancerzBinding;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Pancerz;
import pl.edu.us.warhammer_card.table.Talent;

public class PancerzFragment extends Fragment {
    FragmentKartaPancerzBinding binding;

    PancerzAdapter adapter;
    Karta karta = new Karta();
    int kartaId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding= FragmentKartaPancerzBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId=args.getInt("KartaId");

        karta=dbHelper.getKartaById(db,kartaId);

        adapter = new PancerzAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        PancerzViewModel pancerzViewModel =
                new ViewModelProvider(this).get(PancerzViewModel.class);
        pancerzViewModel.getPancerzFromKarta(db,kartaId);

        pancerzViewModel.getPancerzLiveData().observe(getViewLifecycleOwner(),adapter::updateList);


        binding.fab.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Dodaj Talen");

            List<Pancerz> listPancerz = dbHelper.getAllPancerzList(db);

            String[] listaElementow = new  String[listPancerz.size()];
            int i = 0;
            for (Pancerz pancerz: listPancerz){
                listaElementow[i] = pancerz.getNazwa();
                i++;
            }

            builder.setItems(listaElementow,(dialog, which) ->{
                Pancerz wybranyElement = listPancerz.get(which);

                addPancerzToKarta(db, kartaId,wybranyElement.getId());


                List<Pancerz> newList = pancerzViewModel.getPancerzFromKarta(db, kartaId);
                adapter.updateList(newList);

            });
            builder.show();
        });

/*
        adapter.setOnItemLongClickListener(pancerz -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            // Dodawanie opcji i ich akcji
            options.add("Edytuj");
            actions.put("Edytuj", () -> showEditDialog(kampania));

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeleteKampania(db, kampania));

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
*/




        return binding.getRoot();
    }

    private void addPancerzToKarta(SQLiteDatabase db, int kartaId, int talentId){

        ContentValues values = new ContentValues();
        values.put("karta_id", kartaId);
        values.put("pancerz_id", talentId);
        db.insert("karta_pancerz",null, values);
    }
}