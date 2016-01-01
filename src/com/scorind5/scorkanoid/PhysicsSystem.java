package com.scorind5.scorkanoid;

import java.awt.Rectangle;
import java.util.ArrayList;

public class PhysicsSystem {

	private static ArrayList<Brick> bricks;

	public PhysicsSystem() {
		bricks = new ArrayList<Brick>();
	}

	public static void addBody(Brick brick) {
		bricks.add(brick);
	}

	public static void removeBody(Brick brick) {
		bricks.remove(brick);
	}

	public void update(float delta, Ball ball, BasePaddle paddle) {
		brickCollisionCheck(ball);
		paddleCollisionCheck(ball, paddle);
	}

	public void brickCollisionCheck(Ball ball) {
		for (Brick brick : bricks) {
			if (brick.isAlive()) {
				if (brick.getBounds().intersects(ball.getBounds())) {
					Rectangle intersection = ball.getBounds().intersection(
							brick.getBounds());					
					if (intersection.width >= intersection.height) {
						if(ball.getY() > brick.getY())
							ball.goUp();
						else
							ball.goDown();
					} else {
						if(ball.getX() > brick.getX())
							ball.goRight();
						else
							ball.goLeft();
					}											
					brick.onCollision();
				}
			}
		}
	}

	public void paddleCollisionCheck(Ball ball, BasePaddle paddle) {
		if (ball.getBounds().intersects(paddle.getBounds())) {
			Rectangle intersection = ball.getBounds().intersection(
					paddle.getBounds());
			System.out.println(intersection.toString());
			if (intersection.width >= intersection.height
					&& ball.getY() > paddle.getY()) {
				ball.goUp();
				ball.changeBallAngle(paddle.getX());
			} else {
				if(ball.getX() > paddle.getX())
					ball.goRight();
				else
					ball.goLeft();
			}
		}
	}
}