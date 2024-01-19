package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PLAYER_ATLAS = "res/player_sprites.png";
    public static final String LEVEL_ATLAS = "res/Texture/grassland/Terrain.png";
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
                   System.out.println("Level: " + Levels[i].getName());
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
        return imgs;
    }


        
}
