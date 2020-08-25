package com.cangcui.gametest.engine.graph;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private final int vaoId;

    private final int posVboId;
    private final int texVboId;
    private final int idxVboId;

    private final int vertexCount;
    
    private Texture texture;

    public Mesh(float[] positions, float[] texCoord, int[] indices, Texture texture) {
    	this.texture = texture;
        FloatBuffer verticesBuffer = null;
        FloatBuffer texCoordBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
            vertexCount = indices.length;
            verticesBuffer.put(positions).flip();
            
            texCoordBuffer = MemoryUtil.memAllocFloat(texCoord.length);
            texCoordBuffer.put(texCoord).flip();
            
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            posVboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, posVboId);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            
            texVboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, texVboId);
            glBufferData(GL_ARRAY_BUFFER, texCoordBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            
            idxVboId = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glBindVertexArray(0);         
        } finally {
            if (verticesBuffer != null)
                MemoryUtil.memFree(verticesBuffer);
            if (texCoordBuffer != null)
            	MemoryUtil.memFree(texCoordBuffer);
            if (indicesBuffer != null)
            	MemoryUtil.memFree(indicesBuffer);
        }
    }
    
    public void render() {
    	// Draw the mesh
    	glBindVertexArray(getVaoId());
    	glEnableVertexAttribArray(0);
    	glEnableVertexAttribArray(1);
    	
    	glActiveTexture(GL_TEXTURE0);
    	texture.bind();
    	
    	glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
    	
    	// Restore state
    	glDisableVertexAttribArray(0);
    	glDisableVertexAttribArray(1);
    	glBindVertexArray(0);
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanup() {
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(posVboId);
        glDeleteBuffers(texVboId);
        glDeleteBuffers(idxVboId);
        
        // Delete the texture
        texture.cleanup();

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}