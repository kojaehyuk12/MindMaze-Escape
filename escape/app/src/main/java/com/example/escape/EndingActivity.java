package com.example.escape;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EndingActivity extends AppCompatActivity {
    private TextView characterName;
    private TextView dialogueText;
    private Button nextButton;
    private String[] dialogues;
    private String[] names;
    private int dialogueIndex;
    private MediaPlayer bgmPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        setStickyImmersiveMode();

        bgmPlayer = MediaPlayer.create(this, R.raw.ending_bgm);
        bgmPlayer.setLooping(true); // 반복 재생 설정
        bgmPlayer.start();

        characterName = findViewById(R.id.characterName);
        dialogueText = findViewById(R.id.dialogueText);
        nextButton = findViewById(R.id.nextButton);

        names = new String[]{"조력자", "조력자", "플레이어", "조력자", "조력자", "플레이어", "조력자"};
        dialogues = new String[]{
                "이제야 우리가 만나는군.",
                "내가 그동안 너에게 힌트를 준 이유가 바로 이 순간을 위해서였어.",
                "당신이... 여기까지 날 도와준 분이었군요. 그런데 이 유물은 도대체 어떻게 사용하는 거죠?",
                "이 유물은 시간을 제어할 수 있는 열쇠야.",
                "하지만 어떻게 사용할지는 너의 선택에 달려 있어. 그 전에 마지막 시험을 통과해야 해.",
                "어떤 시험인가요?",
                "간단해, 너의 능력을 평가하는 거야."
        };
        dialogueIndex = 0;

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogueIndex < dialogues.length - 1) {
                    dialogueIndex++;
                    updateDialogue();
                } else if (dialogueIndex == dialogues.length - 1) {
                    Intent intent = new Intent(EndingActivity.this, EndingActivityQuiz1.class);
                    startActivityForResult(intent, 1);
                } else {
                    nextButton.setEnabled(false); // 대화가 끝나면 버튼 비활성화
                }
            }
        });
    }

    private void updateDialogue() {
        characterName.setText(names[dialogueIndex]);
        dialogueText.setText(dialogues[dialogueIndex]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            dialogueText.setText("잘했네, 그럼 다음 문제를 풀어보게.");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EndingActivity.this, EndingActivityQuiz2.class);
                    startActivityForResult(intent, 2);
                }
            });
            nextButton.setEnabled(true);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            dialogueText.setText("잘했네, 마지막 문제를 풀어보게.");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EndingActivity.this, EndingActivityQuiz3.class);
                    startActivityForResult(intent, 3);
                }
            });
            nextButton.setEnabled(true);
        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            // 새로운 대사들로 업데이트
            names = new String[]{"조력자", "플레이어", "조력자", "조력자", "조력자", "조력자", "조력자", "조력자", "조력자", "플레이어", "플레이어"};
            dialogues = new String[]{
                    "축하한다네, 시험에 통과했네.",
                    "당신 덕분에 여기까지 올 수 있었어요. 정말 감사합니다.",
                    "자네에게 이 시계를 주기 전에 할 말이 있네.",
                    "나 또한 자네처럼 평범한 소년이었지. 그러다 우연히 이 시계를 발견하고 시험을 통과했네.",
                    "하지만 저주에는 벗어나지 못했지. 그래서 이 시계를 가지게 되었지만, 저주 때문에 나는 오랫동안 시간 속에 정착할 수 없었네.",
                    "그래서 가족도, 친구도 곁에 둘 수 없었고, 혼자 영겁의 세월에 갇혀 죽지도 못하고 외로움에 빠져들던 중 자네를 발견했네.",
                    "자네가 시계의 시험을 다 통과해줘서 고맙네. 마지막으로 부탁이 있네.",
                    "이 시계의 저주를 풀어 나를 해방시켜줄 수 있겠나? 해방시켜준다면 이 시계를 자네에게 주겠네.",
                    "아니면 저 뒤에 있는 문으로 들어가 다시 평범한 일상으로 돌아가겠나.",
                    "'나는 생각했다. 시계의 저주를 풀면 시간을 넘나들 수 있는 물건을 가지게 되지만-'",
                    "'저주를 풀지 못하면 저 노인처럼 영겁의 세월에 갇히게 되겠지? 나는 어떻게 할까?'"
            };
            dialogueIndex = 0; // 대화 인덱스 초기화
            updateDialogue();
            nextButton.setEnabled(true);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogueIndex < dialogues.length - 1) {
                        dialogueIndex++;
                        updateDialogue();
                    } else {
                        // 대화가 끝나면 다른 액티비티로 전환
                        Intent intent = new Intent(EndingActivity.this, NextActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }
            });
        }
    }

    private void setStickyImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer.release(); // MediaPlayer 리소스 해제
            bgmPlayer = null;
        }
    }
}