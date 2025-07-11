package com.example.escape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EndingActivityQuiz2 extends AppCompatActivity {

    private EditText answerEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_quiz2);
        setStickyImmersiveMode();

        answerEditText = findViewById(R.id.Quiz2_1);
        submitButton = findViewById(R.id.Quiz2_2);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answerEditText.getText().toString();
                if (answer.equals("14")) { // 정답 확인 로직
                    // 정답일 경우 결과 설정
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish(); // 현재 액티비티 종료
                } else {
                    Toast.makeText(EndingActivityQuiz2.this, "다시 생각해 보아라", Toast.LENGTH_SHORT).show();
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