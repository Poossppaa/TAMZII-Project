package com.example.tamz2project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class LargeSprite extends Sprite {
    private static final int MAX_SPEED = 5;
    private int x = 0;
    private int y = 0;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;

    public LargeSprite(Bitmap bmp, int initialX, int initialY) {
        super(bmp, initialX, initialY);
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.x = initialX;
        this.y = initialY;
    }

    public int getWidth(){
        return this.width;
    }

    // updates animation frame
    public void update(int x, int y) {
        this.x = x;
        this.y = y;
        currentFrame = ++currentFrame;
    }

    //updates game board
    public void onDraw(Canvas canvas) {
        // one frame, no need for animation update
        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(x, y, x + width, y + height);

        canvas.drawBitmap(bmp, src, dst, null);
    }

    public Rect getCollisionBoxFromSprite(){
        Rect dst = new Rect(x, y, x + width, y + height);
        return dst;
    }
}
