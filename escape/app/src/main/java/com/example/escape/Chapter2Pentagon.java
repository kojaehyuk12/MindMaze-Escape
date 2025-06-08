package com.example.escape;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Chapter2Pentagon extends AppCompatActivity {

    private EditText answerEditText;
    private TextView hiddenTextView;
    private Button backButton;
    private MediaPlayer mediaPlayer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter2_pentagon);

        // MediaPlayer 초기화 및 시작
        mediaPlayer = MediaPlayer.create(this, R.raw.pentagon);
        mediaPlayer.setLooping(true); // 반복 재생
        mediaPlayer.start();

        answerEditText = findViewById(R.id.answerEditText);
        Button submitButton = findViewById(R.id.submitButton);
        hiddenTextView = findViewById(R.id.hiddenTextView);
        backButton = findViewById(R.id.backButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answerEditText.getText().toString().trim();
                if (answer.equals("12")) {
                    Toast.makeText(Chapter2Pentagon.this, "정답이다", Toast.LENGTH_SHORT).show();
                    hiddenTextView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(Chapter2Pentagon.this, "다시 생각해 보아라", Toast.LENGTH_SHORT).show();
                    answerEditText.setText(""); // EditText 초기화
                }
            }
        });

        // Back button 클릭 리스너 설정
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chapter2Activity로 돌아가기
                Intent intent = new Intent(Chapter2Pentagon.this, Chapter2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        setStickyImmersiveMode();
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