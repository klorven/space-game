package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class SoundsManager {
    private Sound bonkSound;
    private Sound chickenDeathSound;
    private Sound buttonClick;
    private Sound switchSound;
    private Sound boomSound;

    public SoundsManager() {
        try {
            bonkSound = Gdx.audio.newSound(Gdx.files.internal("Bonk.mp3"));
            chickenDeathSound = Gdx.audio.newSound(Gdx.files.internal("chicken_death.mp3"));
            buttonClick = Gdx.audio.newSound(Gdx.files.internal("click-b.mp3"));
            boomSound = Gdx.audio.newSound(Gdx.files.internal("Boom.mp3"));

        } catch (GdxRuntimeException e) {
            e.printStackTrace();
        }
    }

    public void bonkSound() {
        bonkSound.play();
    }

    public void chickenDeathSound() {
        chickenDeathSound.play();
    }

    public void buttonClick() {buttonClick.play();}

    public void bombSound() {boomSound.play();}

    public void dispose() {
        bonkSound.dispose();
        chickenDeathSound.dispose();
        buttonClick.dispose();
        switchSound.dispose();
        boomSound.dispose();
    }
}
