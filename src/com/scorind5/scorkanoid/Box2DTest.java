package com.scorind5.scorkanoid;

import javax.swing.Spring;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Box2DTest implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;

	private CircleShape ballShape;
	private ChainShape groundShape;
	private PolygonShape boxShape;

	private Body box;
	private float speed = 500;
	private Vector2 movement = new Vector2();
	private Sprite boxSprite;

	private Array<Body> tempBodies = new Array<Body>();

	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		box.applyForceToCenter(movement, true);

		camera.position.set(box.getPosition().x, box.getPosition().y, 0);
		camera.update();

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		world.getBodies(tempBodies);
		for (Body body : tempBodies) {
			if (body.getUserData() != null
					&& body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(
						body.getPosition().x - sprite.getWidth() / 2,
						body.getPosition().y - sprite.getHeight() / 2 + 0.5f);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(spriteBatch);
			}
		}
		spriteBatch.end();

		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 20;
		camera.viewportHeight = height / 20;
		camera.update();

	}

	@Override
	public void show() {
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();

		Gdx.input.setInputProcessor(new InputController() {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Keys.ESCAPE:
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new Box2DTest());
					break;
				case Keys.W:
					movement.y = speed;
					break;
				case Keys.S:
					movement.y = -speed;
					break;
				case Keys.A:
					movement.x = -speed;
					break;
				case Keys.D:
					movement.x = speed;
					break;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				switch (keycode) {
				case Keys.W:
				case Keys.S:
					movement.y = 0;
					break;
				case Keys.A:
				case Keys.D:
					movement.x = 0;
					break;
				}
				return true;
			}

			@Override
			public boolean scrolled(int amount) {
				camera.zoom += amount / 10f;
				return true;
			}
		});

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		// Box
		// body definition
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(2.25f, 10);

		// box shape
		boxShape = new PolygonShape();
		boxShape.setAsBox(1, 0.5f);

		// fixture definition
		fixtureDef.shape = boxShape;
		fixtureDef.friction = 0.75f;
		fixtureDef.restitution = 0.1f;
		fixtureDef.density = 5;

		box = world.createBody(bodyDef);
		box.createFixture(fixtureDef);

		boxSprite = new Sprite(new Texture(
				Gdx.files.internal("treesprite1.png")));
		boxSprite.setSize(2, 2);
		boxSprite
				.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
		box.setUserData(boxSprite);

		// Ball
		// ball shape
		ballShape = new CircleShape();
		ballShape.setPosition(new Vector2(0, 1));
		ballShape.setRadius(0.5f);

		// fixture definition
		fixtureDef.shape = ballShape;
		fixtureDef.density = 2.5f;
		fixtureDef.friction = 0.25f;
		fixtureDef.restitution = 0.75f;

		box.createFixture(fixtureDef);

		// Ground
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);

		// ground shape
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(-10f, 0),
				new Vector2(10f, 0) });

		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0f;

		world.createBody(bodyDef).createFixture(fixtureDef);

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		ballShape.dispose();
		groundShape.dispose();
		boxShape.dispose();
		boxSprite.getTexture().dispose();
	}

}
