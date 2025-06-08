package com.example.escape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EndingActivityQuiz3 extends AppCompatActivity {

    private EditText answerEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_quiz3); // XML 파일명 확인
        setStickyImmersiveMode();

        answerEditText = findViewById(R.id.Quiz3_1);
        submitButton = findViewById(R.id.Quiz3_2);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correctAnswer = answerEditText.getText().toString();
                if (correctAnswer.equalsIgnoreCase("d")) {
                    // 정답일 경우 결과 설정
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish(); // 현재 액티비티 종료
                } else {
                    Toast.makeText(EndingActivityQuiz3.this, "다시 생각해 보아라", Toast.LENGTH_SHORT).show();
                    answerEditText.setText(""); // EditText 초기화
                }
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
}