package com.example.librarykiosk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.os.Bundle;

public class WebActivity extends AppCompatActivity {

    private WebView mWebView;
    private ConstraintLayout mBtnBack;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        URL = getIntent().getStringExtra("URL");
        if (URL != null){
            mWebView = (WebView) findViewById(R.id.web);

            mWebView.getSettings().setJavaScriptEnabled(true);

            // 웹뷰 설정을 조정하여 컨텐츠가 WebView의 크기에 맞추어 표시되도록 함
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.getSettings().setUseWideViewPort(true);

            // 줌 컨트롤을 비활성화 (필요한 경우)
            mWebView.getSettings().setBuiltInZoomControls(false);
            mWebView.getSettings().setDisplayZoomControls(false);

            mWebView.loadUrl(URL);
        }
        else{
            Log.e("Web View Error","Not found URL");
        }

        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}