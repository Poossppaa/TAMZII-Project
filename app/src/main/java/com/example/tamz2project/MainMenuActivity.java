package com.example.tamz2project;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainMenuActivity extends AppCompatActivity {

    Context context;
    Button newGameButton;
    Button levelSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        context = this.getApplicationContext();

        final MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.menumusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        newGameButton = findViewById(R.id.newGameButton);
        levelSelectButton = findViewById(R.id.levelSelectButton);

        newGameButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("level","level1");

                    startActivity(intent);
                    mediaPlayer.stop();
                    return true;
                }
                return false;
            }
        });
    }
}
