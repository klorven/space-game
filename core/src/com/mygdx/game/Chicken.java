package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Chicken extends Sprite {
    public static final int MAX_DASHCOOLDOWN = 5;
    public int health = 3;
    public final Vector2 velocity;
    private boolean isFlying;
    private float speed = 300;
     float dashCooldown = MAX_DASHCOOLDOWN;
     float superJumpCooldown = 5;
     float healCooldown = 30;
    private float dashEndCooldown = 0;
    private float damageCooldown = 1.5f;





    public Chicken(Texture texture) {
         super(texture);
         velocity = new Vector2();
    }


    public void update(float deltaTime) {
        dashCooldown-=deltaTime;
        superJumpCooldown-=deltaTime;
        healCooldown-=deltaTime;
        dashEndCooldown+=deltaTime;
        if (isFlying) {
            if (velocity.y <= 200) {
                velocity.y += 200;
            }
        } else {
            float gravity = -15f;
            velocity.y += gravity;
        }

        setPosition(getX() + velocity.x * deltaTime, getY() + velocity.y * deltaTime);

        velocity.x = 0;

        updateAbilities();

    }

    public void fly(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public float getHealth() {
        return health;
    }


    public void move(float direction) {
        velocity.x = speed * direction;
    }

    public void damaged() {
        if (damageCooldown <= 0) {

        }
    }


    //Abilities

    public void updateAbilities() {
        if (dashEndCooldown >= 1) {
            dashEndCooldown = 0;
            speed = 200;
        }

    }
    //Movement abilities
    public void dash() {if (dashCooldown <= 0)
    {
        dashEndCooldown = 0;
        speed = 600;
        dashCooldown = MAX_DASHCOOLDOWN;
    }
    }


    public void superJump() {if (superJumpCooldown <= 0)
    {

        velocity.y = 700;
        superJumpCooldown = 5;
    }
    }

    public void heal() {if (healCooldown <= 0)
    {
        health += 1;
        healCooldown = 20;
    }
    }

    //offensive abilities
}