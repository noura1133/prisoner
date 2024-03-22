package prisoner.sdl;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.List;
import java.util.Random;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean isRunning;
    private int bushX, bushY;
    private boolean bushTouched = false;
    private long startTime;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.isRunning = false;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (isRunning) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    update();
                    draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void update() {
        if (!bushTouched && System.currentTimeMillis() - startTime > 5000) {
            isRunning = false;
        }
    }

    private void startActivity(Intent intent) {

    }

    private void draw(Canvas canvas) {
        if (canvas != null) {
            gameView.draw(canvas);
            gameView.drawBuisson(canvas, bushX, bushY);
        }
    }



    public void startGame() {
        startTime = System.currentTimeMillis();
        isRunning = true;
        bushTouched = false;

        int screenHeight = gameView.getHeight();
        int screenWidth = gameView.getWidth();

        bushX = new Random().nextInt(screenWidth - 50);
        bushY = new Random().nextInt((int) (screenHeight * (2.0 / 3.0))) + (screenHeight / 3);
    }


    public int getBushX() {
        return bushX;
    }
    public int getBushY() {
        return bushY;
    }
    public void setBushY(int y) {
        bushY = y;
    }
    public void setBushX(int x) {
        bushX = x;
    }
}


