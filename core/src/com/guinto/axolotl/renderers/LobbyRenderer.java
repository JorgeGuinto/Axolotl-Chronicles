package com.guinto.axolotl.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.Building;
import com.guinto.axolotl.characters.Axolotl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import lombok.Setter;

public class LobbyRenderer {

    private AxolotlChronicles game;
    private static ArrayList<Axolotl> poppedCharacters;
    public float duration = 0;
    @Setter
    private int currentCharacterIndex = 0;
    private float timeSinceLastCharacter = 0;
    private float characterDelay = 10;
    int charactersToShow = 3;
    private Building lBuilding = new Building("bLobbyL");
    private Building cBuilding = new Building("bLobbyC");
    private Building rBuilding = new Building("bLobbyR");


    public LobbyRenderer(AxolotlChronicles game) {
        this.game = game;
        popCharacters();
    }

    public void render(float delta, float lobbyPositionX) {
        duration += delta;
        renderBackground(lobbyPositionX);
        renderBuildings((int) lobbyPositionX);
        renderCharacters(delta);
    }

    private void renderBackground(float lobbyPositionX) {
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.lobbyBackgroundRegion, lobbyPositionX, 0);
        game.batch.end();
    }

    private void renderBuildings(int lobbyPositionX) {
        game.batch.enableBlending();
        game.batch.begin();
        switch (lobbyPositionX) {
            case 0:
                lBuilding.draw(game.batch);
                break;
            case -2000:
                cBuilding.draw(game.batch);
                break;
            case -4000:
                rBuilding.draw(game.batch);
                break;
        }
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
                axolotl.draw(game.batch, duration);
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
            if (random.nextFloat() < 0.85) {
                if (random.nextBoolean()) {
                    axolotl.setPosition(-400, random.nextInt(300));
                } else {
                    axolotl.setPosition(2010 , random.nextInt(300));
                }
            } else {
                axolotl.setPosition(random.nextInt(Gdx.graphics.getWidth()), -200);
            }
            axolotl.setDestination(new Vector2(axolotl.getX() + 1, axolotl.getY() + 1));
            axolotl.setWaitTimer(0);
        }
    }

    public boolean buildingTouch(int lobbyPositionX, float x, float y){
        switch (lobbyPositionX) {
            case 0:
                if (lBuilding.getRectangle().contains(x, y)) return true;
                break;
            case -2000:
                if (cBuilding.getRectangle().contains(x, y)) return true;
                break;
            case -4000:
                if (rBuilding.getRectangle().contains(x, y)) return true;
                break;
        }
        return false;
    }

}