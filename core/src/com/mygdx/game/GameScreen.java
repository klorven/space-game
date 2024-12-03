package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    MyGame game;

    OrthographicCamera camera;
    Texture dropPng;
    Texture bucketPng;
    Texture backgroundPng;

    Rectangle bucketRect;
    Array<Rectangle> dropletRects;
    Array<Float> dropletDelays;

    float bucketSpeed = 600; // px per second
    float prevTime;
    int score = 0;

    float spawnRate = 1.5f;

    public GameScreen(MyGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        bucketPng = new Texture("bucket.png");
        dropPng = new Texture("drop.png");
        backgroundPng = new Texture("Background1.png");

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
    public void show() {
    }

    @Override
    public void render(float v) {
        // draw
        camera.update();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setScreen(game.mainMenuScreen);
        }

        SpriteBatch batch = game.batch;
        ScreenUtils.clear(0.1f, 0.1f, 0.5f, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundPng, 0, -200);
        batch.draw(bucketPng, bucketRect.x, bucketRect.y);

        for (Rectangle dropletRect : dropletRects) {
            batch.draw(dropPng, dropletRect.x, dropletRect.y);
        }

        BitmapFont font = game.font;
        font.draw(
                batch,
                "Score: " + score,
                Gdx.graphics.getWidth() / 50.0f, Gdx.graphics.getHeight() - 20
        );
        batch.end();

        // game logic
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);
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
        bucketPng.dispose();
        dropPng.dispose();
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
}
