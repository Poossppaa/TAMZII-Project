package com.example.tamz2project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Dimension;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView {

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private PlayerOrientationData orientationData;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        orientationData = new PlayerOrientationData(getContext());
        orientationData.register();

        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);

                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e){}
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createGameObjects(10, 150);
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }
        });
    }

    private void createGameObjects(int xOffset, int yOffset){
        player = new Player(this,getResources(),0,800,R.drawable.ship, orientationData);

        enemies.add(new Enemy(this,getResources(),xOffset, yOffset,R.drawable.invadera));
        enemies.add(new Enemy(this,getResources(),xOffset + 150, yOffset + 0,R.drawable.invadera));
        enemies.add(new Enemy(this,getResources(),xOffset + 300, yOffset + 0,R.drawable.invadera));

        enemies.add(new Enemy(this,getResources(),0, yOffset + 100,R.drawable.invaderb));
        enemies.add(new Enemy(this,getResources(),xOffset + 150, yOffset + 100,R.drawable.invaderb));
        enemies.add(new Enemy(this,getResources(),xOffset + 300, yOffset + 100,R.drawable.invaderb));

        enemies.add(new Enemy(this,getResources(),0, yOffset + 200,R.drawable.invaderc));
        enemies.add(new Enemy(this,getResources(),xOffset + 150, yOffset + 200,R.drawable.invaderc));
        enemies.add(new Enemy(this,getResources(),xOffset + 300, yOffset + 200,R.drawable.invaderc));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        player.draw(canvas);
        for(Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
    }
}