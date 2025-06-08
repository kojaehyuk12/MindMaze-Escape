package com.example.escape;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    private ImageButton startButton;
    private ImageButton exitButton;
    private MediaPlayer bgmPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStickyImmersiveMode();

        startButton = findViewById(R.id.startButton);
        exitButton = findViewById(R.id.exitButton);

        // MediaPlayer 객체를 초기화하고 BGM 파일을 설정합니다.
        bgmPlayer = MediaPlayer.create(this, R.raw.title_bgm);
        bgmPlayer.setLooping(true); // BGM이 반복 재생되도록 설정합니다.
        bgmPlayer.start(); // BGM 재생 시작

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
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
    protected void onDestroy() {
        super.onDestroy();
        if (bgmPlayer != null) {
            bgmPlayer.release(); // MediaPlayer 자원을 해제합니다.
            bgmPlayer = null;
        }
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
}