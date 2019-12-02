package com.example.tamz2project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private static final int BMP_ROWS = 1;
    private static final int BMP_COLUMNS = 2;
    private int x;
    private int y;
    private int width;
    private int height;
    private Bitmap bmp;
    private int currentFrame = 0;

    // constructor enemies
    public Sprite(Bitmap bmp, int initialX, int initialY) {
        this.bmp = bmp;
        this.x = initialX;
        this.y = initialY;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight() { return this.height; }

    public int getX(){
        return this.x;
    }

    public int getY(){ return this.y; }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    //animates sprite
    public void onDraw(Canvas canvas) {
        int srcX = currentFrame * width;
        int srcY = 0;

        int right = srcX + width;
        int bottom = srcY + height;

        Rect src = new Rect(srcX, srcY, right, bottom);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public Rect getCollisionBoxFromSprite(){
        Rect collisionBox = new Rect(x, y,x + width, y + height);
        return collisionBox;
    }
}