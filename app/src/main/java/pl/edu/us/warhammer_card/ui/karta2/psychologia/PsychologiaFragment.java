package pl.edu.us.warhammer_card.ui.karta2.psychologia;

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
import pl.edu.us.warhammer_card.databinding.FragmentKartaBronBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaPsychologiaZepsucieIMutacjeBinding;
import pl.edu.us.warhammer_card.table.Karta;

public class PsychologiaFragment extends Fragment {
    FragmentKartaPsychologiaZepsucieIMutacjeBinding binding;

    PsychologiaViewModel psychologiaViewModel;
    Karta karta = new Karta();
    int kartaId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding= FragmentKartaPsychologiaZepsucieIMutacjeBinding.inflate(inflater, container, false);


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId=args.getInt("KartaId");

        karta=dbHelper.getKartaById(db,kartaId);

        psychologiaViewModel =
                new ViewModelProvider(this).get(PsychologiaViewModel.class);
        psychologiaViewModel.getKarta(kartaId);

        psychologiaViewModel.getKarta(kartaId).observe(getViewLifecycleOwner(), new Observer<Karta>() {
            @Override
            public void onChanged(Karta karta) {
                binding.zepsycieIMutacjeEdit.setText(karta.getZepsucieIMutacje());
                binding.psychologiaEdit.setText(karta.getPsyhologia());
            }
        });


        binding.zepsycieIMutacjeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setZepsucieIMutacje(text);

                psychologiaViewModel.updateKarta(karta);
            }
        });

        binding.psychologiaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                karta.setPsyhologia(text);

                psychologiaViewModel.updateKarta(karta);
            }
        });


        return binding.getRoot();
    }

}