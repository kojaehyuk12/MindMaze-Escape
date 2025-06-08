package com.example.escape;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

public class Chapter2Activity extends AppCompatActivity {

    private TextView timerTextView;
    private MediaPlayer bgmPlayer; // static 제거
    private CountDownTimer countDownTimer; // 타이머 객체
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter2);

        timerTextView = findViewById(R.id.timerTextView);
        button4 = findViewById(R.id.button4);
        Button button2 = findViewById(R.id.button2);
        Button button5 = findViewById(R.id.button5);
        Button button3 = findViewById(R.id.button3);
        Button button = findViewById(R.id.button);

        setStickyImmersiveMode();
        startCountDownTimer();

        playBackgroundMusic();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chapter2Activity.this, KeypadActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 액티비티로 이동
                Intent intent = new Intent(Chapter2Activity.this, Chapter2Listen.class);
                startActivity(intent);
            }
        });

        // button2 클릭 이벤트 처리(클래스 파일로 이동)
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ActivityChapter231로 이동 하는 Intent 생성
                Intent intent = new Intent(Chapter2Activity.this, Chapter231Activity.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bgmPlayer != null) {
                    bgmPlayer.stop();
                    bgmPlayer.release();
                    bgmPlayer = null;
                }
                Intent intent = new Intent(Chapter2Activity.this, RhythmGameActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chapter2Activity.this, Chapter2Pentagon.class);
                startActivity(intent);
            }
        });
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

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(600000, 1000) { // 60 seconds countdown timer with 1 second interval
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Timer : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText("Time's up!");
                // Transition to Chapter2GameOver
                Intent intent = new Intent(Chapter2Activity.this, Chapter2GameOver.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Optional: close the current activity
            }
        }.start();
    }

    private void playBackgroundMusic() {
        if (bgmPlayer == null) {
            bgmPlayer = MediaPlayer.create(this, R.raw.chapter2_bgm);
            bgmPlayer.setLooping(true);
        }
        bgmPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bgmPlayer != null && bgmPlayer.isPlaying()) {
            bgmPlayer.pause(); // 앱이 일시 중지될 때 BGM을 일시 정지합니다.
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        playBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer.release();
            bgmPlayer = null;
        }
        if (countDownTimer != null) {
            countDownTimer.cancel(); // 타이머 취소
        }
    }
}