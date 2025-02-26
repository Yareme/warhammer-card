package pl.edu.us.warhammer_card.ui.karta2.bron;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBronBinding;
import pl.edu.us.warhammer_card.table.Karta;

public class BronFragment extends Fragment {
    FragmentKartaBronBinding binding;
    Karta karta = new Karta();
    int kartaId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding= FragmentKartaBronBinding.inflate(inflater, container, false);


        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId=args.getInt("KartaId");

        karta=dbHelper.getKartaById(db,kartaId);


        return binding.getRoot();
    }
}
