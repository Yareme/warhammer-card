package pl.edu.us.warhammer_card;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AppSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "data";
    private static final int DATABASE_VERSION = 1;
    Context context;

    public AppSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;


    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        try {
            InputStream inputStream = context.getAssets().open("db_init.sql");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
            String sqlStatements = stringBuilder.toString();
            String[] queries = sqlStatements.split(";");

            for (String query : queries) {
                if (query.trim().length() > 0) {
                    db.execSQL(query);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            InputStream inputStream = context.getAssets().open("update.sql");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
            String sqlStatements = stringBuilder.toString();
            String[] queries = sqlStatements.split(";");

            for (String query : queries) {
                if (query.trim().length() > 0) {
                    db.execSQL(query);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
