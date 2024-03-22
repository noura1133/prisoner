package prisoner.sdl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnLaunchEndActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Trouver le bouton par son ID
        btnLaunchEndActivity = findViewById(R.id.btnLaunchEndActivity);

        // Ajouter un écouteur de clic au bouton
        btnLaunchEndActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancer endActivity
                Intent intent = new Intent(MainActivity.this, EndActivity.class);
                startActivity(intent);
            }
        });
    }
}