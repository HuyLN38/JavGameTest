package utilz;

import static main.Game.GAME_HEIGHT;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {

    // public static boolean CanMoveHere(float x, float y, float width, float height, int[][] LevelData){

    //     if (!isSolid(x, y, LevelData)){
    //         if(!isSolid(x+width, y+height, LevelData)){
    //             if(!isSolid(x+width, y, LevelData)){
    //                 if(!isSolid(x+width, y+height, LevelData))
    //                 return true;
    //             }
    //         }
    //     }
    //     return false;

    // }

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] LevelData) {
        if (!isSolid(x, y, LevelData) && !isSolid(x + width, y, LevelData) &&
                !isSolid(x, y + height, LevelData) && !isSolid(x + width, y + height, LevelData)) {
            return true;
        }
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] LevelData){
        int maxWidth = LevelData.length * Game.TILES_SIZE;
        // System.out.println(maxWidth);
        if (x < 0 || x >= maxWidth){
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT){
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = LevelData[(int)xIndex][(int)yIndex];

        if(value >= 42 || value !=  0){
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

        public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] LevelData){
            if(!isSolid(hitbox.x, hitbox.y+hitbox.height, LevelData))
                if(!isSolid(hitbox.x+hitbox.width, hitbox.y+hitbox.height, LevelData))
                    return false;
            return true;
        }
}
    
