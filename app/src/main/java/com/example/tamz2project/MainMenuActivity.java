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
import android.widget.ImageView;

public class MainMenuActivity extends Activity {

    Context context;
    Button newGameButton;
    Button levelSelectButton;
    ImageView splashImage;

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
        splashImage = findViewById(R.id.splashImage);

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

        levelSelectButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Intent intent = new Intent(context, LevelSelectActivity.class);

                    startActivity(intent);
                    mediaPlayer.stop();
                    return true;
                }
                return false;
            }
        });
    }
}
