package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.management.StringValueExp;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

public class ChickenScreen implements Screen {
    MyGame game;
    float timeSinceLastMissile;
    float timeSinceLastFireBall;
    float timeSinceLastKnife;
    private Chicken chicken;
    private Boss boss;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle chickenHitbox;
    private Rectangle bossHitbox;
    private Array<Knife> knives;
    private Array<fireBall> fireBalls;
    private Array<Missile> missiles;
    private BitmapFont font;
    private SoundsManager soundsManager;
    private Texture background;
    private Texture chickenHealthTex;
    private Texture dashTex;
    private Texture dashUsedTex;
    private Texture superJumpTex;
    private Texture superJumpUsedTex;
    private Texture healUsedTex;
    private Texture healTex;
    private ShapeRenderer shapeRenderer;
    private ShaderProgram shaderProgram;


    public ChickenScreen(MyGame game) {this.game = game;}
    @Override
    public void show() {
        Texture chickenTexture = new Texture("Chicken.png");
        chicken = new Chicken(chickenTexture);
        chicken.setPosition(100, 100);
        background = new Texture("pixel_sky.png");
        chickenHealthTex = new Texture("chickenHealth.png");
        Texture bossTexture = new Texture("Boss.png");
        boss = new Boss(bossTexture);
        boss.setPosition(1900, 100);
        batch = new SpriteBatch();
        dashTex = new Texture("Dash.png");
        dashUsedTex = new Texture("DashUsed.png");
        superJumpTex = new Texture("SuperJump.png");
        superJumpUsedTex = new Texture("SuperJumpUsed.png");
        healTex = new Texture("heal.png");
        healUsedTex = new Texture("healUsed.png");
        chickenHitbox = new Rectangle(chicken.getX(), chicken.getY(), chicken.getWidth(), chicken.getHeight());
        bossHitbox = new Rectangle(boss.getX(), boss.getY(), bossTexture.getWidth(), bossTexture.getHeight());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2560, 1440);
        font = new BitmapFont();
        font = new BitmapFont();
        font.getData().setScale(5);
        batch = new SpriteBatch();
        knives = new Array<>();
        missiles = new Array<>();
        fireBalls = new Array<>();
        soundsManager = new SoundsManager();
        shapeRenderer = new ShapeRenderer();
        shaderProgram = new ShaderProgram(Gdx.files.internal("Shaders/CoolDownShader.vert.glsl"),Gdx.files.internal("Shaders/CoolDownShader.frag.glsl"));
    }

    @Override
    public void render(float delta) {
        timeSinceLastKnife += delta;
        timeSinceLastFireBall +=delta;
        timeSinceLastMissile +=delta;
        chicken.dashCooldown -= delta;
        int superJumpCooldownInt = (int)chicken.superJumpCooldown;
        int healCooldownInt = (int) chicken.healCooldown;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        handleInput();
        chicken.update(delta);

        // Update hitboxes
        chickenHitbox.setPosition(chicken.getX(), chicken.getY());

        abilities();
        updateKnives();
        updateFireBalls();
        updateMissiles();
        shootFireBall();
        shootMissile();
        checkCollisions();

        batch.begin();
        batch.draw(background, -200,0);
        chicken.draw(batch);
        boss.draw(batch);
        batch.setShader(shaderProgram);
        shaderProgram.setUniformf("time", chicken.dashCooldown);
        if(chicken.dashCooldown < 0) {
            batch.draw(dashTex,50,100);
        } else {
            batch.draw(dashUsedTex,50,100);
        }
        batch.setShader(null);
        if (chicken.superJumpCooldown < 0) {
            batch.draw(superJumpTex,175,100);
        } else {
            batch.draw(superJumpUsedTex,175,100);
            font.draw(batch,Integer.toString(superJumpCooldownInt), 200,175);
        }

        if (chicken.healCooldown < 0) {
            batch.draw(healTex,300,100);
        } else {
            batch.draw(healUsedTex,300,100);
            font.draw(batch,Integer.toString(healCooldownInt),325,175);
        }

        for (int i = 0; i < chicken.health; i++)
        {
            batch.draw(chickenHealthTex, 20 + i * 60, 20);
        }

        for (int i = knives.size - 1; i >= 0; i--) {
            Knife knife = knives.get(i);
            Rectangle knifeHitbox = knife.getHitbox();
            knife.render(batch);
            if (knifeHitbox.overlaps(bossHitbox)) {
                boss.health -= 10;
                knives.removeIndex(i);


            }
        }
        for (int i = fireBalls.size - 1; i >= 0; i--) {
            fireBall fireBall = fireBalls.get(i);
            Rectangle fireBallHitbox = fireBall.getHitbox();
            fireBall.render(batch);
            if (fireBallHitbox.overlaps(chickenHitbox)) {
                soundsManager.bonkSound();
                chicken.health -= 1;
                fireBalls.removeIndex(i);
            }



        }
        for (int i = missiles.size - 1; i >= 0; i--)    {
            Missile missile = missiles.get(i);
            Rectangle missileHitbox = missile.getHitbox();
            missile.render(batch);
            if (missileHitbox.overlaps(chickenHitbox)) {
                soundsManager.bombSound();
                chicken.health -= 1;
                missiles.removeIndex(i);
            }
        }


        batch.end();
        if (chicken.getHealth() <= 0) {
          //  soundsManager.chickenDeathSound();
            //game.setScreen(game.chickenMenu);
        }
     /*   Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.5f);
        if (chicken.dashCooldown > 0) {
            shapeRenderer.rect(50,100,100*(chicken.dashCooldown/Chicken.MAX_DASHCOOLDOWN),100);
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);*/

    }

    private void handleInput() {
        chicken.fly(Gdx.input.isKeyPressed(Input.Keys.SPACE));
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_7) || Gdx.input.isKeyPressed(Input.Keys.HOME)) {
            float knifeCooldown = 0.5f;
            if (timeSinceLastKnife >= knifeCooldown) {
                shootKnife();
                timeSinceLastKnife = 0;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            chicken.move(1);
            if (!chicken.isFlipX()) {
                chicken.flip(true, false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            chicken.move(-1);
            if (chicken.isFlipX()) {
                chicken.flip(true, false);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F11)) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            chicken.dash();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            chicken.superJump();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            chicken.heal();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            for (int i = 0; i < 10; i++) {
                    timeSinceLastKnife += 0.01F;
            }
        }

    }

    public void abilities() {


    }

    public void shootKnife() {
        knives.add(new Knife(chicken.getX() + 115, chicken.getY() + 105));
    }

    public void updateKnives() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        for (int i = knives.size - 1; i >= 0; i--) {
            Knife knife = knives.get(i);
            knife.update(deltaTime);
            }
        }

    public void shootFireBall() {
        if (timeSinceLastFireBall >= 1.5) {
            fireBalls.add(new fireBall(1900, MathUtils.random(100, 1000)));
            timeSinceLastFireBall = 0;
        }
    }

    public void shootMissile() {
        if (timeSinceLastMissile>= MathUtils.random(5, 10)) {
            missiles.add(new Missile(1900, MathUtils.random(100,1000)));
            timeSinceLastMissile = 0;
        }
    }

    public void updateFireBalls() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        for (int i = fireBalls.size - 1; i >= 0; i--) {
            fireBall fireBall = fireBalls.get(i);
            fireBall.update(deltaTime);
        }
     }

    public void updateMissiles() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        for (int i = missiles.size - 1; i >= 0; i--) {
            Missile missile = missiles.get(i);
            missile.update(deltaTime); // Call the instance method
        }
    }




    private void checkCollisions() {
        if (chickenHitbox.overlaps(bossHitbox)) {
            chicken.health = 0;
        }

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        boss.getTexture().dispose();
        if (chicken.getTexture() != null) {
            chicken.getTexture().dispose();
        }
        for (int i = knives.size - 1; i >= 0; i--) {
            knives.get(i).dispose();
        }
        knives.clear();
        font.dispose();
        soundsManager.dispose();
    }

}
