package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.flipImageHorizontally;
import static utilz.LoadSave.MRCHOMPS;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] ChompImgs;
    private ArrayList<Chomp> chomps = new ArrayList<Chomp>();
    
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();    
        addEnemies();
    }

    private void addEnemies() {
      chomps = LoadSave.GetChomp();
    }

    public void update(int[][] LevelData, Player player){
        for (Chomp chomp : chomps) {
            chomp.update(LevelData, player);
        }

    }

    public void draw(Graphics g, int xLevelOffSet){
        drawCrab(g, xLevelOffSet);
    }

    private void drawCrab(Graphics g, int xLevelOffSet) {
        for (Chomp chomp : chomps) {
            BufferedImage chompImage = ChompImgs[chomp.getEnemyState()][chomp.getAniIndex()];
            
            if (chomp.isFacingLeft()) {
                // Flip the image horizontally
                chompImage = flipImageHorizontally(chompImage);
            }
            
            g.drawImage(chompImage, (int)chomp.getHitbox().x - xLevelOffSet, (int)(chomp.getHitbox().y - CHOMP_HEIGHT/2), CHOMP_WIDTH, CHOMP_HEIGHT, null);
            chomp.drawHitbox(g, xLevelOffSet);
        }
    }

    private void loadEnemyImgs() { 
        ChompImgs = new BufferedImage[4][12];
        BufferedImage temp = LoadSave.GetSpriteAtlast(MRCHOMPS);
        for (int i = 0; i < ChompImgs.length; i++) {
            for (int j = 0; j < ChompImgs[i].length; j++) {
                ChompImgs[i][j] = temp.getSubimage(j * CHOMP_WIDTH_DEFAULT, i * CHOMP_HEIGHT_DEFAULT, CHOMP_WIDTH_DEFAULT, CHOMP_HEIGHT_DEFAULT);
            }
        }
        
    }
}
