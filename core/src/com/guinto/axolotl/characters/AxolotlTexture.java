package com.guinto.axolotl.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.JsonObject;
import com.guinto.axolotl.assets.Assets;
import com.guinto.axolotl.assets.CharacterLoader;

public class AxolotlTexture {
    private final String CODE;
    private TextureRegion walking = null;
    private TextureRegion running = null;
    private TextureRegion idle = null;
    private TextureRegion attack = null;
    private TextureRegion ability = null;
    private TextureRegion hit = null;
    private TextureRegion dying = null;

    private Animation walkingAnimation = null;
    private Animation runningAnimation = null;
    private Animation idleAnimation = null;
    private Animation attackAnimation = null;
    private Animation abilityAnimation = null;
    private Animation hitAnimation = null;
    private Animation dyingAnimation = null;

    public AxolotlTexture(String code) {
        this.CODE = code;

        loadTextures();
        loadAnimations();
    }

    private void loadTextures() {
        walking = Assets.atlas.findRegion(CODE + "Walking");
        running = Assets.atlas.findRegion(CODE + "Running");
        idle = Assets.atlas.findRegion(CODE + "Idle");
    }

    private void loadAnimations() {
        walkingAnimation = getCharacterAnimation(walking, "W");
        runningAnimation = getCharacterAnimation(running, "R");
        idleAnimation = getCharacterAnimation(idle, "I");
    }

    private Animation<TextureRegion> getCharacterAnimation (TextureRegion textureRegion, String state) {
        JsonObject characterFound = CharacterLoader.findCharacter(CODE);

        TextureRegion [][] temp = textureRegion.split(textureRegion.getRegionWidth() / characterFound.get("frames" + state + "H").getAsInt(), textureRegion.getRegionHeight()/ characterFound.get("frames" + state + "V").getAsInt());
        TextureRegion[] frames = new TextureRegion[temp.length * temp[0].length];
        int index = 0;
        for (TextureRegion[] textureRegions : temp) {
            for (TextureRegion textureRegion1 : textureRegions) {
                frames[index++] = textureRegion1;
            }
        }

        return new Animation<>(0.1f, frames);
    }

    public TextureRegion getKeyFrame(float duration, boolean looping, int state) {
        switch (state) {
            case 0:
                return (TextureRegion) idleAnimation.getKeyFrame(duration, looping);
            case 1:
                return (TextureRegion) walkingAnimation.getKeyFrame(duration, looping);
            case 2:
                return (TextureRegion) runningAnimation.getKeyFrame(duration, looping);

        }
        return null;
    }
}
