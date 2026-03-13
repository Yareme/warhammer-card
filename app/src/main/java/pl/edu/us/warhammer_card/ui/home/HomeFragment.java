package pl.edu.us.warhammer_card.ui.home;

import android.app.AlertDialog;
import android.content.ContentValues;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;

import pl.edu.us.warhammer_card.adapter.KampaniaAdapter;
import pl.edu.us.warhammer_card.databinding.FragmentHomeBinding;
import pl.edu.us.warhammer_card.table.Kampania;

import pl.edu.us.warhammer_card.R;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private KampaniaAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new KampaniaAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        HomeFragmentViewModel homeFragmentViewModel =
                new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        homeFragmentViewModel.getKampaniaList(db);

        homeFragmentViewModel.getKampaniaList().observe(getViewLifecycleOwner(), adapter::updateList);

        try {
            db.execSQL("PRAGMA journal_mode = WAL;");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        binding.addKampania.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Dodaj nową kampanię");

            final EditText input = new EditText(requireContext());
            input.setHint("Nazwa kampanii");

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(50, 20, 50, 20);
            input.setLayoutParams(layoutParams);

            builder.setView(input);

            builder.setPositiveButton("Dodaj", (dialog, which) -> {
                String nameKampania = input.getText().toString().trim();
                if (nameKampania.isEmpty()) {
                    nameKampania = "Podstawowa nazwa";
                }

                Date date = new Date();
                long timeInMilliseconds = date.getTime();

                ContentValues values = new ContentValues();
                values.put("nazwa", nameKampania);
                values.put("data_utworzenia", timeInMilliseconds);

                db.insert("kampania", null, values);

                homeFragmentViewModel.getKampaniaList(db);
            });

            builder.setNegativeButton("Anuluj", (dialog, which) -> dialog.dismiss());

            builder.show();
        });

        adapter.setOnItemLongClickListener(kampania -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            options.add("Edytuj");
            actions.put("Edytuj", () -> showEditDialog(kampania));

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeleteKampania(db, kampania));

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

        adapter.setOnItemClickListener(kampania -> {
            Bundle args = new Bundle();
            args.putInt("key", kampania.getId());

            NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                    .getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_content_main);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_nav_home_to_fragment_Karta, args);
        });
        return root;
    }

    private void deleteKampania(SQLiteDatabase db, Kampania kampania) {
        db.delete("Kampania", "id=?", new String[]{String.valueOf(kampania.getId())});
        List<Kampania> currentList = new ArrayList<>(adapter.getKampaniaList());
        currentList.remove(kampania);

        adapter.updateList(currentList);
    }

    private void confirmDeleteKampania(SQLiteDatabase db, Kampania kampania) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć tę kampanię?")
                .setPositiveButton("Tak", (dialog, which) -> deleteKampania(db, kampania))
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void showEditDialog(Kampania kampania) {
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
