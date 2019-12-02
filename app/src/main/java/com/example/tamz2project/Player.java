package com.example.tamz2project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player extends GameObject {
    private LargeSprite sprite;
    private Rect collisionBox;
    private PlayerOrientationData orientationData;
    private long frameTime;
    public static long INIT_TIME;

    public Player(GameView gameView, Resources resources, int x,int y, int resID, PlayerOrientationData orientationData) {
        super(gameView, 5, resources);
        this.sprite = this.createLargeSprite(resID,x,y);
        collisionBox = this.sprite.getCollisionBoxFromSprite();
        this.orientationData = orientationData;
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

        if(frameTime < INIT_TIME)
            frameTime = INIT_TIME;

        int elapsedTime = (int) ((int)System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();

        if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null){
            float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
            float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

            float xSpeed = 2 * roll * gameView.getWidth()/260f;
            x += Math.abs(xSpeed*elapsedTime) > 5 ? xSpeed*elapsedTime : 0;
        }

        if(x < 0)
            x = 0;
        else if(x + sprite.getWidth() > gameView.getWidth())
            x = gameView.getWidth()-sprite.getWidth();

        sprite.update(x,y);
    }
}
