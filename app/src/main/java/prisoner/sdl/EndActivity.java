package prisoner.sdl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EndActivity extends AppCompatActivity {

    private TextView tvScore;
    private TextView tvOldScore;
    private TextView tvBestScore;
    private Button btnQuitter;
    private Button btnRejouer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        tvScore = findViewById(R.id.tvScore);
        tvOldScore = findViewById(R.id.tvOldScore);
        tvBestScore = findViewById(R.id.tvBestScore);
        btnQuitter = findViewById(R.id.btnQuitter);
        btnRejouer = findViewById(R.id.btnRejouer);


        int score = 0;
        int oldScore = 0;
        int bestScore = 0;
        
        tvScore.setText("Score: " + score);
        tvOldScore.setText("Ancien Score: " + oldScore);
        tvBestScore.setText("Meilleur Score: " + bestScore);

        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        btnRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
