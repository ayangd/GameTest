package com.cangcui.gametest.game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.cangcui.gametest.engine.Utils;
import com.cangcui.gametest.engine.Window;
import com.cangcui.gametest.engine.graph.Mesh;
import com.cangcui.gametest.engine.graph.ShaderProgram;

public class Renderer {
	
	private ShaderProgram shader;
	
	public void init() throws Exception {
		shader = new ShaderProgram();
		shader.createVertexShader(Utils.loadResource("/vertex.vs"));
		shader.createFragmentShader(Utils.loadResource("/fragment.fs"));
		shader.link();
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Window window, Mesh mesh) {
	    clear();

	    if ( window.isResized() ) {
	        glViewport(0, 0, window.getWidth(), window.getHeight());
	        window.setResized(false);
	    }

	    shader.bind();

	    // Bind to the VAO
	    glBindVertexArray(mesh.getVaoId());
	    glEnableVertexAttribArray(0);
	    glEnableVertexAttribArray(1);

	    // Draw the vertices
	    glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
	    glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

	    // Restore state
	    glDisableVertexAttribArray(0);
	    glBindVertexArray(0);

	    shader.unbind();
	}
	
	public void cleanup() {
	    if (shader != null) {
	        shader.cleanup();
	    }
	}

}
