package com.example.tamz2project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {

    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.level = (String) getIntent().getExtras().getCharSequence("level");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GameView(this, this.level, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, GameOverScreenActivity.class);
                startActivity(intent);
            }
        }));
    }
}

