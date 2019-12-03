package com.example.tamz2project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy extends GameObject {
    private Sprite sprite;
    private GameView gameView;
    private int x;
    private int y;
    private int initialX;
    private int initialY;
    private int Projectile;

    public Enemy(GameView gameView, Resources resources, int x, int y, int resID) {
        super(gameView, 5, resources);
        this.gameView = gameView;
        this.sprite =  this.createSprite(resID,x,y);
        this.x = sprite.getX();
        this.y = sprite.getY();
        this.initialX = x;
        this.initialY = y;
        this.collisionBox = this.sprite.getCollisionBoxFromSprite();
    }

    public int getX(){ return this.x; }
    public int getY(){ return this.y; }

    @Override
    public void draw(Canvas canvas) {
        this.update();
        sprite.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(this.collisionBox,paint);
    }

    public int getXForProjectile(){
        return this.collisionBox.left + sprite.getWidth()/2;
    }

    public int getYForProjectile(){ return this.collisionBox.bottom + sprite.getHeight()/2 ; }

    @Override
    public void update() {
        int x = sprite.getX();
        int y = sprite.getY();

        if (x > initialX - sprite.getWidth() + 250 ) {
            xSpeed = -5;
        }
        if (x < initialX - sprite.getWidth() + 50) {
            xSpeed = 5;
        }
        x = x + xSpeed;

        this.collisionBox.left = x;
        this.collisionBox.top = y;
        this.collisionBox.right = x + sprite.getWidth();
        this.collisionBox.bottom = y + sprite.getHeight();

        sprite.update(x,y);
    }
}
