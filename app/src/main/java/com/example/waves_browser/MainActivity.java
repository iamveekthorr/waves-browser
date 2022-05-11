package com.example.waves_browser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.waves_browser.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @SuppressWarnings("FieldCanBeLocal")
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // using using View Binding.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Toolbar toolbar = binding.toolBar;

        setTheme(R.style.Theme_Wavesbrowser);
        setContentView(view);
        setSupportActionBar(toolbar);

        // create default settings for app.
        this.doWebSettings();

        // load a page
        this.loadWebPage(binding.webView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private WebView getWebViewInstance() {
        return binding.webView;
    }

    private void doWebSettings() {
        WebView webView = this.getWebViewInstance();
        webView.setWebViewClient(new CustomWebViewClient());
        // This allows pages to execute Javascript.
        // this option is dangerous due to XXS attack
        webView.getSettings().setJavaScriptEnabled(true);
        // This Option makes sure the client never executes local javascript files.
        // Helping the above option to work properly.
        webView.getSettings().setAllowFileAccess(false);

        // allow users zoom webpages.
        webView.getSettings().supportZoom();
    }


    private String getTextFromEditable(EditText editText) {
        return editText.getText().toString();
    }


    public void loadWebPage(WebView webView) {
        EditText search = binding.editText;
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String url = this.getTextFromEditable(search);
                // Check if string carries a domain extension
                if (this.checkDomainExtension(url)) {
                    webView.loadUrl(url);
                    return true;
                }
                webView.loadUrl("https://www.google.com/search?q=" + url);
                return true;
            }
            return false;
        });
    }

    // Checks if the string has a domain.
    private boolean checkDomainExtension(String url) {
        return url.matches("\\b([a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z]{2,}\\b");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}