package com.cangcui.gametest.engine;

public class GameEngine implements Runnable {
	
	private final Thread gameLoopThread;
	private final Window window;
	private final IGameLogic gameLogic;
	private final Timer timer;
	private final MouseInput mouseInput;
	
	public GameEngine(String windowTitle, int width, int height, boolean vsSync, IGameLogic gameLogic) throws Exception {
		gameLoopThread = new Thread(this, "Game Thread");
		window = new Window(windowTitle, width, height, vsSync);
		this.gameLogic = gameLogic;
		this.timer = new Timer();
		mouseInput = new MouseInput();
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
		} finally {
			cleanup();
		}
	}
	
	protected void init() throws Exception {
		window.init();
		timer.init();
		gameLogic.init(window);
		mouseInput.init(window);
	}
	
	protected void gameLoop() {
		float elapsedTime;
		float accumulator = 0.0f;
		float interval = 1/60.0f;
		
		boolean running = true;
		while (running && !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;
			
			input();
			
			while (accumulator >= interval) {
				update(interval);
				accumulator -= interval;
			}
			
			render();
			
			if (!window.isvSync()) {
				sync();
			}
		}
	}
	
	protected void cleanup() {
		gameLogic.cleanup();
	}
	
	private void sync() {
		float loopSlot = 1/60f;
		double endTime = timer.getLastLoopTime() + loopSlot;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	protected void input() {
		mouseInput.input(window);
		gameLogic.input(window, mouseInput);
	}
	
	protected void update(float interval) {
		gameLogic.update(interval, mouseInput);
	}
	
	protected void render() {
		gameLogic.render(window);
		window.update();
	}

}
