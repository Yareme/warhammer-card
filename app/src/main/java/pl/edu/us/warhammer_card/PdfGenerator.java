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

import androidx.annotation.NonNull;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.AcroFields;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import pl.edu.us.warhammer_card.table.Bron;
import pl.edu.us.warhammer_card.table.CechaBroni;
import pl.edu.us.warhammer_card.table.CechaPancerza;
import pl.edu.us.warhammer_card.table.Karta;
import pl.edu.us.warhammer_card.table.LokalizacjaPancerza;
import pl.edu.us.warhammer_card.table.Pancerz;
import pl.edu.us.warhammer_card.table.Talent;
import pl.edu.us.warhammer_card.table.Umiejetnosci;
import pl.edu.us.warhammer_card.table.Wyposarzenia;
import pl.edu.us.warhammer_card.table.Zaklecia;

public class PdfGenerator {
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

    public static void generatePdf(@NonNull Context context, Karta karta) {
        String inputPdf = "WFRP.pdf";

        File outputDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Warhammer");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        File outputFile = new File(context.getExternalFilesDir(null), "Warhammer_karta.pdf");

        String folderName = "Warhammer";
        String fileName = "Warhammer_karta.pdf";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/" + folderName);

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
        Log.d("pdf", String.valueOf(uri) + "to uri");


        Map<String, String> dane = new HashMap<>();
        dane.put("Imię", karta.getImie());
        dane.put("Rasa", karta.getRasa().getName());
        dane.put("Klasa", karta.getProfesja().getKlasa().getNazwa());

        dane.put("Profesja", karta.getProfesja().getNazawa());
        dane.put("Poziom Profesji", karta.getPoziomProfesja().getNazwa());

        dane.put("Ścieżka Profesji", karta.getProfesja().getSciezkaProfesji());
        dane.put("Status", karta.getPoziomProfesja().getStatus().getNazwa());

        dane.put("Wiek", karta.getWiek());
        dane.put("Wzrost", karta.getWzrost());

        dane.put("fill_118", karta.getWlosy());

        dane.put("Oczy", karta.getOczy());


        dane.put("WW_poczatkowa", String.valueOf(karta.getCechyList().get(0).getWartPo()));
        dane.put("US_poczatkowa", String.valueOf(karta.getCechyList().get(1).getWartPo()));
        dane.put("S_poczatkowa", String.valueOf(karta.getCechyList().get(2).getWartPo()));
        dane.put("Wt_poczatkowa", String.valueOf(karta.getCechyList().get(3).getWartPo()));
        dane.put("I_poczatkowa", String.valueOf(karta.getCechyList().get(4).getWartPo()));
        dane.put("ZW_poczatkowa", String.valueOf(karta.getCechyList().get(5).getWartPo()));
        dane.put("ZR_poczatkowa", String.valueOf(karta.getCechyList().get(6).getWartPo()));
        dane.put("INT_poczatkowa", String.valueOf(karta.getCechyList().get(7).getWartPo()));
        dane.put("SW_poczatkowa", String.valueOf(karta.getCechyList().get(8).getWartPo()));
        dane.put("OGD_poczatkowa", String.valueOf(karta.getCechyList().get(9).getWartPo()));

        dane.put("WW_rozwieniecie", String.valueOf(karta.getCechyList().get(0).getRozw()));
        dane.put("US_rozwienicie", String.valueOf(karta.getCechyList().get(1).getRozw()));
        dane.put("S_rozwienicie", String.valueOf(karta.getCechyList().get(2).getRozw()));
        dane.put("WT_rozwienicie", String.valueOf(karta.getCechyList().get(3).getRozw()));
        dane.put("I_rozwienicie", String.valueOf(karta.getCechyList().get(4).getRozw()));
        dane.put("ZW_rozwienicie", String.valueOf(karta.getCechyList().get(5).getRozw()));
        dane.put("ZR_rozwienicie", String.valueOf(karta.getCechyList().get(6).getRozw()));
        dane.put("INT_rozwienicie", String.valueOf(karta.getCechyList().get(7).getRozw()));
        dane.put("SW_rozwienicie", String.valueOf(karta.getCechyList().get(8).getRozw()));
        dane.put("OGD_rozwienicie", String.valueOf(karta.getCechyList().get(9).getRozw()));

        dane.put("WW_aktualna", String.valueOf(karta.getCechyList().get(0).getWartPo() + karta.getCechyList().get(0).getRozw()));
        dane.put("US_aktualna", String.valueOf(karta.getCechyList().get(1).getWartPo() + karta.getCechyList().get(1).getRozw()));
        dane.put("S_aktualna", String.valueOf(karta.getCechyList().get(2).getWartPo() + karta.getCechyList().get(2).getRozw()));
        dane.put("WT_aktualna", String.valueOf(karta.getCechyList().get(3).getWartPo() + karta.getCechyList().get(3).getRozw()));
        dane.put("I_aktualna", String.valueOf(karta.getCechyList().get(4).getWartPo() + karta.getCechyList().get(4).getRozw()));
        dane.put("ZW_aktualna", String.valueOf(karta.getCechyList().get(5).getWartPo() + karta.getCechyList().get(5).getRozw()));
        dane.put("ZR_aktualna", String.valueOf(karta.getCechyList().get(6).getWartPo() + karta.getCechyList().get(6).getRozw()));
        dane.put("INT_aktualna", String.valueOf(karta.getCechyList().get(7).getWartPo() + karta.getCechyList().get(7).getRozw()));
        dane.put("SW_aktualna", String.valueOf(karta.getCechyList().get(8).getWartPo() + karta.getCechyList().get(8).getRozw()));
        dane.put("OGD_aktualna", String.valueOf(karta.getCechyList().get(9).getWartPo() + karta.getCechyList().get(9).getRozw()));

        dane.put("Punkty BohateraRow1", String.valueOf(karta.getPunktyBohatera()));
        dane.put("Punkty Determinacji", String.valueOf(karta.getPunktyDeterminacji()));
        dane.put("Motywacja", karta.getMotywacja());

        dane.put("Punkty Przeznaczenia", String.valueOf(karta.getPunktyPrzeznaczenia()));
        dane.put("Punkty Szczęścia", String.valueOf(karta.getPunktySzczescia()));

        dane.put("Aktualne doświadczenie", String.valueOf(karta.getXpAktualny()));
        dane.put("Wydane doświadczenie", String.valueOf(karta.getXpWydany()));
        dane.put("Suma doświadczenia", String.valueOf(karta.getXpAktualny() + karta.getXpWydany()));

        dane.put("Szybkość", String.valueOf(karta.getSzybkosc()));
        dane.put("Chód", String.valueOf(karta.getSzybkosc() * 2));
        dane.put("Bieg", String.valueOf(karta.getSzybkosc() * 4));

        dane.put("Atletyka_ZW_cecha", String.valueOf(karta.getUmiejetnosciList().get(0).getWortascCecha()));
        dane.put("Atletyka_ZW_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(0).getRozwoj()));
        dane.put("Atletyka_ZW_cecha_SUMA", String.valueOf(karta.getUmiejetnosciList().get(0).getSuma()));

        dane.put("BB_podstawoa_cecha", String.valueOf(karta.getUmiejetnosciList().get(1).getWortascCecha()));
        dane.put("BB_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(1).getRozwoj()));
        dane.put("BB_suma", String.valueOf(karta.getUmiejetnosciList().get(1).getSuma()));

        dane.put("Charyzma_OGD_cecha", String.valueOf(karta.getUmiejetnosciList().get(2).getWortascCecha()));
        dane.put("Charyzma_OGD_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(2).getRozwoj()));
        dane.put("Charyzma_OGD_cecha_suma", String.valueOf(karta.getUmiejetnosciList().get(2).getSuma()));

        dane.put("Dowodzenie_OGD_cecha", String.valueOf(karta.getUmiejetnosciList().get(3).getWortascCecha()));
        dane.put("Dowodzenie_OGD_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(3).getRozwoj()));
        dane.put("Dowodzenie_OGD_cecha_suma", String.valueOf(karta.getUmiejetnosciList().get(3).getSuma()));

        dane.put("HAZARD_INT_cecha", String.valueOf(karta.getUmiejetnosciList().get(4).getWortascCecha()));
        dane.put("HAZARD_INT_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(4).getRozwoj()));
        dane.put("HAZARD_INT_cecha_suma", String.valueOf(karta.getUmiejetnosciList().get(4).getSuma()));

        dane.put("INTUICJA_I_cecha", String.valueOf(karta.getUmiejetnosciList().get(5).getWortascCecha()));
        dane.put("INTUICJA_I_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(5).getRozwoj()));
        dane.put("INTUICJA_I_cecha_SUMA", String.valueOf(karta.getUmiejetnosciList().get(5).getSuma()));

        dane.put("Jezdziectwo_ZW_cecha", String.valueOf(karta.getUmiejetnosciList().get(6).getWortascCecha()));
        dane.put("Jezdziectwo_ZW_cecha_rozwieniecie", String.valueOf(karta.getUmiejetnosciList().get(6).getRozwoj()));
        dane.put("Jezdziectwo_ZW_cecha_suma", String.valueOf(karta.getUmiejetnosciList().get(6).getSuma()));

        dane.put("Mocna głowa_cecha_WT", String.valueOf(karta.getUmiejetnosciList().get(7).getWortascCecha()));
        dane.put("Mocna głowa_cecha_WT_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(7).getRozwoj()));
        dane.put("Mocna głowa_cecha_WT_SUMA", String.valueOf(karta.getUmiejetnosciList().get(7).getSuma()));

        dane.put("Nawigacja_cecha_I_", String.valueOf(karta.getUmiejetnosciList().get(8).getWortascCecha()));
        dane.put("Nawigacja_cecha_I_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(8).getRozwoj()));
        dane.put("Nawigacja_cecha_I_rozwiniecie_SUMA", String.valueOf(karta.getUmiejetnosciList().get(8).getSuma()));

        dane.put("Odporność_WT_cecha", String.valueOf(karta.getUmiejetnosciList().get(9).getWortascCecha()));
        dane.put("Odporność_WT_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(9).getRozwoj()));
        dane.put("Odporność_WT_cecha_SUMA", String.valueOf(karta.getUmiejetnosciList().get(9).getSuma()));

        dane.put("Opanowanie_SW_SW_cecha", String.valueOf(karta.getUmiejetnosciList().get(10).getWortascCecha()));
        dane.put("Opanowanie_SW_SW_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(10).getRozwoj()));
        dane.put("Opanowanie_SW_SW_cecha_suma", String.valueOf(karta.getUmiejetnosciList().get(10).getSuma()));

        dane.put("Oswajanie_cecha_SW", String.valueOf(karta.getUmiejetnosciList().get(11).getWortascCecha()));
        dane.put("Oswajanie_cecha_SW_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(11).getRozwoj()));
        dane.put("Oswajanie_cecha_SW_SUMA", String.valueOf(karta.getUmiejetnosciList().get(11).getSuma()));

        dane.put("PERCEPCJA_cecha_I", String.valueOf(karta.getUmiejetnosciList().get(12).getWortascCecha()));
        dane.put("PERCEPCJA_cecha_I_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(12).getRozwoj()));
        dane.put("PERCEPCJA_cecha_I_SUMA", String.valueOf(karta.getUmiejetnosciList().get(12).getSuma()));

        dane.put("PLOTKOWANIE_CECHA_OGD", String.valueOf(karta.getUmiejetnosciList().get(13).getWortascCecha()));
        dane.put("PLOTKOWANIE_CECHA_OGD_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(13).getRozwoj()));
        dane.put("PLOTKOWANIE_CECHA_OGD_SUMA", String.valueOf(karta.getUmiejetnosciList().get(13).getSuma()));

        dane.put("POWOŻENIE_ZW_cecha", String.valueOf(karta.getUmiejetnosciList().get(14).getWortascCecha()));
        dane.put("POWOŻENIE_ZW_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(14).getRozwoj()));
        dane.put("POWOŻENIE_ZW_cecha_SUMA", String.valueOf(karta.getUmiejetnosciList().get(14).getSuma()));

        dane.put("Przekupstwo_cecha_OGD", String.valueOf(karta.getUmiejetnosciList().get(15).getWortascCecha()));
        dane.put("Przekupstwo_cecha_OGD_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(15).getRozwoj()));
        dane.put("Przekupstwo_cecha_OGD_suma", String.valueOf(karta.getUmiejetnosciList().get(15).getSuma()));

        dane.put("SKRADANIE_ZW_cecha", String.valueOf(karta.getUmiejetnosciList().get(16).getWortascCecha()));
        dane.put("SKRADANIE_ZW_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(16).getRozwoj()));
        dane.put("SKRADANIE_ZW_cecha_suma", String.valueOf(karta.getUmiejetnosciList().get(16).getSuma()));

        dane.put("SZTUKA_cecha_Zr", String.valueOf(karta.getUmiejetnosciList().get(17).getWortascCecha()));
        dane.put("SZTUKA_cecha_Zr_rozwieniecie", String.valueOf(karta.getUmiejetnosciList().get(17).getRozwoj()));
        dane.put("SZTUKA_cecha_Zr_SUMA", String.valueOf(karta.getUmiejetnosciList().get(17).getSuma()));

        dane.put("Sztuka_przetrwania_INT_cecha", String.valueOf(karta.getUmiejetnosciList().get(18).getWortascCecha()));
        dane.put("Sztuka_przetrwania_INT_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(18).getRozwoj()));
        dane.put("Sztuka_przetrwania_INT_cecha_SUMA", String.valueOf(karta.getUmiejetnosciList().get(18).getSuma()));

        dane.put("Targowanie_OGD_cecha_", String.valueOf(karta.getUmiejetnosciList().get(19).getWortascCecha()));
        dane.put("Targowanie_OGD_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(19).getRozwoj()));
        dane.put("Targowanie_OGD_cecha_SUMA", String.valueOf(karta.getUmiejetnosciList().get(19).getSuma()));

        dane.put("UNIK_ZW_cecha_", String.valueOf(karta.getUmiejetnosciList().get(20).getWortascCecha()));
        dane.put("UNIK_ZW_cecha_rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(20).getRozwoj()));
        dane.put("UNIK_ZW_cecha_SUMA", String.valueOf(karta.getUmiejetnosciList().get(20).getSuma()));

        dane.put("wioslarstwo_cecha_S_", String.valueOf(karta.getUmiejetnosciList().get(21).getWortascCecha()));
        dane.put("wioslarstwo_cecha_S_roziwniecie", String.valueOf(karta.getUmiejetnosciList().get(21).getRozwoj()));
        dane.put("wioslarstwo_cecha_S_SUMA", String.valueOf(karta.getUmiejetnosciList().get(21).getSuma()));

        dane.put("Wspinaczka_cecha_S_", String.valueOf(karta.getUmiejetnosciList().get(22).getWortascCecha()));
        dane.put("Wspinaczka_cecha_S_rozwiniecie_", String.valueOf(karta.getUmiejetnosciList().get(22).getRozwoj()));
        dane.put("Wspinaczka_cecha_S_SUMA", String.valueOf(karta.getUmiejetnosciList().get(22).getSuma()));

        dane.put("Występy_OGD_cecha_", String.valueOf(karta.getUmiejetnosciList().get(23).getWortascCecha()));
        dane.put("Występy_OGD_cecha_rozwieniecie", String.valueOf(karta.getUmiejetnosciList().get(23).getRozwoj()));
        dane.put("Występy_OGD_cecha_SUMA", String.valueOf(karta.getUmiejetnosciList().get(23).getSuma()));

        dane.put("Zastraszanie_Cecha_S_", String.valueOf(karta.getUmiejetnosciList().get(24).getWortascCecha()));
        dane.put("Zastraszanie_Cecha_S__rozwiniecie", String.valueOf(karta.getUmiejetnosciList().get(24).getRozwoj()));
        dane.put("Zastraszanie_Cecha_S_SUMA", String.valueOf(karta.getUmiejetnosciList().get(24).getSuma()));


        StringBuilder nazwyUmiejetnosciZaawansowaneBilder = new StringBuilder();
        StringBuilder nazwyCechDlaUmiejetnosciZaawansowaneBilder = new StringBuilder();
        StringBuilder wartosciCechDlaUmiejetnosciZaawansowaneBilder = new StringBuilder();
        StringBuilder rozwojDlaUmiejetnosciZaawansowaneBilder = new StringBuilder();
        StringBuilder sumaDlaUmiejetnosciZaawansowaneBilder = new StringBuilder();


        for (Umiejetnosci umiejetnosc : karta.getUmiejetnosciZaawansowaneList()) {
            nazwyUmiejetnosciZaawansowaneBilder.append(umiejetnosc.getNazwa()).append("\n");
            nazwyCechDlaUmiejetnosciZaawansowaneBilder.append(umiejetnosc.getCechaNazwa()).append("\n");

            wartosciCechDlaUmiejetnosciZaawansowaneBilder.append(umiejetnosc.getWortascCecha()).append("\n");
            rozwojDlaUmiejetnosciZaawansowaneBilder.append(umiejetnosc.getRozwoj()).append("\n");
            sumaDlaUmiejetnosciZaawansowaneBilder.append(umiejetnosc.getSuma()).append("\n");
        }

        String nazwyUmiejetnosciZaawansowane = nazwyUmiejetnosciZaawansowaneBilder.toString();
        String nazwyCechDlaUmiejetnosciZaawansowane = nazwyCechDlaUmiejetnosciZaawansowaneBilder.toString();
        String wartosciCechDlaUmiejetnosciZaawansowane = wartosciCechDlaUmiejetnosciZaawansowaneBilder.toString();
        String rozwojDlaUmiejetnosciZaawansowane = rozwojDlaUmiejetnosciZaawansowaneBilder.toString();
        String sumaDlaUmiejetnosciZaawansowane = sumaDlaUmiejetnosciZaawansowaneBilder.toString();


        dane.put("Nazwa umiejętności zaawansowanej i grupowej", nazwyUmiejetnosciZaawansowane);
        dane.put("Cecha1", nazwyCechDlaUmiejetnosciZaawansowane);
        dane.put("Cecha2", wartosciCechDlaUmiejetnosciZaawansowane);
        dane.put("Rozwinięcie__", rozwojDlaUmiejetnosciZaawansowane);
        dane.put("Suma____", sumaDlaUmiejetnosciZaawansowane);


        StringBuilder nazwaTalentuBilder = new StringBuilder();
        StringBuilder pozimTalentuBilder = new StringBuilder();
        StringBuilder opisTalentuBilder = new StringBuilder();

        for (Talent talent : karta.getTalentList()) {

            nazwaTalentuBilder.append(talent.getNazwa()).append("\n");
            pozimTalentuBilder.append(talent.getPoziom()).append("\n");

            String opis = talent.getOpis();

            if (opis.length() > 34) {
                opis = opis.substring(0, 34);
            }

            opisTalentuBilder.append(opis).append("\n");

        }

        String nazwaTalentu = nazwaTalentuBilder.toString();
        String pozimTalentu = pozimTalentuBilder.toString();
        String opisTalentu = opisTalentuBilder.toString();

        dane.put("Talent", nazwaTalentu);
        dane.put("Poziom Talentu", pozimTalentu);
        dane.put("Opis talentu", opisTalentu);

        dane.put("Ambicja krótkoterminowa", karta.getAmbicjaKrotkoterminowa());
        dane.put("Ambicja długoterminowa", karta.getAmbicjaDlugoterminowa());

        dane.put("Nazwa drużyny", karta.getNazwaDruzyny());
        dane.put("Ambicja drużyny krótkoterminowa", karta.getAmbicjaDruzynowaKrotkoterm());
        dane.put("Ambicja drużyny długoterminowa", karta.getAmbicjaDruzynowaDlugoterm());
        dane.put("Członkowie drużyny", karta.getCzlonkowieDruzyny());


        List<Pancerz> pancerzList = karta.getPancerzList();
        int limit = Math.min(pancerzList.size(), 5);

        for (int i = 0; i < limit; i++) {
           /* if (karta.getPancerzList().size() == 0) {
                break;
            }*/
            Pancerz pancerz = pancerzList.get(i);

            String listaLokalizacja =pancerz.getLokalizacjaPancerzaList().stream()
                    .map(LokalizacjaPancerza::getNazwa)
                    .collect(Collectors.joining(", "));

            String listaNazwCech = pancerz.getCechaPancerzaList().stream()
                    .map(CechaPancerza::getNazwa)
                    .collect(Collectors.joining(", "));

     /*       String listaLokalizacja = karta.getPancerzList().get(i).getLokalizacjaPancerzaList().stream()
                    .map(LokalizacjaPancerza::getNazwa)
                    .collect(Collectors.joining(", "));

            String listaNazwCech = karta.getPancerzList().get(i).getCechaPancerzaList().stream()
                    .map(CechaPancerza::getNazwa)
                    .collect(Collectors.joining(", "));*/



            dane.put("LokacjaRow" + String.valueOf(i + 1), listaLokalizacja);
            dane.put("ObcRow" + String.valueOf(i + 1), "0");
            dane.put("PPRow" + String.valueOf(i + 1), String.valueOf(karta.getPancerzList().get(i).getPunktyPancerza()));
            dane.put("CechyRow" + String.valueOf(i + 1), listaNazwCech);


        }

        List<Pancerz> pancerze = karta.getPancerzList();
        int limit2 = Math.min(pancerze.size(), 5);
        for (int i = 0; i < limit; i++) {
            dane.put("NazwaRow" + (i + 1), pancerze.get(i).getNazwa());
        }


/*
        if (karta.getPancerzList().size() > 0) {

            if (karta.getPancerzList().size() == 1)
                dane.put("NazwaRow1_2", karta.getPancerzList().get(0).getNazwa());
            if (karta.getPancerzList().size() > 1) {
                for (int i = 1; i <= 4; i++) {
                    dane.put("NazwaRow" + String.valueOf(i + 1), karta.getPancerzList().get(i).getNazwa());
                    dane.put("NazwaRow" + String.valueOf(i + 1), karta.getPancerzList().get(i).getNazwa());
                    dane.put("NazwaRow" + String.valueOf(i + 1), karta.getPancerzList().get(i).getNazwa());
                    dane.put("NazwaRow" + String.valueOf(i + 1), karta.getPancerzList().get(i).getNazwa());

                }

            }

        }
*/

        int glowa = 0;
        int korpus = 0;
        int prawaReka = 0;
        int lewaReka = 0;
        int prawaNoga = 0;
        int lewaNoga = 0;

        for (Pancerz pancerz : karta.getPancerzList()) {
            if (pancerz.getCzyZalozone() == 0) {
                continue;
            }
            List<LokalizacjaPancerza> list = pancerz.getLokalizacjaPancerzaList();
            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i).getId()) {
                    case 1:
                        glowa = glowa + pancerz.getPunktyPancerza();
                        korpus = korpus + pancerz.getPunktyPancerza();
                        prawaReka = prawaReka + pancerz.getPunktyPancerza();
                        lewaReka = lewaReka + pancerz.getPunktyPancerza();
                        prawaNoga = prawaNoga + pancerz.getPunktyPancerza();
                        lewaNoga = lewaNoga + pancerz.getPunktyPancerza();
                        break;
                    case 2:
                        break;
                    case 3:
                        korpus = korpus + pancerz.getPunktyPancerza();
                        break;
                    case 4:
                        break;
                    case 5:
                        glowa = glowa + pancerz.getPunktyPancerza();
                        break;
                    case 6:
                        prawaReka = prawaReka + pancerz.getPunktyPancerza();
                        break;
                    case 7:
                        lewaReka = lewaReka + pancerz.getPunktyPancerza();
                        break;
                    case 8:
                        prawaNoga = prawaNoga + pancerz.getPunktyPancerza();
                        break;
                    case 9:
                        lewaNoga = lewaNoga + pancerz.getPunktyPancerza();
                        break;
                    default:
                        break;
                }
            }
        }

        dane.put("Głowa", String.valueOf(glowa));
        dane.put("Lewa ręka", String.valueOf(lewaReka));
        dane.put("Prawa ręka", String.valueOf(prawaReka));
        dane.put("Korpus", String.valueOf(korpus));
        dane.put("Prawa noga", String.valueOf(prawaNoga));
        dane.put("Lewa noga", String.valueOf(lewaNoga));
        dane.put("Tarcza", "0");


        //WYPOSAŻENIE

        StringBuilder wyposazenieBilder = new StringBuilder();

        for (Wyposarzenia wyposarzenia : karta.getWyposarzeniaList()) {
            wyposazenieBilder.append(wyposarzenia.getNazwa()).append(wyposarzenia.getSztuk()).append(" szt.").append(" ,");
        }
        String wyposazenie = wyposazenieBilder.toString();
        dane.put("NazwaRow1_3", wyposazenie);


        String psychologiaKarta = karta.getPsyhologia();
        String psychologia1 = "";
        String psychologia2 = "";
        String psychologia3 = "";
        int dlugosciLinijki = 43;

        if (psychologiaKarta != null) {
            if (psychologiaKarta.length() > 3 * dlugosciLinijki) {
                psychologiaKarta = psychologiaKarta.substring(0, 3 * dlugosciLinijki);
            }
            if (psychologiaKarta.length() > 0) {
                psychologia1 = psychologiaKarta.substring(0, Math.min(dlugosciLinijki, psychologiaKarta.length()));
            }
            if (psychologiaKarta.length() > dlugosciLinijki) {
                psychologia2 = psychologiaKarta.substring(dlugosciLinijki, Math.min(2 * dlugosciLinijki, psychologiaKarta.length()));
            }
            if (psychologiaKarta.length() > 2 * dlugosciLinijki) {
                psychologia3 = psychologiaKarta.substring(2 * dlugosciLinijki, Math.min(3 * dlugosciLinijki, psychologiaKarta.length()));
            }


            dane.put("PSYCHOLOGIA 1", psychologia1);
            if (!psychologia2.isEmpty())
                dane.put("PSYCHOLOGIA 2", psychologia2);

            if (!psychologia3.isEmpty())
                dane.put("PSYCHOLOGIA 3", psychologia3);

        }

        dane.put("Zepsucie i mutacje", karta.getZepsucieIMutacje());

        //ZAMOŻNOŚĆ
        dane.put("P", String.valueOf(karta.getPensy()));
        dane.put("___S", String.valueOf(karta.getSreblo()));
        dane.put("ZK", String.valueOf(karta.getZlotaKorona()));

        //ŻYWOTNOŚĆ
        int S = karta.getCechyList().get(2).getWartPo() + karta.getCechyList().get(2).getRozw();
        int Wt = karta.getCechyList().get(3).getWartPo() + karta.getCechyList().get(3).getRozw();
        int SW = karta.getCechyList().get(8).getWartPo() + karta.getCechyList().get(8).getRozw();

        int BS = getPierwszaCyfra(S);
        int BWtx2 = getPierwszaCyfra(Wt) * 2;
        int BSW = getPierwszaCyfra(SW);

        dane.put("BS", String.valueOf(BS));
        dane.put("BWtx2", String.valueOf(BWtx2));
        dane.put("BSW", String.valueOf(BSW));

        dane.put("Twardziel", "0");

        dane.put("fill_123", String.valueOf(BS + BWtx2 + BSW)); //suma żywotności

        dane.put("Treść", String.valueOf(karta.getZywotnoscAktualna()));

        //BROŃ
        List<Bron> bronList = karta.getBronList();
        if (bronList != null) {
            for (int i = 0; i < bronList.size(); i++) {
                String cechyBroni = bronList.get(i).getListaCech().stream()
                        .map(CechaBroni::getNazwa)
                        .collect(Collectors.joining(", "));

                switch (i + 1) {
                    case 1:
                        dane.put("NazwaRow1_4", bronList.get(i).getNazwa());
                        dane.put("KategoriaRow1", bronList.get(i).getTypBroni().getNazwa());
                        dane.put("ObcRow1_3", "0");
                        dane.put("fill_70", bronList.get(i).getZasieg());
                        dane.put("fill_71", bronList.get(i).getObrazenia());
                        dane.put("CechyRow1_2", cechyBroni);
                        break;
                    case 2:
                        dane.put("NazwaRow2_2", bronList.get(i).getNazwa());
                        dane.put("KategoriaRow2", bronList.get(i).getTypBroni().getNazwa());
                        dane.put("ObcRow2_2", "0");
                        dane.put("fill_74", bronList.get(i).getZasieg());
                        dane.put("fill_75", bronList.get(i).getObrazenia());
                        dane.put("CechyRow2_2", cechyBroni);
                        break;
                    case 3:
                        dane.put("NazwaRow3_2", bronList.get(i).getNazwa());
                        dane.put("KategoriaRow3", bronList.get(i).getTypBroni().getNazwa());
                        dane.put("ObcRow3_2", "0");
                        dane.put("fill_78", bronList.get(i).getZasieg());
                        dane.put("fill_79", bronList.get(i).getObrazenia());
                        dane.put("CechyRow3_2", cechyBroni);
                        break;
                    case 4:
                        dane.put("NazwaRow4_2", bronList.get(i).getNazwa());
                        dane.put("KategoriaRow4", bronList.get(i).getTypBroni().getNazwa());
                        dane.put("ObcRow4_2", "0");
                        dane.put("fill_82", bronList.get(i).getZasieg());
                        dane.put("fill_83", bronList.get(i).getObrazenia());
                        dane.put("CechyRow4_2", cechyBroni);
                        break;
                    case 5:
                        dane.put("NazwaRow5_2", bronList.get(i).getNazwa());
                        dane.put("KategoriaRow5", bronList.get(i).getTypBroni().getNazwa());
                        dane.put("ObcRow5_2", "0");
                        dane.put("fill_86", bronList.get(i).getZasieg());
                        dane.put("fill_87", bronList.get(i).getObrazenia());
                        dane.put("CechyRow5_2", cechyBroni);
                        break;
                    case 6:

                        dane.put("NazwaRow6", bronList.get(i).getNazwa());
                        dane.put("KategoriaRow6", bronList.get(i).getTypBroni().getNazwa());
                        dane.put("ObcRow6", "0");
                        dane.put("fill_90", bronList.get(i).getZasieg());
                        dane.put("fill_91", bronList.get(i).getObrazenia());
                        dane.put("CechyRow6", cechyBroni);
                        break;
                    case 7:
                        dane.put("NazwaRow7", bronList.get(i).getNazwa());
                        dane.put("KategoriaRow7", bronList.get(i).getTypBroni().getNazwa());
                        ;
                        dane.put("ObcRow7", "0");
                        dane.put("fill_15", bronList.get(i).getZasieg());
                        dane.put("fill_16", bronList.get(i).getObrazenia());
                        dane.put("CechyRow7", cechyBroni);
                        break;
                }
            }


        }

        List<Zaklecia> zakleciaList = karta.getZakleciaList();
        if (zakleciaList != null) {
            for (int i = 0; i < zakleciaList.size(); i++) {
                switch (i + 1) {
                    case 1:
                        dane.put("NazwaRow1_5", zakleciaList.get(i).getNazwa());
                        dane.put("PCRow1", String.valueOf(zakleciaList.get(i).getPoziomZaklecie()));
                        dane.put("fill_93", zakleciaList.get(i).getZacieg());
                        dane.put("CelRow1", zakleciaList.get(i).getCel());
                        dane.put("Czas trwaniaRow1", zakleciaList.get(i).getCzas());
                        dane.put("EfektRow1", zakleciaList.get(i).getOpis());
                        break;
                    case 2:
                        dane.put("NazwaRow2_3", zakleciaList.get(i).getNazwa());
                        dane.put("PCRow2",  String.valueOf(zakleciaList.get(i).getPoziomZaklecie()));
                        dane.put("fill_97", zakleciaList.get(i).getZacieg());
                        dane.put("CelRow2",  zakleciaList.get(i).getCel());
                        dane.put("Czas trwaniaRow2",zakleciaList.get(i).getCzas());
                        dane.put("EfektRow2",  zakleciaList.get(i).getOpis());
                        break;
                    case 3:
                        dane.put("NazwaRow3_3", zakleciaList.get(i).getNazwa());
                        dane.put("PCRow3",  String.valueOf(zakleciaList.get(i).getPoziomZaklecie()));
                        dane.put("fill_101", zakleciaList.get(i).getZacieg());
                        dane.put("CelRow3",zakleciaList.get(i).getCel());
                        dane.put("Czas trwaniaRow3", zakleciaList.get(i).getCzas());
                        dane.put("EfektRow3",  zakleciaList.get(i).getOpis());
                        break;
                    case 4:
                        dane.put("NazwaRow4_3",zakleciaList.get(i).getNazwa());
                        dane.put("PCRow4",  String.valueOf(zakleciaList.get(i).getPoziomZaklecie()));
                        dane.put("fill_105", zakleciaList.get(i).getZacieg());
                        dane.put("CelRow4", zakleciaList.get(i).getCel());
                        dane.put("Czas trwaniaRow4", zakleciaList.get(i).getCzas());
                        dane.put("EfektRow4",  zakleciaList.get(i).getOpis());
                        break;
                    case 5:
                        dane.put("NazwaRow5_3", zakleciaList.get(i).getNazwa());
                        dane.put("PCRow5",  String.valueOf(zakleciaList.get(i).getPoziomZaklecie()));
                        dane.put("fill_109", zakleciaList.get(i).getZacieg());
                        dane.put("CelRow5", zakleciaList.get(i).getCel());
                        dane.put("Czas trwaniaRow5", zakleciaList.get(i).getCzas());
                        dane.put("EfektRow5",  zakleciaList.get(i).getOpis());
                        break;
                    case 6:
                        dane.put("NazwaRow6_2", zakleciaList.get(i).getNazwa());
                        dane.put("PCRow6",  String.valueOf(zakleciaList.get(i).getPoziomZaklecie()));
                        dane.put("fill_113", zakleciaList.get(i).getZacieg());
                        dane.put("CelRow6", zakleciaList.get(i).getCel());
                        dane.put("Czas trwaniaRow6", zakleciaList.get(i).getCzas());
                        dane.put("EfektRow6",  zakleciaList.get(i).getOpis());
                        break;
                    case 7:
                        dane.put("NazwaRow7_2", zakleciaList.get(i).getNazwa());
                        dane.put("PCRow7",  String.valueOf(zakleciaList.get(i).getPoziomZaklecie()));
                        dane.put("fill_117",zakleciaList.get(i).getZacieg());
                        dane.put("CelRow7", zakleciaList.get(i).getCel());;
                        dane.put("Czas trwaniaRow7", zakleciaList.get(i).getCzas());
                        dane.put("EfektRow7",  zakleciaList.get(i).getOpis());
                        break;
                    case 8:
                        dane.put("NazwaRow8", zakleciaList.get(i).getNazwa());
                        dane.put("PCRow8",  String.valueOf(zakleciaList.get(i).getPoziomZaklecie()));
                        dane.put("fill_18", zakleciaList.get(i).getZacieg());
                        dane.put("CelRow8", zakleciaList.get(i).getCel());
                        dane.put("Czas trwaniaRow8", zakleciaList.get(i).getCzas());
                        dane.put("EfektRow8",  zakleciaList.get(i).getOpis());
                        break;
                }
            }

        }
        fillPdfFromAssets(context, inputPdf, uri, dane);
    }

    public static int getPierwszaCyfra(int liczba) {
        while (liczba >= 10) {
            liczba /= 10;
        }
        return liczba;
    }

}
