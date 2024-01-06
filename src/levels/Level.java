package levels;

public class Level {
    private int[][] LevelData;

    public Level(int[][] LevelData){
        
        this.LevelData = LevelData;
    
    }

    public int GetSpriteIndex(int x, int y){
        return LevelData[x][y];
    }

    public int[][] getLevelData(){
        return LevelData;
    }
}
