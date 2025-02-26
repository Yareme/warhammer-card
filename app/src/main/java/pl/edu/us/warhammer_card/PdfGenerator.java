package pl.edu.us.warhammer_card;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.AcroFields;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;

public class PdfGenerator {

/*
    public static void fillPdfFromAssets(Context context, String inputPdfName, File outputFile, Map<String, String> data) {
        try {
            // Otwórz plik PDF z assets
            InputStream inputStream = context.getAssets().open("pdf/" + inputPdfName);

            // Tymczasowy plik do kopiowania PDF
            File tempFile = File.createTempFile("temp_pdf", ".pdf", context.getCacheDir());
            FileOutputStream tempOut = new FileOutputStream(tempFile);

            // Skopiuj zawartość pliku PDF do pliku tymczasowego
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                tempOut.write(buffer, 0, length);
            }
            inputStream.close();
            tempOut.close();

            // Otwórz PDF do edycji, używając FileInputStream do odczytu tymczasowego pliku
            FileInputStream fis = new FileInputStream(tempFile);
            PdfReader reader = new PdfReader(fis); // Używamy FileInputStream
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

            // Uzyskaj formularz (AcroFields)
            AcroFields form = stamper.getAcroFields();

            if (form != null) {
                // Wypełnij pola formularza danymi
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    if (form.getField(entry.getKey()) != null) {
                        form.setField(entry.getKey(), entry.getValue());
                    }
                }
            }

            // Zamknij stamper i zapisanie pliku PDF
            stamper.close();
            reader.close();
            fis.close();
            System.out.println("PDF wygenerowany: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
*/

    public static void fillPdfFromAssets(Context context, String inputPdfName, Uri outputUri, Map<String, String> data) {
        try {
            InputStream inputStream = context.getAssets().open("pdf/" + inputPdfName);
            File tempFile = File.createTempFile("temp_pdf", ".pdf", context.getCacheDir());
            FileOutputStream tempOut = new FileOutputStream(tempFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                tempOut.write(buffer, 0, length);
            }
            inputStream.close();
            tempOut.close();

            FileInputStream fis = new FileInputStream(tempFile);
            PdfReader reader = new PdfReader(fis);
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(outputUri, "w");
            FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());
            PdfStamper stamper = new PdfStamper(reader, fos);

            AcroFields form = stamper.getAcroFields();
            if (form != null) {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    if (form.getField(entry.getKey()) != null) {
                        form.setField(entry.getKey(), entry.getValue());
                    }
                }
            }

            stamper.close();
            reader.close();
            fis.close();
            pfd.close();
            System.out.println("PDF wygenerowany: " + outputUri.toString());
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
    public static void generatePdf(Context context) {
        // Nazwa pliku w `assets/pdf/`
        String inputPdf = "WFRP.pdf";

        // Ścieżka do zapisu w pamięci urządzenia (`Documents/Warhammer/`)
        File outputDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Warhammer");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        File outputFile = new File(outputDir, "Warhammer_karta.pdf");

        String folderName = "Warhammer"; //  Podfolder w Documents
        String fileName = "Warhammer_karta.pdf";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/" + folderName);

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
        Log.d("pdf", String.valueOf(uri)+ "to uri");


        // Dane do wypełnienia formularza PDF
        Map<String, String> dane = new HashMap<>();
        dane.put("Imię", "22222");
        dane.put("Rasa", "Człowiek");
        dane.put("Klasa", "Wojownik");
        dane.put("Profesja", "Łowca Potworów");
        dane.put("Poziom Profesji", "2");
        dane.put("Status", "Szlachcic");
        dane.put("Wiek", "35");
        dane.put("Wzrost", "180 cm");
        dane.put("Oczy", "Czarne");
        dane.put("WW_poczatkowa", "22");
        dane.put("WW_rozwieniecie", "1");
        dane.put("WW_aktualna", "23");
        dane.put("US_poczatkowa", "22");
        dane.put("US_rozwienicie", "1");
        dane.put("US_aktualna", "23");
        dane.put("Atletyka_ZW_cecha", "44");
        dane.put("Atletyka_ZW_cecha_rozwiniecie", "2");
        dane.put("Atletyka_ZW_cecha_SUMA", "46");
        dane.put("Charyzma_OGD_cecha", "33");
        dane.put("Charyzma_OGD_cecha_rozwiniecie", "7");
        dane.put("Charyzma_OGD_cecha_suma", "40");

        // Generowanie nowego PDF z danymi
        fillPdfFromAssets(context, inputPdf, uri, dane);
    }
}

/*import android.content.Context;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class PdfGenerator {

    public static void fillPdfFromAssets(Context context, String inputPdfName, String outputPath, Map<String, String> data) {
        try {
            // Załaduj plik PDF z folderu assets
            InputStream inputStream = context.getAssets().open(inputPdfName);
            PdfReader reader = new PdfReader(inputStream);  // Otwórz PDF do odczytu

            // Ustaw plik wyjściowy
            FileOutputStream fos = new FileOutputStream(outputPath);
            PdfWriter writer = new PdfWriter(fos);

            // Zainicjuj PdfDocument
            PdfDocument pdfDoc = new PdfDocument(reader, writer);

            // Pobierz formularz PDF
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

            if (form != null) {
                // Wypełnij pola formularza danymi
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    PdfFormField field = form.getField(entry.getKey());
                    if (field != null) {
                        field.setValue(entry.getValue());
                    }
                }
            }

            // Zamknij dokument i zapisz zmiany
            pdfDoc.close();
            System.out.println("PDF wygenerowany: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Ścieżka do pliku w folderze assets
        String inputPdf = "WFRP.pdf";  // Plik PDF w folderze assets
        String outputPdf = "/path/to/output/Warhammerwygenerowany_karta.pdf";  // Ścieżka do nowego pliku PDF

        // Dane do wypełnienia formularza
        Map<String, String> dane = new HashMap<>();
        dane.put("Imię", "Geralt");
        dane.put("Rasa", "Człowiek");
        dane.put("Klasa", "Wojownik");
        dane.put("Profesja", "Łowca Potworów");
        dane.put("Poziom Profesji", "2");
        dane.put("Status", "Szlachcic");
        dane.put("Wiek", "35");
        dane.put("Wzrost", "180 cm");
        dane.put("Oczy", "Żółte");

        // Generowanie nowego PDF z wypełnionym formularzem
        fillPdfFromAssets(context, inputPdf, outputPdf, dane);
    }
}
*/