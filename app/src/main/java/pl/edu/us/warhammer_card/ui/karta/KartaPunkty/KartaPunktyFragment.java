package pl.edu.us.warhammer_card.ui.karta.KartaPunkty;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.databinding.FragmentKartaPunktyBinding;
import pl.edu.us.warhammer_card.table.Cechy;
import pl.edu.us.warhammer_card.table.Karta;

public class KartaPunktyFragment extends Fragment {
    FragmentKartaPunktyBinding binding;
    private KartaPunktyViewModel kartaPunktyViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaPunktyBinding.inflate(inflater, container, false);


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Bundle args = getArguments();
        assert args != null;
        int kartaId = args.getInt("KartaId");

        Karta karta = dbHelper.getKartaById(db, kartaId);

        kartaPunktyViewModel =
                new ViewModelProvider(this).get(KartaPunktyViewModel.class);
        kartaPunktyViewModel.getKarta(kartaId);

        kartaPunktyViewModel.getKarta(kartaId).observe(getViewLifecycleOwner(), new Observer<Karta>() {
            @Override
            public void onChanged(Karta karta) {
                if (karta != null) {

                    binding.aktualne.setText(String.valueOf(karta.getXpAktualny()));

                    /*TODO Dodać w nawjasach faktyczne wydany ex zależny od rozwinienć*/
                    binding.wydane.setText(String.valueOf(karta.getXpWydany()));

                    int sum;
                    sum = karta.getXpAktualny() + karta.getXpWydany();

                    binding.suma.setText(String.valueOf(sum));

                    binding.bohaterEdit.setText(String.valueOf(karta.getPunktyBohatera()));
                    binding.determinacjaEdit.setText(String.valueOf(karta.getPunktyDeterminacji()));
                    binding.dodatkoweEdit.setText(String.valueOf(karta.getPunktyDodatkowe()));

                    binding.przeznaczeniaEdit.setText(String.valueOf(karta.getPunktyPrzeznaczenia()));
                    binding.szczeEdit.setText(String.valueOf(karta.getPunktySzczescia()));

                    int szybkosc = 0;
                    szybkosc = karta.getSzybkosc();
                    binding.szybkoscEdit.setText(String.valueOf(szybkosc));

                    binding.chodEdit.setText(String.valueOf(szybkosc * 2));
                    binding.biegEdit.setText(String.valueOf(szybkosc * 4));


                    binding.motywacjaEdit.setText(karta.getMotywacja());

                    Karta newKarta = karta;

                    newKarta.setXpWydany(Integer.parseInt(binding.aktualne.getText().toString()));

                    newKarta.getXpWydany();

                    kartaPunktyViewModel.updateKarta(newKarta);
                    kartaPunktyViewModel.getKarta(kartaId);

                }
            }
        });

        binding.aktualne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setXpAktualny(wartosc);
                int wydane = 0;
                if (!binding.wydane.getText().toString().isEmpty()) {
                    wydane = Integer.parseInt(binding.wydane.getText().toString());
                }
                binding.suma.setText(String.valueOf(wartosc + wydane));
                kartaPunktyViewModel.updateKarta(karta);
            }
        });

        binding.wydane.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setXpWydany(wartosc);
                int wydane = 0;
                if (!binding.aktualne.getText().toString().isEmpty()) {
                    wydane = Integer.parseInt(binding.aktualne.getText().toString());
                }
                binding.suma.setText(String.valueOf(wartosc + wydane));
                kartaPunktyViewModel.updateKarta(karta);
            }
        });

        binding.bohaterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setPunktyBohatera(wartosc);
                kartaPunktyViewModel.updateKarta(karta);
            }
        });

        binding.determinacjaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setPunktyDeterminacji(wartosc);
                kartaPunktyViewModel.updateKarta(karta);
            }
        });

        binding.dodatkoweEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setPunktyDodatkowe(wartosc);
                kartaPunktyViewModel.updateKarta(karta);
            }
        });

        binding.przeznaczeniaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setPunktyPrzeznaczenia(wartosc);
                kartaPunktyViewModel.updateKarta(karta);
            }
        });

        binding.szczeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setPunktySzczescia(wartosc);
                kartaPunktyViewModel.updateKarta(karta);
            }
        });

        binding.szybkoscEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                int wartosc = 0;
                if (!text.isEmpty()) {
                    try {
                        wartosc = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                karta.setSzybkosc(wartosc);
                kartaPunktyViewModel.updateKarta(karta);
                binding.chodEdit.setText(String.valueOf(wartosc * 2));
                binding.biegEdit.setText(String.valueOf(wartosc * 4));
            }
        });

        binding.motywacjaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setMotywacja(text);
                kartaPunktyViewModel.updateKarta(karta);
            }
        });

        binding.helpDoswiadczenie.setOnClickListener(v -> {

            new AlertDialog.Builder(getContext())
                    .setTitle("Pomoc")
                    .setMessage("Doświadczenie to suma zdobytych punktów przez gracza.")
                    .setPositiveButton("OK", null)
                    .show();
        });

        binding.helpBohater.setOnClickListener(v -> {

            new AlertDialog.Builder(getContext())
                    .setTitle("Pomoc")
                    .setMessage("Punkty Bohatera to ....")
                    .setPositiveButton("OK", null)
                    .show();
        });

        binding.helpPrzeznaczenie.setOnClickListener(v -> {

            new AlertDialog.Builder(getContext())
                    .setTitle("Pomoc")
                    .setMessage("Punkty Przeznaczenia to ....")
                    .setPositiveButton("OK", null)
                    .show();
        });

        binding.helpSzybkosc.setOnClickListener(v -> {

            new AlertDialog.Builder(getContext())
                    .setTitle("Pomoc")
                    .setMessage("Szybkość to ....")
                    .setPositiveButton("OK", null)
                    .show();
        });

        binding.helpMotywacja.setOnClickListener(v -> {

            new AlertDialog.Builder(getContext())
                    .setTitle("Pomoc")
                    .setMessage("Motywacja postaci to ....")
                    .setPositiveButton("OK", null)
                    .show();
        });

        return binding.getRoot();
    }

}
