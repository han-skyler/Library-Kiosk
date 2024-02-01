package com.example.librarykiosk.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.librarykiosk.R;

public class Navigation extends AppCompatActivity {

    private TextView mTxArea1, mTxArea2, mTxArea3, mTxDesk1, mTxDesk2, mTxDesk3, mTxEtc1, mTxEtc2, mTxReach;
    private TextView selectedTextView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mTxArea1 = findViewById(R.id.tx_area1);
        mTxArea2 = findViewById(R.id.tx_area2);
        mTxArea3 = findViewById(R.id.tx_area3);

        mTxDesk1 = findViewById(R.id.tx_desk1);
        mTxDesk2 = findViewById(R.id.tx_desk2);
        mTxDesk3 = findViewById(R.id.tx_desk3);

        mTxEtc1 = findViewById(R.id.tx_etc1);
        mTxEtc2 = findViewById(R.id.tx_etc2);

        mTxReach = findViewById(R.id.tx_reach);

        mTxArea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxReach.setText("구역1");
                onTextViewClicked(mTxArea1);
            }
        });

        mTxArea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxReach.setText("구역2");
                onTextViewClicked(mTxArea2);
            }
        });

        mTxArea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxReach.setText("구역3");
                onTextViewClicked(mTxArea3);
            }
        });

        mTxDesk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxReach.setText("책상1");
                onTextViewClicked(mTxDesk1);
            }
        });

        mTxDesk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxReach.setText("책상2");
                onTextViewClicked(mTxDesk2);
            }
        });

        mTxDesk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxReach.setText("책상3");
                onTextViewClicked(mTxDesk3);
            }
        });

        mTxEtc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxReach.setText("화장실");
                onTextViewClicked(mTxEtc1);
            }
        });

        mTxEtc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxReach.setText("기타구역");
                onTextViewClicked(mTxEtc2);
            }
        });
    }

    private void onTextViewClicked(TextView textView) {
        // Reset the outline for the previously selected TextView
        if (selectedTextView != null) {
            selectedTextView.setBackgroundResource(R.drawable.icon_area); // Reset background resource
        }

        // Set the new selected TextView
        selectedTextView = textView;

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.selected_textview_background);
        textView.setBackground(drawable);

        //textView.setTextColor(Color.parseColor("#FFA8CBFF"));
    }
}