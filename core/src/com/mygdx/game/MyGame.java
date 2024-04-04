package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture bucket;

	float bucketX = 0;
	float bucketSpeed = 100; // px per second

	float prevTime;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		bucket = new Texture("bucket.png");

		bucketX = (Gdx.graphics.getWidth() / 2.f) - (bucket.getWidth() / 2.0f);
		prevTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		if (left) {
			bucketX -= bucketSpeed * Gdx.graphics.getDeltaTime();
		} else if (right) {
			bucketX += bucketSpeed * Gdx.graphics.getDeltaTime();
		}

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(bucket, bucketX, 20);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
