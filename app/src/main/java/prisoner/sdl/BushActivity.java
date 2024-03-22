package prisoner.sdl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BushActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String score = intent.getStringExtra("score");
        long timer = intent.getLongExtra("timer", 0); // Récupérer le timer depuis l'intent

        setContentView(new GameView(this, score, timer)); // Passer le score et le timer à GameView
    }
}