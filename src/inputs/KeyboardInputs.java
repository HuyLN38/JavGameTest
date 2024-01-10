package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {

	private GamePanel gamePanel;

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (Gamestate.state) {
			case MENU:
				gamePanel.getGame().getMenu().keyReleased(e);
				break;
			case PLAYING:
				gamePanel.getGame().getPlaying().keyReleased(e);
				break;
			default:
				break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            Gamestate.state = Gamestate.MENU;
        }
		switch (Gamestate.state) {
			case PLAYING:
				gamePanel.getGame().getPlaying().keyPressed(e);
				break;
			case MENU:
				gamePanel.getGame().getMenu().keyPressed(e);
				break;
			default:
				break;
		}
	}

}
