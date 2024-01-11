package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;

public class Playing extends State implements Statemethods{

    private Player player;
	private LevelManager LevelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;

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
        }
        pauseOverlay.update();

    }


    @Override
    public void draw(Graphics g) {
           
            LevelManager.draw(g);
            player.render(g);

            if (paused)
            pauseOverlay.draw(g);

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

    public Gamestate getState() {
        return Gamestate.state;
    }

    public void setPaused(boolean pause) {
        this.paused = !isPaused();
    }

    public boolean isPaused() {
        return paused;
    }

	public void windowFocusLost() {
		player.resetDirBoolean();
	}

}
