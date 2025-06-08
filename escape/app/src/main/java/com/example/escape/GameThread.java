package com.example.escape;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private RhythmGameView gameView;
    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, RhythmGameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        gameView.update();
                        gameView.onDraw(canvas);
                    }
                }
            } catch (Exception e) {
                Log.e("GameThread", "Exception in GameThread", e);
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        Log.e("GameThread", "Exception in unlocking canvas", e);
                    }
                }
            }
        }
    }
}