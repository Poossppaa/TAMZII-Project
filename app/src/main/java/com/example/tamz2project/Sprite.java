package com.example.tamz2project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private static final int BMP_ROWS = 1;
    private static final int BMP_COLUMNS = 2;
    private int x = 0;
    private int y = 0;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;

    // constructor enemies
    public Sprite(Bitmap bmp, int initialX, int initialY) {
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.x = initialX;
        this.y = initialY;
    }

    public int getWidth(){
        return this.width;
    }

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
        Rect dst = new Rect(x, y, x + width, y + height);
        return dst;
    }
}