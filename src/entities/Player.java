package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static utilz.Constants.PlayerConstants.*;
// import static utilz.Constants.Direction.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityXPosNextToWall;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.LoadSave.*;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {

    public BufferedImage[][] PlayerAnimation;
    private BufferedImage temp;
    private int animationTick, animationIndex, animationSpeed = 25;
    private int playerAction = IDLE;
    private boolean left, up, right, down = false;
    private boolean moving = false, attacking = false, jump = false;
    private float playerSpeed = 1.7f;
    private int[][] LevelData;
    private float xDrawOffset = 18 * Game.SCALE, yDrawOffset = 16 * Game.SCALE;
    private float airSpeed = 0f;
    private float gravity = 0.1f * Game.SCALE;
    private float jumpSpeed = -4f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.1f * Game.SCALE;
    private boolean inAir = false;
    private boolean direction = true;

    private int i = 0, jump_count = 0, jump_cache = 0;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimation();
        initHitbox(x, y, 12 * Game.SCALE, 16 * Game.SCALE);
        // TODO Auto-generated constructor stub
    }

    public void update() {

        updateAnimationTick();
        setAnimation();
        updatePos();

    }

    public void render(Graphics g) {
        temp = PlayerAnimation[playerAction][animationIndex];
        int xDrawPos = (int) (hitbox.x - xDrawOffset);
        int yDrawPos = (int) (hitbox.y - yDrawOffset);
        int drawWidth = width;
        int drawHeight = height;
        
        if (left || !direction) {
            // Flip the image horizontally
            g.drawImage(temp, xDrawPos + drawWidth, yDrawPos, -drawWidth, drawHeight, null);
        } else {
            g.drawImage(temp, xDrawPos, yDrawPos, drawWidth, drawHeight, null);
        }
    }

    private void loadAnimation() {
        PlayerAnimation = new BufferedImage[21][8];
        BufferedImage img = LoadSave.GetSpriteAtlast(PLAYER_ATLAS);
        for (int i = 0; i < PlayerAnimation.length; i++) {
            for (int j = 0; j < PlayerAnimation[i].length; j++) {
                PlayerAnimation[i][j] = img.getSubimage(48 * j, 48 * i, 48, 48);
            }
        }
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
        animationSpeed = (int) (70 / GetSpiritAmount(playerAction));
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpiritAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
                if (i <= 2)
                    i++;
                else
                    i = 0;
            }
        }

    }

    private void setAnimation() {

        int startedAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        if (inAir)
            if (airSpeed < 0)
                playerAction = JUMPING;
        if (attacking) {
            playerAction = ATTACK[i];
        }
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

        if (jump) {
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

        float xSpeed = 0;

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
        if ((inAir && (jump_cache > 1 || jump_count > 1))|| (jump_count == 0 && jump_cache == 0))
            return;
        inAir = true;
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
}

//
