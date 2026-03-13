package pl.edu.us.warhammer_card.ui.karta2.czaryimodlitwy;

import android.app.AlertDialog;
import android.content.ContentValues;
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
import java.util.List;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.adapter.ZakleciAdapter;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBronBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaCzaryIModlitwyBinding;
import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Zaklecia;

public class CzaryIModlitwyFragment extends Fragment {
    FragmentKartaCzaryIModlitwyBinding binding;

    ZakleciAdapter adapter;
    Karta karta = new Karta();
    int kartaId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding= FragmentKartaCzaryIModlitwyBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId=args.getInt("KartaId");

        karta=dbHelper.getKartaById(db,kartaId);

        adapter = new ZakleciAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        CzaryIModlitwyViewModel czaryIModlitwyViewModel =
                new ViewModelProvider(this).get(CzaryIModlitwyViewModel.class);

        czaryIModlitwyViewModel.getZakleciaForKarta(db,kartaId);

        czaryIModlitwyViewModel.getZakleciaLiveData().observe(getViewLifecycleOwner(),adapter::updateList);

        binding.fab.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Dodaj Zaklęcie");

            List<Zaklecia> zakleciaList = dbHelper.getAllZaklecia(db);

            String[] listaElementow = new  String[zakleciaList.size()];
            int i = 0;
            for (Zaklecia zaklecia : zakleciaList){
                listaElementow[i] = zaklecia.getNazwa();
                i++;
            }

            builder.setItems(listaElementow,(dialog, which) ->{
                Zaklecia wybranyElement = zakleciaList.get(which);

                addZaklecieToKarta(db, kartaId,wybranyElement.getId());


                List<Zaklecia> newList = czaryIModlitwyViewModel.getZakleciaForKarta(db, kartaId);
                adapter.updateList(newList);

            });
            builder.show();
        });

        return binding.getRoot();
    }

    private void confirmDeleteBron(SQLiteDatabase db, Zaklecia zaklecia , int kartaId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć Zaklęcie?")
                .setPositiveButton("Tak", (dialog, which) -> deleteTalent(db, zaklecia,kartaId))
                .setNegativeButton("Anuluj", null)
                .show();
    }
    private void deleteTalent(SQLiteDatabase db, Zaklecia zaklecia, int kartaId) {
        db.delete("karta_zaklęcia", "zaklęcia_id=? AND karta_id=?", new String[]{String.valueOf(zaklecia.getId()),String.valueOf(kartaId)});
        List<Zaklecia> currentList = new ArrayList<>(adapter.getBronList());
        currentList.remove(zaklecia);

        adapter.updateList(currentList);
    }

    private void addZaklecieToKarta(SQLiteDatabase db, int kartaId, int bronId){

        ContentValues values = new ContentValues();
        values.put("karta_id", kartaId);
        values.put("zaklęcia_id", bronId);
        db.insert("karta_zaklęcia",null, values);
    }

}
