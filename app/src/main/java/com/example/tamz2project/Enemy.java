package com.example.tamz2project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Enemy extends GameObject {
    private Sprite sprite;
    private Rect collisionBox;
    private int xSpeed = 5;
    private GameView gameView;
    private int x;
    private int y;
    private int initialX;
    private int initialY;

    public Enemy(GameView gameView, Resources resources, int x, int y, int resID) {
        super(gameView, 5, resources);
        this.gameView = gameView;
        this.sprite =  this.createSprite(resID,x,y);
        this.x = sprite.getX();
        this.y = sprite.getY();
        this.initialX = x;
        this.initialY = y;
        collisionBox = this.sprite.getCollisionBoxFromSprite();
    }

    public int getX(){ return this.x; }
    public int getY(){ return this.y; }

    @Override
    public void draw(Canvas canvas) {
        this.update();
        sprite.onDraw(canvas);
    }

    @Override
    public void update() {
        int x = sprite.getX();
        int y = sprite.getY();

        if (x > initialX - sprite.getWidth() + 200 ) { // x + gameView.getWidth() - sprite.getWidth()-150 - xSpeed
            xSpeed = -5;
        }
        if (x < initialX - sprite.getWidth() + 100) {
            xSpeed = 5;
        }
        x = x + xSpeed;
        sprite.update(x,y);
    }
}
