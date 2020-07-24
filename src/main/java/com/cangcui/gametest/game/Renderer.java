package com.cangcui.gametest.game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.lang.management.MemoryUsage;
import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

import com.cangcui.gametest.engine.Utils;
import com.cangcui.gametest.engine.Window;
import com.cangcui.gametest.engine.graph.ShaderProgram;

public class Renderer {
	
	private ShaderProgram shader;
	private int vaoId;
	private int vboId;
	
	public void init() throws Exception {
		shader = new ShaderProgram();
		shader.createVertexShader(Utils.loadResource("/vertex.vs"));
		shader.createFragmentShader(Utils.loadResource("/fragment.fs"));
		shader.link();
		
		float[] vertices = new float[]{
		     0.0f,  0.5f, 0.0f,
		    -0.5f, -0.5f, 0.0f,
		     0.5f, -0.5f, 0.0f
		};
		
		FloatBuffer verticesBuffer = null;
		try {
			verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
			verticesBuffer.put(vertices).flip();
			
			// Create VAO and bind to it
			vaoId = glGenVertexArrays();
			glBindVertexArray(vaoId);
			
			// Create Vertex VBO and bind to it
			vboId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
			// Enable location 0
			glEnableVertexAttribArray(0);
			// Define the structure data
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			
			// Unbind the VBO
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			// Unbind the VAO
			glBindVertexArray(0);
		} finally {
			if (vertices != null)
				MemoryUtil.memFree(verticesBuffer);
		}
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Window window) {
	    clear();

	    if ( window.isResized() ) {
	        glViewport(0, 0, window.getWidth(), window.getHeight());
	        window.setResized(false);
	    }

	    shader.bind();

	    // Bind to the VAO
	    glBindVertexArray(vaoId);
	    glEnableVertexAttribArray(0);

	    // Draw the vertices
	    glDrawArrays(GL_TRIANGLES, 0, 3);

	    // Restore state
	    glDisableVertexAttribArray(0);
	    glBindVertexArray(0);

	    shader.unbind();
	}
	
	public void cleanup() {
	    if (shader != null) {
	        shader.cleanup();
	    }

	    glDisableVertexAttribArray(0);

	    // Delete the VBO
	    glBindBuffer(GL_ARRAY_BUFFER, 0);
	    glDeleteBuffers(vboId);

	    // Delete the VAO
	    glBindVertexArray(0);
	    glDeleteVertexArrays(vaoId);
	}

}
