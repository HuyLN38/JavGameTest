package levels;

import static main.Game.TILES_SIZE;
import static main.Game.TILE_IN_HEIGHT;
import static main.Game.TILE_IN_WIDTH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage levelSprite[];
    private Level levelOne;

    public LevelManager(Game game){
        this.game = game;
        // 
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());

    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlast(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[42];
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 7; j++){
                int index = i * 7 + j;
                levelSprite[index] = img.getSubimage(j*16, i*16, 16, 16);
            }
    }

    public void draw(Graphics g){
        for(int i = 0; i < Game.TILE_IN_WIDTH; i++)
            for (int j = 0; j < Game.TILE_IN_HEIGHT; j++) {
                int index = levelOne.GetSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE*i, TILES_SIZE*j,TILES_SIZE,TILES_SIZE,  null);
            }
    }
    
    public void update(){

    }

    public Level getLevel() {
        return levelOne;
    }
}
