package com.mygdx.game;

import com.badlogic.gdx.Gdx; // Import Gdx
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.List;

public class Ship {
    private TextureRegion texture;
    private Rectangle hitbox;
    private Vector2 position;
    private Vector2 velocity;
    private float steeringFactor;
    private float maxSpeed;
    private float rotation;
    private List<Projectile> projectiles;

    public Ship(TextureRegion texture, float steeringFactor, float maxSpeed) {
        this.texture = texture;
        this.steeringFactor = steeringFactor;
        this.maxSpeed = maxSpeed;
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.hitbox = new Rectangle(position.x, position.y, texture.getRegionWidth(), texture.getRegionHeight());
        this.projectiles = new ArrayList<>();
    }

    public void update() {
        // Update ship's movement logic...
        Vector2 dir = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            dir.add(0, 1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            dir.add(0, -1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            dir.add(-1, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            dir.add(1, 0);
        }

        dir.nor();

        Vector2 desiredVelocity = new Vector2();
        desiredVelocity.set(dir);
        desiredVelocity.scl(maxSpeed);

        Vector2 steeringVector = new Vector2();
        steeringVector.set(desiredVelocity).sub(velocity);
        steeringVector.scl(Gdx.graphics.getDeltaTime());
        steeringVector.scl(steeringFactor);
        velocity.add(steeringVector);

        Vector2 shipVelDelta = new Vector2();
        shipVelDelta.set(velocity).scl(Gdx.graphics.getDeltaTime());

        hitbox.setPosition(position.x, position.y);

        rotation = MathUtils.atan2(shipVelDelta.y, shipVelDelta.x); // rad
        position.add(shipVelDelta);
        // Update all projectiles
        float deltaTime = Gdx.graphics.getDeltaTime();
        for (Projectile projectile : projectiles) {
            projectile.update(deltaTime);
        }
    }

    public Projectile shoot(TextureRegion projectileTexture, float projectileSpeed) {
        Vector2 shootDirection = new Vector2(velocity).nor();
        Vector2 projectileVelocity = shootDirection.scl(projectileSpeed);
        Projectile projectile = new Projectile(projectileTexture, new Vector2(position), projectileVelocity);
        projectiles.add(projectile);
        return projectile;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                texture,
                position.x, position.y,
                texture.getRegionWidth() / 2.f, texture.getRegionHeight() / 2.f,
                texture.getRegionWidth(), texture.getRegionHeight(),
                1.0f, 1.0f,
                rotation * MathUtils.radiansToDegrees
        );
    }


    public Rectangle getHitbox() {
        return hitbox;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getRotationRad() {
        return MathUtils.atan2(velocity.y, velocity.x);
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }
}
