package pl.edu.us.warhammer_card;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper {


    public String getRasaNameById(SQLiteDatabase db, int id){
        String[] colums = {"*"};

        String[] selectionArgs = {String.valueOf(id)};

        String query = "SELECT id, name " +
                "FROM rasa "+
                "WHERE id=?";

        Cursor cursor = db.rawQuery(query, selectionArgs);

        String nazwa=null;

        if (cursor.moveToNext()) {
            nazwa = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        }
        cursor.close();

        return nazwa;
    }

    public String getProfesjaNameById(SQLiteDatabase db, int id){

        String[] selectionArgs = {String.valueOf(id)};

        String query = "SELECT id, nazwa " +
                "FROM profesja "+
                "WHERE id=?";

        Cursor cursor = db.rawQuery(query, selectionArgs);

        String nazwa=null;

        if (cursor.moveToNext()) {
            nazwa = cursor.getString(cursor.getColumnIndexOrThrow("nazwa"));
        }
        cursor.close();

        return nazwa;
    }

}

