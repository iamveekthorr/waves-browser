package com.example.waves_browser;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
        binding.progressBar.setVisibility(View.GONE);

        setTheme(R.style.Theme_Wavesbrowser);
        setContentView(view);
        setSupportActionBar(toolbar);

        // create default settings for app.
        this.doWebSettings();

        // load a page
        this.loadWebPage(binding.webView);

        // refresh page
        this.handleRefresh(binding.refreshLayout);
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
        webView.setWebViewClient(new CustomWebViewClient(binding.progressBar));
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
        final String GOOGLE = getString(R.string.google_url);
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String url = this.getTextFromEditable(search);
                // Check if string carries a domain extension
                if (this.checkDomainExtension(url)) {
                    search.setText(url);
                    this.hideInputMethod();
                    webView.loadUrl(url);
                    return true;
                }
                search.setText(String.format(GOOGLE, url));
                this.hideInputMethod();
                webView.loadUrl(String.format(GOOGLE, url));
                return true;
            }
            return false;
        });
    }

    // Checks if the string has a domain.
    private boolean checkDomainExtension(String url) {
        return url.matches("\\b([a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z]{2,}\\b");
    }

    private void hideInputMethod(){
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
                .getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void handleRefresh(SwipeRefreshLayout refresh){
        refresh.setOnRefreshListener(() -> {
            this.loadWebPage(binding.webView);
            refresh.setRefreshing(false);
        });
    }
}