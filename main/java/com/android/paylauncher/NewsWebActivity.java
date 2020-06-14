package com.android.paylauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.paylauncher.adapter.NewsListAdapter;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NewsWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startWebViewOrBrowser();
    }

    private void startWebViewOrBrowser(){
        String uri = getIntent().getStringExtra(NewsListAdapter.BOOT_NEWS_URI);
        if(uri == null){
            finish();
            return;
        }

        //https以外は、Chromeで開かれる
        if(uri.startsWith("http:")) {
            Uri tmpUri = Uri.parse(uri);
            Intent intent = new Intent(ACTION_VIEW, tmpUri);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.news_web_activity);
            WebView webView = findViewById(R.id.web_view);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(uri);
        }
    }
}
