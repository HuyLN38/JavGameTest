package main;

import java.awt.Graphics;

import entities.Player;

public class Game implements Runnable{
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 100;
	private final int UPS_SET = 100;

	private Player player;

	
	public Game() {

		initClasses();

		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();

		StartGameLoop();
		
	}

	private void initClasses() {
		player = new Player(100, 100);
	}

	private void StartGameLoop() {

		gameThread = new Thread(this);
		gameThread.start();
		
	}

	private void update(){ 
		player.update();
	}

	public void render(Graphics g){
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

}
