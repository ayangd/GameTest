package com.cangcui.gametest.game;

import org.lwjgl.glfw.GLFW;

import com.cangcui.gametest.engine.IGameLogic;
import com.cangcui.gametest.engine.Window;
import com.cangcui.gametest.engine.graph.Mesh;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class DummyGame implements IGameLogic {
	
	private int direction = 0;
	private float color = 0.0f;
	private final Renderer renderer;
	private Mesh mesh;
	
	public DummyGame() {
		renderer = new Renderer();
	}

	@Override
	public void init() throws Exception {
		renderer.init();
		float[] positions = new float[]{
            -0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
             0.5f, -0.5f, 0.0f,
             0.5f,  0.5f, 0.0f,
        };
        float[] colors = new float[]{
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[]{
            0, 1, 3, 3, 1, 2,
        };
	    mesh = new Mesh(positions, colors, indices);
	}

	@Override
	public void input(Window window) {
		if (window.isKeyPressed(GLFW.GLFW_KEY_UP)) {
			direction = 1;
		} else if (window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
			direction = -1;
		} else {
			direction = 0;
		}
	}

	@Override
	public void update(float interval) {
		color += direction * 0.01f;
		if (color > 1.0f) {
			color = 1.0f;
		} else if (color < 0.0f) {
			color = 0.0f;
		}
	}

	@Override
	public void render(Window window) {
		window.setClearColor(color, color, color, 1.0f);
		renderer.render(window, mesh);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		mesh.cleanup();
	}

}
