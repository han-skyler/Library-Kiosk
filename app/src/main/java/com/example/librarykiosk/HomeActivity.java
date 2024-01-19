package com.example.librarykiosk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeActivity extends AppCompatActivity {

    private Handler handler;
    private TextView mTxTemp,mTxDate, mTxTime, mTxWth;
    private String URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%84%9C%EC%84%9D%EB%8F%99+%EB%82%A0%EC%94%A8";
    private String imageUrl;
    private ConstraintLayout mBtnNotice, mBtnNavigation, mBtnSearch, mBtnRecommend;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // id 선언
        mTxDate = findViewById(R.id.tx_date);
        mTxTime = findViewById(R.id.tx_time);
        mTxTemp = findViewById(R.id.tx_temp);
        mTxWth = findViewById(R.id.tx_weather);

        mBtnNotice = findViewById(R.id.btn_notice);
        mBtnNavigation = findViewById(R.id.btn_navigation);
        mBtnRecommend = findViewById(R.id.btn_recommend);
        mBtnSearch = findViewById(R.id.btn_search);

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeActivity.this, Search.class);
                startActivity(intent);
            }
        });

        mBtnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeActivity.this, Navigation.class);
                startActivity(intent);
            }
        });

        mBtnRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeActivity.this, Recommend.class);
                startActivity(intent);
            }
        });

        mBtnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeActivity.this, Notice.class);
                startActivity(intent);
            }
        });

        // 현재 날짜와 요일 가져오기
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 EEEE", Locale.KOREA);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a",Locale.KOREA);

        // TextView에 날짜와 요일 설정
        mTxDate.setText(dateFormat.format(calendar.getTime()));
        mTxTime.setText(timeFormat.format(calendar.getTime()));

        // Handler 초기화
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String strTemp = msg.getData().getString("temperature");
                String strWth = msg.getData().getString("Weather");

                mTxTemp.setText(strTemp);
                mTxWth.setText(strWth);
            }
        };

        // 백그라운드 스레드에서 웹 페이지 파싱
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Jsoup을 사용해 웹페이지 파싱
                    Document doc = Jsoup.connect(URL).get();
                    // 온도 정보 추출
                    Elements temElements = doc.select(".temperature_text");
                    boolean isEmptyTemp = temElements.isEmpty();
                    String tem = "";
                    if (!isEmptyTemp) {
                        tem = temElements.get(0).text().substring(5);
                    }

                    // 날씨 정보 추출
                    String weather = "";
                    Elements weatherElements = doc.select("span.weather.before_slash");
                    boolean isEmptyWeather = weatherElements.isEmpty();
                    if (!isEmptyWeather) {
                        weather = weatherElements.get(0).text();
                        Log.e("weather code", weather);
                    }

                    // 날씨 아이콘 추출
                    Elements elements = doc.select("div.weather_main i.wt_icon");

                    for (Element element : elements) {
                        String classAttr = element.className();
                        Pattern pattern = Pattern.compile("ico_wt(\\d+)");
                        Matcher matcher = pattern.matcher(classAttr);

                        if (matcher.find()) {
                            String weatherCode = matcher.group(1);
                            String imageUrl = "https://ssl.pstatic.net/sstatic/keypage/outside/scui/weather_new_new/img/weather_svg_v2/icon_flat_wt" + weatherCode + ".svg";

                            // SVG 이미지를 로드하고 ImageView에 표시
                            loadSVG(imageUrl);
                            break; // 첫 번째 일치하는 이미지를 찾으면 루프 종료
                        }
                    }

                    // 데이터를 Handler에 전달
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("temperature", tem);
                    bundle.putString("Weather", weather);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadSVG(String url) {
        new Thread(() -> {
            try {
                //SVG파일 비트맵파일로 변환
                SVG svg = SVG.getFromInputStream(new java.net.URL(url).openStream());
                Bitmap bitmap = Bitmap.createBitmap((int) Math.ceil(svg.getDocumentWidth()),
                        (int) Math.ceil(svg.getDocumentHeight()),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                svg.renderToCanvas(canvas);

                // 변환된 파일을 imageView에 출력
                runOnUiThread(() -> {
                    ImageView imageView = findViewById(R.id.img_weather);
                    imageView.setImageBitmap(bitmap);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}