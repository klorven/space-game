package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Iterator;

public class SpaceScreen implements Screen {
    private MyGame game;
    private OrthographicCamera camera;
    private Ship ship;
    private Projectile projectile;
    private TextureRegion meteorTexture; // Texture for meteors
    private FireParticle fireParticle;
    private TextureRegion projectileTexture; // Texture for projectiles
    private Color bgColor;

    private Array<Meteor> meteors; // List to keep track of meteors
    private float meteorSpawnTimer; // Timer to control meteor spawning
    private final int maxMeteors = 40; // Maximum number of meteors

    public SpaceScreen(MyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgColor = Color.valueOf("1C3D6E");

        ship = new Ship(new TextureRegion(new Texture(Gdx.files.internal("ship_L.png"))), 5f, 500f);

        // Meteor texture and array initialization
        meteorTexture = new TextureRegion(new Texture(Gdx.files.internal("meteor_squareDetailedLarge.png")));
        meteors = new Array<>();

        fireParticle = new FireParticle(ship);

        projectileTexture = new TextureRegion(new Texture(Gdx.files.internal("star_tiny.png")));

        meteorSpawnTimer = 0f; // Initialize spawn timer
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        camera.update();
        camera.position.x = ship.getPosition().x;
        camera.position.y = ship.getPosition().y;

        ship.update();

        // Check for shooting input
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE ) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            ship.shoot(projectileTexture, 600f);
        }

        ScreenUtils.clear(bgColor);
        fireParticle.update();

        // Spawn meteors at regular intervals
        meteorSpawnTimer += delta;
        if (meteorSpawnTimer >= 1f) { // Spawn meteor every 1 second
            spawnMeteor();
            meteorSpawnTimer = 0f;
        }

        // Check for collisions and update meteors
        Iterator<Meteor> meteorIterator = meteors.iterator();
        while (meteorIterator.hasNext()) {
            Meteor meteor = meteorIterator.next();

            // Remove meteor if it collides with the ship
            if (ship.getHitbox().overlaps(meteor.getHitbox())) {
                meteor.destroy();
                meteorIterator.remove();
                continue;
            }

            // Update meteor and remove if destroyed
            if (meteor.isDestroyed()) {
                meteorIterator.remove();
            }
        }

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        // Draw meteors
        for (Meteor meteor : meteors) {
            game.batch.draw(meteor.getTexture(), meteor.getHitbox().x, meteor.getHitbox().y);
        }

        // Draw the ship
        ship.draw(game.batch);

        // Draw projectiles
        Iterator<Projectile> iterator = ship.getProjectiles().iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            game.batch.draw(
                    projectile.getTexture(),
                    projectile.getPosition().x, projectile.getPosition().y,
                    projectile.getTexture().getRegionWidth() / 2f, projectile.getTexture().getRegionHeight() / 2f,
                    projectile.getTexture().getRegionWidth(), projectile.getTexture().getRegionHeight(),
                    1.0f, 1.0f,
                    MathUtils.radiansToDegrees * ship.getRotationRad()
            );
        }

        fireParticle.draw(game.batch);
        game.batch.end();
    }

    // Method to spawn meteors
    private void spawnMeteor() {
        if (meteors.size >= maxMeteors) {
            // Remove the oldest meteor if the limit is reached
            meteors.removeIndex(0);
        }

        // Random spawn position around the ship within a 5000x5000 square
        float spawnX = ship.getPosition().x + MathUtils.random(-2500, 2500);
        float spawnY = ship.getPosition().y + MathUtils.random(-2500, 2500);

        // Create and add a new meteor
        Meteor newMeteor = new Meteor(meteorTexture, spawnX, spawnY);
        meteors.add(newMeteor);
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

    }

}
