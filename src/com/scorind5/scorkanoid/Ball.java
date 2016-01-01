package com.scorind5.scorkanoid;

import java.awt.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Ball extends CircleShape {

	static Texture texture;
	static float WIDTH = 6;
	static float HEIGHT = 15;

	// static final int ballXPosition = 18; //Initial ball position; Must be
	// static final int ballYPosition = 20; // taken care in Scorkanoid.java

	float x, y;
	float ballXSpeed, ballYSpeed;
	float curXSpeed, curYSpeed;
	Rectangle bounds;

	private BodyDef bodyDef;
	private FixtureDef fixtureDef;

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Ball(World world, PhysicsSystem system, float x, float y) {
		this.x = x;
		this.y = y;
		ballXSpeed = 0f;
		ballYSpeed = 300f;
		curXSpeed = 1;
		curYSpeed = 1;
		bounds = new Rectangle((int) x, (int) y, (int) WIDTH, (int) HEIGHT);

		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();

		// body definition
		bodyDef.type = BodyType.DynamicBody;

		// paddle shape

		// this.setPosition(new Vector2(2.75f, -8.75f));
		this.setRadius(15f);

		// fixture definition
		fixtureDef.shape = this;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 1;
		fixtureDef.density = 1;
		fixtureDef.isSensor = true;

		world.createBody(bodyDef).createFixture(fixtureDef);
	}

	public static void loadTexture(Texture texture_) {
		texture = texture_;
	}

	public void draw(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, x, y, WIDTH, HEIGHT);
	}

	public void update(float delta) {
		y += curYSpeed * ballYSpeed * delta;
		x += curXSpeed * ballXSpeed * delta;

		if (x <= 0 + Boundary.LEFT) // if the ball hits left wall
			curXSpeed = Math.abs(curXSpeed);
		if (x >= Scorkanoid.SCREEN_WIDTH - WIDTH - Boundary.RIGHT)
			curXSpeed = -Math.abs(curXSpeed);

		if (y >= Scorkanoid.SCREEN_HEIGHT - HEIGHT - Boundary.TOP) // if the
																	// ball hits
																	// top wall
			curYSpeed = -Math.abs(curYSpeed);

		bounds.setBounds((int) x, (int) y, (int) WIDTH, (int) HEIGHT);

		// game over when outside room
		if (y < 0) {
			System.out.println("Game over");
			System.exit(0);
		}
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

	public void reverseVerticalDirection() {
		curYSpeed *= -1;
	}

	public void reverseHorizontalDirection() {
		curXSpeed *= -1;
	}

	public void goUp() {
		curYSpeed = Math.abs(curYSpeed);
	}

	public void goDown() {
		curYSpeed = -Math.abs(curYSpeed);
	}

	public void goLeft() {
		curXSpeed = -Math.abs(curXSpeed);
	}

	public void goRight() {
		curXSpeed = Math.abs(curXSpeed);
	}

	public void changeBallAngle(float paddlePos) {
		float ballMid = x + WIDTH / 2;
		float factor = (ballMid - (paddlePos + BasePaddle.WIDTH / 2))
				/ (BasePaddle.WIDTH / 2);
		curXSpeed = factor * 2;
	}

}
