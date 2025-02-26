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
            gestureDetector = new GestureDetector(getContext(), new KartaMainFragment2.SwipeGestureListener(args));
            View root = binding.getRoot();  // Pobieramy główny widok fragmentu

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

            root.setOnTouchListener((v, event) -> {
                gestureDetector.onTouchEvent(event);
                boolean isSwipe = gestureDetector.onTouchEvent(event);
                if (!isSwipe) {
                    // Sprawdzamy, czy to rzeczywiście kliknięcie
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.performClick(); // Wywołujemy performClick, jeśli to kliknięcie
                    }
                }
                return true;
            });

        }

        return binding.getRoot();
    }


    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        private final Bundle args;

        SwipeGestureListener(Bundle args) {
            this.args = args;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1 == null || e2 == null) return false; // Zapobiega NullPointerException

            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) { // Swipe w prawo
                    NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.action_kartaMainFragment2_to_fragment_karta_main, args);
         /*           NavHostFragment.findNavController(KartaMainFragment2.this)
                            .navigate(R.id.action_kartaMainFragment2_to_fragment_karta_main, args);*/
                }
                return true;
            }
            return false;
        }
    }
}
