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
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;

    private GameTypeEnum gameType;

    private Integer score = 0;

    private long currentTimeMillis = System.currentTimeMillis();
    private Random random = new Random();

    private long MAX_TIME_ALLOWED = 5000; // 5 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameType = getRandomGameType();
        setBackground();

        // Init score
        Intent intent = getIntent();
        String scoreString = intent.getStringExtra("score");
        Integer scoreFromThread = (scoreString != null) ? Integer.parseInt(scoreString) : null;
        score = (scoreFromThread != null) ? scoreFromThread : 0;


        // Init sensor
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
            checkLoose();
            if(gameType == GameTypeEnum.LIGHT && event.values[0] < 10 ){
                gameManager();
            }
        }
        
        
    }


    public void gameManager(){
        gameType = getRandomGameType();
        score ++;
        // TODO
        // Mettre ça autre part ??
        TextView scoreTextView = findViewById(R.id.textViewScore);
        scoreTextView.setText(score == null ? "Score : 0" : "Score : "  + score);
        if(gameType == GameTypeEnum.OFFICER){
            Intent intent = new Intent(GameActivity.this, BushActivity.class);
            intent.putExtra("score", String.valueOf(score));
            intent.putExtra("timer", MAX_TIME_ALLOWED);
            startActivity(intent);
        }
        setBackground();
        this.currentTimeMillis = System.currentTimeMillis();
    }
    public void checkLoose(){
        if(System.currentTimeMillis() - this.currentTimeMillis > MAX_TIME_ALLOWED ){
            Intent intent = new Intent(GameActivity.this, EndActivity.class);
            startActivity(intent);
        }
    }

    private GameTypeEnum getRandomGameType() {
        GameTypeEnum[] allGameTypes = GameTypeEnum.values();

        // Supprimer l'élément actuel de la liste
        ArrayList<GameTypeEnum> availableTypes = new ArrayList<>(Arrays.asList(allGameTypes));
        availableTypes.remove(gameType);

        // Sélectionner un élément aléatoire de la liste modifiée
        int randomIndex = random.nextInt(availableTypes.size());
        return availableTypes.get(randomIndex);
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