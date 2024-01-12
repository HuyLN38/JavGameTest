package levels;

public class Level {
    private int[][] LevelData;

    public Level(int[][] LevelData){
        
        this.LevelData = LevelData;
    
    }

    public int GetSpriteIndex(int y, int x){
        return LevelData[y][x];
    }

    public int[][] getLevelData(){
        return LevelData;
    }
}
