package com.cangcui.gametest.engine.graph;

import org.joml.Vector3f;

public class Camera {
	
	private final Vector3f position;
	private final Vector3f rotation;
	
	public Camera() {
		position = new Vector3f();
		rotation = new Vector3f();
	}
	
	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position.set(position);
	}
	
	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
	}
	
	public void translate(float directionX, float directionY, float directionZ) {
		position.add(directionX, directionY, directionZ);
	}
	
	public void translateRelative(float offsetX, float offsetY, float offsetZ) {
		if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation.set(rotation);
	}
	
	public void setRotation(float yaw, float pitch, float roll) {
		rotation.set(yaw, pitch, roll);
	}
	
	public void rotate(float directionYaw, float directionPitch, float directionRoll) {
		rotation.add(directionYaw, directionPitch, directionRoll);
	}
}
