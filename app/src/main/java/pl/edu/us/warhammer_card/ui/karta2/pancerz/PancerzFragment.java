package pl.edu.us.warhammer_card.ui.karta2.pancerz;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.adapter.PancerzAdapter;
import pl.edu.us.warhammer_card.databinding.AddWlasnyPancerzDialogBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaPancerzBinding;
import pl.edu.us.warhammer_card.databinding.PancerzDialogBinding;
import pl.edu.us.warhammer_card.table.CechaBroni;
import pl.edu.us.warhammer_card.table.CechaPancerza;
import pl.edu.us.warhammer_card.table.Dostepnosc;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.LokalizacjaPancerza;
import pl.edu.us.warhammer_card.table.Pancerz;
import pl.edu.us.warhammer_card.table.Rasa;
import pl.edu.us.warhammer_card.table.TypPancerza;


public class PancerzFragment extends Fragment {

    private boolean czyGlowaZaznaczona = false;
    private boolean czyKorpusZaznaczony = false;
    private boolean czyPrawaRekaZaznaczona = false;
    private boolean czyLewaRekaZaznaczona = false;
    private boolean czyPrawaNogaZaznaczona = false;
    private boolean czyLewaNogaZaznaczona = false;

    int wsszystkoId = 1;
    int ramionaId = 2;
    int korpusId = 3;
    int nogiId = 4;
    int glowaId = 5;
    int prawaRekaId = 6;
    int lewaRekaId = 7;
    int prawaNogaId = 8;
    int lewaNogaId = 9;

    int typPancerzaId = -1;
    String nazwa = "Nazwa Pancerza";
    String cena = "Cena";
    String kara = "Kara";
    int dostepnoscId = -1;
    int pp = 0;
    FragmentKartaPancerzBinding binding;

    PancerzAdapter adapter;
    Karta karta = new Karta();
    int kartaId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaPancerzBinding.inflate(inflater, container, false);

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

        binding.podstawowyPancerzBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Dodaj Pancerz");

            List<Pancerz> listPancerz = dbHelper.getAllPancerzList(db);

            String[] listaElementow = new String[listPancerz.size()];
            int i = 0;
            for (Pancerz pancerz : listPancerz) {
                listaElementow[i] = pancerz.getNazwa();
                i++;
            }

            builder.setItems(listaElementow,(dialog, which) ->{
                Pancerz wybranyElement = listPancerz.get(which);

                addPancerzToKarta(db, kartaId, wybranyElement.getId());


                List<Pancerz> newList = pancerzViewModel.getPancerzFromKarta(db, kartaId);
                adapter.updateList(newList);

            });
            builder.show();
        });

        binding.wlasnyPancerzBtn.setOnClickListener(v -> {
            AddWlasnyPancerzDialogBinding dialogBinding = AddWlasnyPancerzDialogBinding.inflate(LayoutInflater.from(requireContext()));

            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(dialogBinding.getRoot())
                    .create();

            List<CechaPancerza> cechaPancerzaList = dbHelper.getChechyPancerza(db);
            Map<CechaPancerza, CheckBox> checkBoxMap = new HashMap<>();

            for (CechaPancerza cechaPancerza : cechaPancerzaList) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(cechaPancerza.getNazwa());
                dialogBinding.cechyGrid.addView(checkBox);
                checkBoxMap.put(cechaPancerza, checkBox);
            }

            List<TypPancerza> listaTyp;
            listaTyp = dbHelper.getTypPancerzaList(db);

            List<Dostepnosc> listDostepnosc;
            listDostepnosc = dbHelper.getDostepnoscList(db);

            dialogBinding.typPancerza.setOnClickListener(typ -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Wybierz Typ Pancerza");

                String[] listaElementow = new String[listaTyp.size()];
                final int[] listaId = new int[listaTyp.size()];

                int i = 0;
                for (TypPancerza typPancerza : listaTyp) {
                    listaElementow[i] = typPancerza.getNazwa();
                    listaId[i] = typPancerza.getId();

                    i++;
                }

                builder.setItems(listaElementow, (wlasnyPancerz, which) -> {
                    String wybranyElement = listaElementow[which];
                    dialogBinding.typPancerza.setText(wybranyElement);
                    typPancerzaId = listaId[which];


                });
                builder.show();
            });

            dialogBinding.dostepnosc.setOnClickListener(dost -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Wybierz Dostępność");

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

            Pancerz wlasnyP = new Pancerz();
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
                        wlasnyP.setNazwa(dialogBinding.nazwa.getText().toString());

                    } else if (dialogBinding.cena.hasFocus()) {
                        wlasnyP.setCena(dialogBinding.cena.getText().toString());

                    } else if (dialogBinding.kara.hasFocus()) {
                        wlasnyP.setKara(dialogBinding.kara.getText().toString());

                    } else if (dialogBinding.pp.hasFocus()) {
                        try {
                            int pp = Integer.parseInt(dialogBinding.pp.getText().toString());
                            wlasnyP.setPunktyPancerza(pp);
                        } catch (NumberFormatException e) {
                            wlasnyP.setPunktyPancerza(0);
                        }
                    }
                }
            };

            dialogBinding.nazwa.addTextChangedListener(textWatcher);
            dialogBinding.cena.addTextChangedListener(textWatcher);
            dialogBinding.kara.addTextChangedListener(textWatcher);
            dialogBinding.pp.addTextChangedListener(textWatcher);

            dialogBinding.glowa.setOnCheckedChangeListener((buttonView, isChecked) -> {
                czyGlowaZaznaczona = isChecked;
            });
            dialogBinding.korpus.setOnCheckedChangeListener((buttonView, isChecked) -> {
                czyKorpusZaznaczony = isChecked;
            });
            dialogBinding.prawaReka.setOnCheckedChangeListener((buttonView, isChecked) -> {
                czyPrawaRekaZaznaczona = isChecked;
            });
            dialogBinding.lewaReka.setOnCheckedChangeListener((buttonView, isChecked) -> {
                czyLewaRekaZaznaczona = isChecked;
            });
            dialogBinding.prawaNoga.setOnCheckedChangeListener((buttonView, isChecked) -> {
                czyPrawaNogaZaznaczona = isChecked;
            });
            dialogBinding.lewaNoga.setOnCheckedChangeListener((buttonView, isChecked) -> {
                czyLewaNogaZaznaczona = isChecked;
            });

            dialogBinding.buttonAnuluj.setOnClickListener(v2 -> dialog.dismiss());

            dialogBinding.buttonOk.setOnClickListener(v2 ->{
                List<CechaPancerza> wybraneCechy = new ArrayList<>();

                for (Map.Entry<CechaPancerza, CheckBox> entry : checkBoxMap.entrySet()) {
                    if (entry.getValue().isChecked()) {
                        wybraneCechy.add(entry.getKey());
                    }
                }
                wlasnyP.setCechaPancerzaList(wybraneCechy);

                addWlasnyPancerz(db, wlasnyP);
                List<Pancerz> newList = pancerzViewModel.getPancerzFromKarta(db, kartaId);
                adapter.updateList(newList);
                dialog.dismiss();
            });
            dialog.show();
        });
        adapter.setOnCheckBoxClickListener(new PancerzAdapter.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(Pancerz pancerz, boolean isChecked) {
                pancerz.setCzyZalozone(isChecked ? 1 : 0);

                updatePancerz(db, kartaId, pancerz.getId(), pancerz.getCzyZalozone());

                List<Pancerz> newList = pancerzViewModel.getPancerzFromKarta(db, kartaId);
                adapter.updateList(newList);
            }
        });

        binding.fab.setOnClickListener(v -> {
            PancerzDialogBinding dialogBinding = PancerzDialogBinding.inflate(LayoutInflater.from(requireContext()));

            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(dialogBinding.getRoot())
                    .create();

            List<Pancerz> pancerzList = pancerzViewModel.getPancerzFromKarta(db, kartaId);

            int glowa = 0;
            int korpus = 0;
            int prawaReka = 0;
            int lewaReka = 0;
            int prawaNoga = 0;
            int lewaNoga = 0;

            for (Pancerz pancerz : pancerzList) {
                if (pancerz.getCzyZalozone() == 0) {
                    continue;
                }
                List<LokalizacjaPancerza> list = pancerz.getLokalizacjaPancerzaList();
                for (int i = 0; i < list.size(); i++) {
                    switch (list.get(i).getId()) {
                        case 1:
                            glowa = glowa + pancerz.getPunktyPancerza();
                            korpus = korpus + pancerz.getPunktyPancerza();
                            prawaReka = prawaReka + pancerz.getPunktyPancerza();
                            lewaReka = lewaReka + pancerz.getPunktyPancerza();
                            prawaNoga = prawaNoga + pancerz.getPunktyPancerza();
                            lewaNoga = lewaNoga + pancerz.getPunktyPancerza();
                            break;
                        case 2:
                            break;
                        case 3:
                            korpus = korpus + pancerz.getPunktyPancerza();
                            break;
                        case 4:
                            break;
                        case 5:
                            glowa = glowa + pancerz.getPunktyPancerza();
                            break;
                        case 6:
                            prawaReka = prawaReka + pancerz.getPunktyPancerza();
                            break;
                        case 7:
                            lewaReka = lewaReka + pancerz.getPunktyPancerza();
                            break;
                        case 8:
                            prawaNoga = prawaNoga + pancerz.getPunktyPancerza();
                            break;
                        case 9:
                            lewaNoga = lewaNoga + pancerz.getPunktyPancerza();
                            break;
                        default:
                            break;
                    }
                }
            }


            dialogBinding.glowaTekst.setText("Głowa: " + glowa);
            dialogBinding.korpusText.setText("Korpus: " + korpus);
            dialogBinding.prawaRenkaText.setText("Prawa Ręka: " + prawaReka);
            dialogBinding.lewaRekaText.setText("Lewa Ręka: " + lewaReka);
            dialogBinding.prawaNogaText.setText("Prawa Noga: " + prawaNoga);
            dialogBinding.lewaNogaText.setText("Lewa Noga: " + lewaNoga);
            dialogBinding.buttonOk.setOnClickListener(v2 -> dialog.dismiss());

            dialog.show();
        });

        adapter.setOnItemLongClickListener(pancerz -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Wybierz akcję");

            List<String> options = new ArrayList<>();
            Map<String, Runnable> actions = new HashMap<>();

            options.add("Usuń");
            actions.put("Usuń", () -> confirmDeletePancerz(db, pancerz, kartaId));

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
            List<CechaPancerza> listaCech = v.getCechaPancerzaList();

            SpannableStringBuilder info = new SpannableStringBuilder();

            for (CechaPancerza cechaPancerza: listaCech){
                SpannableString notatka = new SpannableString(cechaPancerza.getNazwa() +": " + cechaPancerza.getOpis() +"\n\n");
                notatka.setSpan(new StyleSpan(Typeface.BOLD), 0, cechaPancerza.getNazwa().length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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

    private void addPancerzToKarta(SQLiteDatabase db, int kartaId, int pancerzId) {

        ContentValues values = new ContentValues();
        values.put("karta_id", kartaId);
        values.put("pancerz_id", pancerzId);
        values.put("czy_zalożony", 0);

        db.insert("karta_pancerz", null, values);
    }

    private void updatePancerz(SQLiteDatabase db, int kartaId, int pancerzId, int czyZalozone) {
        ContentValues values = new ContentValues();
        values.put("karta_id", kartaId);
        values.put("pancerz_id", pancerzId);
        values.put("czy_zalożony", czyZalozone);

        String whereClause = "karta_id = ? AND pancerz_id = ?";
        String[] whereArgs = {String.valueOf(kartaId), String.valueOf(pancerzId)};

        db.update("karta_pancerz", values, whereClause, whereArgs);

    }

    private void confirmDeletePancerz(SQLiteDatabase db, Pancerz pancerz, int kartaId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć?")
                .setPositiveButton("Tak", (dialog, which) -> deletePancerz(db, pancerz, kartaId))
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void deletePancerz(SQLiteDatabase db, Pancerz pancerz, int kartaId) {
        db.delete("karta_pancerz", "pancerz_id=? AND karta_id=?", new String[]{String.valueOf(pancerz.getId()), String.valueOf(kartaId)});
        List<Pancerz> currentList = new ArrayList<>(adapter.getPancerzList());
        currentList.remove(pancerz);

        adapter.updateList(currentList);
    }

    private void addWlasnyPancerz(SQLiteDatabase db, Pancerz pancerz) {

        ContentValues values = new ContentValues();
        values.put("nazwa", pancerz.getNazwa());
        values.put("cena", pancerz.getCena());
        values.put("kara", pancerz.getKara());
        values.put("punkty_pancerza", pancerz.getPunktyPancerza());

        values.put("dostępność_id", dostepnoscId);
        values.put("typ_pancerza_id", typPancerzaId);

        int id = (int) db.insert("pancerz", null, values);

        ContentValues values3 = new ContentValues();

        if (czyGlowaZaznaczona) {
            values3.put("pancerz_id", id);
            values3.put("lokalizacja_pancerza_id", glowaId);
            db.insert("lokalizacja_pancerza_pancerz", null, values3);
            values3.clear();

        }
        if (czyKorpusZaznaczony) {
            values3.put("pancerz_id", id);
            values3.put("lokalizacja_pancerza_id", korpusId);
            db.insert("lokalizacja_pancerza_pancerz", null, values3);
            values3.clear();
        }
        if (czyPrawaRekaZaznaczona) {
            values3.put("pancerz_id", id);
            values3.put("lokalizacja_pancerza_id", prawaRekaId);
            db.insert("lokalizacja_pancerza_pancerz", null, values3);
            values3.clear();
        }
        if (czyLewaRekaZaznaczona) {
            values3.put("pancerz_id", id);
            values3.put("lokalizacja_pancerza_id", lewaRekaId);
            db.insert("lokalizacja_pancerza_pancerz", null, values3);
            values3.clear();
        }
        if (czyPrawaNogaZaznaczona) {
            values3.put("pancerz_id", id);
            values3.put("lokalizacja_pancerza_id", prawaNogaId);
            db.insert("lokalizacja_pancerza_pancerz", null, values3);
            values3.clear();
        }
        if (czyLewaNogaZaznaczona) {
            values3.put("pancerz_id", id);
            values3.put("lokalizacja_pancerza_id", lewaNogaId);
            db.insert("lokalizacja_pancerza_pancerz", null, values3);
            values3.clear();
        }

        values.clear();

        for (CechaPancerza cechaPancerza: pancerz.getCechaPancerzaList()){
            values.put("pancerz_id", id);
            values.put("cecha_id", cechaPancerza.getId());
            db.insert("cechy_pancerz_pancerz",null,values);
            values.clear();
        }

        ContentValues values2 = new ContentValues();
        values2.put("karta_id", kartaId);
        values2.put("pancerz_id", id);
        values2.put("czy_zalożony", 0);

        db.insert("karta_pancerz", null, values2);


    }
}
