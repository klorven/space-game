package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class fireBall {
    private float x, y;
    private final float speed;
    private final Texture fireBallTexture;
    private final Rectangle fireBallHitbox;
    public fireBall(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = -1000;
        this.fireBallTexture = new Texture("hammer.png");
        this.fireBallHitbox = new Rectangle(x, y, fireBallTexture.getWidth(), fireBallTexture.getHeight());
    }

    public void update(float deltaTime) {
        x += speed * deltaTime;
        fireBallHitbox.setPosition(x,y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(fireBallTexture, x, y);
    }

    public Rectangle getHitbox() {
        return fireBallHitbox;
    }

    public void dispose() {
        fireBallTexture.dispose();
    }
}