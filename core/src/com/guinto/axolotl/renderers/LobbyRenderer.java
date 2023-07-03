package com.guinto.axolotl.renderers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guinto.axolotl.AxolotlChronicles;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.characters.Axolotl;

import java.util.ArrayList;
import java.util.Collections;

public class LobbyRenderer {

    private AxolotlChronicles game;
    private static ArrayList<Axolotl> poppedCharacters;
    public static ArrayList<TextureRegion> regions = new ArrayList<>();
    public static ArrayList<Animation> animations = new ArrayList<>();


    public LobbyRenderer(AxolotlChronicles game) {
        this.game = game;
        loadCharacters();
    }
    public void loadCharacters() {
        popCharacters();

        for (Axolotl axolotl : poppedCharacters) {
//            regions.add(Assets.atlas.findRegion(axolotl.CODE));
        }
    }

    public void render() {
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

        game.batch.end();
    }

    private void popCharacters() {
        if (game.user.unlockedCharacters != null && game.user.unlockedCharacters.size() > 0) {
            poppedCharacters = new ArrayList<>(game.user.unlockedCharacters);
            Collections.shuffle(poppedCharacters);

            int characterToShow = Math.min(5, game.user.unlockedCharacters.size());

            poppedCharacters = new ArrayList<>(poppedCharacters.subList(0, characterToShow));
        }
    }
}
