package pl.edu.us.warhammer_card.ui.home;

import android.Manifest;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.CreatorKart;
import pl.edu.us.warhammer_card.PdfGenerator;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.adapter.KampaniaAdapter;
import pl.edu.us.warhammer_card.adapter.KartaAdapter;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBinding;
import pl.edu.us.warhammer_card.table.Karta;

public class KartaFragment extends Fragment {
    private static final int REQUEST_PERMISSION_CODE = 1;
    public static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";
    FragmentKartaBinding binding;

    KartaAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Pobranie ID kampanii z argumentów
        Bundle args = getArguments();
        assert args != null;
        int kampanieId = args.getInt("key");
        Log.d("Karta", String.valueOf("KampaniaID=" + kampanieId));

        // Inicjalizacja adaptera
        adapter = new KartaAdapter(new ArrayList<>(), db);

        // Ustawienie listenerów
        adapter.setOnItemClickListener(karta -> {
            // Obsługa kliknięcia na element listy
            Bundle args2 = new Bundle();
            args2.putInt("KartaId", karta.getId());
            NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                    .getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_content_main);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_fragment_Karta_to_fragment_karta_main, args2);
        });

        adapter.setOnPdfClickListener(karta -> {
            // Obsługa kliknięcia na przycisk PDF
            Log.d("KartaFragment", "PDF clicked for card: " + karta.getId());
        });

        recyclerView.setAdapter(adapter);

        // Tworzenie ViewModel
        KartaFragmentViewModel kartaFragmentViewModel = new ViewModelProvider(this).get(KartaFragmentViewModel.class);
        kartaFragmentViewModel.getKartaList(db, kampanieId);

        // Obserwacja zmian w liście kart
        kartaFragmentViewModel.getKartaList().observe(getViewLifecycleOwner(), adapter::updateList);

        // Obsługa przycisków
        binding.wlasnaKartaBtn.setOnClickListener(v -> {
            CreatorKart creatorKart = new CreatorKart();
            Karta karta = creatorKart.createEmtyKarta(db, kampanieId);
            Bundle args2 = new Bundle();
            args2.putInt("KartaId", karta.getId());

            NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext()))
                    .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_fragment_Karta_to_fragment_karta_main, args2);
        });

        binding.losowaKartaBtn.setOnClickListener(v -> {
            CreatorKart creatorKart = new CreatorKart();
            Karta karta = creatorKart.createRandomKarta(db, kampanieId);

            Bundle args2 = new Bundle();
            args2.putInt("KartaId", karta.getId());

            NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext()))
                    .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_fragment_Karta_to_fragment_karta_main, args2);
        });

        return binding.getRoot();
    }
}

