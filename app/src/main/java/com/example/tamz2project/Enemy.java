package com.example.tamz2project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Enemy extends GameObject {
    private Sprite sprite;
    private Rect collisionBox;
    private int xSpeed = 5;
    private GameView gameView;

    public Enemy(GameView gameView, Resources resources, int x, int y, int resID) {
        super(gameView, 5, resources);
        this.gameView = gameView;
        this.sprite =  this.createSprite(resID,x,y);
        collisionBox = sprite.getCollisionBoxFromSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.onDraw(canvas);
        this.update();
    }

    @Override
    public void update() {
        int x = collisionBox.left;
        int y = collisionBox.top;
        if (x > gameView.getWidth() - sprite.getWidth() - xSpeed) {
            xSpeed = -5;
        }
        if (x + xSpeed < 0) {
            xSpeed = 5;
        }
        x = x + xSpeed;
        sprite.update(x,y);
    }
}
