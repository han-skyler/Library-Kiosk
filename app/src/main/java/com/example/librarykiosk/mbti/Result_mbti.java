package com.example.librarykiosk.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarykiosk.R;
import com.example.librarykiosk.menu.HomeActivity;
import com.example.librarykiosk.menu.Recommend;

public class Result_mbti extends AppCompatActivity {
    private MBTIscore mbtiScore;
    private int M,B,T,I;
    private TextView mbtiHash, bookName, bookText, hashTag;
    private ImageView imgBook;
    private ResultList[] resultList = new ResultList[]{
            new ResultList("ENFJ","#ENFJ를 위한","나의 라임 오렌지나무",
                    "가난과 무관심 속에서도 순수한 영혼을 간직한 제제가 눈물과 웃음을 선사하기에 오래 전에 잃어버렸던 동심 세계의 찬란함과 순수함을 감동적으로 되살려 볼 수 있다.",
                    "#정의로운 사회운동가 #사교적 #의견존중 #과민반응"),
            new ResultList("ENFP","#ENFP를 위한","1일 1페이지, 세상에서 가장 짧은 교양 수업 365",
                    "‘미움받을 용기’를 가졌다면 이제 ‘사랑할 용기’를 가져라! 여전히 우리의 상식을 뛰어넘는, 그러나 더 구체적이고 실천적인 해법",
                    "#재기발랄 활동가 #상상력 #순발력 #직관적"),
            new ResultList("ENTJ","#ENTJ를 위한","다정히도 불어오는 바람",
                    "하루에 시 하나로, 잠깐 일상에서 여유를 찾을 수 있게 돕는다. 시를 잊은 채 살아가는 사람들에게, 잠시 따듯함과 안정감을 담은 선물이 될 것이다.",
                    "#대담하고 리더십이 강한 통솔자 #준비철저 #활동적 #통솔력"),
            new ResultList("ENTP","#ENTP를 위한","파피용",
                    "태양 에너지로 움직이는 거대한 우주 범선 '파피용'을 타고 1천 년간의 우주여행에 나선 14만 4천 명의 마지막 지구인들. 인간에 의해 황폐해진 지구를 떠나 새로운 희망의 별을 찾아 나서는 그들의 모험담을 흥미진진하게 그려내고 있다.",
                    "#뜨거운 논쟁을 즐기는 변론가 #박학다식 #독창성"),
            new ResultList("ESFJ","#ESFJ를 위한","행복한 이기주의자",
                    "미국 유명 심리학자 웨인 다이어 박사가 자신의 임상치료 경험을 바탕으로 제시한 행복해지기 위한 10가지 마음가짐은 불안한 미래에 대한 걱정에서 벗어나 현재를 즐기고자 하는 현대인들에게 큰 공감을 준다.",
                    "#친절하고 사교적인 외교관 #관심 #친절함 #공감능력"),
            new ResultList("ESFP","#ESFP를 위한","포노사피엔스",
                    "제4차 산업혁명의 출발을 신인류의 등장과 특징과 그들이 축이 된 새로운 문명의 실체, 산업군별 시장 변화와 소비행동의 변화, 포노 사피엔스 시대의 성공 전략과 새 시대의 인재상에 대해 진지하게 성찰하고 있다.",
                    "#자유로운 영혼 #호기심 #개방적 #사실중시"),
            new ResultList("INFJ","#INFJ를 위한","언어의 온도",
                    "언어에는 따뜻함과 차가움, 적당한 온기 등 나름의 온도가 있다. 세상살이에 지칠 때 어떤 이는 친구와 이야기를 주고받으며 고민을 털기도 하고, 어떤 이는 책을 읽으며 작가가 건네는 문장으로 위안을 얻는다.",
                    "#선구적인 사색가 #통찰력 #공동체"),
            new ResultList("INFP","#INFP를 위한","어린 왕자",
                    "행성 B612호에서 온 한 어린아이가 바라본 지구별이란 여행지에 대한 단상을 모태로 하고 있지만 실상은 순수하기만 한 아이의 시선으로 보는 어른들의 세계를 은유로 풀어낸 작품이다.",
                    "#열정적인 중재자 #이해심 #개방적 #외유내강"),
            new ResultList("INTJ","#INTJ를 위한","처음 만나는 심리학",
                    "사례를 읽고 생활 속에서 활용할 수 있는 실용적인 면은 물론 심리학의 탄생부터 분야, 성격 그리고 앞으로 발달할 심리학의 미래까지 이론적인 면도 갖추었다.",
                    "#용의주도한 전략가 #호기심 #잠재력 #가능성"),
            new ResultList("INTP","#INTP를 위한","프리다 칼로, 내 영혼의 일기",
                    "그녀의 일기장 속에는 인생 전반부에 걸쳐서 겪은 정신적 및 육체적 고통과 함께 정체성의 근간을 이루는 아즈텍 문명의 상징들, 그리고 연인 디에고 리베라에 대한 사랑이 담겨 있다.",
                    "#논리적인 사색가 #호기심 #잠재력 #가능성"),
            new ResultList("ISFJ","#ISFJ를 위한","소녀는 어떻게 어른이 되는가",
                    "새 시대의 목표와 구시대의 기대들이 상충되는 오늘날, 끝없는 불안과 버거움, 피로와 자책 속에서 청년기 여성을 살아내고 있는 딸들을 위한 성장 심리학",
                    "#용감한 수호자 #헌신적 #인내심 #주의집중"),
            new ResultList("ISFP","#ISFP를 위한","누구나 철학자가 되는 밤",
                    "『누구나 철학자가 되는 밤』은 심야 라디오에서 흘러나오는 나직한 속삭임이 그리운 밤, 그림과 함께 보면 그럴 듯하게 어울리는 철학 우화다.",
                    "#예술적인 탐험가 #온화함 #겸손함 #여유만끽"),
            new ResultList("ISTJ","#ISTJ를 위한","정리의 마법",
                    "모두가 원하지만 어렵기만 한 복잡한 공간과 생각 정리 머릿속과 생활의 공간이 2평 넓어지는 실전 미니멀 라이프 쉽고 즐겁게 내 삶이 달라지는 마법 같은 정리 노하우",
                    "#성실한 관리자 #책임감 #현실적 #보수적"),
            new ResultList("ISTP","#ISTP를 위한","사람을 움직이는 처세술",
                    "『사람을 움직이는 처세술』은 데일 카네기의 How To Win Friends And Infuence People은 오늘 날의 새로운 세기에 맞춰 보완하였다.",
                    "#만능 재주꾼 #과묵함 #분석적 #적응력")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_mbti);

        mbtiHash = findViewById(R.id.tx_mbtihash);
        bookName = findViewById(R.id.tx_bookname);
        bookText = findViewById(R.id.book_text);
        hashTag = findViewById(R.id.hashtag);
        imgBook = findViewById(R.id.book);

        // 이전 페이지에서 MBTIScore를 전달받음
        Intent intent = getIntent();
        //mbtiScore = (MBTIscore) intent.getSerializableExtra("MBTIScore");
        M = intent.getIntExtra("M",M);
        B = intent.getIntExtra("B",B);
        T = intent.getIntExtra("T",T);
        I = intent.getIntExtra("I",I);

        Log.i("M B T I 결과 : ", String.valueOf(M) + String.valueOf(B) + String.valueOf(T) + String.valueOf(I));

        // MBTI 유형을 판별하여 결과 페이지로 이동
        String mbtiResult = getMBTIResult();
        moveToResultPage(mbtiResult);
        TextView mBtnHome = (TextView) findViewById(R.id.btn_home);
        mBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 페이지를 종료하고 MBTI 결과를 이전 페이지로 전달
                Intent intent = new Intent(Result_mbti.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getMBTIResult() {
        // MBTI 결과를 판별하고 String 값 반환
        StringBuilder mbtiResult = new StringBuilder();

        mbtiResult.append(M > 0 ? "I" : "E");
        mbtiResult.append(B > 0 ? "N" : "S");
        mbtiResult.append(T > 0 ? "T" : "F");
        mbtiResult.append(I > 0 ? "J" : "P");

        return mbtiResult.toString();
    }

    private void moveToResultPage(String mbtiResult) {
        // MBTI 결과에 따라 이동할 페이지 결정
        Class<?> resultActivityClass;

        switch (mbtiResult) {
            case "ENFJ":
                loadResult(0);
                imgBook.setImageResource(R.drawable.book_enfj);
                break;

            case "ENFP":
                loadResult(1);
                imgBook.setImageResource(R.drawable.book_enfp);
                break;

            case "ENTJ":
                loadResult(2);
                imgBook.setImageResource(R.drawable.book_entj);
                break;

            case "ENTP":
                loadResult(3);
                imgBook.setImageResource(R.drawable.book_entp);
                break;

            case "ESFJ":
                loadResult(4);
                imgBook.setImageResource(R.drawable.book_esfj);
                break;

            case "ESFP":
                loadResult(5);
                imgBook.setImageResource(R.drawable.book_esfp);
                break;

            case "ESTJ":
                loadResult(6);
                imgBook.setImageResource(R.drawable.book_estj);
                break;

            case "ESTP":
                loadResult(7);
                imgBook.setImageResource(R.drawable.book_estp);
                break;

            case "INFJ":
                loadResult(8);
                imgBook.setImageResource(R.drawable.book_infj);
                break;

            case "INFP":
                loadResult(9);
                imgBook.setImageResource(R.drawable.book_infp);
                break;

            case "INTJ":
                loadResult(10);
                imgBook.setImageResource(R.drawable.book_intj);
                break;

            case "INTP":
                loadResult(11);
                imgBook.setImageResource(R.drawable.book_intp);
                break;

            case "ISFJ":
                loadResult(12);
                imgBook.setImageResource(R.drawable.book_isfj);
                break;

            case "ISFP":
                loadResult(13);
                imgBook.setImageResource(R.drawable.book_isfp);
                break;

            case "ISTJ":
                loadResult(14);
                imgBook.setImageResource(R.drawable.book_istj);
                break;

            case "ISTP":
                loadResult(15);
                imgBook.setImageResource(R.drawable.book_istp);
                break;

            default:
                // 기본적으로 어떤 유형에도 해당하지 않을 때의 처리
                resultActivityClass = Recommend.class;
                break;
        }
    }

    public void loadResult(int resultIndex){
        ResultList currentResult = resultList[resultIndex];
        bookName.setText(currentResult.getBook());
        bookText.setText(currentResult.getInfo());
        mbtiHash.setText(currentResult.getWho());
        hashTag.setText(currentResult.getWord());
    }
}