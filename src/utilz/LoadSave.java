package utilz;

import static utilz.Constants.EnemyConstants.CHOMP;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Chomp;
import main.Game;

public class LoadSave {

    public static final String PLAYER_ATLAS = "res/player_sprites.png";
    public static final String LEVEL_ATLAS = "res/Texture/grassland/Terrain.png";
    private static final String LEVEL_ONE_DATA = "res/Level1.png";
    public static final String MENU_BUTTON = "res/menu.png";
    public static final String MENU_BACKGROUND = "res/table.png";
    public static final String MENU_BACKGROUND_REAL = "res/background.png";
    public static final String PAUSE_BACKGROUND = "res/pausemenu.png";
    public static final String SOUND_BUTTON = "res/volume.png";
    public static final String VOLUME_BAR = "res/volumebar.png";
    public static final String VOLUME_SET = "res/volumeset.png";
    public static final String BUTTON_MENU = "res/Button.png";
    //GRASSLAND
    public static final String GRASSLAND_BACKGROUND = "res/Texture/grassland/sky.png";
    public static final String GRASSLAND_BIGCLOUD = "res/Texture/grassland/bigcloud.png";
    public static final String GRASSLAND_SMALLCLOUD = "res/Texture/grassland/smallcloud.png";
    public static final String GRASSLAND_GROUND = "res/Texture/grassland/ground.png";
    public static final String GRASSLAND_HILLS = "res/Texture/grassland/hills.png";

    //ENEMY
    ////MR.CHOMPS
    public static final String MRCHOMPS = "res/Texture/enemy/mrchomps/MrChomp.png";


    
    public static BufferedImage GetSpriteAtlast(String fileName){
        BufferedImage img = null;    
        try {
            img = ImageIO.read(new FileInputStream(fileName));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return img;
        }

    public static ArrayList<Chomp> GetChomp(){
        BufferedImage img = GetSpriteAtlast(LEVEL_ONE_DATA);
        ArrayList<Chomp> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++) 
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i,j));
                int value = color.getGreen();
                if (value == CHOMP )
                 list.add(new Chomp(i* Game.TILES_SIZE, j* Game.TILES_SIZE));
            }
        return list;
    }

    public static int[][] GetLevelData(){
            BufferedImage img = GetSpriteAtlast(LEVEL_ONE_DATA);
            int[][] LevelData = new int [img.getWidth()][img.getHeight()];

            for (int j = 0; j < img.getHeight(); j++) {
                for (int i = 0; i < img.getWidth(); i++) {
                    Color color = new Color(img.getRGB(i,j));
                    int value = color.getRed();
                    if (LevelData[i][j] >= 42)
                        value = 0;
                    LevelData[i][j] = value;
                }
            }
        return LevelData;  
    }
        
}
