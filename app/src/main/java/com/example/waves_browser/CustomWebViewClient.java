package com.example.waves_browser;


import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class CustomWebViewClient extends WebViewClient {
    private final ProgressBar PROGRESS_BAR;

    CustomWebViewClient(ProgressBar progressBar){
        this.PROGRESS_BAR = progressBar;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.getProgressBar().setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        this.getProgressBar().setVisibility(View.GONE);
    }

    public ProgressBar getProgressBar() {
        return PROGRESS_BAR;
    }
}
