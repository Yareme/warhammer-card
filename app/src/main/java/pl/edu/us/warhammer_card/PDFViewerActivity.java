package pl.edu.us.warhammer_card;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PDFViewerActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rozdzialpdf);

        webView = findViewById(R.id.pdfWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String pdfUrl = getIntent().getStringExtra("pdfUrl");

        String googleDocsUrl = pdfUrl;

        webView.loadUrl(googleDocsUrl);
    }
}