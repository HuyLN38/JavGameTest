package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Direction.*;

import main.Game;

public class Enemy extends Entity {

    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 20;
    protected boolean firstTime = true, inAir = false;
    protected float FallSpeed;
    protected float gravity = 0.1f * Game.SCALE;
    protected float WalkingSpeed = 2.75f;
    protected int walkDirection = LEFT;
    protected int TileY;
    protected int attackSight = (int) (Game.TILES_SIZE * 0.3);
    protected int SightRange = Game.TILES_SIZE * 5;

    protected int MaxHealth;
    protected int Health;
    protected boolean active = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        MaxHealth = GetMaxHealth(enemyType);
        Health = MaxHealth;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if(enemyState == ATTACK)
            aniSpeed = 10;
        else if (enemyState == HIT) {
            aniSpeed = 50;
        } else
            aniSpeed = 20;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
                switch (enemyState) {
                    case ATTACK, HIT -> enemyState = IDLE;
                    case DEATH -> active = false;
                }
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
            direction = false;
        } else {
            xSpeed = this.WalkingSpeed;
            direction = true;
        }
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, LevelData))
            if (IsFloor(hitbox, xSpeed, LevelData)) {
                // if(knockback != 0)
                //     xSpeed = knockback;
                hitbox.x += xSpeed;
                return;
            }
        changeWalkDirection();
    }

    protected void changeWalkDirection() {
        if (walkDirection == LEFT) {
            walkDirection = RIGHT;
            direction = true;
        } else {
            walkDirection = LEFT;
            direction = false;
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
            this.enemyState = newState;
            aniTick = 0;
            aniIndex = 0;
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
        return (((player.hitbox.x + hitbox.width * 0.2 - hitbox.x) >= -attackSight && (player.hitbox.x - hitbox.x) <= 0)
                || ((player.hitbox.x - hitbox.width - hitbox.x) <= attackSight && (player.hitbox.x - hitbox.x) >= 0));
    }

    private boolean isPlayerInSight(Player player) {
        int absSight = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return absSight <= SightRange;
    }

    public void hurt(int value, Player player) {
        Health -= value;
        if (Health <= 0) {
            newState(DEATH);
        } else {
            newState(HIT);
        }
        setKnockback(0.2f, player);
    }

    public boolean isActive() {
        return active;
    }

    protected void checkPlayerHit(Rectangle2D.Float AttackBox, Player player, int enemyType) {
        if (AttackBox.intersects(player.getHitbox())) {
            player.changeHealth(-GetAttackDamage(enemyType));
            attackChecked = true;
            player.isHurt(true);
            player.setKnockback(GetKnockBack(enemyType), this.hitbox);
        }
    }

    protected void resetAll(){
        hitbox.x = x;
        hitbox.y = y;
        Health = MaxHealth;
        active = true;
        firstTime = true;
        newState(IDLE);
        FallSpeed = 0;

    }
}
