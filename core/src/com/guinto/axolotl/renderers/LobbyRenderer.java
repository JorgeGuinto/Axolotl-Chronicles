package com.guinto.axolotl.renderers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Building;
import com.guinto.axolotl.characters.Axolotl;
import com.guinto.axolotl.resources.AxolotlComparator;
import com.guinto.axolotl.resources.BackgroundActor;
import com.guinto.axolotl.screens.ForgeScreen;
import com.guinto.axolotl.screens.InvocationScreen;
import com.guinto.axolotl.screens.TestScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import lombok.Setter;

public class LobbyRenderer {

    private final AxolotlChronicles game;
    private Stage stage;
    private static ArrayList<Axolotl> poppedCharacters;
    private static ArrayList<Axolotl> shownCharacters;
    public float duration = 0;
    private BackgroundActor backgroundActor = new BackgroundActor();
    @Setter
    private int currentCharacterIndex = 0;
    private float timeSinceLastCharacter = 0;
    private float characterDelay = 2;
    int charactersToShow = 3;
    private Building lBuilding = new Building("bLobbyL");
    private Building cBuilding = new Building("bLobbyC");
    private Building rBuilding = new Building("bLobbyR");

    public LobbyRenderer(final AxolotlChronicles game, Stage stage) {
        this.game = game;
        this.stage = stage;
        settingStaticElements();
        popCharacters();
    }

    public void render(float delta) {
        duration += delta;
        stage.draw();
        renderCharacters(delta);
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
    private void settingStaticElements() {

        backgroundActor.setBounds(0, 0, 6000, 1125);

        stage.addActor(backgroundActor);
        stage.addActor(lBuilding);
        stage.addActor(cBuilding);
        stage.addActor(rBuilding);

        lBuilding.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InvocationScreen(game));
            }
        });
        rBuilding.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ForgeScreen(game));
            }
        });
        cBuilding.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TestScreen(game, 3000));
            }
        });

        backgroundActor.addListener(new ClickListener() {
            private float startX;
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startX = x;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                float changedX = x - startX;

                System.out.println("X = " + x);
                System.out.println("Start x = " + startX);
                System.out.println("Changed x = " + changedX);

                game.guiCam.position.add(-changedX, 0, 0);
                game.guiCam.update();

                System.out.println("Cam x = " + game.guiCam.position.x);
            }
        });
    }
}