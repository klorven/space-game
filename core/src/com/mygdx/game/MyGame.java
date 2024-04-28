package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game {
	SpriteBatch batch;
	BitmapFont font;
	GameScreen gameScreen;
	MainMenuScreen mainMenuScreen;
	SpaceScreen spaceScreen;

	@Override
	public void create () {
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		spaceScreen = new SpaceScreen(this);
		batch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}