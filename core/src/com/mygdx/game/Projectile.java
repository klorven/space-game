package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle hitbox;

    public Projectile(TextureRegion texture, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.position = new Vector2(position);
        this.velocity = new Vector2(velocity);
        this.hitbox = new Rectangle(position.x, position.y, texture.getRegionWidth(), texture.getRegionHeight());
    }

    public void update(float deltaTime) {
        position.mulAdd(velocity, deltaTime);
        hitbox.setPosition(position.x, position.y);
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
}
