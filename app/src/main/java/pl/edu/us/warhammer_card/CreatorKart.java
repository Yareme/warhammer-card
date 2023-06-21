package pl.edu.us.warhammer_card;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.Rasa;

public class CreatorKart {



   public Karta createRandomKarta(SQLiteDatabase db,int idKampania){
        Karta karta =new Karta();

        ContentValues values = new ContentValues();
        values.put("kampania_id", idKampania);
        int rasaId = getRandomRasaId(db);

        int profesjaId = getRandomProfesjaId(db,rasaId);
        int wiek = getRandomWiek(rasaId);
        int wzrost =getRandomWzrost(rasaId);
        String name = getRandomName(db,rasaId);
        String oczy = getRandomOczy(db,rasaId);
        String wlosy = getRandomWlosy(db,rasaId);

        values.put("imie",name);
        values.put("rasa_id", rasaId);
        values.put("profesja_id", profesjaId);

        values.put("poziom_profesji_id", 1 + 4 * ( profesjaId - 1 )  );

        values.put("wiek", wiek);
        values.put("wzrost", wzrost);
        values.put("oczy", oczy);
        values.put("wlosy", wlosy);




        int idKarta= (int) db.insert("karta", null, values);
            values.clear();
            karta.setId(idKarta);

            addChechaToCard(db,idKarta,rasaId);


        for (int i =1; i<=26; i++){
            values.put("karta_id",  idKarta);
            values.put("umiejętności_id", i);
            values.put("rozwój",0);
            int k=(int) db.insert("karta_umiętność", null, values);
            values.clear();
        }

        return karta;

    }

    public Karta createEmtyKarta(SQLiteDatabase db, int idKampania){

        Karta karta =new Karta();

        ContentValues values = new ContentValues();
        values.put("kampania_id", idKampania);

        values.put("rasa_id", (Integer) null);
        values.put("profesja_id", (Integer) null);
        values.put("poziom_profesji_id", (Integer) null);

        int idKarta= (int) db.insert("karta", null, values);
        karta.setId(idKarta);
        values.clear();

        for (int i =1; i<=10; i++){
            values.put("karta_id",  idKarta);
            values.put("cechy_id", i);
            values.put("wartość_pociątkowa",0 );
            values.put("rozwój",0);
            db.insert("karta_cecha", null, values);
            values.clear();
        }

        for (int i =1; i<=26; i++){
            values.put("karta_id",  idKarta);
            values.put("umiejętności_id", i);
            values.put("rozwój",0);
            int k=(int) db.insert("karta_umiętność", null, values);
            values.clear();
        }
        return karta;
    }

    int getRandom(){
        return  (int) (1 + Math.floor((101 - 1) * Math.random()));
    }

    int getRandomRasaId(SQLiteDatabase db){

        int randomeNumber = getRandom();
        int rasaId=0;
        String[] colums={"id"," wartość_max","wartość_min"};
        String selection="wartość_max >= ? AND wartość_min <= ?";
        String[] selectionArgs={String.valueOf(randomeNumber),String.valueOf(randomeNumber)};

        Cursor cursor = db.query("rasa", colums, selection,selectionArgs, null, null, null);
        if(cursor.moveToFirst()) {
            rasaId=cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }
        cursor.close();
        return rasaId;
    }

    int getRandomProfesjaId(SQLiteDatabase db,int idRasa){
       int profesjaId=0;
       int random = getRandom();
        String[] colums={"profesja_id","rasa_id"," wartość_max","wartość_min"};
        String selection="rasa_id = ? AND (wartość_max >= ? AND wartość_min <= ?)";
        String[] selectionArgs={String.valueOf(idRasa),String.valueOf(random),String.valueOf(random)};

        Cursor cursor = db.query("rasa_profesja", colums, selection,selectionArgs, null, null, null);
        if(cursor.moveToFirst()) {
         profesjaId=cursor.getInt(cursor.getColumnIndexOrThrow("profesja_id"));
         Log.d("idProf: "," idProfesja: " +String.valueOf(profesjaId)+" random: " +String.valueOf(random));
        }
        cursor.close();



        return profesjaId;
    }

    String getRandomName(SQLiteDatabase db,int idRasa){
        String name = null;
        String[] columns = {"imię"};
        String selection = "rasa_id = ?";
        String[] selectionArgs = {String.valueOf(idRasa)};

        // Zliczanie liczby rekordów spełniających warunek
        int count = (int) DatabaseUtils.queryNumEntries(db, "Imiona", selection, selectionArgs);

        if (count > 0) {
            // Wylosowanie losowego indeksu
            int randomIndex = new Random().nextInt(count);

            // Utworzenie nowego zapytania z ograniczeniem na wylosowany indeks
            String query = "SELECT imię FROM Imiona WHERE rasa_id = ? LIMIT 1 OFFSET ?";
            String[] queryArgs = {String.valueOf(idRasa), String.valueOf(randomIndex)};

            Cursor cursor = db.rawQuery(query, queryArgs);
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndexOrThrow("imię"));
            }
            cursor.close();
        }

        return name;
    }

    void addChechaToCard(SQLiteDatabase db,int idKarta,int rasaId){
        ContentValues values = new ContentValues();
        Map<Integer, Integer> idRasaMap = new HashMap<>();
        switch (rasaId){
            case 1:
                for (int i =1; i<=10; i++){
                    values.put("karta_id",  idKarta);
                    values.put("cechy_id", i);
                    values.put("wartość_pociątkowa", 22 + Math.floor((41 - 22) * Math.random())  +20 );
                    values.put("rozwój",0);
                    db.insert("karta_cecha", null, values);
                }
                values.clear();
                break;
            case 2:

                idRasaMap.put(1, 10);
                idRasaMap.put(2, 30);
                idRasaMap.put(3, 10);
                idRasaMap.put(4, 20);
                idRasaMap.put(5, 20);
                idRasaMap.put(6, 20);
                idRasaMap.put(7, 30);
                idRasaMap.put(8, 20);
                idRasaMap.put(9, 30);
                idRasaMap.put(10, 30);

                for (int i =1; i<=10; i++){
                    values.put("karta_id",  idKarta);
                    values.put("cechy_id", i);
                    values.put("wartość_pociątkowa", 22 + Math.floor((41 - 22) * Math.random())  + idRasaMap.get(i) );
                    values.put("rozwój",0);
                    db.insert("karta_cecha", null, values);
                }
                values.clear();
                idRasaMap.clear();


                break;
            case 3:

                idRasaMap.put(1, 30);
                idRasaMap.put(2, 20);
                idRasaMap.put(3, 20);
                idRasaMap.put(4, 30);
                idRasaMap.put(5, 20);
                idRasaMap.put(6, 10);
                idRasaMap.put(7, 30);
                idRasaMap.put(8, 20);
                idRasaMap.put(9, 40);
                idRasaMap.put(10, 10);

                for (int i =1; i<=10; i++){
                    values.put("karta_id",  idKarta);
                    values.put("cechy_id", i);
                    values.put("wartość_pociątkowa", 22 + Math.floor((41 - 22) * Math.random())  + idRasaMap.get(i) );
                    values.put("rozwój",0);
                    db.insert("karta_cecha", null, values);
                }
                values.clear();
                idRasaMap.clear();


                break;
            case 4 :
            case 5:

                idRasaMap.put(1, 30);
                idRasaMap.put(2, 30);
                idRasaMap.put(3, 20);
                idRasaMap.put(4, 20);
                idRasaMap.put(5, 40);
                idRasaMap.put(6, 30);
                idRasaMap.put(7, 30);
                idRasaMap.put(8, 30);
                idRasaMap.put(9, 30);
                idRasaMap.put(10, 20);

                for (int i =1; i<=10; i++){
                    values.put("karta_id",  idKarta);
                    values.put("cechy_id", i);
                    values.put("wartość_pociątkowa", 22 + Math.floor((41 - 22) * Math.random())  + idRasaMap.get(i) );
                    values.put("rozwój",0);
                    db.insert("karta_cecha", null, values);
                }
                values.clear();
                idRasaMap.clear();

                break;

        }

    }

    int getRandomWiek(int rasaId){
       int wiek=0;

       switch (rasaId){
           case 1:
               wiek = (int) ( 1 + Math.floor((11 - 1) * Math.random())  +15);
                    break;
           case 2:
               wiek = (int) ( 5 + Math.floor((51 - 5) * Math.random())  +15);

               break;
           case 3:
               wiek = (int) ( 10 + Math.floor((101 - 10) * Math.random())  +15);

               break;
           case 4:
               case 5:
                   wiek = (int) ( 10 + Math.floor((101 - 10) * Math.random())  +30);

                   break;
       }

       return wiek;
    }

    int getRandomWzrost(int rasaId){
        int wzrost=0;

        switch (rasaId){
            case 1:
                wzrost = (int) ( 4 + Math.floor((41 - 4) * Math.random())  +150);
                break;
            case 2:
                wzrost = (int) ( 2 + Math.floor((21 - 2) * Math.random())  +95);

                break;
            case 3:
                wzrost = (int) ( 2 + Math.floor((21 - 2) * Math.random())  +130);

                break;
            case 4:
            case 5:
                wzrost = (int) ( 3 + Math.floor((31 - 3) * Math.random())  +180);

                break;
        }

        return wzrost;
    }


    String getRandomOczy(SQLiteDatabase db,int idRasa){
        String oczy = null;
        String[] columns = {"imię"};
        String selection = "rasa_id = ?";
        String[] selectionArgs = {String.valueOf(idRasa)};

        // Zliczanie liczby rekordów spełniających warunek
        int count = (int) DatabaseUtils.queryNumEntries(db, "kolor_oczu", selection, selectionArgs);

        if (count > 0) {
            // Wylosowanie losowego indeksu
            int randomIndex = new Random().nextInt(count);

            // Utworzenie nowego zapytania z ograniczeniem na wylosowany indeks
            String query = "SELECT kolor FROM kolor_oczu WHERE rasa_id = ? LIMIT 1 OFFSET ?";
            String[] queryArgs = {String.valueOf(idRasa), String.valueOf(randomIndex)};

            Cursor cursor = db.rawQuery(query, queryArgs);
            if (cursor.moveToFirst()) {
                oczy = cursor.getString(cursor.getColumnIndexOrThrow("kolor"));
            }
            cursor.close();
        }

        return oczy;
    }

    String getRandomWlosy(SQLiteDatabase db,int idRasa){
        String wlosy = null;
        String[] columns = {"imię"};
        String selection = "rasa_id = ?";
        String[] selectionArgs = {String.valueOf(idRasa)};

        // Zliczanie liczby rekordów spełniających warunek
        int count = (int) DatabaseUtils.queryNumEntries(db, "kolor_wlosy", selection, selectionArgs);

        if (count > 0) {
            // Wylosowanie losowego indeksu
            int randomIndex = new Random().nextInt(count);

            // Utworzenie nowego zapytania z ograniczeniem na wylosowany indeks
            String query = "SELECT kolor FROM kolor_wlosy WHERE rasa_id = ? LIMIT 1 OFFSET ?";
            String[] queryArgs = {String.valueOf(idRasa), String.valueOf(randomIndex)};

            Cursor cursor = db.rawQuery(query, queryArgs);
            if (cursor.moveToFirst()) {
                wlosy = cursor.getString(cursor.getColumnIndexOrThrow("kolor"));
            }
            cursor.close();
        }

        return wlosy;
    }

}



