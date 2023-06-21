package pl.edu.us.warhammer_card.ui.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import pl.edu.us.warhammer_card.AppSQLiteHelper;
import pl.edu.us.warhammer_card.databinding.FragmentSlideshowBinding;
import pl.edu.us.warhammer_card.databinding.FragmentTestBinding;
import pl.edu.us.warhammer_card.ui.slideshow.SlideshowViewModel;

public class TestFragment extends Fragment {

    ArrayList<String> namesList = new ArrayList<>();
    List<String> nameList;
    private FragmentTestBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



    /*    SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);*/

        binding = FragmentTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
/*    final TextView textView = binding.textTest;*/
/*      slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        AppSQLiteHelper dbHelper = new AppSQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        if (db==null){
           // binding.textTest.setText("NULLL");

        }else {
            List<String> data = getNameList(db);
            settupList(data);
        }
        return root;
    }

   private void settupList(List<String> data){
       ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.test_list_item,
                android.R.id.text1,
                data);

        binding.list.setAdapter(arrayAdapter);
   }
    public List<String> getNameList(SQLiteDatabase db) {


            String[] projection = { "name" };
            String sortOrder = "name ASC";
            Cursor cursor = db.query("rasa", null, null, null, null, null, sortOrder);
            List<String> listaImion = new ArrayList<>();
            while (cursor.moveToNext()) {
                String imie = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                listaImion.add(imie);


            }
        cursor.close();
        return listaImion;

    }
}
