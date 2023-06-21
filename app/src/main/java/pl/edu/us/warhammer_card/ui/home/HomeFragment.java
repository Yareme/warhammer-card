package pl.edu.us.warhammer_card.ui.home;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.databinding.FragmentHomeBinding;
import pl.edu.us.warhammer_card.table.Kampania;

import pl.edu.us.warhammer_card.R;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("PRAGMA journal_mode = WAL;");
        } catch (SQLiteException e) {
            // Obsłuż wyjątek, jeśli wystąpił błąd przy wykonaniu zapytania
            e.printStackTrace();
        }

        List<Kampania> list = getKampaniaList(db);

        settupList(list);






        Cursor cursor = db.rawQuery("PRAGMA journal_mode", null);
        if (cursor.moveToFirst()) {
            String journalMode = cursor.getString(0);
            // Przetwarzaj wartość journalMode
            Log.d("Tag", "Tryb dziennika: " + journalMode);
        }
        cursor.close();




        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameKapmani =binding.nameKampani.getText().toString().trim();
                if (nameKapmani.equals("")){
                        nameKapmani="Podstawowa nazwa";
                }


                Date date = new Date();
                int timeInMilliseconds = (int) date.getTime();


                ContentValues values = new ContentValues();
                values.put("nazwa", nameKapmani);
                values.put("data_utworzenia", timeInMilliseconds);

                db.insert("kampania",null,values);

                settupList(getKampaniaList(db));


            }

        });


        binding.list.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Kampania> list = getKampaniaList(db);

                Bundle args = new Bundle();
                args.putInt("key", list.get(position).getId());

                NavHostFragment navHostFragment = (NavHostFragment) ((FragmentActivity) Objects.requireNonNull(getContext())).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
                NavController navController = navHostFragment.getNavController();

                navController.navigate(R.id.action_nav_home_to_fragment_Karta,args);


            }
        });

        binding.list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                deleteKampanie(list.get(position),db,list,position);

                return true;
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void deleteKampanie(Kampania kampania, SQLiteDatabase db,List<Kampania> list,int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("Czy chcesz usunąnć");
        builder.setPositiveButton("Usuń", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selection = "id = ?";
                String[] selectionArgs = { Integer.toString(kampania.getId()) };

                db.delete("kampania",selection, selectionArgs);
              //  db.delete("karta","kampania_id = ?", selectionArgs);

                list.remove(position);
                settupList(list);

            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();

    }

    private void settupList(List<Kampania> data){
        ArrayAdapter<Kampania> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.test_list_item,
                android.R.id.text1, data);

        binding.list.setAdapter(arrayAdapter);
    }
    public List<Kampania> getKampaniaList(SQLiteDatabase db) {


        String[] projection = { "nazwa" };
        String[] colums={"*"};
        String sortOrder = "nazwa ASC";

        Cursor cursor = db.query("kampania", colums, null, null, null, null, sortOrder);
        List<Kampania> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Kampania kampania = new Kampania();
            kampania.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            kampania.setNazwa(cursor.getString(cursor.getColumnIndexOrThrow("nazwa")));
            kampania.setDate(cursor.getInt(cursor.getColumnIndexOrThrow("data_utworzenia")));
            list.add(kampania);
            Log.d("Kampania", String.valueOf(kampania.getId()) + " " +kampania.getNazwa()+ " "+ kampania.getDate());

        }
        cursor.close();
        return list;

    }


}