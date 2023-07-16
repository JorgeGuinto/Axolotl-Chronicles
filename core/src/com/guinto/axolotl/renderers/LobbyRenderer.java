package com.guinto.axolotl.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.characters.Axolotl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

public class LobbyRenderer {

    private AxolotlChronicles game;
    private static ArrayList<Axolotl> poppedCharacters;
    public static ArrayList<TextureRegion> regions = new ArrayList<>();
    public static ArrayList<Animation> animations = new ArrayList<>();
    public float duration = 0;
    @Setter
    private int currentCharacterIndex = 0;
    private float timeSinceLastCharacter = 0;
    private float characterDelay;
    int charactersToShow = 5;


    public LobbyRenderer(AxolotlChronicles game) {
        this.game = game;
        this.characterDelay = 5;
        loadCharacters();
    }
    public void loadCharacters() {
        popCharacters();
        for (Axolotl axolotl : poppedCharacters) {
            animations.add(axolotl.getCharacterAnimation());
        }
    }

    public void render(float delta, float lobbyPositionX) {
        duration += delta;
        renderBackground(lobbyPositionX);
        renderCharacters(delta);
    }

    private void renderBackground(float lobbyPositionX) {
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.lobbyBackgroundRegion, lobbyPositionX, 0);
        game.batch.end();
    }

    private void renderCharacters(float delta) {
        Random rand = new Random();
        if (timeSinceLastCharacter > characterDelay && currentCharacterIndex < charactersToShow){
            currentCharacterIndex++;
            timeSinceLastCharacter = 0;
            characterDelay = rand.nextInt(3, 5);
        }
        game.batch.enableBlending();
        game.batch.begin();
        int i = 0;
        for (Animation animation : animations) {
            if (i < currentCharacterIndex) {
//                TextureRegion temp = (TextureRegion) animation.getKeyFrame(duration, true);
                Axolotl tempAxolotl = poppedCharacters.get(i);
                TextureRegion temp = tempAxolotl.getKeyFrame(duration, true);
                if (tempAxolotl.getDestination().x < tempAxolotl.getPosition().x) {
                    game.batch.draw(temp, tempAxolotl.getX() + 200, tempAxolotl.getY(),-200,200);
                } else {
                    game.batch.draw(temp, tempAxolotl.getX(), tempAxolotl.getY(), 200, 200);
                }
                tempAxolotl.update(Axolotl.STATE_WALK);
            }
            i ++;
        }
        game.batch.end();

        timeSinceLastCharacter += delta;
    }

    private void popCharacters() {
        if (game.user.unlockedCharacters != null && game.user.unlockedCharacters.size() > 0) {
            poppedCharacters = new ArrayList<>(game.user.unlockedCharacters);
            Collections.shuffle(poppedCharacters);

            charactersToShow = Math.min(charactersToShow, game.user.unlockedCharacters.size());

            poppedCharacters = new ArrayList<>(poppedCharacters.subList(0, charactersToShow));
            setOriginalPositions();
        }
    }

    public void setOriginalPositions() {
        Random random = new Random();
        for (Axolotl axolotl : poppedCharacters) {
            if (random.nextFloat() < 0.85) {
                if (random.nextBoolean()) {
                    axolotl.setPosition(-200, random.nextInt(700));
                } else {
                    axolotl.setPosition(2000 , random.nextInt(700));
                }
            } else {
                axolotl.setPosition(random.nextInt(Gdx.graphics.getWidth()), -200);
            }
            axolotl.setDestination(new Vector2(axolotl.getPosition().x + 1, axolotl.getPosition().y + 1));
        }
    }

}
