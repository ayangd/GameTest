package com.cangcui.gametest.game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Matrix4f;

import com.cangcui.gametest.engine.GameItem;
import com.cangcui.gametest.engine.Utils;
import com.cangcui.gametest.engine.Window;
import com.cangcui.gametest.engine.graph.ShaderProgram;
import com.cangcui.gametest.engine.graph.Transformation;

public class Renderer {
	
	private static final float FOV = (float) Math.toRadians(60.0);
	private static final float near = 0.01f;
	private static final float far = 1000.0f;
//	private Matrix4f projectionMatrix;
	private final Transformation transformation;
	
	private ShaderProgram shader;
	
	public Renderer() {
		transformation = new Transformation();
	}
	
	public void init(Window window) throws Exception {
		shader = new ShaderProgram();
		shader.createVertexShader(Utils.loadResource("/vertex.vs"));
		shader.createFragmentShader(Utils.loadResource("/fragment.fs"));
		shader.link();
		
		shader.createUniform("projectionMatrix");
		shader.createUniform("worldMatrix");
		shader.createUniform("texture_sampler");
		
		window.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Window window, GameItem[] gameItems) {
	    clear();

	    if ( window.isResized() ) {
	        glViewport(0, 0, window.getWidth(), window.getHeight());
	        window.setResized(false);
	    }

	    shader.bind();
	    
	    Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), near, far);
	    shader.setUniform("projectionMatrix", projectionMatrix);
	    shader.setUniform("texture_sampler", 0);
	    
	    for (GameItem item: gameItems) {
	    	Matrix4f worldMatrix =
	    			transformation.getWorldMatrix(
	    					item.getPosition(),
	    					item.getRotation(),
	    					item.getScale());
	    	shader.setUniform("worldMatrix", worldMatrix);
	    	item.getMesh().render();
	    }

	    shader.unbind();
	}
	
	public void cleanup() {
	    if (shader != null) {
	        shader.cleanup();
	    }
	}

}
