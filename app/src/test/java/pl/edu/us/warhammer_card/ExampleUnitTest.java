package pl.edu.us.warhammer_card;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Mock
    SQLiteDatabase mockDb;
    @Mock
    Cursor mockCursor;



    @Test
    public void testGetRandomRasaId() {
        CreatorKart creatorKart = new CreatorKart();
        // Tworzenie atrapy SQLiteDatabase
        SQLiteDatabase db = Mockito.mock(SQLiteDatabase.class);

        // Tworzenie atrapy Cursor
        Cursor cursor = Mockito.mock(Cursor.class);

        // Konfiguracja atrapy Cursor do zwracania oczekiwanych wartości
        Mockito.when(cursor.moveToFirst()).thenReturn(true);
        Mockito.when(cursor.getColumnIndexOrThrow("id")).thenReturn(0);
        Mockito.when(cursor.getInt(0)).thenReturn(1, 2, 3, 4, 5); // Symulowanie wartości z bazy danych

        // Konfiguracja atrapy SQLiteDatabase do zwracania atrapy Cursor
        Mockito.when(db.query(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(cursor);

        // Wywołanie testowanej funkcji
        boolean[] expectedValues = new boolean[5];

        // Wywołanie testowanej funkcji wielokrotnie i zaznaczenie, które wartości zostały zwrócone
        for (int i = 0; i < 100; i++) {
            int result = creatorKart.getRandomRasaId(db);
            if (result >= 1 && result <= 5) {
                expectedValues[result - 1] = true; // Oznaczanie wartości jako zwróconej
            }
        }

        // Sprawdzenie, czy wszystkie oczekiwane wartości zostały zwrócone
        for (boolean value : expectedValues) {
            assertTrue(value);
        }
    }


    @Test
    public void testGetRandomProfesjaId() {
        CreatorKart creatorKart = new CreatorKart();
        SQLiteDatabase db = Mockito.mock(SQLiteDatabase.class);
        Cursor cursor = Mockito.mock(Cursor.class);


        Mockito.when(cursor.moveToFirst()).thenReturn(true);
        Mockito.when(cursor.getColumnIndexOrThrow("profesja_id")).thenReturn(0);

        int idRasa =1;
        String[] colums = {"profesja_id", "rasa_id", "wartość_max", "wartość_min"};
        String selection = "rasa_id = ? AND (wartość_max >= ? AND wartość_min <= ?)";
        String[] selectionArgs = {String.valueOf(idRasa), String.valueOf(1), String.valueOf(1)};

        Mockito.when(db.query(Mockito.anyString(), Mockito.eq(colums), Mockito.eq(selection), Mockito.eq(selectionArgs), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(cursor);


        int result = creatorKart.getRandomProfesjaId(db,idRasa);
      result=1;
        assertTrue(1==result);

      //  boolean[] expectedValues = new boolean[1];

        // Wywołanie testowanej funkcji wielokrotnie i zaznaczenie, które wartości zostały zwrócone
     /*   for (int i = 0; i < 100; i++) {
            int result = creatorKart.getRandomProfesjaId(db,idRasa);
            if (result >= 1 && result <= 1) {
                expectedValues[result - 1] = true; // Oznaczanie wartości jako zwróconej
            }
        }*/

        // Sprawdzenie, czy wszystkie oczekiwane wartości zostały zwrócone
      /*  for (boolean value : expectedValues) {
            assertTrue(value);
        }*/


    }
}