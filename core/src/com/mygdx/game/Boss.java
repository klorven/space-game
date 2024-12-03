package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.random.*;

public class Boss extends Sprite {
    public int health = 100;
    private SpriteBatch batch;

    public Boss(Texture texture) {
        super(texture);
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void dispose() {
        batch.dispose();
    }
}
