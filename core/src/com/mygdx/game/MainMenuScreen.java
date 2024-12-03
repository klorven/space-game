package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    MyGame game;

    public MainMenuScreen(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    Texture MenuBackground;

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.1f, 0.1f, 0.5f, 1);


        MenuBackground = new Texture("MainMenuBackground.png");

        SpriteBatch batch = game.batch;
        BitmapFont font = game.font;
        batch.begin();
        batch.draw(MenuBackground, 0, 0);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            game.setScreen(game.gameScreen);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            game.setScreen(game.spaceScreen);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            game.setScreen(game.chickenMenu);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
