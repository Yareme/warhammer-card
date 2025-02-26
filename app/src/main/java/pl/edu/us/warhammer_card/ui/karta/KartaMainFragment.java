package pl.edu.us.warhammer_card.ui.karta;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;
import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.CreatorKart;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBinding;
import pl.edu.us.warhammer_card.databinding.FragmentKartaMainBinding;
import pl.edu.us.warhammer_card.table.Karta;

public class KartaMainFragment extends Fragment {

    FragmentKartaMainBinding binding;
    private GestureDetector gestureDetector;

    Karta karta = new Karta();
    int kartaId;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



    binding= FragmentKartaMainBinding.inflate(inflater, container, false);

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        kartaId=args.getInt("KartaId");

        karta=getKarta(db,kartaId);


        Log.d("Karta", String.valueOf("KartaID="+args.getInt("kartaId")));

       /* binding.button1.setText("Id tej karty: "+ kartaId);*/
        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("KartaId",kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();

                navController.navigate(R.id.action_fragment_karta_main_to_fragment_karta_front,args);
               /* String[] a ={"1"};
                db.delete("karta",null,null);*/
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putInt("KartaId",kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_main_to_fragment_karta_cechy,args);

            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putInt("KartaId", kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_main_to_fragment_karta_umiejetnosci,args);

            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("KartaId",kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_main_to_fragment_karta_umiejetnosci2,args);
            }
        });

        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("KartaId",kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_main_to_kartaPunktyFragment,args);
            }
        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("KartaId",kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_main_to_kartaTalentFragment,args);
            }
        });

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("KartaId",kartaId);
                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_karta_main_to_kartaAmbicjeIDruzynaFragment,args);

            }
        });

        gestureDetector = new GestureDetector(getContext(), new SwipeGestureListener(args));

        View root = binding.getRoot();  // Pobieramy główny widok fragmentu

        // Ustawiamy OnTouchListener na główny widok
        root.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            v.performClick(); // Dodajemy dla poprawności Accessibility API
            return true;
        });


        return root;
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
                if (diffX < 0) { // Swipe w lewo

                    NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.action_fragment_karta_main_to_kartaMainFragment2,args);
                    /*NavHostFragment.findNavController(KartaMainFragment.this)
                            .navigate(R.id.action_fragment_karta_main_to_kartaMainFragment2, args);*/
                }
                return true;
            }
            return false;
        }
    }

    Karta getKarta(SQLiteDatabase db,int id){

        String[] projection = { "kampania_id" };
        String[] colums={"*"};
        String[] selectionArgs={String.valueOf(id)};
        // String sortOrder = "nazwa ASC";
        // String[] id ={"3"};
        Cursor cursor = db.query("karta", colums, "id = ?",selectionArgs, null, null, null);

        Log.d("KartaID", Integer.toString(id));

        Karta karta = null;

        if (cursor.moveToFirst()) {
            karta = new Karta();
            karta.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            karta.setProfesjaId(cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id")));
            karta.setRasaId(cursor.getInt(cursor.getColumnIndexOrThrow("rasa_id")));
            karta.setPoziomProfesjiId(cursor.getInt(cursor.getColumnIndexOrThrow("poziom_id")));
            karta.setImie(cursor.getString(cursor.getColumnIndexOrThrow("imie")));

            karta.setWiek(cursor.getString(cursor.getColumnIndexOrThrow("wiek")));
            karta.setWzrost(cursor.getString(cursor.getColumnIndexOrThrow("wzrost")));
            karta.setWlosy(cursor.getString(cursor.getColumnIndexOrThrow("wlosy")));
            karta.setOczy(cursor.getString(cursor.getColumnIndexOrThrow("oczy")));

            Log.d("Karta", String.valueOf(karta.getId()));
        }

        cursor.close();

        return karta;
    }
}
