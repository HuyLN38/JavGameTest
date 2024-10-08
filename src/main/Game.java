package main;

import java.awt.Graphics;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import utilz.LoadSave;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 100;
	private final int UPS_SET = 100;

	private Playing playing;
	private Menu menu;

	public final static int TILE_DEFAULT_SIZE = 32;
	public final static float SCALE = 2.0f;
	public final static int TILE_IN_WIDTH = 26;
	public final static int TILE_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILE_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILE_IN_HEIGHT;

	public Game() {

		// LoadSave.GetAllLevels();
		initClasses();

		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();

		StartGameLoop();

	}

	private void initClasses() {

		menu = new Menu(this);
		playing = new Playing(this);

	}

	private void StartGameLoop() {

		gameThread = new Thread(this);
		gameThread.start();

	}

	private void update() {
		switch (Gamestate.state) {
			case PLAYING:
				playing.update();
				break;
			case MENU:
				menu.update();
				break;
			case QUIT:
				System.exit(0);
				break;
			default:
				break;
		}
	}

	public void render(Graphics g) {
		switch (Gamestate.state) {
			case PLAYING:
				playing.draw(g);
				break;
			case MENU:
				menu.draw(g);
				break;
			default:
				break;
		}
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int fps = 0;
		int updates = 0;
		long LastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {

			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				deltaF--;
				fps++;
			}

			// if (System.nanoTime() - lastFrame >= timePerFrame){

			// gamePanel.repaint();
			// lastFrame = now;
			// fps++;

			// }

			if (System.currentTimeMillis() - LastCheck >= 1000) {
				LastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + fps + "    " + "UPS: " + updates);
				fps = 0;
				updates = 0;
			}

		}

	}

	public void windowFocusLost() {

		if(Gamestate.state == Gamestate.PLAYING)
			playing.getPlayer().resetDirBoolean();
			playing.paused = true;

	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	

}
