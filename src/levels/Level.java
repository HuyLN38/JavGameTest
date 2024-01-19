package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Chomp;
import main.Game;

import static utilz.HelpMethods.*;



public class Level {

    private BufferedImage img;
    private int[][] LevelData;
    private ArrayList<Chomp> Chomps;
    private int LevelTitesWide;
    private int maxTitlesOffSet;
    private int maxLevelOffSetX;
    private Point LevelSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        CalculateLevelOffSet();
        CalculatePlayerSpawn();        
    }

    private void CalculatePlayerSpawn() {
        LevelSpawn = GetPlayerSpawn(img);
    }
    

    private void CalculateLevelOffSet() {
        LevelTitesWide = img.getWidth();
        maxTitlesOffSet = LevelTitesWide - Game.TILE_IN_WIDTH;
        maxLevelOffSetX = maxTitlesOffSet * Game.TILES_SIZE;
    }

    private void createEnemies() {
        Chomps = GetChomp(img);
    }

    private void createLevelData() {
        LevelData = GetLevelData(img);
    }

    public int GetSpriteIndex(int y, int x){
        return LevelData[y][x];
    }

    public int[][] getLevelData(){
        return LevelData;
    }

    public int getLevelOffset(){
        return maxLevelOffSetX;
    }

    public ArrayList<Chomp> getChomps(){
        return Chomps;
    }

    public Point getPlayerSpawn(){
        return LevelSpawn;
    }


}
