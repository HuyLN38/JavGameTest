package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;

import static utilz.Constants.PlayerConstants.*;
// import static utilz.Constants.Direction.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityXPosNextToWall;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.LoadSave.*;
import static utilz.Constants.UI.HealthBar.*;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {

    public BufferedImage[][] PlayerAnimation;
    private BufferedImage temp;
    private int animationTick, animationIndex, animationSpeed = 25;
    private int playerAction = IDLE;
    private boolean left, up, right, down = false;
    private boolean moving = false, attacking = false, jump = false, hurt = false;
    private float playerSpeed = 3.00f;
    private float knockback = 0;
    private int[][] LevelData;
    private float xDrawOffset = 25 * Game.SCALE, yDrawOffset = 18 * Game.SCALE;
    private float airSpeed = 0f;
    private float gravity = 0.1f * Game.SCALE;
    private float jumpSpeed = -3f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.1f * Game.SCALE;
    private boolean inAir = false;
    private boolean direction = true, previousDirection = true;
    private float xSpeed = 0;
    private boolean attackChecked;

    // HEALTH
    private BufferedImage HealthBar;
    private int MaxHealth = 100;
    // ATTACK BOX

    private int i = 0, jump_count = 0, jump_cache = 0;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimation();
        Health = MaxHealth;
        initHitbox(x, y, (int) (width / 8) * Game.SCALE, height / 5 * Game.SCALE);
        initAttackBox(20, 20);
    }

    public void SetSpawn(Point spawn){
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    public void update() {

        if (Health <= 0) {
            if (isDead()){
                playing.setGameOver(true);
            return;
            }
        }

        updateHealth();
        updateAttackBox();

        updatePos();
        if (attacking)
            checkAttack();
        else
            attackChecked = false;
        updateAnimationTick();
        setAnimation();

    }

    private void checkAttack() {
        if (attackChecked || animationIndex != 1) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(AttackBox);
    }

    protected void updateAttackBox() {
        if (direction)
            AttackBox.x = hitbox.x + hitbox.width;
        else
            AttackBox.x = hitbox.x - AttackBox.width;
        AttackBox.y = hitbox.y + (hitbox.height / 2) - (AttackBox.height / 2);
    }

    private void updateHealth() {

    }

    public void render(Graphics g, int LevelOffSet) {
        temp = PlayerAnimation[playerAction][animationIndex];
        int xDrawPos = (int) (hitbox.x - xDrawOffset);
        int yDrawPos = (int) (hitbox.y - yDrawOffset);
        int drawWidth = width;
        int drawHeight = height;

        if (attacking) {
            direction = previousDirection;
            if (left)
                left = !left;
            if (right)
                right = !right;
        } else
            previousDirection = direction;

        if (left || !direction) {
            // Flip the image horizontally
            g.drawImage(temp, (xDrawPos + drawWidth) - LevelOffSet, yDrawPos, -drawWidth, drawHeight, null);
        } else {
            g.drawImage(temp, xDrawPos - LevelOffSet, yDrawPos, drawWidth, drawHeight, null);
        }
        // Draw attackBox
        // g.setColor(Color.PINK);
        // g.drawRect((int) AttackBox.x - LevelOffSet, (int) AttackBox.y, (int) AttackBox.width, (int) AttackBox.height);

        // Draw the hitbox
        // Enable hitbox drawing
        // g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width,
        // (int)hitbox.height);

        // Draw the health bar
        g.drawImage(HealthBar, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE), HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT,
                null);
        g.setColor(Color.RED);
        g.fillRect(HEALTH_BAR_DRAWOFFX, HEALTH_BAR_DRAWOFFY,
                (int) (HEALTH_WIDTH * ((float) Health / (float) MaxHealth)), (int) (HEALTH_HEIGHT));
    }

    private void loadAnimation() {
        PlayerAnimation = new BufferedImage[21][8];
        BufferedImage img = LoadSave.GetSpriteAtlast(PLAYER_ATLAS);
        for (int i = 0; i < PlayerAnimation.length; i++) {
            for (int j = 0; j < PlayerAnimation[i].length; j++) {
                PlayerAnimation[i][j] = img.getSubimage(48 * j, 48 * i, 48, 48);
            }
        }
        HealthBar = LoadSave.GetSpriteAtlast(HEALTH_BAR);
    }

    public void loadLvData(int[][] LevelData) {
        this.LevelData = LevelData;
        if (!IsEntityOnFloor(hitbox, LevelData))
            inAir = true;
    }

    public void changeXDelta(int value) {
        this.x += value;
    }

    public void changeYDelta(int value) {
        this.y += value;
    }

    public void setRectPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void updateAnimationTick() {

        animationTick++;
        if(attacking == true || hurt == true)
            animationSpeed = 10;
        else if (moving == true){
            animationSpeed = 10;
        }
        else animationSpeed = 25;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpiritAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
                hurt = false;
                if (i <= 2)
                    i++;
                else
                    i = 0;
            }
        }

    }

    private void setAnimation() {
        int startedAni = playerAction;
        if (playerAction != DEATH){

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        if (inAir)
            if (airSpeed < 0)
                playerAction = JUMPING;
        if (attacking && !hurt) {
            playerAction = ATTACK[i];
            if (startedAni != ATTACK[i]) {
                animationIndex = 1;
                animationTick = 0;
            }
        }
        if (hurt && !attacking) {
            playerAction = HURT;
            if (startedAni != HURT) {
                animationIndex = 1;
                animationTick = 0;
            }
        }
    }

        if( Health <= 0 ) playerAction = DEATH;
        if (startedAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        animationTick = 0;
        animationIndex = 0;

    }

    private void updatePos() {
        moving = false;
        xSpeed = 0;

        if (hurt) {
            xSpeed = knockback;
            updateXPos(xSpeed);
            xSpeed = 0;
            return;
        }

        if (jump && !attacking) {
            jump();
        }

        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, LevelData)) {
                inAir = true;
            }
            jump_count = 0;
            jump_cache = 0;
        }

        if (!left && !right && !inAir)
            return;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, LevelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else
            updateXPos(xSpeed);
        if (xSpeed != 0)
            moving = true;
        else
            moving = false;

    }

    private void jump() {
        if (jump_count == 0 || jump_count == 1 || jump_count == 2)
            jump_cache++;
        if ((inAir && (jump_cache > 1 || jump_count > 1)) || (jump_count == 0 && jump_cache == 0))
            return;
        inAir = true;
        if (jump_count == 1) {
            airSpeed = (int) (jumpSpeed / 1.2);
        } else
            airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, LevelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void resetDirBoolean() {
        left = false;
        down = false;
        up = false;
        right = false;
    }

    public void SetAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isUp() {
        return up;
    }

    public void setKnockback(float knockback, Rectangle2D.Float enemy) {
        if (enemy.x > hitbox.x)
            this.knockback = -knockback;
        else if (enemy.x < hitbox.x)
            this.knockback = knockback;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
        if (!jump) {
            jump_count++;
            jump_cache = 0;
        }
    }

    public void setDir(boolean direction) {
        this.direction = direction;
    }

    public boolean getInAir() {
        return inAir;
    }

    public void isHurt(boolean hurt) {
        this.hurt = hurt;
    }

    public void resetAll(){
		resetDirBoolean();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		Health = MaxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, LevelData))
			inAir = true;
	}

    public boolean isDead(){
        return (animationIndex == 6 && playerAction == DEATH);
    }

}

//
