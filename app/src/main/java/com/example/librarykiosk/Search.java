package com.example.librarykiosk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class Search extends AppCompatActivity {

    private TextView mBtnSearch;
    private EditText mEtSerch;
    String Keyword;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mBtnSearch = findViewById(R.id.btn_search);
        mEtSerch = findViewById(R.id.et_search);

        // 검색창에 입력이 감지되었을 때, 검색 버튼 활성화
        mEtSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 이 메서드를 사용하지 않음
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 이 메서드를 사용하지 않음
            }
            @Override
            public void afterTextChanged(Editable s) {
                // EditText의 텍스트가 변경될 때 호출됨
                if (s.toString().isEmpty()) {
                    // EditText에 값이 입력되지 않았을 때 버튼 비활성화
                    mBtnSearch.setEnabled(false);
                } else {
                    // EditText에 값이 입력되었을 때 버튼 활성화
                    mBtnSearch.setEnabled(true);
                }
            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 검색창의 문자열을 저장한다
                Keyword = mEtSerch.getText().toString();

                // 검색결과 페이지 URL 지정
                URL = "https://library.chosun.ac.kr/searchTotal/result?st=KWRD&si=TOTAL&q=" + Keyword + "&x=16&y=3";
                Intent intent = new Intent(Search.this, WebActivity.class);

                // 웹페이지로 검색어와 URL 전달
                intent.putExtra("URL",URL);
                startActivity(intent);
            }
        });


    }
}