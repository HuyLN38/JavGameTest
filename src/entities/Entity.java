package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {

    protected int dir = 1; 
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox, AttackBox;
    protected int Health;
    protected boolean direction = true;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    protected void drawHitbox(Graphics g, int xLevelOffSet) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLevelOffSet, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void updateAttackBox() {
        if(direction)
         AttackBox.x = hitbox.x + hitbox.width;
        else 
         AttackBox.x = hitbox.x - AttackBox.width;
         AttackBox.y = hitbox.y + (hitbox.height/2) - (AttackBox.height/2);
     }

    protected void initHitbox(float x, float y, float width, float height) {
        this.hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    protected void initAttackBox(int width, int height) {
        this.AttackBox = new Rectangle2D.Float(x, y, (int)(width*Game.SCALE), (int)(height*Game.SCALE));
    }

    // public void updateHitbox(){
    // hitbox.x = (int)x;
    // hitbox.y = (int)y;
    // }
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void changeHealth(int value){
        this.Health += value;
        if(this.Health <= 0){
            this.Health = 0;
        } else if(this.Health >= 100){
            this.Health = 100;
        }
    }

}
