package com.mygdx.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Sound;
import sun.jvm.hotspot.utilities.PointerLocation;

import java.awt.*;
import java.awt.event.InputEvent;

public class ChickenMenu implements Screen{

    MyGame game;
    private SoundsManager soundsManager;
    private boolean sound = true;
    private boolean music = true;
    private Texture background;
    private Texture soundButton;
    private Texture soundButtonHover;
    private Texture soundButtonClicked;
    private Texture noSoundButton;
    private Texture musicButton;
    private Texture musicButtonClicked;
    private Texture musicButtonHover;
    private Texture noMusicButton;
    private Texture playButton;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public ChickenMenu(MyGame game) {this.game = game;}
    public void show() {
        background = new Texture("pixel_sky.png");
        playButton = new Texture("arrow_basic_e.png");
        soundButton = new Texture("soundButton.png");
        soundButtonHover = new Texture("soundButtonHover.png");
        soundButtonClicked = new Texture("soundButtonClicked.png");
        noSoundButton = new Texture("noSoundButton.png");
        musicButton = new Texture("musicButton.png");
        musicButtonHover = new Texture("musicButtonHover.png");
        musicButtonClicked = new Texture("musicButtonClicked.png");
        noMusicButton = new Texture("noMusicButton.png");
        soundsManager = new SoundsManager();

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2560, 1440);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background,-200,0);
        if (music) {
            batch.draw(musicButton,50,1190);
        } else {
            batch.draw(noMusicButton,50,1190);
        }

        if (sound) {
            batch.draw(soundButton,2310,1190);
        } else {
            batch.draw(noSoundButton,2310,1190);
        }
        batch.draw(playButton, 1024, 464);
        batch.end();

        handleInput();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.F11)) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (Gdx.input.getX() >= 50 && Gdx.input.getX() <= 250 && Gdx.input.getY() >= 50 && Gdx.input.getY() <= 250) {
                soundsManager.buttonClick();
                batch.begin();
                batch.draw(musicButtonClicked,50,1190);
                batch.end();
                music = !music;
            }

            if (Gdx.input.getX() >= 2310 && Gdx.input.getX() <= 2510 && Gdx.input.getY() >= 50 && Gdx.input.getY() <= 250) {
                soundsManager.buttonClick();
                batch.begin();
                batch.draw(soundButtonClicked,2310,1190);
                batch.end();
                sound = !sound;
            }

            if (Gdx.input.getX() >= 1024 && Gdx.input.getX() <= 1536 && Gdx.input.getY() >= 464 && Gdx.input.getY() <= 976) {
                soundsManager.buttonClick();
                game.setScreen(game.chickenScreen);
            }
        }
    }
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,2560,1440);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        musicButton.dispose();
        musicButtonHover.dispose();
        musicButtonClicked.dispose();
        soundButton.dispose();
        soundButtonHover.dispose();
        soundButtonClicked.dispose();
    }
}
