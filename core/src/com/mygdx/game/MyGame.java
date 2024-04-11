package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;


public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;

	Texture dropPng;
	Texture bucketPng;

	float bucketX = 0;
	float bucketSpeed = 600; // px per second

	float prevTime;

	Array<Float> dropletPositions;
	float dropletY = 300;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		bucketPng = new Texture("bucket.png");
		dropPng = new Texture("drop.png");

		bucketX = (Gdx.graphics.getWidth() / 2.f) - (bucketPng.getWidth() / 2.0f);
		prevTime = TimeUtils.nanoTime();

		dropletPositions = new Array<>();
		for (int i = 0; i < 8; i++) {
			dropletPositions.add(MathUtils.random(0.0f, 800.0f - dropPng.getWidth()));
		}
	}

	@Override
	public void render () {
		// draw
		camera.update();

		ScreenUtils.clear(0.1f, 0.1f, 0.5f, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketPng, bucketX, 20);
		for (int i = 0; i < dropletPositions.size; i++) {
			batch.draw(dropPng, dropletPositions.get(i), dropletY);
		}
		batch.end();

		// game logic
		boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		if (left) {
			bucketX = bucketX - (bucketSpeed * Gdx.graphics.getDeltaTime()); // how much time it took to render last frame
		} else if (right) {
			bucketX = bucketX + (bucketSpeed * Gdx.graphics.getDeltaTime());
		}

		dropletY--;
	}

	@Override
	public void dispose () {
		batch.dispose();
		bucketPng.dispose();
		dropPng.dispose();
	}
}
