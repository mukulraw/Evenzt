package com.evenzt;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import java.util.Objects;

public class Terms extends AppCompatActivity {

    Toolbar toolbar;
    WebView web;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        type = getIntent().getStringExtra("type");

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        web = (WebView)findViewById(R.id.web);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        toolbar.setTitleTextColor(Color.WHITE);

        if (Objects.equals(type, "terms"))
        {
            toolbar.setTitle("Terms of Use");
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl("file:///android_asset/terms.htm");
        }
        else
        {
            toolbar.setTitle("Privacy Policy");
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl("file:///android_asset/privacypolicy.htm");
        }



    }
}
