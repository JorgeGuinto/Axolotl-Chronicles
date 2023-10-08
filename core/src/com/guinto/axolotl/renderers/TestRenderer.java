package com.guinto.axolotl.renderers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.Building;
import com.guinto.axolotl.characters.Axolotl;
import com.guinto.axolotl.resources.AxolotlComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import lombok.Setter;

public class TestRenderer {

    private AxolotlChronicles game;
    private Stage stage;
    private static ArrayList<Axolotl> poppedCharacters;
    private static ArrayList<Axolotl> shownCharacters;
    public Rectangle door = new Rectangle(0, 0, 50, 1000);
    public float duration = 0;
    @Setter
    private int currentCharacterIndex = 0;
    private float timeSinceLastCharacter = 0;
    private float characterDelay = 5;
    int charactersToShow = 2;
    private Building lBuilding = new Building("bLobbyL");
    private Building cBuilding = new Building("bLobbyC");
    private Building rBuilding = new Building("bLobbyR");
//    public Building mannequin = new Building("mannequin");

    public TestRenderer(AxolotlChronicles game,Stage stage) {
        this.game = game;
        this.stage = stage;
        Assets.loadTest();
        stage.addActor(lBuilding);
        stage.addActor(cBuilding);
        stage.addActor(rBuilding);
        popCharacters();
    }

    public void render(float delta) {
        duration += delta;
        renderBackground();
        game.batch.enableBlending();
        stage.act();
        stage.draw();
//        renderCharacters(delta);

        //        game.batch.enableBlending();
//        game.batch.begin();
//        Assets.titleFont.draw(game.batch, "AXOLOTL CHRONICLES: THE LOST KINGDOM", game.guiCam.position.x - 700, 100 );
//        game.batch.draw(Assets.shellsRegion, 690, 1070, 60,50);
//        game.batch.draw(Assets.gemsRegion, 900, 1070, 40,40);
//        game.batch.draw(Assets.obsidianRegion, 1110, 1070, 50,50);
//        game.batch.end();
    }

    public void staticObjects(){

        Building mannequin = new Building("mannequin");

        mannequin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Closed manneq");
            }
        });

        TextButton closeButton = new TextButton("Close", Assets.skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Closed table");
            }
        });

        closeButton.setPosition(1000, 700);
        closeButton.setSize(300, 100);

        mannequin.setTouchable(Touchable.enabled);

        mannequin.setBounds(400, 300, 400, 400);

        stage.addActor(mannequin);
        stage.addActor(closeButton);

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
            shownCharacters.add(poppedCharacters.get(currentCharacterIndex));
            currentCharacterIndex++;
            timeSinceLastCharacter = 0;
            characterDelay = rand.nextInt(5) + 4;
        }
//        game.batch.enableBlending();
        game.batch.begin();
        Collections.sort(shownCharacters, new AxolotlComparator());
        for (Axolotl axolotl : shownCharacters) {
            axolotl.draw(game.batch, duration, game.guiCam.position.x);
        }
        game.batch.end();
        timeSinceLastCharacter += delta;
    }

    private void renderBuildings() {
        game.batch.enableBlending();
        game.batch.begin();
        switch ((int) game.guiCam.position.x) {
            case 1000:
                cBuilding.setVisible(false);
                rBuilding.setVisible(false);
                lBuilding.setVisible(true);
                break;
            case 3000:
                cBuilding.setVisible(true);
                rBuilding.setVisible(false);
                lBuilding.setVisible(false);
                break;
            case 5000:
                cBuilding.setVisible(false);
                rBuilding.setVisible(true);
                lBuilding.setVisible(false);
                break;
        }
        game.batch.end();
    }

    public void popCharacters() {
        shownCharacters = new ArrayList<>();
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
            axolotl.setPosition(game.guiCam.position.x - 1400, random.nextInt(300));
            axolotl.setDestination(new Vector2(axolotl.getX() + 1, axolotl.getY() + 1));
            axolotl.setWaitTimer(0);
        }
    }
}