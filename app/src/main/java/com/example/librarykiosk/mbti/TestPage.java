package com.example.librarykiosk.mbti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.librarykiosk.R;

public class TestPage extends AppCompatActivity {
    private int currentQuestionIndex = 0;
    private MBTIscore mbtiScore;
    int M=0,B=0,T=0,I=0;
    private QuestionList[] questions = new QuestionList[]{
            new QuestionList("1",
                    "M", "인상깊게 책을 읽었을때 나는?",
                    "조용히 나만의 인생 책으로 선정한다.",
                    "친구들에게 공유하고 친구도 읽도록 추천한다."),
            new QuestionList("2",
                    "B", "여행을 가서 마음에 드는 이상형과 대화를 하게된 나는",
                    "풍부한 공감과 리액션을 해준다.",
                    "그 사람에 대한 궁금한 것을 질문한다."),
            new QuestionList("3",
                    "T", "슬픈 책을 읽을때",
                    "계속해서 다음 내용을 읽는다.",
                    "주인공의 감정에 동화되어 눈물을 흘린다."),
            new QuestionList("4",
                    "I", "여행중에 계획했던 일에 차질이 생겼을때",
                    "최대한 빨리 계획대로 돌아가기 위해 노력한다",
                    "계획은 틀일뿐 할 수 있는 다른 일을 한다."),
            new QuestionList("5",
                    "M", "친구들과 술을 마시러 갔을때",
                    "가까운 친구들 몇명과 이야기 하면서 놀고 싶다.",
                    "여러명의 친구들과 시끌벅적하게 놀고 싶다."),
            new QuestionList("6",
                    "B", "친구의 생일 선물을 고를때",
                    "예쁘고 기억에 남을 선물을 고른다.",
                    "실용적이고 필요할것 같은 선물을 고른다."),
            new QuestionList("7",
                    "T", "친구가 갑자기 배탈이 난것 같다고 할때",
                    "뭐 잘못 먹었나? 약 먹어.",
                    "약 먹었어? 조금 쉬어야겠다."),
            new QuestionList("8",
                    "I", "친구들과 사진을 찍으러 가기로 했을때 나는",
                    "나가기 직전에 마음에 드는 옷을 입는다.",
                    "전날밤에 헤어부터 옷까지 세팅해둔다."),
            new QuestionList("9",
                    "M", "갑자기 약속이 취소되었을때",
                    "집에서 쉴 시간이 늘어나서 좋다.",
                    "다른 친구들에게 연락을 돌려 약속을 잡는다."),
            new QuestionList("10",
                    "B", "해외여행을 가기 위해서 비행기를 탈때",
                    "비행기를 탈동안 뭘 하면서 시간을 보낼지 생각한다.",
                    "비행기 사고가 일어나진 않을지 걱정한다."),
            new QuestionList("11",
                    "T", "친구의 고민을 상담해 줄 때 나는?",
                    "최대한 오래 고민을 들어주려고 한다.",
                    "고민에 대한 해결책을 제시한다."),
            new QuestionList("12",
                    "I", "공부를 하기 전 나는",
                    "보이는것 부터 먼저 한다.",
                    "시간을 분 단위로 계획을 새운다.")
    };

    private TextView questionTextView, option1Button, option2Button, indexTextView1, indexTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        mbtiScore = new MBTIscore();
        option1Button = findViewById(R.id.tx_a1);
        option2Button = findViewById(R.id.tx_a2);
        questionTextView = findViewById(R.id.tx_question);
        indexTextView1 = findViewById(R.id.tx_test_num1);
        indexTextView2 = findViewById(R.id.tx_test_num2);

        loadQuestion();

        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(-1); // 첫 번째 응답 선택 시 점수
                loadNextQuestion();
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(1); // 두 번째 응답 선택 시 점수
                loadNextQuestion();
            }
        });
    }

    private void loadQuestion() {
        QuestionList currentQuestion = questions[currentQuestionIndex];
        indexTextView1.setText(currentQuestion.getIndex());
        indexTextView2.setText(currentQuestion.getIndex());
        questionTextView.setText(currentQuestion.getQuestionText());
        option1Button.setText(currentQuestion.getAnswer1());
        option2Button.setText(currentQuestion.getAnswer2());
    }

    private void updateScore(int scoreChange) {
        String category = questions[currentQuestionIndex].getCategory();
        switch (category) {
            case "M":
                M = M + scoreChange;
                break;
            case "B":
                B = B + scoreChange;
                break;
            case "T":
                T = T + scoreChange;
                break;
            case "I":
                I = I + scoreChange;
                break;
        }

    }

    private void loadNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            Log.i("M B T I : ", String.valueOf(M) + String.valueOf(B) + String.valueOf(T) + String.valueOf(I));
            loadQuestion();
        } else {
            // 설문 완료 처리
            Intent intent = new Intent(TestPage.this, Result_mbti.class);
            Log.i("M B T I : ", String.valueOf(M) + String.valueOf(B) + String.valueOf(T) + String.valueOf(I));
            intent.putExtra("M",M);
            intent.putExtra("B",B);
            intent.putExtra("T",T);
            intent.putExtra("I",I);
            startActivity(intent);
        }
    }
}
