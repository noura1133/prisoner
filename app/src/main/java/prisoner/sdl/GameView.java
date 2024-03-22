package prisoner.sdl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.widget.Toast;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Bitmap buissonBitmap;
    private boolean buissonVisible;
    private int buissonX, buissonY;
    private int score;


    public GameView(Context context, String score, long timer) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        buissonVisible = false;
        this.score = Integer.parseInt(score);

        buissonBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bush);

        int newWidth = buissonBitmap.getWidth() / 2;
        int newHeight = buissonBitmap.getHeight() / 2;
        buissonBitmap = Bitmap.createScaledBitmap(buissonBitmap, newWidth, newHeight, false);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
        gameThread.startGame();
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

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
                // En cas d'interruption, réessayer de fermer le thread
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            Drawable background = ContextCompat.getDrawable(getContext(), R.drawable.fond_jeu);
            if (background != null) {
                background.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                background.draw(canvas);
            }
            if (buissonVisible) {
                canvas.drawBitmap(buissonBitmap, buissonX, buissonY, null);
            }
        }
    }

    public void drawBuisson(Canvas canvas, int x, int y) {
        canvas.drawBitmap(buissonBitmap, x, y, null);
    }
    public void setBuissonPosition(int x, int y) {
        buissonX = x;
        buissonY = y;
        buissonVisible = true;
    }

    public void hideBuisson() {
        buissonVisible = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        buissonX = gameThread.getBushX();
        buissonY = gameThread.getBushY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (touchX >= buissonX && touchX < buissonX + buissonBitmap.getWidth() &&
                    touchY >= buissonY && touchY < buissonY + buissonBitmap.getHeight()) {
                score ++;
                Intent intent = new Intent(getContext(), GameActivity.class);
                intent.putExtra("score", String.valueOf(score));
                getContext().startActivity(intent);
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}

