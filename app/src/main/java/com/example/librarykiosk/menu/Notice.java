package com.example.librarykiosk.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.os.Bundle;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.librarykiosk.R;
import com.example.librarykiosk.menu.HomeActivity;
import com.example.librarykiosk.view.WebActivity;

public class Notice extends AppCompatActivity {

    private WebView mWebView;
    private ConstraintLayout mBtnBack;
    private LinearLayout mBtnGoback;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mBtnGoback = findViewById(R.id.btn_goback);

        URL = "https://library.chosun.ac.kr/bbs/list/1";
        if (URL != null){
            mWebView = (WebView) findViewById(R.id.web);

            mWebView.getSettings().setJavaScriptEnabled(true);

            // 웹뷰 설정을 조정하여 컨텐츠가 WebView의 크기에 맞추어 표시되도록 함
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.getSettings().setUseWideViewPort(true);

            // 줌 컨트롤을 비활성화 (필요한 경우)
            mWebView.getSettings().setBuiltInZoomControls(false);
            mWebView.getSettings().setDisplayZoomControls(false);

            mWebView.setWebViewClient(new WebViewClient() {
                @SuppressWarnings("deprecation")
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        view.loadUrl(request.getUrl().toString());
                    }
                    return true;
                }
            });

            mWebView.loadUrl(URL);

        }
        else{
            Log.e("Web View Error","Not found URL");
        }

        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notice.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mBtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }
            }
        });

    }
}