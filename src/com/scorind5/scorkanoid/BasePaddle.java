package com.scorind5.scorkanoid;

import java.awt.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class BasePaddle {

	Texture texture;
	static final float WIDTH = 32;
	static final float HEIGHT = 8;
	static final float X_VELOCITY = 1f;
	static float paddleSpeed = 250f;
	Rectangle bounds;

	private BodyDef bodyDef;
	private FixtureDef fixtureDef;
	private PolygonShape paddleShape;

	float x = 5, y = 15;

	public BasePaddle(World world, PhysicsSystem physicsSystem, Texture t) {
		// super(physicsSystem, 5, 15, WIDTH, HEIGHT);
		texture = t;
		bounds = new Rectangle((int) x, (int) y, (int) WIDTH, (int) HEIGHT);
		// type = 1;

		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();

		// body definition
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(2.25f, -10);

		// paddle shape
		paddleShape = new PolygonShape();
		paddleShape.setAsBox(2.5f, 0.5f);

		// fixture definition
		fixtureDef.shape = paddleShape;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;
		fixtureDef.density = 1;
		fixtureDef.isSensor = true;

		world.createBody(bodyDef).createFixture(fixtureDef);
	}

	private void screenBounds() {
		if (x < 0)
			x = 0;
		if (x > Scorkanoid.SCREEN_WIDTH - WIDTH)
			x = Scorkanoid.SCREEN_WIDTH - WIDTH;
	}

	private void move(float delta) {

		if (Gdx.input.isKeyPressed(Keys.A)) {
			x -= X_VELOCITY * paddleSpeed * delta;
			if (!Scorkanoid.hasStarted())
				Scorkanoid.getBallInstance().setX(x + Scorkanoid.BALL_START_X);
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			x += X_VELOCITY * paddleSpeed * delta;
			if (!Scorkanoid.hasStarted())
				Scorkanoid.getBallInstance().setX(x + Scorkanoid.BALL_START_X);
		}
	}

	public void update(float delta) {
		move(delta);
		screenBounds();
		bounds.setBounds((int) x, (int) y, (int) WIDTH, (int) HEIGHT);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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

	public void draw(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, x, y, WIDTH, HEIGHT);
	}

}
