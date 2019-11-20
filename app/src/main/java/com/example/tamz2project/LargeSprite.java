package com.example.tamz2project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class LargeSprite extends Sprite {
    private static final int MAX_SPEED = 5;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;

    public LargeSprite(GameView gameView, Bitmap bmp, int initialX, int initialY) {
        super(gameView, bmp, initialX, initialY);

        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.x = initialX;
        this.y = initialY;
    }

    // updates animation frame
    private void update() {
        if (x > gameView.getWidth() - width - xSpeed) {
            xSpeed = -5;
        }
        if (x + xSpeed < 0) {
            xSpeed = 5;
        }
        x = x + xSpeed;
        currentFrame = ++currentFrame;
    }

    //updates game board
    public void onDraw(Canvas canvas) {
        update();

        // one frame, no need for animation update
        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(x, y, x + width, y + height);

        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean isCollision(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

}
