package prisoner.sdl;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Looper;
import android.view.SurfaceHolder;

import java.util.List;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean isRunning;
    private int bushX, bushY;
    private boolean bushTouched = false;
    private long startTime;
    private long elapsedTime;
    private int targetX, targetY;
    private long timer;


    public GameThread(SurfaceHolder surfaceHolder, GameView gameView, long timer) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.isRunning = false;
        this.timer = timer;
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
        elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime >= timer) {
            isRunning = false;
            Context context = gameView.getContext();
            Intent gameOverIntent = new Intent(context, EndActivity.class);
            context.startActivity(gameOverIntent);

        }
    }

    private void update() {
        if (bushTouched) {
            isRunning = false;
        } else {
            if (Math.abs(bushX - targetX) <= 5 && Math.abs(bushY - targetY) <= 5) {
                targetX = new Random().nextInt(gameView.getWidth() - 50);
                targetY = new Random().nextInt((int) (gameView.getHeight() / 3.0)) + (2 * gameView.getHeight() / 3);
            } else {
                moveBush();
            }
        }
    }

    private void moveBush() {
        if (bushX < targetX) {
            bushX += 5;
        } else if (bushX > targetX) {
            bushX -= 5;
        }

        if (bushY < targetY) {
            bushY += 5;
        } else if (bushY > targetY) {
            bushY -= 5;
        }

        gameView.setBuissonPosition(bushX, bushY);
    }


    private void startActivity(Intent intent) {

    }

    private void draw(Canvas canvas) {
        if (canvas != null) {
            gameView.draw(canvas);
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

    public void setBushTouched(boolean touched) {
        bushTouched = touched;
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


