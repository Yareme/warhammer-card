package pl.edu.us.warhammer_card.ui.karta.KartaUmiejetnosci;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.adapter.UmiejetnoscAdapter;
import pl.edu.us.warhammer_card.adapter.WyposazeniaAdapter;
import pl.edu.us.warhammer_card.databinding.FragmentKartaUmiejetnosciBinding;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.PoziomProfesja;
import pl.edu.us.warhammer_card.table.Umiejetnosci;

public class KartaUmiejetnosciFragment extends Fragment {

    FragmentKartaUmiejetnosciBinding binding;
    UmiejetnoscAdapter adapter;
    Karta karta = new Karta();
    int kartaId;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaUmiejetnosciBinding.inflate(inflater, container, false);


        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId = args.getInt("KartaId");

        karta = dbHelper.getKartaById(db, kartaId);

        adapter = new UmiejetnoscAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        UmiejetnosciViewModel umiejetnosciViewModel =
                new ViewModelProvider(this).get(UmiejetnosciViewModel.class);
        umiejetnosciViewModel.getUmiejetnosciPodstawoweList(db, kartaId);

        umiejetnosciViewModel.getUmiejetnoscLiveData().observe(getViewLifecycleOwner(),adapter::updateList);

        adapter.setOnMinusClickListener(umiejetnosci -> {
            int currentRozw = umiejetnosci.getRozwoj();
            if (currentRozw > 0) {
                umiejetnosci.setRozwoj(currentRozw - 1);
                umiejetnosciViewModel.updateUmiejetnosc(db, umiejetnosci, kartaId);
            }
        });

        adapter.setOnPlusClickListener(umiejetnosci -> {
            int currentRozw = umiejetnosci.getRozwoj();
            umiejetnosci.setRozwoj(currentRozw + 1);
            umiejetnosciViewModel.updateUmiejetnosc(db, umiejetnosci, kartaId);
        });

        return binding.getRoot();
    }
}
