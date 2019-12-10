package com.example.tamz2project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GameOverScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over_screen);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this.getApplicationContext(),R.raw.gameovermusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future handler = executor.submit(new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                Intent intent = new Intent(GameOverScreenActivity.this.getApplicationContext(), MainMenuActivity.class);
                mediaPlayer.stop();
                startActivity(intent);
                return "";
            }
        });
    }
}
