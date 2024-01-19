package levels;

import static main.Game.TILES_SIZE;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage levelSprite[];
    private ArrayList<Level> levels;
    private int LevelIndex = 0;

    public LevelManager(Game game){
        this.game = game;
        // 
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();

    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for(BufferedImage img : allLevels){
            levels.add(new Level(img));
        }
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

    public void draw(Graphics g, int xLevelOffSet){
        for(int i = 0; i < levels.get(LevelIndex).getLevelData().length; i++)
            for (int j = 0; j < Game.TILE_IN_HEIGHT; j++) {
                int index = levels.get(LevelIndex).GetSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE*i - xLevelOffSet, TILES_SIZE*j,TILES_SIZE,TILES_SIZE,  null);
            }
    }
    
    public void update(){

    }

    public Level getLevel() {
        return levels.get(LevelIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

	public void nextLevel() {
        LevelIndex++;
        if(LevelIndex >= levels.size()){
            LevelIndex = 0;
            System.out.println("No more levels, returning to level 1");
            Gamestate.state = Gamestate.MENU;
        }

        Level NewLevel = levels.get(LevelIndex);
        game.getPlaying().getEnemyManager().LoadEnemies(NewLevel);
        game.getPlaying().getPlayer().loadLvData(NewLevel.getLevelData());
        game.getPlaying().SetLevelOffSet(NewLevel.getLevelOffset());
        game.getPlaying().resetAll();
	}
}
