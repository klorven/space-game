package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class FireParticle {
    private ParticleEffect flame;
    private ParticleEmitter flameEmitter;

    private Vector2 position;

    private Ship spaceship;

    private float angle;

    public FireParticle(Ship spaceship) {
        position = new Vector2(-30, 0);

        flame = new ParticleEffect();
        flame.load(Gdx.files.internal("flame.p"), Gdx.files.internal("."));
        flameEmitter = flame.findEmitter("Flame");

        this.spaceship = spaceship;
        angle = spaceship.getRotationRad();
    }

    public void update() {
        float difference = (spaceship.getRotationRad() - angle) * MathUtils.radiansToDegrees;
        angle = spaceship.getRotationRad();

        position.rotateDeg(difference);

        ParticleEmitter.ScaledNumericValue oldAngle = flameEmitter.getAngle();
        oldAngle.setHigh(oldAngle.getHighMin() + difference, oldAngle.getHighMax() + difference);
        oldAngle.setLow(oldAngle.getLowMin() + difference);

        flame.setPosition(
                spaceship.getPosition().x + spaceship.getHitbox().width / 2 + position.x,
                spaceship.getPosition().y + spaceship.getHitbox().height / 2 + position.y
        );

    }

    public void draw(SpriteBatch batch) {
        flame.draw(batch, Gdx.graphics.getDeltaTime());
    }

}