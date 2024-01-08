package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;


public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private Game game;

	// private Color color;

	public GamePanel(Game game) {

		this.game = game;
		setPanelSize();
		mouseInputs = new MouseInputs(this);
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

	}

	private void setPanelSize() {
		Dimension hd = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(hd);
	}
	public void updateGame() {

		}

	public void paintComponent(Graphics g) {
	
	super.paintComponent(g);

	g.setColor(Color.BLACK);

	game.render(g);

	}

	public Game getGame(){
		return game;
	}

		
}
