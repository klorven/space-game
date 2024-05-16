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
//Test
    OrthographicCamera camera;
    TextureRegion shipTex;
    Color bgColor;

    float MAX_SPEED = 500;
    Vector2 shipPos;
    Vector2 shipVel;
    float STEERING_FACTOR = 5;

    public SpaceScreen(MyGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgColor = Color.valueOf("1C3D6E");
        shipTex = new TextureRegion(new Texture(Gdx.files.internal("ship_L.png")));
        shipPos = new Vector2();
        shipVel = new Vector2();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        camera.update();

        Vector2 dir = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dir.add(0, 1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            dir.add(0, -1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dir.add(-1, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dir.add(1, 0);
        }

        dir.nor();

        Vector2 desiredVelocity = new Vector2();
        desiredVelocity.set(dir);
        desiredVelocity.scl(MAX_SPEED);

        Vector2 steeringVector = new Vector2();
        steeringVector.set(desiredVelocity).sub(shipVel);
        steeringVector.scl(Gdx.graphics.getDeltaTime());
        steeringVector.scl(STEERING_FACTOR);
        shipVel.add(steeringVector);

        Vector2 shipVelDelta = new Vector2();
        shipVelDelta.set(shipVel).scl(Gdx.graphics.getDeltaTime());

        float rotationRad = MathUtils.atan2(shipVelDelta.y, shipVelDelta.x); // rad
        shipPos.add(shipVelDelta);

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
