package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Meteor {
    private TextureRegion texture;
    private Rectangle hitbox;
    private Vector2 position;
    private boolean destroyed;

    public Meteor(TextureRegion texture, float x, float y) {
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.hitbox = new Rectangle(x, y, texture.getRegionWidth(), texture.getRegionHeight());
        this.destroyed = false;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }
}
