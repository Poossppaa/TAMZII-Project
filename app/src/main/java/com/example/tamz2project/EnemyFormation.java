package com.example.tamz2project;

import java.util.List;

public class EnemyFormation {
    private List<Enemy> enemies;
    private int formationStartX;
    private int xSpeed = 5;
    private int formationEndX;
    private GameView gameView;

    public EnemyFormation(GameView gameView, List<Enemy> enemies, int formationStartX, int formationEndX) {
        this.gameView = gameView;
        this.enemies = enemies;
        this.formationStartX = formationStartX;
        this.formationEndX = formationEndX;
    }

    public void update() {
        for(Enemy enemy : enemies){
            enemy.update();
        }
        if (formationEndX > gameView.getWidth() - xSpeed) {
            xSpeed = -5;
        }
        if (formationStartX + xSpeed < 0) {
            xSpeed = 5;
        }
        formationStartX = formationStartX + xSpeed;
        formationEndX = formationEndX + xSpeed;
    }
}
