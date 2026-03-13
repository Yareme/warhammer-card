package pl.edu.us.warhammer_card.ui.karta;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.databinding.FragmentKartaMain2Binding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaWyposazenieBinding;
import pl.edu.us.warhammer_card.table.Karta;

public class KartaMainFragment2 extends Fragment {
    private GestureDetector gestureDetector;

    private FragmentKartaMain2Binding binding;
    Karta karta = new Karta();
    int kartaId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKartaMain2Binding.inflate(inflater, container, false);

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId = args.getInt("KartaId");

        karta = dbHelper.getKartaById(db, kartaId);


        {
            View root = binding.getRoot();

            binding.pancerz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("KartaId", kartaId);
                    NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.action_kartaMainFragment2_to_pancerzFragment, args);
                }
            });

            binding.wyposazenie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("KartaId", kartaId);
                    NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.action_kartaMainFragment2_to_wyposazeniaFragment, args);
                }
            });

            binding.zamoznoscIZywotnosc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("KartaId", kartaId);
                    NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.action_kartaMainFragment2_to_zamoznoscIZywotnoscFragment, args);
                }
            });

            binding.psychologiaZepsucieIMutacje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("KartaId", kartaId);
                    NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.action_kartaMainFragment2_to_psychologiaFragment, args);
                }
            });

            binding.bron.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("KartaId", kartaId);
                    NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.action_kartaMainFragment2_to_bronFragment, args);
                }
            });

            binding.czaryIModlitwy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("KartaId", kartaId);
                    NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.action_kartaMainFragment2_to_czaryIModlitwyFragment, args);
                }
            });


        }

        return binding.getRoot();
    }



}
