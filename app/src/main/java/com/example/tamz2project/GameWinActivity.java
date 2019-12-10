package com.example.tamz2project;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameWinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_win);


        final MediaPlayer mediaPlayer = MediaPlayer.create(this.getApplicationContext(),R.raw.victorysound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future handler = executor.submit(new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                Intent intent = new Intent(GameWinActivity.this.getApplicationContext(), MainMenuActivity.class);
                mediaPlayer.stop();
                startActivity(intent);
                return "";
            }
        });
    }
}
