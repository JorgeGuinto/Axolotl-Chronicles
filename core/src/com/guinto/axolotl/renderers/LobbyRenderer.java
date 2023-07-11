package com.guinto.axolotl.renderers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.characters.Axolotl;

import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Collections;

public class LobbyRenderer {

    private AxolotlChronicles game;
    private static ArrayList<Axolotl> poppedCharacters;
    public static ArrayList<TextureRegion> regions = new ArrayList<>();
    public static ArrayList<Animation> animations = new ArrayList<>();
    public float duration = 0;


    public LobbyRenderer(AxolotlChronicles game) {
        this.game = game;
        loadCharacters();
    }
    public void loadCharacters() {
        popCharacters();
        for (Axolotl axolotl : poppedCharacters) {
            animations.add(axolotl.getCharacterAnimation());
        }
    }

    public void render(float delta) {
        duration += delta;
        renderBackground();
        renderCharacters();
    }

    private void renderBackground() {
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.lobbyBackgroundRegion, 0, 0);
        game.batch.end();
    }

    private void renderCharacters() {
        game.batch.enableBlending();
        game.batch.begin();
        int i = 0;
        for (Animation animation : animations) {
            TextureRegion temp = (TextureRegion) animation.getKeyFrame(duration, true);
            Axolotl tempAxolotl = poppedCharacters.get(i);
            game.batch.draw(temp, tempAxolotl.getX(), tempAxolotl.getY());
            i ++;
        }
        game.batch.end();
    }

    private void popCharacters() {
        if (game.user.unlockedCharacters != null && game.user.unlockedCharacters.size() > 0) {
            poppedCharacters = new ArrayList<>(game.user.unlockedCharacters);
            Collections.shuffle(poppedCharacters);

            int characterToShow = Math.min(7, game.user.unlockedCharacters.size());

            poppedCharacters = new ArrayList<>(poppedCharacters.subList(0, characterToShow));
        }
    }
}
