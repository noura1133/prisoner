package prisoner.sdl;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

//    public class GameActivity extends Activity

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;

    private GameTypeEnum gameType;

    private long currentTimeMillis = System.currentTimeMillis();
    private Random random = new Random();

    private long MAX_TIME_ALLOWED = 5000; // 5 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//        setContentView(new GameView(this));

        gameType = GameTypeEnum.DOG;
        setBackground();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


    }

    private void setBackground() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier(gameType.getLabel(), "drawable", getPackageName());
        if (resourceId != 0) {
            Drawable restaurantDrawable = resources.getDrawable(resourceId);
            findViewById(R.id.main).setBackground(restaurantDrawable);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if(gameType == GameTypeEnum.DOG && (x > 20 || y > 20 || z > 20)){
                gameManager();
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            checkLose();
            if(gameType == GameTypeEnum.LIGHT && event.values[0] < 10 ){
                gameManager();
            }
        }
        
        
    }


    public void gameManager(){
        gameType = getRandomGameType();
        setBackground();
        this.currentTimeMillis = System.currentTimeMillis();
    }
    public void checkLose(){
        if(System.currentTimeMillis() - this.currentTimeMillis > MAX_TIME_ALLOWED ){
            Intent intent = new Intent(GameActivity.this, EndActivity.class);
            startActivity(intent);
        }
    }

    private GameTypeEnum getRandomGameType() {
        GameTypeEnum[] allGameTypes = { GameTypeEnum.DOG, GameTypeEnum.LIGHT };
        int randomIndex = random.nextInt(allGameTypes.length);
        return allGameTypes[randomIndex];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}