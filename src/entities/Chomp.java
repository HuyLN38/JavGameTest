package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Direction.*;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Chomp extends Enemy {

    public Chomp(float x, float y) {
        super(x, y, CHOMP_WIDTH, CHOMP_HEIGHT, CHOMP);
        WalkingSpeed = 2.5f;
        initHitbox(x, y, (int) (CHOMP_WIDTH * 0.8), (int) (CHOMP_HEIGHT * 0.5));
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) (CHOMP_WIDTH * 0.8), (int) (CHOMP_HEIGHT * 0.5));
    }

    private void updateMove(int[][] LevelData, Player player) {
        if (firstTime) {
            firstUpdate(LevelData);
        }
        if (inAir) {
            updateInAir(LevelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(CRAWLING);
                    break;
                case CRAWLING:
                    if(canSeePlayer(LevelData, player))
                        turnTowardsPlayer(player);
                    if(isPlayerClose(player))
                        newState(ATTACK);
                    Move(LevelData);
                    break;
            }
        }
    }

    public void update(int[][] LevelData, Player player) {
        updateMove(LevelData, player);
        updateAnimationTick();
    }

}
