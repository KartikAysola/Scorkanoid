package com.scorind5.scorkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TopBrick extends Brick {

	Texture texture;

	// float x = 0, y = 400; // must go into the base 'Brick' class.

	public TopBrick(PhysicsSystem system, float x, float y, int type) {
		super(system, x, y, type);
		type = 2;
	}

	public void loadTexture(Texture texture) {
		this.texture = texture;
	}

	@Override
	void onCollision() {
		if (type == 0 || type == 2)
			alive = false;

	}

	public void update(float delta) {
		// if (alive && health <= 0) {
		// particleSystem.burst(x, y, 50);
		// physicsSystem.removeBody(this);
		// alive = false;
		// }
	}

	public void draw(SpriteBatch spriteBatch) {
		if (alive)
			spriteBatch.draw(texture, x, y, WIDTH, HEIGHT);
	}

}
