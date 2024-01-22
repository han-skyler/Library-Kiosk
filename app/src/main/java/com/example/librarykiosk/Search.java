package com.example.librarykiosk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Search extends AppCompatActivity {

    private TextView mBtnSearch;
    private EditText mEtSerch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mBtnSearch = findViewById(R.id.btn_search);
        mEtSerch = findViewById(R.id.et_search);


    }
}