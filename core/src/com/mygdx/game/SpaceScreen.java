package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class SpaceScreen implements Screen {
    MyGame game;

    OrthographicCamera camera;
    TextureRegion shipTex;
    Color bgColor;

    float MAX_SPEED = 500;
    Vector2 shipPos;

    public SpaceScreen(MyGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgColor = Color.valueOf("1C3D6E");
        shipTex = new TextureRegion(new Texture(Gdx.files.internal("ship_L.png")));
        shipPos = new Vector2();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        camera.update();

        Vector2 velocity = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
           velocity.add(0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.add(0, -1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.add(-1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.add(1, 0);
        }

        velocity.scl(MAX_SPEED * Gdx.graphics.getDeltaTime());

        float rotationRad = MathUtils.atan2(velocity.y, velocity.x); // rad
        shipPos.add(velocity);

        ScreenUtils.clear(bgColor);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(
            shipTex,
            shipPos.x, shipPos.y,
            shipTex.getRegionWidth() / 2.f, shipTex.getRegionHeight() / 2.f,
            shipTex.getRegionWidth(), shipTex.getRegionHeight(),
            1.0f, 1.0f,
            MathUtils.radiansToDegrees * rotationRad
        );
        game.batch.end();
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
