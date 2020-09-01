package com.cangcui.gametest.game;

import com.cangcui.gametest.engine.GameItem;
import com.cangcui.gametest.engine.IGameLogic;
import com.cangcui.gametest.engine.MouseInput;
import com.cangcui.gametest.engine.Window;
import com.cangcui.gametest.engine.graph.Camera;
import com.cangcui.gametest.engine.graph.Mesh;
import com.cangcui.gametest.engine.graph.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class DummyGame implements IGameLogic {
	
	private static final float MOUSE_SENSITIVITY = 0.2f;
	private static final float CAMERA_POS_STEP = 0.05f;
	
	private int direction = 0;
	private float color = 0.0f;
	private final Renderer renderer;
	private Mesh mesh;
	private GameItem gameItem[];
	private final Camera camera;
	private final Vector3f cameraInc;
	
	public DummyGame() {
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);
		float[] positions = new float[] {
            // V0
            -0.5f, 0.5f, 0.5f,
            // V1
            -0.5f, -0.5f, 0.5f,
            // V2
            0.5f, -0.5f, 0.5f,
            // V3
            0.5f, 0.5f, 0.5f,
            // V4
            -0.5f, 0.5f, -0.5f,
            // V5
            0.5f, 0.5f, -0.5f,
            // V6
            -0.5f, -0.5f, -0.5f,
            // V7
            0.5f, -0.5f, -0.5f,
            
            // For text coords in top face
            // V8: V4 repeated
            -0.5f, 0.5f, -0.5f,
            // V9: V5 repeated
            0.5f, 0.5f, -0.5f,
            // V10: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V11: V3 repeated
            0.5f, 0.5f, 0.5f,

            // For text coords in right face
            // V12: V3 repeated
            0.5f, 0.5f, 0.5f,
            // V13: V2 repeated
            0.5f, -0.5f, 0.5f,

            // For text coords in left face
            // V14: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V15: V1 repeated
            -0.5f, -0.5f, 0.5f,

            // For text coords in bottom face
            // V16: V6 repeated
            -0.5f, -0.5f, -0.5f,
            // V17: V7 repeated
            0.5f, -0.5f, -0.5f,
            // V18: V1 repeated
            -0.5f, -0.5f, 0.5f,
            // V19: V2 repeated
            0.5f, -0.5f, 0.5f,
        };
        float[] texCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            
            // For text coords in top face
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,

            // For text coords in right face
            0.0f, 0.0f,
            0.0f, 0.5f,

            // For text coords in left face
            0.5f, 0.0f,
            0.5f, 0.5f,

            // For text coords in bottom face
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
        };
        int[] indices = new int[]{
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            8, 10, 11, 9, 8, 11,
            // Right face
            12, 13, 7, 5, 12, 7,
            // Left face
            14, 15, 6, 4, 14, 6,
            // Bottom face
            16, 18, 19, 17, 16, 19,
            // Back face
            4, 6, 7, 5, 4, 7,
        };
        Texture texture = new Texture("textures/grassblock.png");
	    mesh = new Mesh(positions, texCoords, indices, texture);
	    gameItem = new GameItem[1];
	    gameItem[0] = new GameItem(mesh);
	    gameItem[0].setPosition(0.0f, 0.0f, -2.0f);
	}

	@Override
	public void input(Window window, MouseInput mouseInput) {
		cameraInc.set(0);
		if (window.isKeyPressed(GLFW_KEY_W)) {
			cameraInc.z += -1;
		}
		if (window.isKeyPressed(GLFW_KEY_S)) {
			cameraInc.z += 1;
		}
		
		if (window.isKeyPressed(GLFW_KEY_A)) {
			cameraInc.x += -1;
		}
		if (window.isKeyPressed(GLFW_KEY_D)) {
			cameraInc.x += 1;
		}
		
		if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
			cameraInc.y += -1;
		}
		if (window.isKeyPressed(GLFW_KEY_SPACE)) {
			cameraInc.y += 1;
		}
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		// Update camera position
		camera.translateRelative(
				cameraInc.x * CAMERA_POS_STEP,
				cameraInc.y * CAMERA_POS_STEP,
				cameraInc.z * CAMERA_POS_STEP);
		
		// Update camera based on mouse
		if (mouseInput.isLeftButtonPressed()) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.rotate(
					rotVec.x * MOUSE_SENSITIVITY,
					rotVec.y * MOUSE_SENSITIVITY,
					0);
		}
	}

	@Override
	public void render(Window window) {
		window.setClearColor(color, color, color, 1.0f);
		renderer.render(window, gameItem, camera);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		mesh.cleanup();
	}

}
