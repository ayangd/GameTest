package com.cangcui.gametest.engine;

public interface IGameLogic {
	
	/**
	 * Initialize the game logic.
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * Process any input from window.
	 * @param window window input
	 */
	void input(Window window);
	
	/**
	 * Update game by interval.
	 * @param interval interval in seconds
	 */
	void update(float interval);
	
	/**
	 * Render the game to window.
	 * @param window window for rendering
	 */
	void render(Window window);
}
