package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D.Float;

import static utilz.Constants.Direction.*;

import main.Game;

public class Enemy extends Entity {

    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstTime = true, inAir = false;
    protected float FallSpeed;
    protected float gravity = 0.1f * Game.SCALE;
    protected float WalkingSpeed = 2.75f;
    protected int walkDirection = LEFT;
    protected int TileY;
    protected int attackSight = (int)(Game.TILES_SIZE*0.3);
    protected int SightRange = Game.TILES_SIZE * 5;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)){
                aniIndex = 0;
                if(enemyState == ATTACK)
                    enemyState = IDLE;
            }
        }
    }

    protected void updateInAir(int[][] LevelData) {
        if (CanMoveHere(hitbox.x, hitbox.y + FallSpeed, hitbox.width, hitbox.height, LevelData)) {
            hitbox.y += FallSpeed;
            FallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, FallSpeed);
            TileY = (int) hitbox.y / Game.TILES_SIZE;
        }
    }

    protected void firstUpdate(int[][] LevelData) {
        if (firstTime) {
            if (!IsEntityOnFloor(hitbox, LevelData))
                inAir = true;
            firstTime = false;
        }
    }

    protected void Move(int[][] LevelData) {
        float xSpeed = 0;

        if (walkDirection == LEFT) {
            xSpeed = -this.WalkingSpeed;
        } else
            xSpeed = this.WalkingSpeed;
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, LevelData))
            if (IsFloor(hitbox, xSpeed, LevelData)) {
                hitbox.x += xSpeed;
                return;
            }
        changeWalkDirection();
    }

    protected void changeWalkDirection() {
        if (walkDirection == LEFT) {
            walkDirection = RIGHT;
        } else {
            walkDirection = LEFT;
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isFacingLeft() {
        return !(walkDirection == LEFT);
    }

    protected void newState(int newState) {
        if (enemyState != newState) {
            this.enemyState = newState;
            aniTick = 0;
            aniIndex = 0;
        }
    }

    protected boolean canSeePlayer(int[][] LevelData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == TileY)
            if (isPlayerInSight(player))
                if (IsSightClear(LevelData, hitbox, player.hitbox, TileY)) {
                    return true;
                }
        return false;
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x < hitbox.x) {
            walkDirection = LEFT;
        } else
            walkDirection = RIGHT;
    }

    protected boolean isPlayerClose(Player player) {
        int absSight = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return absSight <= attackSight;
    }


    private boolean isPlayerInSight(Player player) {
        int absSight = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return absSight <= SightRange;
    }

}
