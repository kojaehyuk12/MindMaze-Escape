package com.example.escape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Note {
    private float x;
    private float y;
    private float width;
    private float height;
    private float speed;
    private boolean isClicked;
    private Bitmap noteImage;

    public Note(Context context, float x, float y, float width, float height, float speed) {
        this.x = x;
        this.y = y;
        this.width = width + 100;  // 노트의 너비를 약간 키움
        this.height = height + 100; // 노트의 높이를 약간 키움
        this.speed = speed;
        this.isClicked = false;
        this.noteImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.note_image); // 아이콘 이미지 로드
        this.noteImage = Bitmap.createScaledBitmap(noteImage, (int) this.width, (int) this.height, true); // 아이콘 크기 조정
    }

    public void update() {
        y += speed;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (!isClicked) {
            canvas.drawBitmap(noteImage, x, y, null);
        }
    }

    public boolean isOutOfScreen(int screenHeight) {
        return y > screenHeight;
    }

    public void click() {
        isClicked = true;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public boolean isInside(float touchX, float touchY) {
        return touchX >= x && touchX <= x + width && touchY >= y && touchY <= y + height;
    }

    public float getY() {
        return y;
    }
}