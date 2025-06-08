package com.example.escape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;

public class StartActivity extends AppCompatActivity {

    private TextView storyTextView;
    private ImageButton nextButton;
    private MediaPlayer bgmPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setStickyImmersiveMode();

        storyTextView = findViewById(R.id.storyTextView);
        nextButton = findViewById(R.id.nextButton);

        // MediaPlayer 초기화 및 BGM 설정
        bgmPlayer = MediaPlayer.create(this, R.raw.start_dub);
        bgmPlayer.setLooping(false); // 반복 재생 설정
        bgmPlayer.start();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 본격적인 게임 액티비티로 전환
                Intent intent = new Intent(StartActivity.this, Chapter1Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish(); // 현재 액티비티 종료
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

    @Override
    protected void onPause() {
        super.onPause();
        if (bgmPlayer != null && bgmPlayer.isPlaying()) {
            bgmPlayer.pause(); // 액티비티가 일시 중지될 때 BGM을 일시 중지
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bgmPlayer != null) {
            bgmPlayer.start(); // 액티비티가 다시 시작될 때 BGM을 재생
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgmPlayer != null) {
            bgmPlayer.release(); // MediaPlayer 리소스 해제
            bgmPlayer = null;
        }
    }
}