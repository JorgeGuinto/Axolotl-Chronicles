package com.guinto.axolotl.renderers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.characters.Axolotl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import lombok.Setter;

public class InvocationRenderer {

    private AxolotlChronicles game;
    private static ArrayList<Axolotl> poppedCharacters;
    public Rectangle door = new Rectangle(1800, 0, 200, 1200);
    public float duration = 0;
    @Setter
    private int currentCharacterIndex = 0;
    private float timeSinceLastCharacter = 0;
    private float characterDelay = 5;
    int charactersToShow = 2;

    public InvocationRenderer(AxolotlChronicles game) {
        this.game = game;
        Assets.loadInvocation();
        game.guiCam.position.set(1000, game.guiCam.position.y, 0);
        popCharacters();
    }

    public void render(float delta) {
        duration += delta;
        renderBackground();
        renderCharacters(delta);
    }

    private void renderBackground() {
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.backgroundRegion, 0, 0);
        game.batch.end();
    }

    private void renderCharacters(float delta) {
        Random rand = new Random();
        if (timeSinceLastCharacter > characterDelay && currentCharacterIndex < charactersToShow){
            currentCharacterIndex++;
            timeSinceLastCharacter = 0;
            characterDelay = rand.nextInt(5) + 4;
        }
        game.batch.enableBlending();
        game.batch.begin();
        int i = 0;
        for (Axolotl axolotl : poppedCharacters) {
            if (i < currentCharacterIndex) {
                axolotl.draw(game.batch, duration, game.guiCam.position.x);
            }
            i ++;
        }
        game.batch.end();
        timeSinceLastCharacter += delta;
    }

    public void popCharacters() {
        if (game.user.unlockedCharacters != null && game.user.unlockedCharacters.size() > 0) {
            poppedCharacters = new ArrayList<>(game.user.unlockedCharacters);
            Collections.shuffle(poppedCharacters);
            charactersToShow = Math.min(charactersToShow, game.user.unlockedCharacters.size());
            poppedCharacters = new ArrayList<>(poppedCharacters.subList(0, charactersToShow));
            setOriginalPositions();
        }
    }

    public void setOriginalPositions() {
        currentCharacterIndex = 0;
        Random random = new Random();
        for (Axolotl axolotl : poppedCharacters) {
            axolotl.setPosition(game.guiCam.position.x + 1010 , random.nextInt(300));
            axolotl.setDestination(new Vector2(axolotl.getX() + 1, axolotl.getY() + 1));
            axolotl.setWaitTimer(0);
        }
    }
}