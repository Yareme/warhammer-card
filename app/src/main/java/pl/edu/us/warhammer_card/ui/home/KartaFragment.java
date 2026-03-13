package pl.edu.us.warhammer_card.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.CreatorKart;
import pl.edu.us.warhammer_card.PdfGenerator;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.adapter.KampaniaAdapter;
import pl.edu.us.warhammer_card.adapter.KartaAdapter;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBinding;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Pancerz;

public class KartaFragment extends Fragment {
    private static final int REQUEST_PERMISSION_CODE = 1;
    public static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";
    FragmentKartaBinding binding;

    KartaAdapter adapter;

    Karta karta;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        int kampanieId = args.getInt("key");
        Log.d("Karta", String.valueOf("KampaniaID=" + kampanieId));


        adapter = new KartaAdapter(new ArrayList<>(), db);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(karta -> {
            Bundle args2 = new Bundle();
            args2.putInt("KartaId", karta.getId());
            NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                    .getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_content_main);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_fragment_Karta_to_fragment_karta_main, args2);
        });

        adapter.setOnPdfClickListener(karta -> {

            Toast.makeText(getContext(), "Generacja PDF", Toast.LENGTH_LONG).show();
            try {
                PdfGenerator.generatePdf(getContext(), dbHelper.getKartaById(db, karta.getId()));

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(), "Błąd zapisu PDF", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
            }

            Log.d("KartaFragment", "PDF clicked for card: " + karta.getId());
        });

        adapter.setOnItemLongClickListener(karta ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeletePancerz(db, karta));

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


        KartaFragmentViewModel kartaFragmentViewModel = new ViewModelProvider(this).get(KartaFragmentViewModel.class);
        kartaFragmentViewModel.getKartaList(db, kampanieId);

        kartaFragmentViewModel.getKartaList().observe(getViewLifecycleOwner(), adapter::updateList);

        binding.wlasnaKartaBtn.setOnClickListener(v -> {
            CreatorKart creatorKart = new CreatorKart();
            Karta karta = creatorKart.createEmtyKarta(db, kampanieId);
            Bundle args2 = new Bundle();
            args2.putInt("KartaId", karta.getId());
            getParentFragmentManager().setFragmentResult("requestKey", args2);


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
            getParentFragmentManager().setFragmentResult("requestKey", args2);

            NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext()))
                    .getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_fragment_Karta_to_fragment_karta_main, args2);

        });

        return binding.getRoot();
    }

    private void confirmDeletePancerz(SQLiteDatabase db,Karta karta) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz karte?")
                .setPositiveButton("Tak", (dialog, which) -> deleteKarta(db, karta))
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void deleteKarta(SQLiteDatabase db, Karta karta){
        db.delete("karta", "id=?", new String[]{String.valueOf(karta.getId())});
        List<Karta> currentList = new ArrayList<>(adapter.getKartaList());
        currentList.remove(karta);
        adapter.updateList(currentList);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PdfGenerator pdfGenerator = new PdfGenerator();
                pdfGenerator.generatePdf(getContext(),karta);
            } else {

                Toast.makeText(getContext(), "Uprawnienie do zapisu w pamięci zostało odmówione.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static final int REQUEST_CODE_CREATE_PDF = 1001;

    private void selectSaveLocation() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "Warhammer_karta.pdf");
        startActivityForResult(intent, REQUEST_CODE_CREATE_PDF);
    }
}

