package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;

public class Playing extends State implements Statemethods{

    private Player player;
	private LevelManager LevelManager;

    public Playing(Game game) {
        
        super(game);
        initClasses();
        
    }


    private void initClasses() {
		LevelManager = new LevelManager(game);
		player = new Player(250, 250, (int) (68 * Game.SCALE), (int) (68 * Game.SCALE));
		player.loadLvData(LevelManager.getLevel().getLevelData());
	}

    @Override
    public void update() {

        LevelManager.update();
        player.update();

    }


    @Override
    public void draw(Graphics g) {
           
            LevelManager.draw(g);
            player.render(g);

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
			player.SetAttack(true);
		}
    }


    @Override
    public void mousePressed(MouseEvent e) {
      
    }


    @Override
    public void mouseReleased(MouseEvent e) {
     
    }


    @Override
    public void mouseMoved(MouseEvent e) {
  
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
		}
    }

    public Player getPlayer() {
		return player;
	}

    public Gamestate getState() {
        return Gamestate.state;
    }

	public void windowFocusLost() {
		player.resetDirBoolean();
	}

}
