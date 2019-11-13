package com.example.aman.firstapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class openpdf extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openpdf);

        webView = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
        String url = intent.getExtras().getString("URL");
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.loadUrl(url);
//        Toast.makeText(this,url , Toast.LENGTH_SHORT).show();

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.android.chrome");
        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Chrome is probably not installed
            // Try with the default browser
            i.setPackage(null);
            startActivity(i);
        }
        finish();

    }
}
