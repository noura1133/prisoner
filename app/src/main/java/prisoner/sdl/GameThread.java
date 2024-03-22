package prisoner.sdl;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

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
        bushX = new Random().nextInt(gameView.getWidth() - 100);  // 100 est la largeur du buisson
        bushY = new Random().nextInt(gameView.getHeight() - 100); // 100 est la hauteur du buisson
    }

    public boolean onTouchEvent(int touchX, int touchY) {
        if (touchX >= bushX && touchX < bushX + 100 && touchY >= bushY && touchY < bushY + 100) {
            // Buisson touché
            bushTouched = true;
            // TODO: Déplacer le personnage et gérer la victoire
            return true;
        }
        return false;
    }
}