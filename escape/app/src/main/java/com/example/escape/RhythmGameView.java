package com.example.escape;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RhythmGameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private List<Note> notes;
    private Paint paint;
    private int screenWidth;
    private int screenHeight;
    private int touchCount = 0;
    private int missCount = 0; // 터치 실패 횟수
    private boolean gameRunning = true;
    private Bitmap backgroundImage;
    private final Object backgroundImageLock = new Object();
    private Context context;
    private RelativeLayout layout;

    private Button clearButton;
    private TextView hiddenTextView;

    public RhythmGameView(Context context, RelativeLayout layout) {
        super(context);
        init(context, layout);
    }

    public RhythmGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public RhythmGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    private void init(Context context, RelativeLayout layout) {
        this.context = context;
        this.layout = layout;
        getHolder().addCallback(this);
        notes = new ArrayList<>();
        paint = new Paint();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        synchronized (backgroundImageLock) {
            Bitmap originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.notegameimage); // 원본 이미지 로드
            if (originalImage != null) {
                backgroundImage = Bitmap.createScaledBitmap(originalImage, screenWidth, screenHeight, true); // 화면 크기에 맞게 이미지 조정
                Log.d("RhythmGameView", "Background image loaded and scaled");
            } else {
                // 로드 실패 시 로그 출력
                Log.e("RhythmGameView", "Failed to load background image");
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (backgroundImageLock) {
            if (backgroundImage != null) {
                canvas.drawBitmap(backgroundImage, 0, 0, null); // 배경 이미지 그리기
                Log.d("RhythmGameView", "Background image drawn");
            } else {
                Log.e("RhythmGameView", "Background image is null in onDraw");
            }
        }
        synchronized (notes) {
            for (Note note : notes) {
                note.draw(canvas, paint);
            }
        }
    }

    public void update() {
        if (!gameRunning) return;

        synchronized (notes) {
            Iterator<Note> iterator = notes.iterator();
            while (iterator.hasNext()) {
                Note note = iterator.next();
                note.update();
                if (note.isOutOfScreen(screenHeight)) {
                    iterator.remove();
                    missCount++; // 노트가 화면 밖으로 나가면 터치 실패로 간주
                    if (missCount >= 5) {
                        gameRunning = false;
                        if (context instanceof RhythmGameActivity) {
                            ((RhythmGameActivity) context).failGame();
                        }
                        break;
                    }
                } else if (note.isClicked()) {
                    iterator.remove();
                }
            }
        }
    }

    public void addNote() {
        if (!gameRunning || touchCount >= 20) return; // 터치 카운트를 20으로 설정

        Random rand = new Random();
        float x = rand.nextInt(screenWidth - 60); // 랜덤 x 좌표, 노트의 너비만큼 뺌
        synchronized (notes) {
            notes.add(new Note(context, x, 0, 60, 60, 11)); // 노트의 크기를 약간 키움
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameRunning) return super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();
            synchronized (notes) {
                for (Note note : notes) {
                    if (note.isInside(touchX, touchY)) {
                        note.click();
                        touchCount++;
                        if (touchCount >= 20) { // 터치 카운트를 20으로 설정
                            gameRunning = false;
                            showClearButtonAndHiddenText();
                        }
                        return true;
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void showClearButtonAndHiddenText() {
        if (context instanceof RhythmGameActivity) {
            ((RhythmGameActivity) context).runOnUiThread(() -> {
                // Clear Button 설정
                clearButton = new Button(context);
                clearButton.setBackgroundResource(R.drawable.noteclear); // clear 버튼 이미지 설정
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(200, 200); // 버튼 크기 설정
                buttonParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                clearButton.setLayoutParams(buttonParams);

                clearButton.setOnClickListener(v -> {
                    Intent intent = new Intent(context, Chapter2Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                    if (context instanceof RhythmGameActivity) {
                        ((RhythmGameActivity) context).finish();
                    }
                });

                // 히든 텍스트 설정
                hiddenTextView = new TextView(context);
                hiddenTextView.setText("비밀번호: #9##");
                hiddenTextView.setTextColor(0xFFFF6666);
                hiddenTextView.setTextSize(18);
                RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                textParams.addRule(RelativeLayout.BELOW, clearButton.getId());
                textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                textParams.setMargins(0, 200, 0, 0);
                hiddenTextView.setLayoutParams(textParams);

                // 레이아웃에 추가
                layout.addView(clearButton);
                layout.addView(hiddenTextView);

                // clear 버튼과 히든 텍스트를 표시
                clearButton.setVisibility(View.VISIBLE);
                hiddenTextView.setVisibility(View.VISIBLE);
            });
        }
    }

    public boolean isGameRunning() {
        return gameRunning;
    }
}