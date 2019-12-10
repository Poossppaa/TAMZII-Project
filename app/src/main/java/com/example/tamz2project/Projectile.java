package com.example.tamz2project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Projectile extends GameObject {
    private Sprite sprite;
    private int ySpeed = 5;
    private GameView gameView;
    private int x;
    private int y;
    private int initialX;
    private int initialY;
    private boolean isSourcePlayer;

    public Projectile(GameView gameView, Resources resources, int x, int y, int resID, boolean isSourcePlayer) {
        super(gameView, 5, resources);
        this.gameView = gameView;
        this.sprite = this.createSprite(resID, x, y);
        this.x = sprite.getX();
        this.y = sprite.getY();
        this.initialX = x;
        this.initialY = y;
        collisionBox = this.sprite.getCollisionBoxFromSprite();
        this.isSourcePlayer = isSourcePlayer;
    }

    @Override
    public void draw(Canvas canvas) {
        this.update();
        sprite.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(this.collisionBox,paint);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isSourcePlayer() {
        return this.isSourcePlayer;
    }

    @Override
    public void update() {
        int x = sprite.getX();
        int y = sprite.getY();

        if (isSourcePlayer)
        {
            if (y > sprite.getHeight() + 20) { // x + gameView.getWidth() - sprite.getWidth()-150 - xSpeed
                ySpeed = -20;
            }
        }
        else
        {
            if (y < sprite.getHeight() - 20 ) { // x + gameView.getWidth() - sprite.getWidth()-150 - xSpeed
                ySpeed = 40;
            }
        }

        y = y + ySpeed;

        this.collisionBox.left = x;
        this.collisionBox.top = y;
        this.collisionBox.right = x + sprite.getWidth();
        this.collisionBox.bottom = y + sprite.getHeight();

        sprite.update(x, y);
    }
}
