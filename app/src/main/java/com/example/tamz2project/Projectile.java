package com.example.tamz2project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Projectile extends GameObject {
    private Sprite sprite;
    private int ySpeed = 5;
    private GameView gameView;
    private int x;
    private int y;
    private int initialX;
    private int initialY;

    public Projectile(GameView gameView, Resources resources, int x,int y, int resID) {
        super(gameView, 5, resources);
        this.gameView = gameView;
        this.sprite =  this.createSprite(resID,x,y);
        this.x = sprite.getX();
        this.y = sprite.getY();
        this.initialX = x;
        this.initialY = y;
        collisionBox = this.sprite.getCollisionBoxFromSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.onDraw(canvas);
        this.update();
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}

    @Override
    public void update() {
        int x = sprite.getX();
        int y = sprite.getY();

        if (y > sprite.getHeight() + 20 ) { // x + gameView.getWidth() - sprite.getWidth()-150 - xSpeed
            ySpeed = -20;
        }

        y = y + ySpeed;
        this.collisionBox.left = x;
        this.collisionBox.top = y;
        this.collisionBox.right = x + sprite.getWidth();
        this.collisionBox.bottom = y + sprite.getHeight();
        sprite.update(x,y);
    }
}
