package utilz;

import static utilz.Constants.EnemyConstants.CHOMP;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Chomp;
import main.Game;

public class HelpMethods {

    // public static boolean CanMoveHere(float x, float y, float width, float
    // height, int[][] LevelData){

    // if (!isSolid(x, y, LevelData)){
    // if(!isSolid(x+width, y+height, LevelData)){
    // if(!isSolid(x+width, y, LevelData)){
    // if(!isSolid(x+width, y+height, LevelData))
    // return true;
    // }
    // }
    // }
    // return false;

    // }

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] LevelData) {
        if (!isSolid(x, y, LevelData) && !isSolid(x + width, y, LevelData) &&
                !isSolid(x, y + height, LevelData) && !isSolid(x + width, y + height, LevelData)) {
            return true;
        }
        return false;
    }

    public static boolean isSolid(float x, float y, int[][] LevelData) {
        int maxWidth = LevelData.length * Game.TILES_SIZE;
        // System.out.println(maxWidth);
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isSingelTileSolid((int) xIndex, (int) yIndex, LevelData);
    }

    private static boolean isSingelTileSolid(int xTile, int yTile, int[][] LevelData) {
        int value = LevelData[xTile][yTile];
        if (value >= 42 || value != 0) {
            return true;
        }
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILES_SIZE;

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] LevelData) {
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height, LevelData))
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height, LevelData))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] LevelData) {
        if (xSpeed > 0)
            return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, LevelData);
        else
            return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, LevelData);
    }

    public static BufferedImage flipImageHorizontally(BufferedImage image) {
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-image.getWidth(), 0);
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(image, null);
    }

    public static boolean isAllXTileWalkable(int XStart, int XEnd, int y, int[][] LevelData) {
        for (int i = 0; i < XEnd - XStart; i++) {
            if (isSingelTileSolid(XStart + i, y, LevelData))
                return false;
            if (!isSingelTileSolid(XStart + i, y + 1, LevelData))
                return false;
        }
        return true;
    }

    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float Hitbox1st, Rectangle2D.Float Hitbox2nd,
            int TileY) {
        int x1 = (int) (Hitbox1st.x / Game.TILES_SIZE);
        int x2 = (int) (Hitbox2nd.x / Game.TILES_SIZE);

        if (x1 > x2)
            return isAllXTileWalkable(x2, x1, TileY, levelData);
        else
            return isAllXTileWalkable(x1, x2, TileY, levelData);
    }

    public static int[][] GetLevelData(BufferedImage img) {
        int[][] LevelData = new int[img.getWidth()][img.getHeight()];
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (LevelData[i][j] >= 42)
                    value = 0;
                LevelData[i][j] = value;
            }
        }
        return LevelData;
    }

    public static ArrayList<Chomp> GetChomp(BufferedImage img) {
        ArrayList<Chomp> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == CHOMP)
                    list.add(new Chomp(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }

    public static Point GetPlayerSpawn(BufferedImage img){
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }
}
