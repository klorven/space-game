package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Knife {
    private float x, y;
    private final float speed;
    private final Texture knifeTexture;
    private final Rectangle knifeHitbox;

    public Knife(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = 500;
        this.knifeTexture = new Texture("knife.png");
        this.knifeHitbox = new Rectangle(x, y, knifeTexture.getWidth(), knifeTexture.getHeight());
    }

    public void update(float deltaTime) {
        x += speed * deltaTime;
        knifeHitbox.setPosition(x,y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(knifeTexture, x, y);
    }

    public Rectangle getHitbox() {
        return knifeHitbox;
    }

    public void dispose() {
        knifeTexture.dispose();
    }
}