package com.cangcui.gametest.engine;

public interface IGameLogic {
	
	/**
	 * Initialize the game logic.
	 * @throws Exception
	 */
	void init(Window window) throws Exception;
	
	/**
	 * Process any input from window.
	 * @param window the window corresponding to the game
	 * @param mouseInput mouse input
	 */
	void input(Window window, MouseInput mouseInput);
	
	/**
	 * Update game by interval.
	 * @param interval interval in seconds
	 * @param mouseInput mouse input
	 */
	void update(float interval, MouseInput mouseInput);
	
	/**
	 * Render the game to window.
	 * @param window window for rendering
	 */
	void render(Window window);
	
	/**
	 * Perform game cleanup.
	 */
	void cleanup();
}
