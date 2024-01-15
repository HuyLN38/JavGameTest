package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Direction.*;

import main.Game;

public class Enemy extends Entity{

    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstTime = true, inAir = false;
    protected float FallSpeed;
    protected float gravity = 0.1f * Game.SCALE;
    protected float WalkingSpeed = 2.75f;
    protected int walkDirection = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    protected void updateAnimationTick(){
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState))
                aniIndex = 0;
        }
    }

    protected void updateInAir(int[][] LevelData){
        if(CanMoveHere(hitbox.x, hitbox.y + FallSpeed, hitbox.width, hitbox.height, LevelData)){
            hitbox.y += FallSpeed;
            FallSpeed += gravity;
        } else{
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, FallSpeed);
        }
    }

    protected void firstUpdate(int[][] LevelData){
        if(firstTime){
            if(!IsEntityOnFloor(hitbox, LevelData))
                inAir = true;
            firstTime = false;
            }
    }

    protected void changeWalkDirection() {
       if(walkDirection == LEFT){
           walkDirection = RIGHT;
       } else {
           walkDirection = LEFT;
       }
    }

    public int getAniIndex(){
        return aniIndex;
    }

    public int getEnemyState(){
        return enemyState;
    }

    public boolean isFacingLeft() {
        return !(walkDirection == LEFT);
    }
    
    
}
