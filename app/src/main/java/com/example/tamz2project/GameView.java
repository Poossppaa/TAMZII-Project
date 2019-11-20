package com.example.tamz2project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView {

    private List<Sprite> sprites = new ArrayList<>();
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private long lastClick;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
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
                createSprites(10, 150);
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }
        });
    }

    private void createSprites(int xOffset, int yOffset){
          sprites.add(createSprite(R.drawable.invadera, xOffset + 0, yOffset + 0));
          sprites.add(createSprite(R.drawable.invadera, xOffset +150, yOffset + 0));
          sprites.add(createSprite(R.drawable.invadera, xOffset +300, yOffset + 0));

          sprites.add(createSprite(R.drawable.invaderb, xOffset +0, yOffset + 100));
          sprites.add(createSprite(R.drawable.invaderb, xOffset +150, yOffset + 100));
          sprites.add(createSprite(R.drawable.invaderb, xOffset +300, yOffset + 100));

          sprites.add(createSprite(R.drawable.invaderc, xOffset +0, yOffset + 200));
          sprites.add(createSprite(R.drawable.invaderc, xOffset +150, yOffset + 200));
          sprites.add(createSprite(R.drawable.invaderc, xOffset +300, yOffset + 200));

          //player
          sprites.add(createLargeSprite(R.drawable.ship,xOffset +0, yOffset + 400));
    }

    private Sprite createSprite(int resources, int initialX, int initialY) {
        BitmapFactory.Options bmo = new BitmapFactory.Options();
        bmo.inScaled = true;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resources, bmo);
        return new Sprite(this,bmp,initialX, initialY);
    }

    private LargeSprite createLargeSprite(int resources, int initialX, int initialY) {
        BitmapFactory.Options bmo = new BitmapFactory.Options();
        bmo.inScaled = true;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resources, bmo);
        return new LargeSprite(this,bmp,initialX, initialY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for(Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 500) {
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if (sprite.isCollision(event.getX(), event.getY())) {
                        sprites.remove(sprite);
                        break;
                    }
                }
            }
        }
        return true;
    }
}