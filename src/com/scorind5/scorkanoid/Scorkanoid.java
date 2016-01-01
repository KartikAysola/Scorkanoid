package com.scorind5.scorkanoid;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class Scorkanoid extends Game implements ApplicationListener {

	SpriteBatch spriteBatch;
	Texture baseBrickTexture;
	BasePaddle paddle;
	TopBrick[] topBrickPink, topBrickGreen, topBrickYellow, topBrickRed,
			topBrickBlue;
	PhysicsSystem physicsSystem;
	Boundary boundary;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	
	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	
	static Ball ball;

	static final float SCREEN_WIDTH = 320;
	static final float SCREEN_HEIGHT = 480;

	static final float BALL_START_X = 17;
	static final float BALL_START_Y = 24;

	long frameTime = System.nanoTime();
	long lastFrameTime = System.nanoTime();
	float delta;
	int brickCount = 12;
	static boolean start = false;
	Random random;

	@Override
	public void create() {	
		setScreen(new Box2DTest());
		/*
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();	
		
		spriteBatch = new SpriteBatch();
		physicsSystem = new PhysicsSystem();
		boundary = new Boundary(physicsSystem);
		baseBrickTexture = new Texture(Gdx.files.internal("base.png"));
		ball = new Ball(world, physicsSystem, BALL_START_X, BALL_START_Y);
		topBrickPink = new TopBrick[brickCount];
		topBrickGreen = new TopBrick[brickCount];
		topBrickYellow = new TopBrick[brickCount];
		topBrickRed = new TopBrick[brickCount];
		topBrickBlue = new TopBrick[brickCount];
		random = new Random();

		for (int i = 0; i < brickCount; i++) {
			if (random.nextFloat() * 10 < 8) {
				topBrickPink[i] = new TopBrick(physicsSystem, Boundary.LEFT + i
						* (TopBrick.WIDTH), SCREEN_HEIGHT - 30, 0); // SCREEN_HEIGHT-30
																	// -> to set
																	// a gap
																	// between
																	// screen
																	// top and
																	// the
																	// starting
				topBrickPink[i].loadTexture(new Texture(Gdx.files // of the
																	// bricks.
						.internal("rose_brick.png")));
			} else {
				topBrickPink[i] = new TopBrick(physicsSystem, Boundary.LEFT + i
						* (TopBrick.WIDTH), SCREEN_HEIGHT - 30, 1);
				topBrickPink[i].loadTexture(new Texture(Gdx.files // screen top
																	// and the
																	// starting
																	// of the
																	// bricks.
						.internal("white_brick.png")));
			}

			if (random.nextFloat() * 10 < 8) {
				topBrickGreen[i] = new TopBrick(physicsSystem, Boundary.LEFT
						+ i * (TopBrick.WIDTH), SCREEN_HEIGHT - 30 - 17, 0);
				topBrickGreen[i].loadTexture(new Texture(Gdx.files
						.internal("green_brick.png")));
			} else {
				topBrickGreen[i] = new TopBrick(physicsSystem, Boundary.LEFT
						+ i * (TopBrick.WIDTH), SCREEN_HEIGHT - 30 - 17, 1);
				topBrickGreen[i].loadTexture(new Texture(Gdx.files
						.internal("white_brick.png")));
			}

			if (random.nextFloat() * 10 < 8) {
				topBrickYellow[i] = new TopBrick(physicsSystem, Boundary.LEFT
						+ i * (TopBrick.WIDTH), SCREEN_HEIGHT - 30 - 17 * 2, 0);
				topBrickYellow[i].loadTexture(new Texture(Gdx.files
						.internal("yellow_brick.png")));
			} else {
				topBrickYellow[i] = new TopBrick(physicsSystem, Boundary.LEFT
						+ i * (TopBrick.WIDTH), SCREEN_HEIGHT - 30 - 17 * 2, 1);
				topBrickYellow[i].loadTexture(new Texture(Gdx.files
						.internal("white_brick.png")));
			}

			if (random.nextFloat() * 10 < 8) {
				topBrickRed[i] = new TopBrick(physicsSystem, Boundary.LEFT + i
						* (TopBrick.WIDTH), SCREEN_HEIGHT - 30 - 17 * 3, 0);
				topBrickRed[i].loadTexture(new Texture(Gdx.files
						.internal("red_brick.png")));
			} else {
				topBrickRed[i] = new TopBrick(physicsSystem, Boundary.LEFT + i
						* (TopBrick.WIDTH), SCREEN_HEIGHT - 30 - 17 * 3, 1);
				topBrickRed[i].loadTexture(new Texture(Gdx.files
						.internal("white_brick.png")));
			}

			if (random.nextFloat() * 10 < 8) {
				topBrickBlue[i] = new TopBrick(physicsSystem, Boundary.LEFT + i
						* (TopBrick.WIDTH), SCREEN_HEIGHT - 30 - 17 * 4, 0);
				topBrickBlue[i].loadTexture(new Texture(Gdx.files
						.internal("blue_brick.png")));
			} else {
				topBrickBlue[i] = new TopBrick(physicsSystem, Boundary.LEFT + i
						* (TopBrick.WIDTH), SCREEN_HEIGHT - 30 - 17 * 4, 1);
				topBrickBlue[i].loadTexture(new Texture(Gdx.files
						.internal("white_brick.png")));
			}
		}
		paddle = new BasePaddle(world, physicsSystem, baseBrickTexture);
		Ball.loadTexture(new Texture(Gdx.files.internal("ball.png")));
		*/
	}

	@Override
	public void dispose() {
		super.dispose();
		//world.dispose();
		//debugRenderer.dispose();
		// batch.dispose();
		// texture.dispose();
	}

	@Override
	public void render() {
		super.render();
		
		/*
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				System.out.println("Contact");
				
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}		    
		});
		debugRenderer.render(world, camera.combined);
		
		frameTime = System.nanoTime();
		delta = (frameTime - lastFrameTime) / 1000000000.0f;
		lastFrameTime = System.nanoTime();

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		boundary.update(delta);
		paddle.update(delta);
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			start = true;
		}
		if (start) {
			physicsSystem.update(delta, ball, paddle);
			ball.update(delta);
		}
		for (int i = 0; i < brickCount; i++) {
			topBrickPink[i].update(delta);
			topBrickGreen[i].update(delta);
			topBrickYellow[i].update(delta);
			topBrickRed[i].update(delta);
			topBrickBlue[i].update(delta);
		}

		spriteBatch.begin();

		boundary.draw(spriteBatch);
		paddle.draw(spriteBatch);
		ball.draw(spriteBatch);
		for (int i = 0; i < brickCount; i++) {
			topBrickPink[i].draw(spriteBatch);
			topBrickGreen[i].draw(spriteBatch);
			topBrickYellow[i].draw(spriteBatch);
			topBrickRed[i].draw(spriteBatch);
			topBrickBlue[i].draw(spriteBatch);
		}

		spriteBatch.end();
		*/
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		/*
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0); // default
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		*/
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public static Ball getBallInstance() {
		return ball;
	}

	public static boolean hasStarted() {
		return start;
	}
}
