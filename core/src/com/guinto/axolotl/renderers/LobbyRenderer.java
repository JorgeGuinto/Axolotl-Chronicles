package com.guinto.axolotl.renderers;

import com.badlogic.gdx.math.Vector2;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.Building;
import com.guinto.axolotl.characters.Axolotl;
import com.guinto.axolotl.resources.AxolotlComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import lombok.Setter;

public class LobbyRenderer {

    private AxolotlChronicles game;
    private static ArrayList<Axolotl> poppedCharacters;
    private static ArrayList<Axolotl> shownCharacters;
    public float duration = 0;
    @Setter
    private int currentCharacterIndex = 0;
    private float timeSinceLastCharacter = 0;
    private float characterDelay = 2;
    int charactersToShow = 3;
    private Building lBuilding = new Building("bLobbyL");
    private Building cBuilding = new Building("bLobbyC");
    private Building rBuilding = new Building("bLobbyR");


    public LobbyRenderer(AxolotlChronicles game) {
        this.game = game;
        popCharacters();
    }

    public void render(float delta) {
        duration += delta;
        renderBackground();
        renderBuildings();
        renderCharacters(delta);
    }

    private void renderBackground() {
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.backgroundRegion, 0, 0);
        game.batch.end();
    }

    private void renderBuildings() {
        game.batch.enableBlending();
        game.batch.begin();
        switch ((int) game.guiCam.position.x) {
            case 1000:
                lBuilding.draw(game.batch);
                break;
            case 3000:
                cBuilding.draw(game.batch);
                break;
            case 5000:
                rBuilding.draw(game.batch);
                break;
        }
        game.batch.end();
    }

    private void renderCharacters(float delta) {
        Random rand = new Random();
        if (timeSinceLastCharacter > characterDelay && currentCharacterIndex < charactersToShow){
            shownCharacters.add(poppedCharacters.get(currentCharacterIndex));
            currentCharacterIndex++;
            timeSinceLastCharacter = 0;
            characterDelay = rand.nextInt(5) + 4;
        }
        game.batch.enableBlending();
        game.batch.begin();
        Collections.sort(shownCharacters, new AxolotlComparator());
        for (Axolotl axolotl : shownCharacters) {
            axolotl.draw(game.batch, duration, game.guiCam.position.x);
        }
        game.batch.end();
        timeSinceLastCharacter += delta;
    }

    public void popCharacters() {
        if (game.user.unlockedCharacters != null && game.user.unlockedCharacters.size() > 0) {
            shownCharacters = new ArrayList<>();
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
                    axolotl.setPosition(game.guiCam.position.x - 1400, random.nextInt(300));
                } else {
                    axolotl.setPosition(game.guiCam.position.x + 1010 , random.nextInt(300));
                }
            } else {
                axolotl.setPosition((game.guiCam.position.x - 1000) + random.nextInt(2000), -200);
            }
            axolotl.setDestination(new Vector2(axolotl.getX() + 1, axolotl.getY() + 1));
            axolotl.setWaitTimer(0);
        }
    }

    public boolean buildingTouch(float x, float y){
        switch ((int) game.guiCam.position.x) {
            case 1000:
                if (lBuilding.getPolygon().contains(x, y)) return true;
                break;
            case 3000:
                if (cBuilding.getPolygon().contains(x, y)) return true;
                break;
            case 5000:
                if (rBuilding.getPolygon().contains(x, y)) return true;
                break;
        }
        return false;
    }

}