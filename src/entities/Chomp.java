package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;


public class Chomp extends Enemy {

    public Chomp(float x, float y) {
        super(x, y, CHOMP_WIDTH, CHOMP_HEIGHT, CHOMP);
        WalkingSpeed = 2.5f;
        initHitbox(x, y, (int) (CHOMP_WIDTH * 0.8), (int) (CHOMP_HEIGHT * 0.5));
        initAttackBox((int) (CHOMP_WIDTH * 0.3), (int) (CHOMP_HEIGHT * 0.3));
        
    }

    public void drawHitbox(Graphics g, int xLevelOffSet) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLevelOffSet , (int) hitbox.y, (int) (CHOMP_WIDTH * 0.8), (int) (CHOMP_HEIGHT * 0.5));

        g.setColor(Color.PINK);
        g.drawRect((int)AttackBox.x - xLevelOffSet, (int)AttackBox.y, (int)AttackBox.width, (int)AttackBox.height);
    }

    private void updateBehavior(int[][] LevelData, Player player) {
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
                case ATTACK:
                    if(aniIndex == 0)
                        attackChecked = false;
                    if(aniIndex == 3 && !attackChecked)
                        checkPlayerHit(AttackBox, player, this.enemyType);
                    break;
                case HIT:
                    break;
            }
        }
    }

    public void update(int[][] LevelData, Player player) {
        updateAttackBox();

        updateBehavior(LevelData, player);

        updateAnimationTick();
        
    }

}
