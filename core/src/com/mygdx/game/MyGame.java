package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;


public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	BitmapFont font;

	Texture dropPng;
	Texture bucketPng;

	Rectangle bucketRect;
	Array<Rectangle> dropletRects;
	Array<Float> dropletDelays;

	float bucketSpeed = 600; // px per second
	float prevTime;
	int score = 0;

	float spawnRate = 1.5f;

	/*
		1. Get rid off the droplets when they leave screen.
		2. Make it more interesting.
	 */

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		font = new BitmapFont();

		bucketPng = new Texture("bucket.png");
		dropPng = new Texture("drop.png");

		bucketRect = new Rectangle(
			(Gdx.graphics.getWidth() / 2.f) - (bucketPng.getWidth() / 2.0f), 20,
			bucketPng.getWidth(), bucketPng.getHeight()
		);

		prevTime = TimeUtils.nanoTime();

		dropletRects = new Array<>();
		dropletDelays = new Array<>();
		for (int i = 0; i < 8; i++) {
			createDroplet();
		}
	}

	@Override
	public void render () {
		// draw
		camera.update();

		ScreenUtils.clear(0.1f, 0.1f, 0.5f, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketPng, bucketRect.x, bucketRect.y);
		for (Rectangle dropletRect : dropletRects) {
			batch.draw(dropPng, dropletRect.x, dropletRect.y);
		}

		font.draw(
				batch,
				"Score: " + score,
				Gdx.graphics.getWidth() / 50.0f, Gdx.graphics.getHeight() - 20
		);
		batch.end();

		// game logic
		boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		if (left) {
			bucketRect.x -= (bucketSpeed * Gdx.graphics.getDeltaTime()); // how much time it took to render last frame
		} else if (right) {
			bucketRect.x += (bucketSpeed * Gdx.graphics.getDeltaTime());
		}

		for (int i = 0; i < dropletRects.size; i++) {
			float delay = dropletDelays.get(i);
			if (delay < 0) {
				Rectangle dropletRect = dropletRects.get(i);
				dropletRect.y -= 100 * Gdx.graphics.getDeltaTime();

				if (dropletRect.overlaps(bucketRect)) {
					dropletRects.removeValue(dropletRect, true);
					dropletDelays.removeIndex(i);
					score++;
				}
			} else {
				delay -= Gdx.graphics.getDeltaTime(); // seconds 0.016 0.0123
				dropletDelays.set(i, delay);
			}
		}

		if (spawnRate < 0) {
			createDroplet();
			spawnRate = 1.5f;
		} else {
			spawnRate -= Gdx.graphics.getDeltaTime();
		}
	}

	private void createDroplet () {
		float delay = MathUtils.random(0.5f, 2.f);
		dropletDelays.add(delay);
		Rectangle dropletRect = new Rectangle(
				MathUtils.random(0.0f, 800.0f - dropPng.getWidth()),
				Gdx.graphics.getHeight() + MathUtils.random(20, 200),
				dropPng.getWidth(),
				dropPng.getHeight()
		);
		dropletRects.add(dropletRect);
	}

	@Override
	public void dispose () {
		batch.dispose();
		bucketPng.dispose();
		dropPng.dispose();
	}
}
