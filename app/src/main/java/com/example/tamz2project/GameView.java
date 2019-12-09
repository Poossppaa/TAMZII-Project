package com.example.tamz2project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private PlayerOrientationData orientationData;
    private Context context;
    private ArrayList<HashMap<String, String>> enemyCoordinates = new ArrayList<>();
    private HashMap<String, String> m_li;
    private List<Projectile> projectiles = new ArrayList<>();
    private String gameLevel = "";
    private SoundPool soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
    private int shootSound = soundPool.load(getContext(),R.raw.shoot,1);
    private int explosionSound = soundPool.load(getContext(),R.raw.explosion,1);

    public GameView(Context context, String level) {
        super(context);
        this.context = context;
        gameLoopThread = new GameLoopThread(this);
        orientationData = new PlayerOrientationData(getContext());
        orientationData.register();
        holder = getHolder();

        this.gameLevel = level;

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.levelmusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

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
                createGameObjects(gameLevel);
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }

    private void createGameObjects(String gameLevel) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAssets("json/"+gameLevel));
            JSONArray m_jArry = obj.getJSONArray("level");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String x_coordinate = jo_inside.getString("x");
                String y_coordinate = jo_inside.getString("y");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("x", x_coordinate);
                m_li.put("y", y_coordinate);

                enemyCoordinates.add(m_li);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        for (HashMap<String,String> item : enemyCoordinates){
            int xOrigin = Integer.parseInt(item.get("x"));
            int yOrigin = Integer.parseInt(item.get("y"));

            switch(yOrigin){
                case 200 : {
                    enemies.add(new Enemy(this, getResources(),xOrigin,yOrigin, R.drawable.invadera));
                    break;
                }
                case 300 : {
                    enemies.add(new Enemy(this, getResources(),xOrigin,yOrigin, R.drawable.invaderb));
                    break;
                }
                case 400 : {
                    enemies.add(new Enemy(this, getResources(),xOrigin,yOrigin, R.drawable.invaderc));
                    break;
                }
            }
        }

        player = new Player(this, getResources(), (this.getWidth() / 2) - 79, this.getHeight() - 84, R.drawable.ship, orientationData);
    }

    public String loadJSONFromAssets(String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        player.draw(canvas);

        List<Projectile> toRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();

        if(projectiles.size() != 0 && enemies.size() != 0){
            for(Enemy enemy : enemies){
                if(enemy != null){
                    for(Projectile projectile : projectiles){
                        if(projectile != null){
                            if(projectile.collideWith(enemy.getCollisionBox())&& projectile.isSourcePlayer()) {
                                enemiesToRemove.add(enemy);
                                toRemove.add(projectile);
                                soundPool.play(explosionSound, 1, 1, 1, 0, 1);
                                new Enemy(this, getResources(),enemy.getX(),enemy.getY(), R.drawable.explosion).draw(canvas);
                            }
                        }
                    }
                }
            }
        }

        for(Enemy enemy : enemies) {
            enemy.draw(canvas);
            int random = (int) (Math.random()*((1000-1)+1))+1;
            if(random < 3){
                soundPool.play(shootSound, 1, 1, 1, 0, 1);
                projectiles.add(new Projectile(this,getResources(),enemy.getXForProjectile(),enemy.getYForProjectile(), R.drawable.bullet, false));
            }
        }

        if(player != null){
            for(Projectile projectile : projectiles){
                if(projectile != null){
                    if(projectile.collideWith(player.getCollisionBox())&& !projectile.isSourcePlayer()) {
                        Log.d("player","game over");
                        toRemove.add(projectile);
                        // lose condition
                    }
                }
            }
        }

        for(Projectile projectile : projectiles) {
            if(projectile.getY() > this.getHeight()){
                toRemove.add(projectile);
            }
            else{
                projectile.draw(canvas);
            }
        }

        projectiles.removeAll(toRemove);
        enemies.removeAll(enemiesToRemove);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:{
                soundPool.play(shootSound, 1, 1, 1, 0, 1);
                projectiles.add(new Projectile(this,getResources(),player.getXForProjectile(),player.getYForProjectile(), R.drawable.bullet, true));
            }
            return true;
        }
        return false;
    }
}