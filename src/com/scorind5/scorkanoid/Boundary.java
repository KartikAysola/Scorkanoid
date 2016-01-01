package com.scorind5.scorkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Boundary {

	static final float TOP = 2, RIGHT = 1, LEFT = 1;
	Line top, right, left;

	public Boundary(PhysicsSystem system) {
		// setting the boundaries here. Hard coding. Check if it can be
		// variabalized.

		top = new Line(system, new Texture(
				Gdx.files.internal("horizontal_line.png")), 0,
				Scorkanoid.SCREEN_HEIGHT - TOP, Scorkanoid.SCREEN_WIDTH, TOP);
		right = new Line(system, new Texture(
				Gdx.files.internal("vertical_right.png")),
				Scorkanoid.SCREEN_WIDTH - RIGHT, -1, RIGHT,
				Scorkanoid.SCREEN_HEIGHT);

		left = new Line(system, new Texture(
				Gdx.files.internal("vertical_left.png")), 0, -1, LEFT,
				Scorkanoid.SCREEN_HEIGHT);
	}

	public void update(float delta) {
		top.update(delta);
		left.update(delta);
		right.update(delta);
	}

	public void draw(SpriteBatch spriteBatch) {
		top.draw(spriteBatch);
		left.draw(spriteBatch);
		right.draw(spriteBatch);
	}

}

class Line {

	float x, y, width, height;
	Texture texture;

	Line(PhysicsSystem system, Texture texture, float x, float y, float width,
			float height) {
		//super(system, x, y, width, height);
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update(float delta) {

	}

	public void draw(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, x, y, width, height);
	}

}