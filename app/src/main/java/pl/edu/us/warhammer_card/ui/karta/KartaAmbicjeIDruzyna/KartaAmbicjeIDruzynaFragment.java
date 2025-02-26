package pl.edu.us.warhammer_card.ui.karta.KartaAmbicjeIDruzyna;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.databinding.FramgentAmbicjeIDruzynaBinding;
import pl.edu.us.warhammer_card.table.Karta;

public class KartaAmbicjeIDruzynaFragment extends Fragment {

   private FramgentAmbicjeIDruzynaBinding binding;
   private KartaAmbicjeIDruzynaViewModel kartaAmbicjeIDruzynaViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
        binding = FramgentAmbicjeIDruzynaBinding.inflate(inflater,container,false);


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

        Karta karta = dbHelper.getKartaById(db, kartaId);

        kartaAmbicjeIDruzynaViewModel =
                new ViewModelProvider(this).get(KartaAmbicjeIDruzynaViewModel.class);
        kartaAmbicjeIDruzynaViewModel.getKarta(kartaId);

        kartaAmbicjeIDruzynaViewModel.getKarta(kartaId).observe(getViewLifecycleOwner(), new Observer<Karta>() {
            @Override
            public void onChanged(Karta karta) {
                // Jeśli dane są dostępne, zaktualizuj UI
                if (karta != null) {

                    binding.ambicjaKrotkoterminowaEdit.setText(karta.getAmbicjaKrotkoterminowa());
                    binding.ambicjaDlugoterminowaEdit.setText(karta.getAmbicjaDlugoterminowa());

                    binding.nazwaDruzynyEdit.setText(karta.getNazwaDruzyny());
                    binding.ambicjaDryzunowaKrutkaEdit.setText(karta.getAmbicjaDruzynowaKrotkoterm());
                    binding.ambicjaDryzunowaDlugoEdit.setText(karta.getAmbicjaDruzynowaDlugoterm());

                    binding.czlonkowieDruzynyEdit.setText(karta.getCzlonkowieDruzyny());

                    Karta newKarta = karta;

                    kartaAmbicjeIDruzynaViewModel.updateKarta(newKarta);
                    kartaAmbicjeIDruzynaViewModel.getKarta(kartaId);

                }
            }
        });


        binding.ambicjaKrotkoterminowaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setAmbicjaKrotkoterminowa(text);
                kartaAmbicjeIDruzynaViewModel.updateKarta(karta);
            }
        });

        binding.ambicjaDlugoterminowaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setAmbicjaDlugoterminowa(text);
                kartaAmbicjeIDruzynaViewModel.updateKarta(karta);
            }
        });

        binding.nazwaDruzynyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setNazwaDruzyny(text);
                kartaAmbicjeIDruzynaViewModel.updateKarta(karta);
            }
        });

        binding.ambicjaDryzunowaKrutkaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setAmbicjaDruzynowaKrotkoterm(text);
                kartaAmbicjeIDruzynaViewModel.updateKarta(karta);
            }
        });

        binding.ambicjaDryzunowaDlugoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setAmbicjaDruzynowaDlugoterm(text);
                kartaAmbicjeIDruzynaViewModel.updateKarta(karta);
            }
        });
        
        binding.czlonkowieDruzynyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setCzlonkowieDruzyny(text);
                kartaAmbicjeIDruzynaViewModel.updateKarta(karta);
            }
        });

        binding.helpAmbicje.setOnClickListener(v -> {

            new AlertDialog.Builder(getContext())
                    .setTitle("Pomoc")
                    .setMessage("Ambicje postaci ....")
                    .setPositiveButton("OK", null)
                    .show();
        });

        binding.helpDruzyna.setOnClickListener(v -> {

            new AlertDialog.Builder(getContext())
                    .setTitle("Pomoc")
                    .setMessage("Drużuna i jej ambicje.")
                    .setPositiveButton("OK", null)
                    .show();
        });



        return binding.getRoot();

    }
}
