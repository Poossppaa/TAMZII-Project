package com.example.tamz2project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

    public GameView(Context context) {
        super(context);
        this.context = context;
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
                createGameObjects();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }

    private void createGameObjects() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAssets());
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

    public String loadJSONFromAssets() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("level1.json");
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
        for(Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
    }
}