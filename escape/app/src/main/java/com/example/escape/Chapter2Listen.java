package com.example.escape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Chapter2Listen extends AppCompatActivity {

    private EditText inputEditText;
    private Button submitButton;
    private TextView hiddenTextView;
    private MediaPlayer bgmPlayer;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter2_listen);

        setStickyImmersiveMode();

        inputEditText = findViewById(R.id.inputEditText);
        submitButton = findViewById(R.id.submitButton);
        hiddenTextView = findViewById(R.id.hiddenTextView);
        backButton = findViewById(R.id.backButton);

        playBackgroundMusic();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = inputEditText.getText().toString();
                if (inputText.equalsIgnoreCase("cyber")) {
                    Toast.makeText(Chapter2Listen.this, "정답이다", Toast.LENGTH_SHORT).show();
                    hiddenTextView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(Chapter2Listen.this, "다시 생각해 보아라", Toast.LENGTH_SHORT).show();
                    inputEditText.setText(""); // EditText 초기화
                }
            }
        });

        // Back button 클릭 리스너 설정
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chapter2Activity로 돌아가기
                Intent intent = new Intent(Chapter2Listen.this, Chapter2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void playBackgroundMusic() {
        bgmPlayer = MediaPlayer.create(this, R.raw.chapter2_listen_bgm);
        bgmPlayer.setLooping(true);
        bgmPlayer.start();
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
    protected void onPause() {
        super.onPause();
        if (bgmPlayer != null && bgmPlayer.isPlaying()) {
            bgmPlayer.pause(); // 앱이 일시 중지될 때 BGM을 일시 정지합니다.
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bgmPlayer != null) {
            bgmPlayer.start(); // 앱이 다시 시작될 때 BGM을 재생합니다.
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer.release();
            bgmPlayer = null;
        }
    }
}