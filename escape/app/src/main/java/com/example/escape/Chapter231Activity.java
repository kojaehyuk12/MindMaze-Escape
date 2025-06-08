package com.example.escape;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class Chapter231Activity extends AppCompatActivity {
    private TextView gameStatusTextView;
    private TextView passwordHintTextView;
    private Button button1, button2, button3, buttonBackToChapter2, ButtonWinToChapter2;
    private int currentNumber = 0;
    private final int MAX_NUMBER = 31;
    private MediaPlayer mediaPlayer;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter2_31);
        setStickyImmersiveMode();

        // MediaPlayer 초기화 및 시작
        mediaPlayer = MediaPlayer.create(this, R.raw.game_31);
        mediaPlayer.setLooping(true); // 반복 재생
        mediaPlayer.start();

        gameStatusTextView = findViewById(R.id.textView2);
        passwordHintTextView = findViewById(R.id.textView3);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttonBackToChapter2 = findViewById(R.id.buttonBackToChapter2);
        ButtonWinToChapter2 = findViewById(R.id.ButtonWinToChapter2);

        random = new Random();

        button1.setOnClickListener(v -> userTurn(1));
        button2.setOnClickListener(v -> userTurn(2));
        button3.setOnClickListener(v -> userTurn(3));

        buttonBackToChapter2.setOnClickListener(v -> {
            Intent intent = new Intent(Chapter231Activity.this, Chapter2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        ButtonWinToChapter2.setOnClickListener(v -> {
            Intent intent = new Intent(Chapter231Activity.this, Chapter2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void userTurn(int number) {
        if (currentNumber < MAX_NUMBER) {
            currentNumber += number;
            gameStatusTextView.setText("You called: " + currentNumber);
            if (currentNumber >= MAX_NUMBER) {
                gameStatusTextView.setText("Game Over: You lose!");
                disableButtons();
                buttonBackToChapter2.setVisibility(View.VISIBLE);  // 사용자가 졌을 때 버튼 보이기
            } else {
                gameStatusTextView.postDelayed(this::computerTurn, 500);
            }
        }
    }

    private void computerTurn() {
        if (currentNumber < MAX_NUMBER) {
            int computerChoice = getOptimalMove();
            currentNumber += computerChoice;
            gameStatusTextView.setText("Computer called: " + currentNumber);
            if (currentNumber >= MAX_NUMBER) {
                gameStatusTextView.setText("Game Over: You win!");
                disableButtons();
                ButtonWinToChapter2.setVisibility(View.VISIBLE);  // 컴퓨터가 졌을 때 버튼 보이기
                passwordHintTextView.setVisibility(View.VISIBLE);  // 사용자가 이겼을 때 힌트 텍스트 보이기
            }
        }
    }

    private int getOptimalMove() {
        int remaining = MAX_NUMBER - currentNumber;
        if (remaining == 2 || remaining == 6 || remaining == 10 || remaining == 14) {
            return 1;
        } else if (remaining == 3 || remaining == 7 || remaining == 11 || remaining == 15) {
            return 2;
        } else if (remaining == 4 || remaining == 8 || remaining == 12 || remaining == 16) {
            return 3;
        } else {
            // 1부터 3까지 랜덤한 수 선택
            return random.nextInt(3) + 1;
        }
    }

    private void disableButtons() {
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
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
}