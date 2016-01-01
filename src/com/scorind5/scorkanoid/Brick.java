package com.scorind5.scorkanoid;

import java.awt.Rectangle;

abstract class Brick {

	float x, y;
	// float width, height;
	int type = 0; // 0 = Normal, 1 = Solid, 2 = WithPower
	Rectangle bounds;
	boolean alive;

	static float WIDTH = 26.53f;
	static float HEIGHT = 16;

	public Brick(PhysicsSystem system, float x, float y, int type) {
		this.x = x;
		this.y = y;
		bounds = new Rectangle((int) x, (int) y, (int) WIDTH, (int) HEIGHT);
		PhysicsSystem.addBody(this);
		alive = true;
		this.type = type;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	abstract void onCollision();

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void physicsUpdate(float delta) {

	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getType() {
		return type;
	}

}