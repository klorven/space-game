package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Missile {
    private float x, y;
    private float speed;
    private final Texture missileTexture;
    private final Rectangle missileHitbox;

    public Missile(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = -1.1F;
        this.missileTexture = new Texture("Missile2.GIF");
        this.missileHitbox = new Rectangle(x, y, missileTexture.getWidth(), missileTexture.getHeight());
    }

    public void update(float deltaTime) {
        speed *= 1.1f;
        x += speed * deltaTime;
        missileHitbox.setPosition(x,y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(missileTexture, x, y);
    }

    public Rectangle getHitbox() {
        return missileHitbox;
    }

    public void dispose() {
        missileTexture.dispose();
    }
}