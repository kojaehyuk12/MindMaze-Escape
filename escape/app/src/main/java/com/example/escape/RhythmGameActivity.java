package com.example.escape;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Handler;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RhythmGameActivity extends AppCompatActivity {

    private static final String TAG = "RhythmGameActivity";

    private MediaPlayer gameMusicPlayer;
    private RhythmGameView rhythmGameView;
    private Button nextButton;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStickyImmersiveMode();

        RelativeLayout layout = new RelativeLayout(this);
        try {
            rhythmGameView = new RhythmGameView(this, layout);
            layout.addView(rhythmGameView);
        } catch (Exception e) {
            Log.e(TAG, "Error initializing RhythmGameView", e);
            return;
        }

        nextButton = new Button(this);
        nextButton.setText("Next");
        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(RhythmGameActivity.this, Chapter2Activity.class);
            startActivity(intent);
            finish();
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(nextButton, params);

        setContentView(layout);

        // 리듬 게임 시작 로직
        startRhythmGame();
    }

    private void startRhythmGame() {
        try {
            // MediaPlayer를 사용하여 게임 음악 재생
            gameMusicPlayer = MediaPlayer.create(this, R.raw.game_music);
            if (gameMusicPlayer != null) {
                gameMusicPlayer.setLooping(true);
                gameMusicPlayer.start();
            } else {
                Log.e(TAG, "Failed to create MediaPlayer");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error starting game music", e);
        }

        // 일정 시간마다 노트를 추가
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rhythmGameView.isGameRunning()) {
                    rhythmGameView.addNote();
                    handler.postDelayed(this, 300); // 1초마다 노트 추가
                }
            }
        }, 300);
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

    public void showNextButton() {
        runOnUiThread(() -> nextButton.setVisibility(View.VISIBLE));
    }

    public void failGame() {
        runOnUiThread(() -> {
            if (gameMusicPlayer != null && gameMusicPlayer.isPlaying()) {
                gameMusicPlayer.stop();
                gameMusicPlayer.release();
                gameMusicPlayer = null;
            }
            Intent intent = new Intent(RhythmGameActivity.this, Chapter2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            Toast.makeText(RhythmGameActivity.this, "실패", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameMusicPlayer != null && gameMusicPlayer.isPlaying()) {
            gameMusicPlayer.pause(); // 앱이 일시 중지될 때 BGM을 일시 정지합니다.
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameMusicPlayer != null) {
            gameMusicPlayer.start(); // 앱이 다시 시작될 때 BGM을 재생합니다.
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameMusicPlayer != null) {
            gameMusicPlayer.stop();
            gameMusicPlayer.release();
            gameMusicPlayer = null;
        }
        handler.removeCallbacksAndMessages(null); // 모든 콜백 제거
    }
}