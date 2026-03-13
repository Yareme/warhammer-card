package pl.edu.us.warhammer_card.ui.mistrzgry.rozdzialy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.List;

import pl.edu.us.warhammer_card.PDFViewerActivity;
import pl.edu.us.warhammer_card.R;

public class RozdzialFragment extends Fragment {


    private ActivityResultLauncher<Intent> openPdfLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rozdzial, container, false);

        openPdfLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri pdfUri = result.getData().getData();
                        if (pdfUri != null) {
                            final int takeFlags = result.getData().getFlags()
                                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            requireContext().getContentResolver().takePersistableUriPermission(pdfUri, takeFlags);

                            savePdfUri(pdfUri);

                            openPdfWithUri(pdfUri);
                        }
                    }
                }
        );

        SharedPreferences prefs = requireContext().getSharedPreferences("pdf_prefs", Context.MODE_PRIVATE);
        String savedUriString = prefs.getString("pdf_uri", null);
        if (savedUriString != null) {
            Uri savedUri = Uri.parse(savedUriString);

            List<UriPermission> permissions = requireContext().getContentResolver().getPersistedUriPermissions();
            boolean hasPermission = false;
            for (UriPermission perm : permissions) {
                if (perm.getUri().equals(savedUri) && perm.isReadPermission()) {
                    hasPermission = true;
                    break;
                }
            }

            if (hasPermission) {
                openPdfWithUri(savedUri);
            } else {
                prefs.edit().remove("pdf_uri").apply();
            }
        }

        View btnWprowadzenie = view.findViewById(R.id.rozdzialWprowadzenie);
        btnWprowadzenie.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            openPdfLauncher.launch(intent);
        });

        return view;
    }
    private void savePdfUri(Uri uri) {
        SharedPreferences prefs = requireContext().getSharedPreferences("pdf_prefs", Context.MODE_PRIVATE);
        prefs.edit().putString("pdf_uri", uri.toString()).apply();
    }

    private void openPdfWithUri(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(intent, "Otwórz PDF"));
    }

    private void openPDF() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        startActivity(intent);
    }

}
   /* private void openPDF() {
        File pdfFile = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),
                "WFRP_4_ed._FINAL_podlinkowany_bookmarks.pdf"

        );

        if (!pdfFile.exists()) {
            Toast.makeText(getContext(),
                    "Plik PDF nie istnieje",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Uri uri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().getPackageName() + ".provider",
                pdfFile
        );

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);
    }*/

/*    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_rozdzial, container, false);

        View btnWprowadzenie = view.findViewById(R.id.rozdzialWprowadzenie);
        View btnPostac = view.findViewById(R.id.rozdzialPostac);
        View btnKlasy = view.findViewById(R.id.rozdzialKlawssyIProfesje);
        View btnUmie = view.findViewById(R.id.rozdzialUmiejetnosciItalenty);

        View btnZasady = view.findViewById(R.id.rozdzialZasady);
        View btnPomiePrzyg = view.findViewById(R.id.rozdzialPomieddzyGry);
        View btnReligWiar = view.findViewById(R.id.rozdzialReligiaIWiara);
        View btnMagia = view.findViewById(R.id.rozdzialMagia);

        View btnMistrz = view.findViewById(R.id.rozdzialMistrzGry);
        View btnReikl = view.findViewById(R.id.rozdzialWspanialyReikland);
        View btnKonsum = view.findViewById(R.id.rozdzialPrzewodnikKonsumenta);
        View btnBesti = view.findViewById(R.id.rozdzialBestiariusz);

        btnWprowadzenie.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1OgfMPXINKQtLUhtUzBqOhcENsQw6A5Ms/view?usp=sharing")
        );

        btnPostac.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1qDzpAzVya29q7PgQ5y0e2XGYKXG9igd-/view?usp=sharing")
        );

        btnKlasy.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1dncpmYfR4U0VHVlfqPQvRmylBSrZZAS_/view?usp=sharing")
        );

        btnUmie.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1N48UpGTECfvTmh4UEs-myCURHZ_E55HW/view?usp=sharing")
        );
        btnZasady.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1ZI6zm1rbtYlnhIBSFkJbjlfnPTFIakr3/view?usp=sharing")
        );
        btnPomiePrzyg.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1WBSpXLdSIqSv7rkh0Cg4PQ6p0sAiHKNk/view?usp=sharing")
        );
        btnReligWiar.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1qH8n770sgSvXZAtV8zeWNR0nZ3mLOaZK/view?usp=sharing")
        );
        btnMagia.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1Menx6_NAiVLROZNOe8en7A9OMz9Xt00x/view?usp=sharing")
        );
        btnMistrz.setOnClickListener(v ->
                        openPDF("https://drive.google.com/file/d/1nBRyv2Zl2CMgPTAF2HBpZZheV6zbUolJ/view?usp=sharing")
                );
        btnReikl.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1O-SYwsefiHKXTSaKgDvClxoNtaiCuKYB/view?usp=sharing")
        );
        btnKonsum.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1w3UhH-v413QnHGtMR0HMBMjGabUMj2tX/view?usp=sharing")
        );
        btnBesti.setOnClickListener(v ->
                openPDF("https://drive.google.com/file/d/1ogJxBDkJkuWaKKRAjs_yY-d8AXluYElM/view?usp=sharing")
        );
        return view;
    }

    private void openPDF(String pdfUrl) {
        Intent intent = new Intent(getContext(), PDFViewerActivity.class);
        intent.putExtra("pdfUrl", pdfUrl);
        startActivity(intent);
    }*/