package pl.edu.us.warhammer_card.ui.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.CreatorKart;
import pl.edu.us.warhammer_card.R;
import pl.edu.us.warhammer_card.databinding.FragmentKartaBinding;
import pl.edu.us.warhammer_card.table.Karta;

public class KartaFragment   extends Fragment {

     FragmentKartaBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentKartaBinding.inflate(inflater, container, false);

        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Bundle args = getArguments();
        assert args != null;
        int kampanieId = args.getInt("key");
       // binding.button.setText(kampanieId);

        List<Karta> data = getKartaList(db,kampanieId);
        settupList(data);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreatorKart creatorKart = new CreatorKart();
                Karta karta = creatorKart.createEmtyKarta(db,kampanieId);
                Bundle args = new Bundle();
               args.putInt("KartaId", karta.getId());

                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_Karta_to_fragment_karta_front,args);

            }
        });



        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreatorKart creatorKart = new CreatorKart();
                Karta karta =creatorKart.createRandomKarta(db,kampanieId);

                Bundle args = new Bundle();

                args.putInt("KartaId", karta.getId());

                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_fragment_Karta_to_fragment_karta_front,args);

            }
        });



        binding.list.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle args = new Bundle();
                args.putInt("KartaId", data.get(position).getId());

                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();

                navController.navigate(R.id.action_fragment_Karta_to_fragment_karta_front,args);


            }
        });


        View root = binding.getRoot();
        return root;
}






    private void settupList(List<Karta> data){
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.test_list_item,
                android.R.id.text1,
                data);

        binding.list.setAdapter(arrayAdapter);
    }
    public List<Karta> getKartaList(SQLiteDatabase db, int kampanieID) {


        String[] projection = { "kampania_id" };
        String[] colums={"*"};  /*id,profesja_id,rasa_id,imie*/
        String[] selectionArgs={Integer.toString(kampanieID)};
       // String sortOrder = "nazwa ASC";
       // String[] id ={"3"};

        Cursor cursor = db.query("karta", colums, "kampania_id = ?",selectionArgs, null, null, null);
        List<Karta> listaKarta = new ArrayList<>();
        Log.d("Karta", Integer.toString(kampanieID));


        while (cursor.moveToNext()) {
            Karta karta = new Karta();
            karta.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            karta.setProfesjaId(cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id")));
            karta.setRasaId(cursor.getInt(cursor.getColumnIndexOrThrow("rasa_id")));
            karta.setImie(cursor.getString(cursor.getColumnIndexOrThrow("imie")));

            listaKarta.add(karta);
            Log.d("Karta", String.valueOf(karta.getId()));

        }
        cursor.close();
        return listaKarta;

    }

}
