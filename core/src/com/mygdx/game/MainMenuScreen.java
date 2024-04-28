package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.1f, 0.1f, 0.5f, 1);

        SpriteBatch batch = game.batch;
        BitmapFont font = game.font;
        batch.begin();
        font.draw(batch, "Press anywhere to start!!!", Gdx.graphics.getWidth() / 2.f - 65, Gdx.graphics.getHeight() / 2.f);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(game.spaceScreen);
        }
    }

    @Override
    public void resize(int i, int i1) {

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
