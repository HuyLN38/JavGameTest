package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {

    public static final String PLAYER_ATLAS = "res/player_sprites.png";
    public static final String LEVEL_ATLAS = "res/Terrain.png";
    private static final String LEVEL_ONE_DATA = "res/Level1.png";
    
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

    public static int[][] GetLevelData(){
        int[][] LevelData = new int [Game.TILE_IN_WIDTH][Game.TILE_IN_HEIGHT];
            BufferedImage img = GetSpriteAtlast(LEVEL_ONE_DATA);

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
