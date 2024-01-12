package gamestates;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements Statemethods{

    private Player player;
	private LevelManager LevelManager;
    private PauseOverlay pauseOverlay;
    public static boolean paused = false;

    private int xLevelOffSet;
    private int leftBorder = (int )(0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int )(0.8 * Game.GAME_WIDTH);
    private int LevelTitesWide = LoadSave.GetLevelData().length;
    private int maxTitlesOffSet = LevelTitesWide - Game.TILE_IN_WIDTH;
    private int maxLevelOffSetX = maxTitlesOffSet * Game.TILES_SIZE;

    public Playing(Game game) {
        
        super(game);
        initClasses();
        
        
    }


    private void initClasses() {
		LevelManager = new LevelManager(game);
		player = new Player(250, 250, (int) (68 * Game.SCALE), (int) (68 * Game.SCALE));
		player.loadLvData(LevelManager.getLevel().getLevelData());
        pauseOverlay = new PauseOverlay();
	}

    @Override
    public void update() {
        if (!paused){
        LevelManager.update();
        player.update();
        checkBorder();
        }
        pauseOverlay.update();

    }


    private void checkBorder() {
       int playerX = (int) player.getHitbox().x;
       int diff = playerX - xLevelOffSet;

       if(diff > rightBorder){
           xLevelOffSet += diff - rightBorder;
       } else if (diff < leftBorder){
           xLevelOffSet += diff - leftBorder;
       }

       if(xLevelOffSet > maxLevelOffSetX){
           xLevelOffSet = maxLevelOffSetX;
       } else if (xLevelOffSet < 0){
           xLevelOffSet = 0;
       }
    }


    @Override
    public void draw(Graphics g) {
           
            LevelManager.draw(g, xLevelOffSet);
            player.render(g, xLevelOffSet);

            if (paused){
            g.setColor(new Color(0,0,0,200));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(g);
            }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
			player.SetAttack(true);
		}
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
  
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(true);
			player.setDir(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			player.setDir(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		}
    }


    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
        case KeyEvent.VK_P:
		    setPaused(true);
			break;
		}
    }

    public Player getPlayer() {
		return player;
	}

    public static Gamestate getState() {
        return Gamestate.state;
    }

    public static void setPaused(boolean pause) {
        paused = pause;
    }

    public boolean isPaused() {
        return paused;
    }

	public void windowFocusLost() {
		player.resetDirBoolean();
	}

}
