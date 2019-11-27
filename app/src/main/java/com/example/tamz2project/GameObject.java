package com.example.tamz2project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class GameObject {
    protected GameView gameView;
    protected Rect collisionBox;
    protected int xSpeed = 5;
    protected Resources res;

    public GameObject(GameView gameView, int xSpeed, Resources res) {
        this.res = res;
        this.gameView = gameView;
        this.xSpeed = xSpeed;
    }
    public Rect getCollisionBox(){
        return this.collisionBox;
    }
    public boolean collideWith(GameObject sec){
        return sec.getCollisionBox().intersect(this.collisionBox);
    }
    public abstract void draw(Canvas canvas);
    public abstract void update();

    protected Sprite createSprite(int resources, int initialX, int initialY) {
        BitmapFactory.Options bmo = new BitmapFactory.Options();
        bmo.inScaled = true;
        Bitmap bmp = BitmapFactory.decodeResource(res, resources, bmo);
        return new Sprite(bmp,initialX, initialY);
    }

    protected LargeSprite createLargeSprite(int resources, int initialX, int initialY) {
        BitmapFactory.Options bmo = new BitmapFactory.Options();
        bmo.inScaled = true;
        Bitmap bmp = BitmapFactory.decodeResource(res, resources, bmo);
        return new LargeSprite(bmp,initialX, initialY);
    }

}
