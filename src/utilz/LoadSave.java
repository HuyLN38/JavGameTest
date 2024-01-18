package utilz;

import static utilz.Constants.EnemyConstants.CHOMP;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Chomp;
import main.Game;

public class LoadSave {

    public static final String PLAYER_ATLAS = "res/player_sprites.png";
    public static final String LEVEL_ATLAS = "res/Texture/grassland/Terrain.png";
    private static final String LEVEL_ONE_DATA = "res/Level1_impossible.png";
    public static final String MENU_BUTTON = "res/menu.png";
    public static final String MENU_BACKGROUND = "res/table.png";
    public static final String MENU_BACKGROUND_REAL = "res/background.png";
    public static final String PAUSE_BACKGROUND = "res/pausemenu.png";
    public static final String SOUND_BUTTON = "res/volume.png";
    public static final String VOLUME_BAR = "res/volumebar.png";
    public static final String VOLUME_SET = "res/volumeset.png";
    public static final String BUTTON_MENU = "res/Button.png";
    public static final String HEALTH_BAR = "res/healthbar.png";
    public static final String URM_BUTTONS = "res/urm_buttons.png";
    public static final String LEVEL_COMPLETED = "res/completed_sprite.png";
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
                e.printStackTrace();
            }
        return img;
        }

    public static BufferedImage [] GetAllLevels(){
        URL url = LoadSave.class.getResource("/Lvls");
        File file = null;

        try{
            file = new File(url.toURI());
        } catch(URISyntaxException e){
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] Levels = new File[files.length];

        for(int i = 0; i < files.length; i++){
           for(int j = 0; j < files.length; j++){
               if(files[j].getName().equals((i+1) + ".png")){
                   Levels[i] = files[j];
               }
           }
        }

        // for(File f : files)
        //     System.out.println("File: " + f.getName());
        // for(File f : Levels)
        //     System.out.println("Level: " + f.getName());

        BufferedImage[] imgs = new BufferedImage[Levels.length];

        for(int i = 0; i < Levels.length; i++){
            try{
                imgs[i] = ImageIO.read(Levels[i]);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
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
