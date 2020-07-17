package com.cangcui.gametest.engine;

public class GameEngine implements Runnable {
	
	private final Thread gameLoopThread;
	private final Window window;
	private final IGameLogic gameLogic;
	
	public GameEngine(String windowTitle, int width, int height, boolean vsSync, IGameLogic gameLogic) throws Exception {
		gameLoopThread = new Thread(this, "Game Thread");
		window = new Window(windowTitle, width, height, vsSync);
		this.gameLogic = gameLogic;
	}
	
	public void start() {
		gameLoopThread.start();
	}

	@Override
	public void run() {
		try {
			init();
			gameLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws Exception {
		
	}
	
	public void gameLoop() {
		
	}
	
	protected void input() {
		gameLogic.input(window);
	}
	
	protected void update(float interval) {
		gameLogic.update(interval);
	}
	
	protected void render() {
		gameLogic.render(window);
		window.update();
	}

}
