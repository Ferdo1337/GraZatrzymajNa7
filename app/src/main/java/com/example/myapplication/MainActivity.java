package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvHits, tvCounter, tvMessage;
    private Button btnStart, btnStop;
    private int currentDigit = 0;
    private int hitCount = 0;
    private final Handler handler = new Handler();

    private final Runnable counterRunnable = new Runnable() {
        @Override
        public void run() {
            currentDigit++;
            if (currentDigit > 9) {
                currentDigit = 0;
            }
            tvCounter.setText(String.valueOf(currentDigit));
            handler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvHits = findViewById(R.id.tvHits);
        tvCounter = findViewById(R.id.tvCounter);
        tvMessage = findViewById(R.id.tvMessage);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDigit = 0;
                tvCounter.setText("0");
                tvMessage.setText("");
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                handler.postDelayed(counterRunnable, 500);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(counterRunnable);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                if (currentDigit == 7) {
                    hitCount++;
                    tvHits.setText("Trafienia: " + hitCount);
                    tvMessage.setText("Trafienie!");
                } else {
                    tvMessage.setText("Pudło! Zatrzymano na " + currentDigit);
                }
            }
        });
    }
}
