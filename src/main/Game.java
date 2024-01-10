package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;

public class Game implements Runnable{
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 100;
	private final int UPS_SET = 100;

	public final static int TILE_DEFAULT_SIZE = 32;
	public final static float SCALE = 2.0f;
	public final static int TILE_IN_WIDTH = 26;
	public final static int TILE_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int)(TILE_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILE_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILE_IN_HEIGHT;

	private Player player;
	private LevelManager LevelManager;

	
	public Game() {

		initClasses();

		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();

		StartGameLoop();
		
	}

	private void initClasses() {
		LevelManager = new LevelManager(this);
		player = new Player(250, 250, (int)(68*SCALE), (int)(68*SCALE));
		player.loadLvData(LevelManager.getLevel().getLevelData());
	}

	private void StartGameLoop() {

		gameThread = new Thread(this);
		gameThread.start();
		
	}

	private void update(){ 
		player.update();
		LevelManager.update();
	}

	public void render(Graphics g){
		LevelManager.draw(g);
		player.render(g);
	}

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0/FPS_SET;
		double timePerUpdate = 1000000000.0/UPS_SET;

		long previousTime = System.nanoTime();

		int fps = 0;
		int updates = 0;
		long LastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;
		

		while(true){

			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if(deltaU >= 1){
				update();
				updates++;
				deltaU--;
			}

			if(deltaF >= 1){
				gamePanel.repaint();
				deltaF--;
				fps++;
			}


			// if (System.nanoTime() - lastFrame >= timePerFrame){

			// 	gamePanel.repaint();
			// 	lastFrame = now;
			// 	fps++;

			// }

		if (System.currentTimeMillis() - LastCheck >= 1000){
			LastCheck = System.currentTimeMillis();
			System.out.println("FPS: " + fps + "    " + "UPS: "+ updates);
			fps = 0;
			updates = 0;
		}

		}

	}

		public Player getPlayer(){
			return player;
	}

		public void windowFocusLost() {
			player.resetDirBoolean();
		}

}
