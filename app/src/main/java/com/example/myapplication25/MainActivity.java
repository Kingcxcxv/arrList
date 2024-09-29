package com.example.myapplication25;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView gameImage;
    private Button startStopButton;
    private Button increaseSpeedButton;
    private Button decreaseSpeedButton;
    private TextView scoreTextView;
    private RelativeLayout parentLayout;

    private int score = 0;
    private int speed = 5; // Initial speed
    private boolean isGameRunning = false;
    private Handler handler = new Handler();
    private Random random = new Random();

    private Runnable moveImageRunnable = new Runnable() {
        @Override
        public void run() {
            moveImage();
            handler.postDelayed(this, 1000 / speed);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameImage = findViewById(R.id.imageView2);
        startStopButton = findViewById(R.id.startStop);
        increaseSpeedButton = findViewById(R.id.button3);
        decreaseSpeedButton = findViewById(R.id.button4);
        scoreTextView = findViewById(R.id.textView);
        parentLayout = findViewById(R.id.ahmad);

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameRunning) {
                    stopGame();
                } else {
                    startGame();
                }
            }
        });

        increaseSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseSpeed();
            }
        });

        decreaseSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseSpeed();
            }
        });

        gameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGameRunning) {
                    updateScore();
                }
            }
        });
    }

    private void startGame() {
        isGameRunning = true;
        startStopButton.setText("Stop");
        score = 0;
        scoreTextView.setText("Score: 0");
        handler.post(moveImageRunnable);
    }

    private void stopGame() {
        isGameRunning = false;
        startStopButton.setText("Start");
        handler.removeCallbacks(moveImageRunnable);
        showScoreDialog();
    }

    private void increaseSpeed() {
        speed++;
    }

    private void decreaseSpeed() {
        if (speed > 1) {
            speed--;
        }
    }

    private void moveImage() {
        int imageWidth = gameImage.getWidth();
        int imageHeight = gameImage.getHeight();

        int maxX = parentLayout.getWidth() - imageWidth;
        int maxY = parentLayout.getHeight() - imageHeight;

        int newX = random.nextInt(maxX);
        int newY = random.nextInt(maxY);

        gameImage.setX(newX);
        gameImage.setY(newY);
    }

    private void updateScore() {
        score += speed; // Score based on difficulty (speed)
        scoreTextView.setText("Score: " + score);
    }

    private void showScoreDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Your score: " + score)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
