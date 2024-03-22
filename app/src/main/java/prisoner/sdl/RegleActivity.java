package prisoner.sdl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regle);

        Button buttonPlay = findViewById(R.id.buttonStart);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crée un Intent pour démarrer RegleActivity
                Intent intent = new Intent(RegleActivity.this, GameActivity.class);
                // Démarre l'activité
                startActivity(intent);
            }
        });


    }
}
