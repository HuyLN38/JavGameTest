package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Direction.*;

import javax.imageio.ImageIO;

public class Player extends Entity {

    public BufferedImage[][] PlayerAnimation;
    private BufferedImage img;
    private int animationTick, animationIndex, animationSpeed = 25;
	private int playerAction = IDLE;
	private boolean left, up, right, down = false;
	private boolean moving = false;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimation();
        //TODO Auto-generated constructor stub
    }

    public void update(){

        updateAnimationTick();
		setAnimation();
		updatePos();

    }

    public void render(Graphics g){
        g.drawImage(PlayerAnimation[playerAction][animationIndex], (int) x, (int) y, 128, 128,null); 
    }

    private void loadAnimation() {
		PlayerAnimation = new BufferedImage[21][8];
        try { 
        img = ImageIO.read(new FileInputStream("res/player_sprites.png"));
            for (int i = 0; i < PlayerAnimation.length; i++) {
			    for (int j = 0; j < PlayerAnimation[i].length; j++){
			        PlayerAnimation[i][j] = img.getSubimage(48 * j, 48 * i , 48, 48);
			}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
            animationSpeed = (int)(100 / GetSpiritAmount(playerAction));
            if (animationTick >= animationSpeed) {
                animationTick = 0;
                animationIndex++;
                if (animationIndex >= GetSpiritAmount(playerAction)) {
                    animationIndex = 0;
                }
            }
    
        }
    
        private void setAnimation() {
            if (moving)
                playerAction = RUNNING;
            else
                playerAction = IDLE;
        }
   
        private void updatePos() {

            moving = false;

            if(left && !right){
                x -= playerSpeed;
                moving = true;
            } else if (right && !left){
                x += playerSpeed;
                moving = true;
            }
            
            if (up && !down) {
                y -= playerSpeed;
                moving = true;
            } else if (down && !up) {
                y += playerSpeed;
                moving = true;
            }
           
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


}
    

