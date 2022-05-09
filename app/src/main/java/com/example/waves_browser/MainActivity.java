package com.example.waves_browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

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

        setContentView(view);
        setSupportActionBar(toolbar);

        // create default settings for app.
        this.doWebSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private WebView getWebViewInstance(){
        return binding.webView;
    }

    public void doWebSettings(){
        WebView webView = this.getWebViewInstance();
        webView.setWebViewClient(new WebViewClient());
        // This allows pages to execute Javascript.
        // this option is dangerous due to XXS attack
        webView.getSettings().setJavaScriptEnabled(true);
        // This Option makes sure the client never executes local javascript files.
        // Helping the above option to work properly.
        webView.getSettings().setAllowFileAccess(false);

        // allow users zoom webpages.
        webView.getSettings().supportZoom();
    }

    public void loadWebPage(String url){
        WebView webView = this.getWebViewInstance();
        webView.loadUrl(url);
    }


    
    public String getTextFromEditable(EditText editText) {
        EditText search = binding.editText;
        return search.getText().toString();
    }
}