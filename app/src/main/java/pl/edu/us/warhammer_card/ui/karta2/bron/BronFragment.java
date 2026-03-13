package pl.edu.us.warhammer_card.ui.karta2.bron;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

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
import pl.edu.us.warhammer_card.adapter.BronAdapter;
import pl.edu.us.warhammer_card.databinding.AddWlasnaBronDialogBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBronBinding;
import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.CechaBroni;
import pl.edu.us.warhammer_card.table.CechaPancerza;
import pl.edu.us.warhammer_card.table.Dostepnosc;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Talent;
import pl.edu.us.warhammer_card.table.TypBroni;
import pl.edu.us.warhammer_card.table.TypPancerza;


public class BronFragment extends Fragment {
    FragmentKartaBronBinding binding;
    BronAdapter adapter;
    Karta karta = new Karta();
    int kartaId;
    int typBroniId = -1;
    int dostepnoscId = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaBronBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId = args.getInt("KartaId");

        karta = dbHelper.getKartaById(db, kartaId);

        adapter = new BronAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        BronViewModel bronViewModel =
                new ViewModelProvider(this).get(BronViewModel.class);
        bronViewModel.getBronListForKarta(db, kartaId);

        bronViewModel.getBronLiveData().observe(getViewLifecycleOwner(), adapter::updateList);

        binding.podstawowaBronBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Dodaj Broń");

            List<Bron> listBron = dbHelper.getAllBronList(db);

            String[] listaElementow = new String[listBron.size()];
            int i = 0;
            for (Bron bron : listBron) {
                listaElementow[i] = bron.getNazwa();
                i++;
            }

            builder.setItems(listaElementow, (dialog, which) -> {
                Bron wybranyElement = listBron.get(which);

                addBronToKarta(db, kartaId, wybranyElement.getId());


                List<Bron> newList = bronViewModel.getBronListForKarta(db, kartaId);
                adapter.updateList(newList);

            });
            builder.show();
        });

        binding.wlasnaBronBtn.setOnClickListener(v -> {

            AddWlasnaBronDialogBinding dialogBinding = AddWlasnaBronDialogBinding.inflate(LayoutInflater.from(requireContext()));
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(dialogBinding.getRoot())
                    .create();

            List<CechaBroni> cechaBroniList = dbHelper.getCechyBroni(db);
            Map<CechaBroni, CheckBox> checkBoxMap = new HashMap<>();

            for (CechaBroni cechaBroni : cechaBroniList) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(cechaBroni.getNazwa());
                dialogBinding.lokalizacjaGrid.addView(checkBox);
                checkBoxMap.put(cechaBroni, checkBox);
            }

            dialogBinding.typBroni.setOnClickListener(typ -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Wybierz Typ Broni");

                List<TypBroni> listaTyp;
                listaTyp = dbHelper.getTypBroniList(db);

                String[] listaElementow = new String[listaTyp.size()];
                final int[] listaId = new int[listaTyp.size()];

                int i = 0;
                for (TypBroni typBroni : listaTyp) {
                    listaElementow[i] = typBroni.getNazwa();
                    listaId[i] = typBroni.getId();

                    i++;
                }

                builder.setItems(listaElementow, (wlasnyPancerz, which) -> {
                    String wybranyElement = listaElementow[which];
                    dialogBinding.typBroni.setText(wybranyElement);
                    typBroniId = listaId[which];


                });
                builder.show();
            });

            dialogBinding.dostepnosc.setOnClickListener(dost -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Wybierz Dostępność");

                List<Dostepnosc> listDostepnosc;
                listDostepnosc = dbHelper.getDostepnoscList(db);

                String[] listaElementow = new String[listDostepnosc.size()];
                final int[] listaId = new int[listDostepnosc.size()];

                int i = 0;
                for (Dostepnosc dostepnosc : listDostepnosc) {
                    listaElementow[i] = dostepnosc.getNazwa();
                    i++;
                }
                builder.setItems(listaElementow, (dostep, which) -> {
                    String wybranyElement = listaElementow[which];
                    dialogBinding.dostepnosc.setText(wybranyElement);
                    dostepnoscId = listaId[which];
                });
                builder.show();
            });

            Bron bron = new Bron();
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if (dialogBinding.nazwa.hasFocus()) {
                        bron.setNazwa(dialogBinding.nazwa.getText().toString());

                    } else if (dialogBinding.cena.hasFocus()) {
                        bron.setCena(dialogBinding.cena.getText().toString());

                    } else if (dialogBinding.obrazenia.hasFocus()) {
                        bron.setObrazenia(dialogBinding.obrazenia.getText().toString());

                    } else if (dialogBinding.zasieg.hasFocus()) {
                            bron.setZasieg(dialogBinding.zasieg.getText().toString());
                    }
                }
            };

            dialogBinding.nazwa.addTextChangedListener(textWatcher);
            dialogBinding.cena.addTextChangedListener(textWatcher);
            dialogBinding.obrazenia.addTextChangedListener(textWatcher);
            dialogBinding.zasieg.addTextChangedListener(textWatcher);


            dialogBinding.buttonOk.setOnClickListener(view -> {
                List<CechaBroni> wybraneCechy = new ArrayList<>();

                for (Map.Entry<CechaBroni, CheckBox> entry : checkBoxMap.entrySet()) {
                    if (entry.getValue().isChecked()) {
                        wybraneCechy.add(entry.getKey());
                    }
                }
                bron.setListaCech(wybraneCechy);
                addWlasnaBronToKarta(db, bron);
                List<Bron> newList = bronViewModel.getBronListForKarta(db, kartaId);
                adapter.updateList(newList);

                dialog.dismiss();
            });

            dialogBinding.buttonAnuluj.setOnClickListener(view -> dialog.dismiss());

            dialog.show();
        });

        adapter.setOnItemLongClickListener(bron -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeleteBron(db, bron, kartaId));

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

            List<CechaBroni> listaCech = v.getListaCech();

            SpannableStringBuilder info = new SpannableStringBuilder();

            for (CechaBroni cechaBroni: listaCech){
                SpannableString notatka = new SpannableString(cechaBroni.getNazwa() +": " + cechaBroni.getOpis() +"\n\n");
                notatka.setSpan(new StyleSpan(Typeface.BOLD), 0, cechaBroni.getNazwa().length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                info.append(notatka);
            }

            new AlertDialog.Builder(getContext())
                    .setTitle("Informacja")
                    .setMessage(info)
                    .setPositiveButton("OK", null)
                    .show();
        });


        return binding.getRoot();
    }

    private void confirmDeleteBron(SQLiteDatabase db, Bron bron, int kartaId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć talent?")
                .setPositiveButton("Tak", (dialog, which) -> deleteTalent(db, bron, kartaId))
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void deleteTalent(SQLiteDatabase db, Bron bron, int kartaId) {
        db.delete("karta_broń", "broń_id=? AND karta_id=?", new String[]{String.valueOf(bron.getId()), String.valueOf(kartaId)});
        List<Bron> currentList = new ArrayList<>(adapter.getBronList());
        currentList.remove(bron);

        adapter.updateList(currentList);
    }

    private void addBronToKarta(SQLiteDatabase db, int kartaId, int bronId) {

        ContentValues values = new ContentValues();
        values.put("karta_id", kartaId);
        values.put("broń_id", bronId);
        db.insert("karta_broń", null, values);
    }

    private void addWlasnaBronToKarta(SQLiteDatabase db, Bron bron){
        ContentValues values = new ContentValues();
        values.put("nazwa", bron.getNazwa());
        values.put("cena", bron.getCena());
        values.put("zasięg", bron.getZasieg());
        values.put("obrażenia", bron.getObrazenia());
        values.put("czy_własna", 1);


        values.put("dostępność_id", dostepnoscId);
        values.put("typ_broni_id", typBroniId);
        int id = (int) db.insert("broń", null, values);

        values.clear();

        for (CechaBroni cechaBroni: bron.getListaCech()){
            values.put("broń_id", id);
            values.put("cecha_id", cechaBroni.getId());
            db.insert("cecha_broni_broń",null,values);
            values.clear();
        }

        values.put("karta_id", kartaId);
        values.put("broń_id", id);
        db.insert("karta_broń", null, values);

    }
}
