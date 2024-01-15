package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Direction.*;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Chomp  extends Enemy{

    public Chomp(float x, float y) {
        super(x, y, CHOMP_WIDTH, CHOMP_HEIGHT, CHOMP);
        initHitbox(x, y, (int)(CHOMP_WIDTH*0.8), (int)(CHOMP_HEIGHT*0.5));
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int)(CHOMP_WIDTH*0.8), (int)(CHOMP_HEIGHT*0.5));
    }

    private void updateMove(int[][] LevelData){
        if(firstTime){
            firstUpdate(LevelData);
            }
        if (inAir){
            updateInAir(LevelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    enemyState = CRAWLING;
                    break;
                case CRAWLING:
                    float xSpeed = 0;

                    if(walkDirection == LEFT){
                        xSpeed = -WalkingSpeed;
                    } else 
                        xSpeed = WalkingSpeed;
                    if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, LevelData))
                        if(IsFloor(hitbox, xSpeed, LevelData)){
                            hitbox.x += xSpeed;
                            return;
                        }

                    changeWalkDirection();
                    
                    
                    break;
                default:
                    break;
            }
        }
    }

    public void update(int[][] LevelData){
        updateMove(LevelData);
        updateAnimationTick();
    }
    
}
