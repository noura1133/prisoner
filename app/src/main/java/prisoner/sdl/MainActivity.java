package prisoner.sdl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import prisoner.sdl.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonRules = findViewById(R.id.buttonRules);

        buttonRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crée un Intent pour démarrer RegleActivity
                Intent intent = new Intent(MainActivity.this, RegleActivity.class);
                // Démarre l'activité
                startActivity(intent);
            }
        });

    }
}